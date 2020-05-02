package com.example.uniapp.views.popup;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.uniapp.R;
import com.example.uniapp.models.MarketTimes;

public class TimerPopup extends Dialog {
    private Activity activity;
    private Thread timerThread;
    private MediaPlayer soundTic;
    private MediaPlayer soundStart;

    private int nbSets;
    private Long timeWork;
    private Long timeRest;
    private Long timer;
    private Long timerForThread;
    private Long currentTimer;
    private Long oldTimer;
    private int  timerIndex;
    private int  nbSetsIndex;
    private boolean isRunning;
    private boolean isTic1;
    private boolean isTic2;
    private boolean isTic3;
    private boolean isStart;

    private ConstraintLayout backgroundLayout;
    private TextView         setsCounter;
    private TextView         timerTV;
    private TextView         stepState;

    public TimerPopup(Activity activity, int nbSets, String timeWork, String timeRest) {
        super(activity, R.style.Theme_AppCompat_Dialog);
        setContentView(R.layout.popup_timer);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        this.activity    = activity;
        this.nbSets      = nbSets;
        this.timeWork    = MarketTimes.convertTimerToLong(timeWork);
        this.timeRest    = MarketTimes.convertTimerToLong(timeRest);
        backgroundLayout = findViewById(R.id.popup_timer_background);
        setsCounter      = findViewById(R.id.popup_timer_setsTV);
        stepState        = findViewById(R.id.popup_timer_state);
        timerTV          = findViewById(R.id.popup_timer_timer);
        timerIndex       = 0;
        nbSetsIndex      = 0;
        oldTimer         = 0L;
        isRunning        = true;
        soundTic         = MediaPlayer.create(activity.getApplicationContext(), R.raw.tic);
        soundStart       = MediaPlayer.create(activity.getApplicationContext(), R.raw.start);
        initTests();

        runStartTimer();
    }

    private void nextTimer() {
        if ((timerIndex)%2 == 0) nbSetsIndex++;
        if (nbSetsIndex != nbSets+1) {
            if (timerIndex == 0)      runWorkTimer();
            else if (timerIndex == 1) runRestTimer();
        } else {
            // soundStop.start();
            dismiss();
        }
        timerIndex = (timerIndex + 1)%2;
    }

    private void runStartTimer() {
        setupStartTimer();
        setupTimerThread();
        timerThread.start();
    }

    private void runWorkTimer() {
        setupWorkTimer();
        if (timer != 0) {
            setupTimerThread();
            timerThread.start();
        }
    }

    private void runRestTimer() {
        setupRestTimer();
        setupTimerThread();
        timerThread.start();
    }

    private void setupTimerLong(long timeToAddInMs) {
        oldTimer       = 0L;
        timer          = timeToAddInMs;
        timerForThread = System.currentTimeMillis()/10 + (timeToAddInMs);
    }

    private void setupStartTimer() {
        setupTimerLong(500);
        setsCounter.setText("S E T S  :  " + nbSetsIndex + " / " + nbSets);
        timerTV.setText(MarketTimes.convertLongMilliToTime(timer));
        stepState.setText("S T A R T I N G");
        updateBckColor(R.color.orangeDeep);
        updateSetsColor(R.color.colorText);
        updateTimerColor(R.color.colorText);
        updateStateColor(R.color.colorText);
    }

    private void setupWorkTimer() {
        setupTimerLong(timeWork * 100);
        setsCounter.setText("S E T S  :  " + nbSetsIndex + " / " + nbSets);
        timerTV.setText(MarketTimes.converLongToTimer(timer));
        stepState.setText("W O R K");
        updateBckColor(R.color.greenDeep);
        updateSetsColor(R.color.colorText);
        updateTimerColor(R.color.colorText);
        updateStateColor(R.color.colorText);
    }

    private void setupRestTimer() {
        setupTimerLong(timeRest * 100);
        setsCounter.setText("S E T S  :  " + nbSetsIndex + " / " + nbSets);
        timerTV.setText(MarketTimes.converLongToTimer(timer));
        stepState.setText("R E S T");
        updateBckColor(R.color.blueLight);
        updateSetsColor(R.color.colorText);
        updateTimerColor(R.color.colorText);
        updateStateColor(R.color.colorText);
    }

    private void setupTimerThread() {
        //setupStartTimer();
        timerThread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(10);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                currentTimer = timerForThread - System.currentTimeMillis()/10;
                                System.out.println("time : " + currentTimer);
                                if (currentTimer >= 390 && currentTimer <= 399 && !isTic3) {
                                    soundTic.start();
                                    isTic3 = true;
                                }
                                if (currentTimer >= 290 && currentTimer <= 299 && !isTic2) {
                                    soundTic.start();
                                    isTic2 = true;
                                }
                                if (currentTimer >= 190 && currentTimer <= 199 && !isTic1) {
                                    soundTic.start();
                                    isTic1 = true;
                                }
                                if (currentTimer >= 90 && currentTimer <= 99 && !isStart) {
                                    soundStart.start();
                                    isStart = true;
                                }
                                if (currentTimer < 0) {
                                    interrupt();
                                    timerTV.setText("00:00");
                                    initTests();
                                    nextTimer();
                                } else timerTV.setText(MarketTimes.converLongToTimer(currentTimer/100));

                                backgroundLayout.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        manageStartStop();
                                    }
                                });
                            }
                        });
                    }
                } catch (Exception ex) { System.out.println(ex.getMessage()); }
            }
        };
    }

    @Override
    public boolean dispatchKeyEvent(@NonNull KeyEvent event) {
        int action;
        int keyCode;

        action = event.getAction();
        keyCode = event.getKeyCode();

        if (keyCode == KeyEvent.KEYCODE_BACK && action == KeyEvent.ACTION_DOWN) {
            timerThread.interrupt();
            dismiss();
            return true;
        } else return super.dispatchKeyEvent(event);
    }

    private void updateBckColor(int idColor) {
        backgroundLayout.setBackgroundColor(getContext().getResources().getColor(idColor));
    }

    private void updateSetsColor(int idColor) {
        setsCounter.setTextColor(getContext().getResources().getColor(idColor));
    }

    private void updateTimerColor(int idColor) {
        timerTV.setTextColor(getContext().getResources().getColor(idColor));
    }

    private void updateStateColor(int idColor) {
        stepState.setTextColor(getContext().getResources().getColor(idColor));
    }

    public void build() {
        show();
        Window window = getWindow();
        if (window != null) window.setLayout(GridLayout.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.MATCH_PARENT);
    }

    private void initTests() {
        isTic1 = false;
        isTic2 = false;
        isTic3 = false;
        isStart = false;
    }

    private void manageStartStop() {
        isRunning = !isRunning;
        if (!isRunning) {
            timerThread.interrupt();
            oldTimer = timerForThread - System.currentTimeMillis()/10;
            System.out.println("end");
        } else {
            timer = oldTimer;
            timerForThread = System.currentTimeMillis()/10 + (oldTimer);
            System.out.println("timer : " + timer + " | timerForThread : " + timerForThread);
            setupTimerThread();
            timerThread.start();
        }
    }
}
