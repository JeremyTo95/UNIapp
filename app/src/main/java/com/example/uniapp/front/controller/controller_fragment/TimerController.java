package com.example.uniapp.front.controller.controller_fragment;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;

import com.example.uniapp.R;
import com.example.uniapp.front.controller.global.Controller;
import com.example.uniapp.front.model.market.MarketTimes;
import com.example.uniapp.front.view.fragments.TimerFragment;
import com.example.uniapp.front.view.popup.TimerPopup;

public class TimerController extends Controller {
    private TimerFragment view;
    private TimerPopup timerPopup;
    private Context context;

    private String nbSetsStr;
    private String timeWorkStr;
    private String timeRestStr;

    public TimerController(TimerFragment view) {
        super(view);
        this.view    = view;
        this.context = view.getContext();
    }

    @Override
    public void onStart() {
        super.onStart();
        view.setupUIElements();
    }

    public void startTimer() {
        if (checkStartTimer()) {
            nbSetsStr   = view.getNbSetsET().getText().toString();
            timeWorkStr = view.getTimeWorkET().getText().toString();
            timeRestStr = view.getTimeRestET().getText().toString();
            timerPopup = new TimerPopup(view.getActivity(), this);
            timerPopup.build();
        }
    }

    private boolean checkStartTimer() {
        if (checkNbSet() && checkWorkTime() && checkRestTime()) {
            return true;
        } else {
            if (!checkNbSet())    view.getNbSetsET().setHintTextColor(view.getResources().getColor(R.color.redDeep));
            if (!checkWorkTime()) view.getTimeWorkET().setHintTextColor(view.getResources().getColor(R.color.redDeep));
            if (!checkRestTime()) view.getTimeRestET().setHintTextColor(view.getResources().getColor(R.color.redDeep));
            return false;
        }
    }

    private boolean checkNbSet() {    return view.getNbSetsET().getText().length() != 0; }
    private boolean checkWorkTime() { return view.getTimeWorkET().getText().length() != 0; }
    private boolean checkRestTime() { return view.getTimeRestET().getText().length() != 0; }


    /*
    POPUP PART
     */
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

    public void onStartPopup(TimerPopup timerPopup) {
        nbSets      = Integer.parseInt(nbSetsStr);
        timeWork    = MarketTimes.convertTimerToLong(timeWorkStr);
        timeRest    = MarketTimes.convertTimerToLong(timeRestStr);

        timerIndex  = 0;
        nbSetsIndex = 0;
        oldTimer    = 0L;
        isRunning   = true;
        soundTic    = MediaPlayer.create(view.getActivity().getApplicationContext(), R.raw.tic);
        soundStart  = MediaPlayer.create(view.getActivity().getApplicationContext(), R.raw.start);

        this.timerPopup = timerPopup;
        this.timerPopup.setupUIElements();
    }

    public void initTests() {
        isTic1 = false;
        isTic2 = false;
        isTic3 = false;
        isStart = false;
    }

    private void nextTimer() {
        if ((timerIndex)%2 == 0) nbSetsIndex++;
        if (nbSetsIndex != nbSets+1) {
            if (timerIndex == 0)      runWorkTimer();
            else if (timerIndex == 1) runRestTimer();
        } else {
            timerPopup.dismiss();
        }
        timerIndex = (timerIndex + 1)%2;
    }

    public void runStartTimer() {
        timerPopup.setupStartTimer();
        setupTimerThread();
        timerThread.start();
    }

    private void runWorkTimer() {
        timerPopup.setupWorkTimer();
        if (timer != 0) {
            setupTimerThread();
            timerThread.start();
        }
    }

    private void runRestTimer() {
        timerPopup.setupRestTimer();
        setupTimerThread();
        timerThread.start();
    }

    public void setupTimerLong(long timeToAddInMs) {
        oldTimer       = 0L;
        timer          = timeToAddInMs;
        timerForThread = System.currentTimeMillis()/10 + (timeToAddInMs);
    }

    private void setupTimerThread() {
        timerThread = new Thread() {
            @Override
            public void run() {
            super.run();
            try {
                while (!isInterrupted()) {
                    Thread.sleep(10);
                    view.getActivity().runOnUiThread(new Runnable() {
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
                                timerPopup.setTimerTVText("00:00");
                                initTests();
                                nextTimer();
                            } else timerPopup.setTimerTVText(MarketTimes.converLongToTimer(currentTimer/100));

                            timerPopup.getBackgroundLayout().setOnClickListener(new View.OnClickListener() {
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

    public void interruptThread() { timerThread.interrupt(); }
    public String getTitleSet() { return "S E T S  :  " + nbSetsIndex + " / " + nbSets; }
    public String getTimerStr() { return MarketTimes.converLongToTimer(timer); }
    public Long getTimeWork() { return timeWork * 100; }
    public Long getTimeRest() { return timeRest * 100; }
}
