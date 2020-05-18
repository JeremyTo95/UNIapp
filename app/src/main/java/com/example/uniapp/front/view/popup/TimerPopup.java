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
import com.example.uniapp.front.presenter.presenter_fragment.TimerPresenter;

public class TimerPopup extends Dialog {
    private TimerPresenter presenter;

    private ConstraintLayout backgroundLayout;
    private TextView         setsCounter;
    private TextView         timerTV;
    private TextView         stepState;

    public TimerPopup(Activity activity, TimerPresenter presenter) {
        super(activity, R.style.Theme_AppCompat_Dialog);
        this.setContentView(R.layout.popup_timer);
        this.presenter = presenter;

        if (getWindow() != null) {
            this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        presenter.onStartPopup(this);
        presenter.initTests();
        presenter.runStartTimer();
    }

    public void setupUIElements() {
        backgroundLayout = findViewById(R.id.popup_timer_background);
        setsCounter      = findViewById(R.id.popup_timer_setsTV);
        stepState        = findViewById(R.id.popup_timer_state);
        timerTV          = findViewById(R.id.popup_timer_timer);
    }

    public void setupStartTimer() {
        presenter.setupTimerLong(500);
        setsCounter.setText(presenter.getTitleSet());
        timerTV.setText(presenter.getTimerStr());
        stepState.setText("S T A R T I N G");
        updateBckColor(R.color.orangeDeep);
        updateSetsColor();
        updateTimerColor();
        updateStateColor();
    }

    public void setupWorkTimer() {
        presenter.setupTimerLong(presenter.getTimeWork());
        setsCounter.setText(presenter.getTitleSet());
        timerTV.setText(presenter.getTimerStr());
        stepState.setText("W O R K");
        updateBckColor(R.color.greenDeep);
        updateSetsColor();
        updateTimerColor();
        updateStateColor();
    }

    public void setupRestTimer() {
        presenter.setupTimerLong(presenter.getTimeRest());
        setsCounter.setText(presenter.getTitleSet());
        timerTV.setText(presenter.getTimerStr());
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
            presenter.interruptThread();
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
