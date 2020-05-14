package com.example.uniapp.back.executor;

import android.content.Context;

import androidx.core.content.ContextCompat;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {

    private static AppExecutors sInstance;
    private Executor diskIo;
    private Executor UIThread;

    private AppExecutors(Executor diskIo, Executor UIThread){
        this.diskIo =  diskIo;
        this.UIThread = UIThread;
    }

    public static AppExecutors getInstance(Context context){
        if (sInstance == null){
            sInstance = new AppExecutors(Executors.newSingleThreadExecutor(), ContextCompat.getMainExecutor(context));
        }
        return sInstance;
    }

    public Executor getDiskIo(){
        return diskIo;
    }

    public Executor getUIThread(){
        return UIThread;
    }
}
