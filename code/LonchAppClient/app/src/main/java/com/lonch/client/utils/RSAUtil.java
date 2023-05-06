package com.lonch.client.utils;

import android.content.Context;
import android.util.Log;

import com.blankj.utilcode.util.FileIOUtils;
import com.lonch.client.LonchCloudApplication;

import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.encodings.PKCS1Encoding;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.crypto.util.PrivateKeyInfoFactory;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.bouncycastle.crypto.util.SubjectPublicKeyInfoFactory;
import org.bouncycastle.util.encoders.Base64;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.SecureRandom;


public class RSAUtil {

    public static void test(Context mContext) throws Exception {
        RSAKeyPairGenerator rsaKeyPairGenerator = new RSAKeyPairGenerator();
        RSAKeyGenerationParameters rsaKeyGenerationParameters = new RSAKeyGenerationParameters(BigInteger.valueOf(3),
                new SecureRandom(), 1024, 25);
        //初始化参数
        rsaKeyPairGenerator.init(rsaKeyGenerationParameters);
        AsymmetricCipherKeyPair keyPair = rsaKeyPairGenerator.generateKeyPair();
        //公钥
        AsymmetricKeyParameter publicKey = keyPair.getPublic();
        //私钥
        AsymmetricKeyParameter privateKey = keyPair.getPrivate();

        SubjectPublicKeyInfo subjectPublicKeyInfo = SubjectPublicKeyInfoFactory.createSubjectPublicKeyInfo(publicKey);
        PrivateKeyInfo privateKeyInfo = PrivateKeyInfoFactory.createPrivateKeyInfo(privateKey);
        //变字符串
        ASN1Object asn1ObjectPublic = subjectPublicKeyInfo.toASN1Primitive();
        byte[] publicInfoByte = asn1ObjectPublic.getEncoded();
        ASN1Object asn1ObjectPrivate = privateKeyInfo.toASN1Primitive();
        byte[] privateInfoByte = asn1ObjectPrivate.getEncoded();
        //这里可以将密钥对保存到本地
        //加密、解密
        ASN1Object pubKeyObj = subjectPublicKeyInfo.toASN1Primitive();//这里也可以从流中读取，从本地导入
        AsymmetricKeyParameter pubKey = PublicKeyFactory.createKey(SubjectPublicKeyInfo.getInstance(pubKeyObj));
        AsymmetricBlockCipher cipher = new RSAEngine();
        cipher.init(true, pubKey);//true表示加密

        //加密
        String data = "create table app_im_message\n" +
                "(\n" +
                "   id                   varchar(50) not null comment '编号',\n" +
                "   send_type            varchar(50) comment '发送类型(1:单发单聊消息；2:批量发单聊消息；3:全员推送消息)',\n" +
                "   to_account           text comment '消息接收人',\n" +
                "   message_type         varchar(50) comment '消息类型',\n" +
                "   text                 varchar(500) comment '发送内容',\n" +
                "   des_content          varchar(500) comment '离线推送内容',\n" +
                "   ext_content          text comment '透传内容',\n" +
                "   send_time            datetime comment '发送时间',\n" +
                "   status               tinyint comment '接口调用是否成功',\n" +
                "   request_text         text comment '接口请求参数',\n" +
                "   response_text        text comment '接口返回内容',\n" +
                "   is_del               tinyint not null default 0 comment '是否已删除',\n" +
                "   create_time          datetime default CURRENT_TIMESTAMP comment '创建时间',\n" +
                "   update_time          datetime default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',\n" +
                "   modified_userId      varchar(50) comment '最后修改人',\n" +
                "   primary key (id)\n" +
                ");";
        System.out.println("\n明文：" + data);
//        byte[] encryptData = cipher.processBlock(data.getBytes("utf-8")
//                , 0, data.getBytes("utf-8").length);
        byte[] encryptData = encryptAndDecrypt(data.getBytes("utf-8"), cipher, 117);

        System.out.println("密文:" + Base64.toBase64String(encryptData));

        //解密
        AsymmetricKeyParameter priKey = PrivateKeyFactory.createKey(privateInfoByte);
        cipher.init(false, priKey);//false表示解密
        byte[] decriyptData = encryptAndDecrypt(encryptData, cipher, 117);
        String decryptData = new String(decriyptData, "utf-8");
        System.out.println("解密后密文：" + decryptData);
    }

    public static void encryptUserData(String userLog, String filePath) throws Exception {
        if (!new File(userLog).exists()) {
            return;
        }
        AsymmetricKeyParameter pubKey = PublicKeyFactory.createKey(Base64.decode(LonchCloudApplication.getAppConfigDataBean().RSA_PUBLIC_KEY));
        AsymmetricBlockCipher cipher = new PKCS1Encoding(new RSAEngine());
        cipher.init(true, pubKey);//true表示加密
        byte[] encryptData = encryptAndDecrypt(FileIOUtils.readFile2BytesByChannel(userLog), cipher, 117);
        String aesKey = LonchCloudApplication.getAppConfigDataBean().AES_KEY;
        byte[] encryptedBytes = encrypt(encryptData, aesKey.getBytes(), INIT_VECTOR);
        FileIOUtils.writeFileFromBytesByStream(filePath, encryptedBytes);
    }

    public static void test2() throws Exception {
        long start = System.currentTimeMillis();
        Log.i("rsa-", start + "");
        String publicKey1 = "MIGdMA0GCSqGSIb3DQEBAQUAA4GLADCBhwKBgQCJxAAmQdZYV76NoH4BGXMoZ2flIE1bu1wtQQRlQQqqTwUjCMrgcYzqobD/rcNroyo/hrMigC2q3YgP9OByAVteO4qh2U/x7Yb30RvrYgbjC3uMTWbZaHN+IqZUEFjFkJx+QiDfvmFzf+uUW4Bx3BPVeitsucJU83sV4cNy0hWKmQIBAw==";
        AsymmetricKeyParameter pubKey = PublicKeyFactory.createKey(Base64.decode(publicKey1));
        AsymmetricBlockCipher cipher = new PKCS1Encoding(new RSAEngine());

        cipher.init(true, pubKey);//true表示加密

//        InputStream is = new FileInputStream(LonchCloudApplication.getApplicationsContext().getFilesDir().getAbsolutePath()+"/b.txt");
//        StringBuffer sb;
//        BufferedReader br = null;
//        try {
//            br = new BufferedReader(new InputStreamReader(is));
//
//            sb = new StringBuffer();
//
//            String data1;
//            while ((data1 = br.readLine()) != null) {
//                sb.append(data1);
//            }
//        } finally {
//            br.close();
//        }
        String data = "aaa";

        byte[] encryptData = encryptAndDecrypt(data.getBytes("utf-8"), cipher, 117);


        Log.i("rsa--", new String(encryptData, "utf-8"));


        // FileIOUtils.writeFileFromBytesByStream(LonchCloudApplication.getApplicationsContext().getFilesDir().getAbsolutePath()+"/ccc.txt",encryptData);
        //   byte[] encry = FileIOUtils.readFile2BytesByStream(LonchCloudApplication.getApplicationsContext().getFilesDir().getAbsolutePath()+"/cc.txt");
        //解密
        String privatecKey1 = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAInEACZB1lhXvo2gfgEZcyhnZ+UgTVu7XC1BBGVBCqpPBSMIyuBxjOqhsP+tw2ujKj+GsyKALardiA/04HIBW147iqHZT/HthvfRG+tiBuMLe4xNZtloc34iplQQWMWQnH5CIN++YXN/65RbgHHcE9V6K2y5wlTzexXhw3LSFYqZAgEDAoGAFvYABmBOZA6fwkVqVYQ93BE7+4ViOfSPXOArZjWBxw0rhdbMer2XfHBIKpz150XcX+vIhcAHnHpBV/4laFWPOh2vY8oDqJBwcPCIjDro6RWZk4iCkTDn+pFxHGinXsewr4+7e7QgKCpDMH9F3gNDVriL7OlUr2RzG8W/4zfc98kCQQD/Rc87HU6rFmql1Z7VMP3IvbAgXh1QGBZSl9FQoCnIwRuzVLKHRTu8qVLNvkyy0vZ06N+XUoBTvUpqx8qju0k3AkEAiih74hyv383niBMDK2BuwSRm+flU8uuIZ2fYS8xjGbdFMGdG/ltGxa8ekh7xFazWsfr/qnO4RQsk1HxU3yxyrwJBAKouiido3xy5nG6OaeN1/oXTysA+vjVlZDcP4OBqxoXWEneNzFouJ9MbjIkpiHc3TvibP7o3ADfThvHahxfSMM8CQFwa/UFodT/emlq3V3JASdYYRKamOKHyWu+akDKIQhEk2MrvhKmSLy50vwwUoLkd5HanVRxNJYNcwzhS4z9y9x8CQQDrWDyuOcb5IyqFnWflNuMn1aJW5qCLbFGmHtRiAiKsBdKtyAGfBSJUYnz8ji51QIwKYpDjA2h371SFG5g267br";

        AsymmetricKeyParameter priKey = PrivateKeyFactory.createKey(Base64.decode(privatecKey1));
        cipher.init(false, priKey);//false表示解密
        byte[] decriyptData = encryptAndDecrypt(encryptData, cipher, 128);
        String decryptData = new String(decriyptData, "utf-8");
        Log.i("rsa--", decryptData);
        //   FileUtils.writeFileFromString(new File(LonchCloudApplication.getApplicationsContext().getFilesDir().getAbsolutePath()+"/DD.txt"),decryptData,true);
        /**
         * AES加密
         */
//        String apiKey = "3fa0900d24c34cd1";
//
//        String encrypted = encrypt(string1,apiKey);
//        Log.i("rsa--",System.currentTimeMillis() - start + "   ms");
    }

    public static String test4() throws Exception {

        InputStream is = new FileInputStream(LonchCloudApplication.getApplicationsContext().getFilesDir().getAbsolutePath() + "/c.txt");
        StringBuffer sb;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(is));

            sb = new StringBuffer();

            String data1;
            while ((data1 = br.readLine()) != null) {
                sb.append(data1);
            }
        } finally {
            br.close();
        }
        String encryptData = sb.toString();
        //解密
        String privatecKey1 = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAInEACZB1lhXvo2gfgEZcyhnZ+UgTVu7XC1BBGVBCqpPBSMIyuBxjOqhsP+tw2ujKj+GsyKALardiA/04HIBW147iqHZT/HthvfRG+tiBuMLe4xNZtloc34iplQQWMWQnH5CIN++YXN/65RbgHHcE9V6K2y5wlTzexXhw3LSFYqZAgEDAoGAFvYABmBOZA6fwkVqVYQ93BE7+4ViOfSPXOArZjWBxw0rhdbMer2XfHBIKpz150XcX+vIhcAHnHpBV/4laFWPOh2vY8oDqJBwcPCIjDro6RWZk4iCkTDn+pFxHGinXsewr4+7e7QgKCpDMH9F3gNDVriL7OlUr2RzG8W/4zfc98kCQQD/Rc87HU6rFmql1Z7VMP3IvbAgXh1QGBZSl9FQoCnIwRuzVLKHRTu8qVLNvkyy0vZ06N+XUoBTvUpqx8qju0k3AkEAiih74hyv383niBMDK2BuwSRm+flU8uuIZ2fYS8xjGbdFMGdG/ltGxa8ekh7xFazWsfr/qnO4RQsk1HxU3yxyrwJBAKouiido3xy5nG6OaeN1/oXTysA+vjVlZDcP4OBqxoXWEneNzFouJ9MbjIkpiHc3TvibP7o3ADfThvHahxfSMM8CQFwa/UFodT/emlq3V3JASdYYRKamOKHyWu+akDKIQhEk2MrvhKmSLy50vwwUoLkd5HanVRxNJYNcwzhS4z9y9x8CQQDrWDyuOcb5IyqFnWflNuMn1aJW5qCLbFGmHtRiAiKsBdKtyAGfBSJUYnz8ji51QIwKYpDjA2h371SFG5g267br";
        AsymmetricBlockCipher cipher = new RSAEngine();
        AsymmetricKeyParameter priKey = PrivateKeyFactory.createKey(Base64.decode(privatecKey1));
        cipher.init(false, priKey);//false表示解密
        byte[] decriyptData = encryptAndDecrypt(Base64.decode(encryptData), cipher, 117);
        String decryptData = new String(decriyptData, "utf-8");
        return decryptData;
    }

    public static String test3() throws Exception {
        //AES解密
        String apiKey = "3fa0900d24c34cd1";
        InputStream is = new FileInputStream(LonchCloudApplication.getApplicationsContext().getFilesDir().getAbsolutePath() + "/d.txt");
        StringBuffer sb;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(is));

            sb = new StringBuffer();

            String data1;
            while ((data1 = br.readLine()) != null) {
                sb.append(data1);
            }
        } finally {
            br.close();
        }
        String data = sb.toString();

        byte[] decrypt = encrypt(data.getBytes("utf-8"), apiKey.getBytes(), INIT_VECTOR);
        //RSA解密
        String privatecKey1 = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAInEACZB1lhXvo2gfgEZcyhnZ+UgTVu7XC1BBGVBCqpPBSMIyuBxjOqhsP+tw2ujKj+GsyKALardiA/04HIBW147iqHZT/HthvfRG+tiBuMLe4xNZtloc34iplQQWMWQnH5CIN++YXN/65RbgHHcE9V6K2y5wlTzexXhw3LSFYqZAgEDAoGAFvYABmBOZA6fwkVqVYQ93BE7+4ViOfSPXOArZjWBxw0rhdbMer2XfHBIKpz150XcX+vIhcAHnHpBV/4laFWPOh2vY8oDqJBwcPCIjDro6RWZk4iCkTDn+pFxHGinXsewr4+7e7QgKCpDMH9F3gNDVriL7OlUr2RzG8W/4zfc98kCQQD/Rc87HU6rFmql1Z7VMP3IvbAgXh1QGBZSl9FQoCnIwRuzVLKHRTu8qVLNvkyy0vZ06N+XUoBTvUpqx8qju0k3AkEAiih74hyv383niBMDK2BuwSRm+flU8uuIZ2fYS8xjGbdFMGdG/ltGxa8ekh7xFazWsfr/qnO4RQsk1HxU3yxyrwJBAKouiido3xy5nG6OaeN1/oXTysA+vjVlZDcP4OBqxoXWEneNzFouJ9MbjIkpiHc3TvibP7o3ADfThvHahxfSMM8CQFwa/UFodT/emlq3V3JASdYYRKamOKHyWu+akDKIQhEk2MrvhKmSLy50vwwUoLkd5HanVRxNJYNcwzhS4z9y9x8CQQDrWDyuOcb5IyqFnWflNuMn1aJW5qCLbFGmHtRiAiKsBdKtyAGfBSJUYnz8ji51QIwKYpDjA2h371SFG5g267br";

        AsymmetricKeyParameter priKey = PrivateKeyFactory.createKey(Base64.decode(privatecKey1));
        AsymmetricBlockCipher cipher = new RSAEngine();
        cipher.init(false, priKey);//false表示解密
        byte[] decriyptData = encryptAndDecrypt(decrypt, cipher, 117);
        String decryptData = new String(decriyptData, "utf-8");
        return decryptData;

    }

    private static byte[] encryptAndDecrypt(byte[] data, AsymmetricBlockCipher cipher, int maxSize) throws Exception {
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > maxSize) {
                cache = cipher.processBlock(data, offSet, maxSize);
            } else {
                cache = cipher.processBlock(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * maxSize;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    public static void AESTest() throws Exception {
        /**
         * AES 128位 加密参数
         * key:3fa0900d24c34cd1
         * Mode：ECB
         * Padding：PKCS7
         */

        String apiKey = "3fa0900d24c34cd1";

//        InputStream is = new FileInputStream(LonchCloudApplication.getApplicationsContext().getFilesDir().getAbsolutePath()+"/b.txt");
//        StringBuffer sb;
//        BufferedReader br = null;
//        try {
//            br = new BufferedReader(new InputStreamReader(is));
//
//            sb = new StringBuffer();
//
//            String data1;
//            while ((data1 = br.readLine()) != null) {
//                sb.append(data1);
//            }
//        } finally {
//            br.close();
//        }
//        String content  = sb.toString().replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("\n", "");
        String content = "ssssssss123242442425";
        Log.i("aes--", content);
        long start = System.currentTimeMillis();
        String encrypt = encrypt(content, apiKey);
        Log.i("aes--", System.currentTimeMillis() - start + "   ms");
        Log.i("aes--", encrypt);
        String decrypt = decrypt(encrypt, apiKey);
        Log.i("aes--", decrypt);
    }


    private static final byte[] INIT_VECTOR = {0x31, 0x37, 0x36, 0x35,
            0x34, 0x33, 0x32, 0x31,
            0x38, 0x27, 0x36, 0x35,
            0x33, 0x23, 0x32, 0x33};


    public static String encrypt(String content, String apiKey)
            throws Exception {
        if (apiKey == null) {
            throw new IllegalArgumentException("Key cannot be null!");
        }
        String encrypted = null;
        byte[] keyBytes = apiKey.getBytes();
        if (keyBytes.length != 32 && keyBytes.length != 24
                && keyBytes.length != 16) {
            throw new IllegalArgumentException(
                    "Key length must be   128/192/256 bits!");
        }
        byte[] encryptedBytes = null;
        encryptedBytes = encrypt(content.getBytes(), keyBytes, INIT_VECTOR);
        encrypted = new String(Base64.encode(encryptedBytes));
        return encrypted;
    }


    public static String decrypt(String content, String apiKey)
            throws Exception {
        if (apiKey == null) {
            throw new IllegalArgumentException("Key cannot be null!");
        }
        String decrypted = null;
        byte[] encryptedContent = Base64.decode(content);
        byte[] keyBytes = apiKey.getBytes();
        byte[] decryptedBytes = null;
        if (keyBytes.length != 32 && keyBytes.length != 24
                && keyBytes.length != 16) {
            throw new IllegalArgumentException(
                    "Key length must be   128/192/256 bits!");
        }
        decryptedBytes = decrypt(encryptedContent, keyBytes, INIT_VECTOR);
        decrypted = new String(decryptedBytes);
        return decrypted;
    }


    private static byte[] encrypt(byte[] plain, byte[] key, byte[] iv)
            throws Exception {
        PaddedBufferedBlockCipher aes = new PaddedBufferedBlockCipher(
                new AESEngine());
//        CipherParameters ivAndKey = new ParametersWithIV(new KeyParameter(key),
//                iv);
        CipherParameters keyValue = new ParametersWithRandom(new KeyParameter(key));
        aes.init(true, keyValue);
        return cipherData(aes, plain);
    }


    private static byte[] decrypt(byte[] cipher, byte[] key, byte[] iv)
            throws Exception {
        PaddedBufferedBlockCipher aes = new PaddedBufferedBlockCipher(new AESEngine());
//        CipherParameters ivAndKey = new ParametersWithIV(new KeyParameter(key),
//                iv);
        CipherParameters keyValue = new ParametersWithRandom(new KeyParameter(key));
        aes.init(false, keyValue);
        return cipherData(aes, cipher);
    }


    private static byte[] cipherData(PaddedBufferedBlockCipher cipher,
                                     byte[] data) throws Exception {
        int minSize = cipher.getOutputSize(data.length);
        byte[] outBuf = new byte[minSize];
        int length1 = cipher.processBytes(data, 0, data.length, outBuf, 0);
        int length2 = cipher.doFinal(outBuf, length1);
        int actualLength = length1 + length2;
        byte[] result = new byte[actualLength];
        System.arraycopy(outBuf, 0, result, 0, result.length);
        return result;
    }


}
