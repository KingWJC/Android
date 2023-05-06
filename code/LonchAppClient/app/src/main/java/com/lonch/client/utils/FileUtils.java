/*
 * Copyright 2018 Yan Zhenjie.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lonch.client.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.lonch.client.LonchCloudApplication;
import com.lonch.client.bean.PlistPackageBean;
import com.lonch.client.database.bean.WebVersionEntity;
import com.lonch.client.database.tabutils.WebVersionUtils;
import com.yanzhenjie.andserver.http.multipart.MultipartFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by YanZhenjie on 2018/6/9.
 */
public class FileUtils {

    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            Log.i("md5----","no file");
            return "";
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("md5----",e.getMessage());
            return "";
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }

    /**
     * Create a random file based on mimeType.
     *
     * @param file file.
     *
     * @return file object.
     */
    public static File createRandomFile(MultipartFile file) {
        String extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(file.getContentType().toString());
        if (StringUtils.isEmpty(extension)) {
            extension = MimeTypeMap.getFileExtensionFromUrl(file.getFilename());
        }
        String uuid = UUID.randomUUID().toString();
        return new File(LonchCloudApplication.getRootDir(), uuid + "." + extension);
    }

    /**
     * SD is available.
     *
     * @return true, otherwise is false.
     */
    public static boolean storageAvailable() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File down = LonchCloudApplication.getApplicationsContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
            if (null!=down){
                File sd = new File(down.getAbsolutePath());
                return sd.canWrite();
            }else{
                return false;
            }
        } else {
            return false;
        }
    }
    /**
     *  根据路径删除指定的目录或文件，无论存在与否
     *@param filePath  要删除的目录或文件
     *@return 删除成功返回 true，否则返回 false。
     */
    public static boolean deleteFolder(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        } else {
            if (file.isFile()) {
                // 为文件时调用删除文件方法
                return deleteFile(filePath);
            } else {
                // 为目录时调用删除目录方法
                return deleteDirectory(filePath);
            }
        }
    }

    /**
     * 删除单个文件
     * @param   filePath    被删除文件的文件名
     * @return 文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 删除文件夹以及目录下的文件
     * @param   filePath 被删除目录的文件路径
     * @return  目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String filePath) {
        boolean flag = false;
        //如果filePath不以文件分隔符结尾，自动添加文件分隔符
        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        File dirFile = new File(filePath);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        File[] files = dirFile.listFiles();
        //遍历删除文件夹下的所有文件(包括子目录)
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                //删除子文件
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } else {
                //删除子目录
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前空目录
        return dirFile.delete();
    }

    /**
     * 保存文件
     * @param data
     */
    public static void saveDataToFile(PlistPackageBean filePath, String data) {
        SpUtils.put(filePath.getSoftware_id(),data);
        SpUtils.put(filePath.getSoftware_name(),data);
        WebVersionEntity webVersionEntity = new WebVersionEntity();
        webVersionEntity.setSoftware_id(filePath.getSoftware_id());
        webVersionEntity.setJson(data);
        webVersionEntity.setVersion(filePath.getVersion());
        WebVersionUtils.getInstance().insert(webVersionEntity);
    }
    /**
     * 读取文件
     */
    public static String getDatafromFile(String software_id) {
        return (String) SpUtils.get(software_id, "");
    }

    /**
     *  从assets目录中复制整个文件夹内容
     *  @param  context  Context 使用CopyFiles类的Activity
     *  @param  oldPath  String  原文件路径  如：/aa
     *  @param  newPath  String  复制后路径  如：xx:/bb/cc
     */
    public static void copyFilesFassets(Context context, String oldPath, String newPath) {
        try {
            String fileNames[] = context.getAssets().list(oldPath);//获取assets目录下的所有文件及目录名
            if (fileNames.length > 0) {//如果是目录
                File file = new File(newPath);
                file.mkdirs();//如果文件夹不存在，则递归
                for (String fileName : fileNames) {
                    copyFilesFassets(context,oldPath + "/" + fileName,newPath+"/"+fileName);
                }
            } else {//如果是文件
                InputStream is = context.getAssets().open(oldPath);
                FileOutputStream fos = new FileOutputStream(new File(newPath));
                byte[] buffer = new byte[1024];
                int byteCount=0;
                while((byteCount=is.read(buffer))!=-1) {//循环从输入流读取 buffer字节
                    fos.write(buffer, 0, byteCount);//将读取的输入流写入到输出流
                }
                fos.flush();//刷新缓冲区
                is.close();
                fos.close();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.i("eeee",e.getMessage());
            //如果捕捉到错误则通知UI线程
        }
    }

    public static void deleteOldFile(Context mContext,PlistPackageBean packageBean) {
        String[] split = packageBean.getZip_path().split("\\/");
        int lastResouse = split.length - 1;
        //Zip包路径
        final String package_zip_resource =mContext. getFilesDir().getAbsolutePath() + "/" + "Zip/" + split[lastResouse];
        File file = new File(package_zip_resource);
        if (file.exists()) {
            file.delete();
        }

        if (!TextUtils.isEmpty(packageBean.getSoftware_type()) && packageBean.getSoftware_type().equals("3")){
            return;
        }
        String filePath = mContext.getFilesDir().getAbsolutePath() + "/App/" + packageBean.getApp_package_name();
        String version = packageBean.getVersion();
        File[] allFiles = new File(filePath).listFiles();

        if (null != allFiles) {
                for (int y = 0; y < allFiles.length; y++) {
                    if (!allFiles[y].getName().equals(version)) {
                        //次方法只能删除文件夹下的所有目录
                        com.blankj.utilcode.util.FileUtils.delete(new File(mContext.getFilesDir().getAbsolutePath() + "/App/" + packageBean.getApp_package_name() + "/" + allFiles[y].getName()));
                    }
                }

        }
    }

    /**
     * 创建文件夹
     */
    public static void mkdirs(File file) {
        if (file != null && !file.exists() && file.isDirectory()) {
            File parentFile = file.getParentFile();
            if (!parentFile.exists() && parentFile.isDirectory()) {
                mkdirs(parentFile);
            } else {
                file.mkdirs();
            }
        }
    }
    public static void writeLog(Context context,String message){
        File SDCardFile = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        final File target = new File(SDCardFile, "message.txt");
        if (!target.exists()) {
            // 判断父文件夹是否存在
            if (!target.getParentFile().exists()) {
                target.getParentFile().mkdirs();
            }
        }

        FileWriter fileWriter;
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String dateStr = simpleDateFormat.format(date);
        try {
            fileWriter = new FileWriter(target, true);
            fileWriter.write(dateStr + "：" + message + "\r\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("rsa--",e.getMessage());
        }

    }

    public static File getFileByPath(final String filePath) {
        return StringUtils.isSpace(filePath) ? null : new File(filePath);
    }

    public static boolean createOrExistsFile(final String filePath) {
        return createOrExistsFile(getFileByPath(filePath));
    }

    /**
     * Create a file if it doesn't exist, otherwise do nothing.
     *
     * @param file The file.
     * @return {@code true}: exists or creates successfully<br>{@code false}: otherwise
     */
    public static boolean createOrExistsFile(final File file) {
        if (file == null) return false;
        if (file.exists()) return file.isFile();
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Create a directory if it doesn't exist, otherwise do nothing.
     *
     * @param dirPath The path of directory.
     * @return {@code true}: exists or creates successfully<br>{@code false}: otherwise
     */
    public static boolean createOrExistsDir(final String dirPath) {
        return createOrExistsDir(getFileByPath(dirPath));
    }

    /**
     * Create a directory if it doesn't exist, otherwise do nothing.
     *
     * @param file The file.
     * @return {@code true}: exists or creates successfully<br>{@code false}: otherwise
     */
    public static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }


    /**
     * Write file from string.
     *
     * @param file    The file.
     * @param content The string of content.
     * @param append  True to append, false otherwise.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean writeFileFromString(final File file,
                                              final String content,
                                              final boolean append) {
        if (file == null || content == null) return false;
        if (!createOrExistsFile(file)) {
            Log.e("FileIOUtils", "create file <" + file + "> failed.");
            return false;
        }
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file, append));
            bw.write(content);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isFileExists(File file){
        if (file == null){
            return false;
        }
        if (file.exists()){
            return true;
        }else{
            return false;
        }
    }


}