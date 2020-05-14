package com.example.uniapp.front.controller.controller_activity;

import android.content.Context;
import android.widget.Toast;

import com.example.uniapp.front.controller.global.Controller;
import com.example.uniapp.front.model.market.MarketTimes;
import com.example.uniapp.front.view.actvities.ChronometerActivity;

import java.util.ArrayList;
import java.util.List;

public class ChronometerController extends Controller {
    private ChronometerActivity view;
    private Context             context;

    private int          offsetAnchor;
    private int          periodeAnchor;
    private Thread       chronoThread;
    private long         startTime;
    private boolean      isRunning;
    private long         currentChrono;
    private long         currentLapChrono;
    private Double       currentDiffChrono;
    private List<Long>   allChronos;
    private List<Long>   lapChronos;
    private List<Double> diffChronos;

    public ChronometerController(ChronometerActivity view) {
        super(view);
        this.view    = view;
        this.context = view.getApplicationContext();
    }

    @Override
    public void onStart() {
        super.onStart();
        offsetAnchor  = 32;
        periodeAnchor = 5;
        isRunning     = false;
        initChronos();

        view.setupUIElements();
    }

    private void initChronos() {
        chronoThread      = null;
        currentChrono     = 0;
        currentLapChrono  = 0L;
        currentDiffChrono = 0D;
        allChronos        = new ArrayList<>();
        lapChronos        = new ArrayList<>();
        diffChronos       = new ArrayList<>();
    }

    private void setupThreadChrono(long oldTime) {
        chronoThread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(5);
                         view.runOnUiThread(() -> {
                             currentChrono = System.currentTimeMillis()/10 - startTime + oldTime;
                             view.setChronoTVText(MarketTimes.convertLongMilliToTime(currentChrono));
                             view.rotateAnchorStopping(periodeAnchor);
                             updateChronoLap();
                             updateChronoDiff();
                         });
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
    }

    public void reset() {
        if (isRunning) {
            Toast.makeText(context, "Le chronomètre doit être arréter", Toast.LENGTH_SHORT).show();
        } else {
            initChronos();
            view.updateLapChronoRV();
            setupThreadChrono(0);
            view.initChronoUI();
        }
    }

    public void lap() {
        allChronos.add(currentChrono);
        lapChronos.add(currentLapChrono);
        diffChronos.add(currentDiffChrono);
        view.updateLapChronoRV();
    }

    public void startStop() {
        isRunning = !isRunning;
        if (isRunning) {
            startTime = System.currentTimeMillis()/10;
            setupThreadChrono(currentChrono);
            chronoThread.start();
        } else {
            chronoThread.interrupt();
            currentChrono = MarketTimes.convertTimeToLongMilli(view.getChronoTVText());
            view.rotateAnchorStopping(periodeAnchor);
        }
        view.showResetButton();
    }

    private void updateChronoLap() {
        if (allChronos.size() != 0) currentLapChrono = currentChrono - allChronos.get(allChronos.size() - 1);
        else currentLapChrono = currentChrono;

        view.setChronoLapText(currentLapChrono);
    }

    private void updateChronoDiff() {
        long diffChronoLong = 0;

        if (allChronos.size() == 1) diffChronoLong = currentLapChrono - (allChronos.get(allChronos.size() - 1));
        if (allChronos.size() >= 2) diffChronoLong = currentLapChrono - (allChronos.get(allChronos.size() - 1) - allChronos.get(allChronos.size() - 2));

        currentDiffChrono = (double) diffChronoLong / 100;
        view.setChronoDiffText(currentDiffChrono);
    }

    public int getAnchorRotation(int timeForRoundInS) {
        return (int) ((((double) currentChrono / 100) * 360 / timeForRoundInS) + offsetAnchor)%360;
    }

    public List<Long>   getAllChronos()    { return allChronos; }
    public List<Long>   getLapChronos()    { return lapChronos; }
    public List<Double> getDiffChronos()   { return diffChronos; }
    public int          getPeriodeAnchor() { return periodeAnchor; }
}
