package com.example.uniapp.back.executor;

import android.content.Context;
import android.util.Log;

import androidx.core.content.ContextCompat;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {

    private static AppExecutors appExecutors;
    private Executor diskIo;
    private Executor UIThread;

    private AppExecutors(Executor diskIo, Executor UIThread){
        this.diskIo =  diskIo;
        this.UIThread = UIThread;
    }

    public static AppExecutors getInstance(Context context){
        if (appExecutors == null) {
            Log.i("AppExecutor", "new app executor is loading");
            appExecutors = new AppExecutors(Executors.newSingleThreadExecutor(), ContextCompat.getMainExecutor(context));
        }
        return appExecutors;
    }

    public Executor getDiskIo(){
        return diskIo;
    }

    public Executor getUIThread(){
        return UIThread;
    }
}
