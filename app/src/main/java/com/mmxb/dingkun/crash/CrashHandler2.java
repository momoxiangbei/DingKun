package com.mmxb.dingkun.crash;

import android.util.Log;

import androidx.annotation.NonNull;

/**
 * Created by mmxb on 8/2/21.
 * <p>
 * 多个CrashHandler时，会从最后一个初始化的handler依次执行uncaughtException方法
 */
public class CrashHandler2 implements Thread.UncaughtExceptionHandler {
    private final String TAG = "CrashHandler2";
    private static CrashHandler2 crashHandler = new CrashHandler2();
    private Thread.UncaughtExceptionHandler mDefaultExceptionHandler;

    private CrashHandler2() {
    }

    public static CrashHandler2 getInstance() {
        return crashHandler;
    }

    public void init() {
        // 注意这里的写法：设置DefaultHandler之前先获取上一个handler
        mDefaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(@NonNull Thread thread, @NonNull Throwable throwable) {
        Log.d(TAG, "uncaughtException: ");
        // 注意这里的写法：当uncaughtException调用的时候，会调用到前一个自定义的handler，会依次执行到系统的uncaughtException方法。
        if (mDefaultExceptionHandler != null) {
            // 这个方法必须要调用，否则uncaughtException的链路就会断掉，会导致com.android.internal.os.RuntimeInit$KillApplicationHandler执行不到，app卡死
            mDefaultExceptionHandler.uncaughtException(thread, throwable);
        }
    }
}
