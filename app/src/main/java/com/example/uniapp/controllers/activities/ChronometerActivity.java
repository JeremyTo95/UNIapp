package com.example.uniapp.controllers.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
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
    private Thread chronoThread;

    private boolean isRunning;
    private int offsetAnchor;
    private long chronoTime;
    private long startTime;
    private List<Long> allChronos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronometer);

        setupUIElements();
        updateLapChronoRV();

        startStopBtn.setOnClickListener(this);
        resetBtn.setOnClickListener(this);
        lapBtn.setOnClickListener(this);
    }

    private void setupUIElements() {
        offsetAnchor = 32;
        chronoTime   = 0;
        isRunning    = false;
        allChronos   = new ArrayList<Long>();

        startStopBtn  = findViewById(R.id.activity_chronometer_btn_start);
        resetBtn      = findViewById(R.id.activity_chronometer_btn_reset);
        lapBtn        = findViewById(R.id.activity_chronometer_btn_lap);

        anchorChrono  = findViewById(R.id.activity_chronometer_anchor);
        animChrono    = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.roundingalone);
        spaceResetLep = findViewById(R.id.activity_chronometer_linear_layout_options);
        chronoTV      = findViewById(R.id.activity_chronometer_chrono);
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
            setupThreadChrono(MarketTimes.convertTimeToLongMilli(chronoTV.getText().toString()));
            chronoThread.start();
            anchorChrono.startAnimation(animChrono);
        } else {
            System.out.println("stop button");
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
            spaceResetLep.setVisibility(View.GONE);
            anchorChrono.clearAnimation();
            anchorChrono.setRotation(offsetAnchor);
        }
    }

    private void lap() {
        System.out.println("lap button newTime : " + MarketTimes.convertLongMilliToTime(chronoTime));
        allChronos.add(chronoTime);
        updateLapChronoRV();
    }

    private void rotateAnchorStopping(int timeForRoundInS) {
        anchorChrono.setRotation((int) ((((double) chronoTime / 100) * 360 / timeForRoundInS) + offsetAnchor)%360);
    }

    private void updateLapChronoRV() {
        allChronosRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvChronometerAdapter = new RvChronometerAdapter(allChronos);
        allChronosRV.setAdapter(rvChronometerAdapter);
        allChronosRV.setNestedScrollingEnabled(false);
        rvChronometerAdapter.notifyDataSetChanged();
    }
}
