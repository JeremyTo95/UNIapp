package com.example.uniapp.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.uniapp.controllers.activities.MainActivity;
import com.example.uniapp.models.database.dao.pointFFN.PointFFN;
import com.github.mikephil.charting.utils.Utils;

import java.lang.ref.WeakReference;

public class MyHandlerThread extends HandlerThread {
    private WeakReference<ProgressBar> progressBarWeakReference;

    public MyHandlerThread(String name, ProgressBar progressBar) {
        super(name);
        progressBarWeakReference = new WeakReference<>(progressBar);
    }

    public void startHandler() {
        if (progressBarWeakReference.get() != null) progressBarWeakReference.get().setVisibility(View.VISIBLE);

        if (!this.isAlive()) this.start();

        Handler handler = new Handler(this.getLooper());

        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.e("TAG", "Long action is starting...");
                loadUser();
                loadPointsFFN();
                loadRaces();
                loadTrainings();
                Log.e("TAG", "Long action is finished !");
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (progressBarWeakReference.get() != null) progressBarWeakReference.get().setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    private void loadUser() {
        if (MainActivity.appDataBase.userDAO().getNbUser() == 1) {
            MainActivity.user = MainActivity.appDataBase.userDAO().getAll().get(0);
        } else {
            MainActivity.appDataBase.userDAO().deleteAll();
        }
    }

    private void loadPointsFFN() {
        if (MainActivity.appDataBase.pointFFNDAO().getNbPoint() != 0) {
            MainActivity.pointFFNList = MainActivity.appDataBase.pointFFNDAO().getAllPoints();
        } else {
            PointFFN.makePointFFNApiCall();
        }
    }

    private void loadRaces() {
        MainActivity.allRaces = MainActivity.appDataBase.raceDAO().getAllRaces();
    }

    private void loadTrainings() {
        MainActivity.allTrainings = MainActivity.appDataBase.trainingDAO().getAllTrainings();
    }
}
