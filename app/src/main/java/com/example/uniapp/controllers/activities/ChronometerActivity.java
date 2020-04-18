package com.example.uniapp.controllers.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.uniapp.R;
import com.example.uniapp.models.MarketTimes;

public class ChronometerActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout spaceResetLep;

    private ImageView anchorChrono;
    private Animation animChrono;
    private ConstraintLayout startStopBtn;
    private Button resetBtn;
    private Button lapBtn;

    private TextView chronoTV;
    private Thread chronoThread;

    private int offsetAnchor;
    private long chronoTime;
    private long startTime;
    private boolean isRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronometer);

        setupUIElements();
        setupThreadChrono();

        startStopBtn.setOnClickListener(this);
        resetBtn.setOnClickListener(this);
        lapBtn.setOnClickListener(this);
    }

    private void setupUIElements() {
        offsetAnchor = 32;
        chronoTime   = 0;
        isRunning    = false;

        startStopBtn  = findViewById(R.id.activity_chronometer_btn_start);
        resetBtn      = findViewById(R.id.activity_chronometer_btn_reset);
        lapBtn        = findViewById(R.id.activity_chronometer_btn_lap);

        anchorChrono  = findViewById(R.id.activity_chronometer_anchor);
        animChrono    = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.roundingalone);
        spaceResetLep = findViewById(R.id.activity_chronometer_linear_layout_options);
        chronoTV      = findViewById(R.id.activity_chronometer_chrono);

        anchorChrono.setRotation(offsetAnchor);
        spaceResetLep.setVisibility(View.GONE);
    }

    private void setupThreadChrono() {
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
                                chronoTime = System.currentTimeMillis()/10 - startTime;
                                chronoTV.setText(MarketTimes.convertLongMilliToTime(chronoTime));
                            }
                        });
                    }
                } catch (Exception ex) {}
            }
        };
    }

    @Override
    public void onClick(View v) {
             if (v.getTag().equals("startStop")) startStop();
        else if (v.getTag().equals("reset")) reset();
        else if (v.getTag().equals("lap")) lap();
    }

    private void startStop() {
        isRunning = !isRunning;
        if (isRunning) {
            System.out.println("start button");
            startTime = System.currentTimeMillis()/10;
            chronoThread.start();
            anchorChrono.setRotation(offsetAnchor);
            anchorChrono.startAnimation(animChrono);
        } else {
            System.out.println("stop button");
            chronoThread.interrupt();
            chronoTime = MarketTimes.convertTimeToLongMilli(chronoTV.getText().toString());
            anchorChrono.clearAnimation();
            System.out.println("chronoTime : " + chronoTime);
            anchorChrono.setRotation(offsetAnchor + (chronoTime/1000)%10 * offsetAnchor);
        }
        spaceResetLep.setVisibility(View.VISIBLE);
    }

    private void reset() {
        System.out.println("reset button");
        chronoThread = null;
        setupThreadChrono();
        chronoTV.setText("00:00.00");
        spaceResetLep.setVisibility(View.GONE);
        anchorChrono.clearAnimation();
        anchorChrono.setRotation(offsetAnchor);
    }

    private void lap() {
        System.out.println("lap button");
    }
}
