package com.lonch.client.exception;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.lonch.client.LonchCloudApplication;
import com.lonch.client.bean.AppLog;
import com.lonch.client.utils.OkHttpUtil;
import com.lonch.client.utils.SpUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    public static final String TAG = "CrashHandler";

    //系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    //CrashHandler实例
    private static CrashHandler INSTANCE = new CrashHandler();
    //程序的Context对象
    private Context mContext;
    //用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();

    //用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    /** 保证只有一个CrashHandler实例 */
    private CrashHandler() {
    }

    /** 获取CrashHandler实例 ,单例模式 */
    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);

    }

    private void setMainThreadException(final Thread thread) {
        LonchCloudApplication.handler.post(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Looper.loop();
                    } catch (Throwable e) {
                        e.printStackTrace();
                        return;
//                        mDefaultHandler.uncaughtException(Thread.currentThread(), e);
//                        IHandlerException handler = mainThreadFactory.get(e);
//                        if (onExceptionCallBack != null) {
//                            onExceptionCallBack.onThrowException(Thread.currentThread(), e, handler);
//                        }
//                        if (handler == null) {
//                            mDefaultHandler.uncaughtException(Thread.currentThread(), e);
//                            return;
//                        }
//                        if (handler.handler(e)) {
//                            return;
//                        }
                    }
                }
            }
        });

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            Log.e(TAG, "uncaughtException : ");
            setMainThreadException(thread);
        } else {
            //退出程序
            if(handleException(ex)){
                setMainThreadException(thread);
            }
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(final Throwable ex) {
        if (ex == null) {//如果已经处理过这个Exception,则让系统处理器进行后续关闭处理
            return false;
        }
        //使用Toast来显示异常信息
//        new Thread() {
//            @Override
//            public void run() {
//                Looper.prepare();
//                Toast.makeText(mContext, "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_LONG).show();
//                Looper.loop();
//            }
//        }.start();
        new Thread() {
            @Override
            public void run() {

                //收集设备参数信息
                collectDeviceInfo(mContext);
                //保存日志文件
                saveCrashInfo2File(ex);
            }
        }.start();

        return true;
    }

    /**
     * 收集设备参数信息
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return  返回文件名称,便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex) {

        StringBuffer sb = new StringBuffer();
//        for (Map.Entry<String, String> entry : infos.entrySet()) {
//            String key = entry.getKey();
//            String value = entry.getValue();
//            sb.append(key + "=" + value + "\n");
//        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        String error = sb.toString();
        //上报crash
        AppLog appLog = OkHttpUtil.getInstance().getPhoneInfo(mContext);
        appLog.setErrCode("ANDyfc0001400010");
        appLog.setErrMsg(error);
        appLog.setErrLevel("warn");
        appLog.setEventName("app");
        appLog.setRemark("app");
        appLog.setToken((String) SpUtils.get("token",""));
        OkHttpUtil.getInstance().sendPostRequest(LonchCloudApplication.getAppConfigDataBean().SERVICE_LOG_URL,appLog);

//        try {
//            long timestamp = System.currentTimeMillis();
//            String time = formatter.format(new Date());
//            String fileName = "crash-" + time + "-" + timestamp + ".log";
//            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//                saveToFile(fileName,sb.toString());
//            }
//            return fileName;
//        } catch (Exception e) {
//            Log.e(TAG, "an error occured while writing file...", e);
//        }
        return null;
    }
    public void saveToFile(String fileName,String content) {
        BufferedWriter out = null;

        //获取SD卡状态
//        String state = getActivity().getFilesDir().getAbsolutePath()+"/App";
        String state  = Environment.getExternalStorageState();
        //判断SD卡是否就绪
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(mContext, "请检查SD卡", Toast.LENGTH_SHORT).show();
        }
        File SDCardFile = mContext.getExternalCacheDir();
        File file = new File(SDCardFile, "data.txt");
        Log.d("sssss667","ssssss="+file.getAbsolutePath());

        //取得SD卡根目录
//        File file = new File(state);
        try {
            Log.e("TAG", "======SD卡根目录：" + file.getCanonicalPath());
            if(!file.exists()){
                file.mkdirs();
                file.createNewFile();
                Log.e("TAG", "file.getCanonicalPath() == " + file.getCanonicalPath());
            }
            /*
            输出流的构造参数1：可以是File对象  也可以是文件路径
            输出流的构造参数2：默认为False=>覆盖内容； true=>追加内容
             */
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file.getCanonicalPath() + "/"+fileName,true)));
            out.newLine();
            out.write(content);
//            Toast.makeText(getActivity(), "保存成功", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
