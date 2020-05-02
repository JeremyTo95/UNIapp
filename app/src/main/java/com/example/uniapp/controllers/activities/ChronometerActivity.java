package com.example.uniapp.controllers.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uniapp.R;
import com.example.uniapp.controllers.adapters.recyclerview.RvChronometerAdapter;
import com.example.uniapp.models.MarketTimes;
import com.example.uniapp.views.AboutScreen;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

public class ChronometerActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout spaceResetLep;

    private ImageView anchorChrono;
    private Animation animChrono;
    private RecyclerView allChronosRV;
    private RvChronometerAdapter rvChronometerAdapter;
    private ConstraintLayout startStopBtn;
    private Button resetBtn;
    private Button lapBtn;

    private TextView chronoTV;
    private TextView chronoLapTV;
    private TextView chronoDiffTV;
    private Thread chronoThread;

    private boolean isRunning;
    private int offsetAnchor;
    private long chronoTime;
    private long startTime;
    private long oldDiffTime;
    private List<Long> allChronos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronometer);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setupUIElements();
        updateLapChronoRV();

        startStopBtn.setOnClickListener(this);
        resetBtn.setOnClickListener(this);
    }

    private void setupUIElements() {
        offsetAnchor = 32;
        chronoTime   = 0;
        oldDiffTime  = 0;
        isRunning    = false;
        allChronos   = new ArrayList<Long>();

        startStopBtn  = findViewById(R.id.activity_chronometer_btn_start);
        resetBtn      = findViewById(R.id.activity_chronometer_btn_reset);

        anchorChrono  = findViewById(R.id.activity_chronometer_anchor);
        animChrono    = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.roundingalone);
        spaceResetLep = findViewById(R.id.activity_chronometer_linear_layout_options);
        chronoTV      = findViewById(R.id.activity_chronometer_chrono);
        chronoLapTV   = findViewById(R.id.activity_chronometer_lap);
        chronoDiffTV  = findViewById(R.id.activity_chronometer_diff);
        allChronosRV  = findViewById(R.id.activity_chronometer_rv);

        anchorChrono.setRotation(offsetAnchor);
        spaceResetLep.setVisibility(View.GONE);
    }

    private void setupThreadChrono(long oldTime) {
        chronoThread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(10);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                chronoTV = findViewById(R.id.activity_chronometer_chrono);
                                chronoTime = System.currentTimeMillis()/10 - startTime + oldTime;
                                chronoTV.setText(MarketTimes.convertLongMilliToTime(chronoTime));
                                updateChronoLap();
                                updateChronoDiff();
                            }
                        });
                    }
                } catch (Exception ex) {}
            }
        };
    }

    @Override
    public void onClick(View v) {
        if (v.getTag().equals("reset")) reset();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action;
        int keyCode;

        action = event.getAction();
        keyCode = event.getKeyCode();

        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP && action == KeyEvent.ACTION_DOWN) {
            lap();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN && action == KeyEvent.ACTION_DOWN) {
            startStop();
            return true;
        } else return super.dispatchKeyEvent(event);
    }

    private void startStop() {
        isRunning = !isRunning;
        if (isRunning) {
            startTime = System.currentTimeMillis()/10;
            setupThreadChrono(MarketTimes.convertTimeToLongMilli(chronoTV.getText().toString()));
            chronoThread.start();
            anchorChrono.startAnimation(animChrono);
        } else {
            chronoThread.interrupt();
            chronoTime = MarketTimes.convertTimeToLongMilli(chronoTV.getText().toString());
            anchorChrono.clearAnimation();
            rotateAnchorStopping(10);
        }
        spaceResetLep.setVisibility(View.VISIBLE);
    }

    private void reset() {
        if (isRunning) {
            Toast.makeText(getApplicationContext(), "Le chronomètre doit être arréter", Toast.LENGTH_SHORT).show();
        } else {
            chronoThread = null;
            allChronos = new ArrayList<Long>();
            updateLapChronoRV();
            setupThreadChrono(0);
            chronoTV.setText("00:00.00");
            chronoLapTV.setText("00:00.00");
            chronoDiffTV.setText("(-00.00)");
            chronoDiffTV.setTextColor(getResources().getColor(R.color.greenDeep));
            spaceResetLep.setVisibility(View.GONE);
            anchorChrono.clearAnimation();
            anchorChrono.setRotation(offsetAnchor);
        }
    }

    private void lap() {
        allChronos.add(chronoTime);
        updateLapChronoRV();

        if (allChronos.size() == 1) oldDiffTime = 0;
        if (allChronos.size() == 2) oldDiffTime = chronoTime - allChronos.get(allChronos.size() - 1);
        if (allChronos.size()  > 2) oldDiffTime = chronoTime - allChronos.get(allChronos.size() - 1) - (allChronos.get(allChronos.size() - 1) - allChronos.get(allChronos.size() - 2));
    }

    private void rotateAnchorStopping(int timeForRoundInS) {
        anchorChrono.setRotation((int) ((((double) chronoTime / 100) * 360 / timeForRoundInS) + offsetAnchor)%360);
    }

    private void updateLapChronoRV() {
        allChronosRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvChronometerAdapter = new RvChronometerAdapter(getApplicationContext(), allChronos);
        allChronosRV.setAdapter(rvChronometerAdapter);
        allChronosRV.setNestedScrollingEnabled(false);
        rvChronometerAdapter.notifyDataSetChanged();
    }

    private void updateChronoLap() {
        if (allChronos.size() != 0) chronoLapTV.setText(MarketTimes.convertLongMilliToTime(chronoTime - allChronos.get(allChronos.size() - 1)));
        else chronoLapTV.setText(MarketTimes.convertLongMilliToTime(chronoTime));
    }

    private void updateChronoDiff() {
        double diffChrono;
        long diffChronoLong = 0;

        if (allChronos.size() == 0) diffChronoLong = 0;
        if (allChronos.size() == 1) diffChronoLong = chronoTime - allChronos.get(allChronos.size() - 1) - (allChronos.get(allChronos.size() - 1) - 0);
        if (allChronos.size() >= 2) diffChronoLong = chronoTime - allChronos.get(allChronos.size() - 1) - (allChronos.get(allChronos.size() - 1) - allChronos.get(allChronos.size() - 2));

        diffChrono = (double) diffChronoLong / 100; // OK

        int integer = (int) diffChrono;
        int decimal = (int) (diffChrono*100)%100;

        String integerStr = String.valueOf(integer);
        String decimalStr = String.valueOf(decimal);

        if (integer <= -10) integerStr = String.valueOf(integer * (-1));
        else if (integer > -10 && integer <  0) integerStr = "0" + integer*(-1);
        else if (integer >= 0  && integer < 10) integerStr = "0" + integer;

        if (decimal <= -10) decimalStr = String.valueOf(decimal * (-1));
        else if (decimal > -10 && decimal <  0) decimalStr = "0" + decimal*(-1);
        else if (decimal >= 0  && decimal < 10) decimalStr = "0" + decimal;

        if (diffChrono > 0) {
            chronoDiffTV.setText("(+" + integerStr + "." + decimalStr + ")");
            chronoDiffTV.setTextColor(getResources().getColor(R.color.redDeep));
        } else {
            chronoDiffTV.setText("(-" + integerStr + "." + decimalStr + ")");
            chronoDiffTV.setTextColor(getResources().getColor(R.color.greenDeep));
        }
    }

    /*@Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) AboutScreen.hideNavigationBar(this);
    }*/
}
