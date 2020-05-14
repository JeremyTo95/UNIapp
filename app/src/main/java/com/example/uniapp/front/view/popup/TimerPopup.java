package com.example.uniapp.front.view.popup;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.uniapp.R;
import com.example.uniapp.front.controller.controller_fragment.TimerController;

public class TimerPopup extends Dialog {
    private TimerController controller;

    private ConstraintLayout backgroundLayout;
    private TextView         setsCounter;
    private TextView         timerTV;
    private TextView         stepState;

    public TimerPopup(Activity activity, TimerController controller) {
        super(activity, R.style.Theme_AppCompat_Dialog);
        this.setContentView(R.layout.popup_timer);
        this.controller = controller;

        if (getWindow() != null) {
            this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        controller.onStartPopup(this);
        controller.initTests();
        controller.runStartTimer();
    }

    public void setupUIElements() {
        backgroundLayout = findViewById(R.id.popup_timer_background);
        setsCounter      = findViewById(R.id.popup_timer_setsTV);
        stepState        = findViewById(R.id.popup_timer_state);
        timerTV          = findViewById(R.id.popup_timer_timer);
    }

    public void setupStartTimer() {
        controller.setupTimerLong(500);
        setsCounter.setText(controller.getTitleSet());
        timerTV.setText(controller.getTimerStr());
        stepState.setText("S T A R T I N G");
        updateBckColor(R.color.orangeDeep);
        updateSetsColor();
        updateTimerColor();
        updateStateColor();
    }

    public void setupWorkTimer() {
        controller.setupTimerLong(controller.getTimeWork());
        setsCounter.setText(controller.getTitleSet());
        timerTV.setText(controller.getTimerStr());
        stepState.setText("W O R K");
        updateBckColor(R.color.greenDeep);
        updateSetsColor();
        updateTimerColor();
        updateStateColor();
    }

    public void setupRestTimer() {
        controller.setupTimerLong(controller.getTimeRest());
        setsCounter.setText(controller.getTitleSet());
        timerTV.setText(controller.getTimerStr());
        stepState.setText("R E S T");
        updateBckColor(R.color.blueLight);
        updateSetsColor();
        updateTimerColor();
        updateStateColor();
    }

    @Override
    public boolean dispatchKeyEvent(@NonNull KeyEvent event) {
        int action;
        int keyCode;

        action = event.getAction();
        keyCode = event.getKeyCode();

        if (keyCode == KeyEvent.KEYCODE_BACK && action == KeyEvent.ACTION_DOWN) {
            controller.interruptThread();
            dismiss();
            return true;
        } else return super.dispatchKeyEvent(event);
    }

    private void updateBckColor(int idColor)   { backgroundLayout.setBackgroundColor(getContext().getResources().getColor(idColor)); }
    private void updateSetsColor()  { setsCounter.setTextColor(getContext().getResources().getColor(R.color.textColorDark)); }
    private void updateTimerColor() { timerTV.setTextColor(getContext().getResources().getColor(R.color.textColorDark)); }
    private void updateStateColor() { stepState.setTextColor(getContext().getResources().getColor(R.color.textColorDark)); }

    public void build() {
        show();
        Window window = getWindow();
        if (window != null) window.setLayout(GridLayout.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.MATCH_PARENT);
    }


    public void setTimerTVText(String text) { timerTV.setText(text); }
    public ConstraintLayout getBackgroundLayout() { return backgroundLayout; }

}
