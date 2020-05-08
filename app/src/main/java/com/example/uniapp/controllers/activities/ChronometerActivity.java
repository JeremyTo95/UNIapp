package com.example.uniapp.controllers.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.uniapp.models.markets.MarketTimes;
import com.example.uniapp.views.AboutScreen;

import java.util.ArrayList;
import java.util.List;

public class ChronometerActivity extends AppCompatActivity implements View.OnClickListener {
    private ConstraintLayout startStopBtn;
    private LinearLayout     spaceResetLep;
    private Animation        animChrono;
    private ImageView        anchorChrono;
    private int              offsetAnchor;

    private TextView chronoTV;
    private TextView chronoLapTV;
    private TextView chronoDiffTV;
    private Button   resetBtn;

    private Thread       chronoThread;
    private long         startTime;
    private boolean      isRunning;
    private long         currentChrono;
    private long         currentLapChrono;
    private Double       currentDiffChrono;
    private List<Long>   allChronos;
    private List<Long>   lapChronos;
    private List<Double> diffChronos;

    private RecyclerView         allChronosRV;
    private RvChronometerAdapter rvChronometerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AboutScreen.setupThemeApp(this);
        setContentView(R.layout.activity_chronometer);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setupUIElements();
        updateLapChronoRV();

        startStopBtn.setOnClickListener(this);
        resetBtn.setOnClickListener(this);
    }

    private void setupUIElements() {
        offsetAnchor      = 32;
        currentChrono     = 0;
        currentLapChrono  = 0l;
        currentDiffChrono = 0d;
        allChronos        = new ArrayList<Long>();
        lapChronos        = new ArrayList<Long>();
        diffChronos       = new ArrayList<Double>();
        isRunning         = false;

        startStopBtn      = findViewById(R.id.activity_chronometer_btn_start);
        resetBtn          = findViewById(R.id.activity_chronometer_btn_reset);
        anchorChrono      = findViewById(R.id.activity_chronometer_anchor);
        animChrono        = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.roundingalone);
        spaceResetLep     = findViewById(R.id.activity_chronometer_linear_layout_options);
        chronoTV          = findViewById(R.id.activity_chronometer_chrono);
        chronoLapTV       = findViewById(R.id.activity_chronometer_lap);
        chronoDiffTV      = findViewById(R.id.activity_chronometer_diff);
        allChronosRV      = findViewById(R.id.activity_chronometer_rv);

        anchorChrono.setImageDrawable(getResources().getDrawable((AboutScreen.isNightMode(this)) ? R.drawable.anchor_chrono_dark : R.drawable.anchor_chrono_light));
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
                            currentChrono = System.currentTimeMillis()/10 - startTime + oldTime;
                            chronoTV.setText(MarketTimes.convertLongMilliToTime(currentChrono));
                            updateChronoLap();
                            updateChronoDiff();
                        }
                    });
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            }
        };
    }

    @Override
    public void onClick(View v) {
        if (v.getTag().equals("reset")) reset();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action  = event.getAction();
        int keyCode = event.getKeyCode();

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
            setupThreadChrono(currentChrono);
            chronoThread.start();
            anchorChrono.startAnimation(animChrono);
        } else {
            chronoThread.interrupt();
            currentChrono = MarketTimes.convertTimeToLongMilli(chronoTV.getText().toString());
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
            allChronos   = new ArrayList<Long>();
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
        allChronos.add(currentChrono);
        lapChronos.add(currentLapChrono);
        diffChronos.add(currentDiffChrono);
        updateLapChronoRV();
    }

    private void rotateAnchorStopping(int timeForRoundInS) {
        anchorChrono.setRotation((int) ((((double) currentChrono / 100) * 360 / timeForRoundInS) + offsetAnchor)%360);
    }

    private void updateLapChronoRV() {
        allChronosRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvChronometerAdapter = new RvChronometerAdapter(getApplicationContext(), allChronos, lapChronos, diffChronos);
        allChronosRV.setAdapter(rvChronometerAdapter);
        allChronosRV.setNestedScrollingEnabled(false);
        rvChronometerAdapter.notifyDataSetChanged();
    }

    private void updateChronoLap() {
        if (allChronos.size() != 0) currentLapChrono = currentChrono - allChronos.get(allChronos.size() - 1);
        else currentLapChrono = currentChrono;
        chronoLapTV.setText(MarketTimes.convertLongMilliToTime(currentLapChrono));
    }

    private void updateChronoDiff() {
        long diffChronoLong = 0;

        if (allChronos.size() == 0) diffChronoLong = 0;
        if (allChronos.size() == 1) diffChronoLong = currentLapChrono - (allChronos.get(allChronos.size() - 1) - 0);
        if (allChronos.size() >= 2) diffChronoLong = currentLapChrono - (allChronos.get(allChronos.size() - 1) - allChronos.get(allChronos.size() - 2));

        currentDiffChrono = (double) diffChronoLong / 100;
        chronoDiffTV.setText(MarketTimes.getDiffTime(currentDiffChrono));

        if (currentDiffChrono > 0) chronoDiffTV.setTextColor(getResources().getColor(R.color.redDeep));
        else chronoDiffTV.setTextColor(getResources().getColor(R.color.greenDeep));
    }
}
