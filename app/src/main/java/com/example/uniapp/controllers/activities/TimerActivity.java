package com.example.uniapp.controllers.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.uniapp.R;
import com.example.uniapp.views.AboutScreen;
import com.example.uniapp.views.popup.TimerPopup;

public class TimerActivity extends AppCompatActivity {
    private Context context;

    private EditText nbSetsET;
    private EditText timeWorkET;
    private EditText timeRestET;
    private Button startButton;

    private String nbSetsStr;
    private String timeWorkStr;
    private String timeRestStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AboutScreen.setupThemeApp(this);
        setContentView(R.layout.activity_timer);

        setupUIElements();
        updateWorkTimeET();
        updateRestTimeET();
    }

    private void setupUIElements() {
        context     = getApplicationContext();
        nbSetsET    = (EditText) findViewById(R.id.activity_timer_set);
        timeWorkET  = (EditText) findViewById(R.id.activity_timer_work_time);
        timeRestET  = (EditText) findViewById(R.id.activity_timer_rest_time);
        startButton = (Button)   findViewById(R.id.activity_timer_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { startTimer(); }
        });
    }

    private void updateWorkTimeET() {
        timeWorkET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                timeWorkET.removeTextChangedListener(this);
                timeWorkStr = timeWorkET.getText().toString().replaceAll(":", "");
                if (timeWorkStr.length() > 2) timeWorkStr = new StringBuilder(timeWorkStr).insert(timeWorkStr.length() - 2, ":").toString();
                timeWorkET.setText(timeWorkStr);
                timeWorkET.setSelection(timeWorkStr.length());
                timeWorkET.addTextChangedListener(this);
            }
        });
    }

    private void updateRestTimeET() {
        timeRestET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                timeRestET.removeTextChangedListener(this);
                timeRestStr = timeRestET.getText().toString().replaceAll(":", "");
                if (timeRestStr.length() > 2) timeRestStr = new StringBuilder(timeRestStr).insert(timeRestStr.length() - 2, ":").toString();
                timeRestET.setText(timeRestStr);
                timeRestET.setSelection(timeRestStr.length());
                timeRestET.addTextChangedListener(this);
            }
        });
    }

    private void startTimer() {
        if (checkStartTimer()) {
            nbSetsStr   = nbSetsET.getText().toString();
            timeWorkStr = timeWorkET.getText().toString();
            timeRestStr = timeRestET.getText().toString();
            System.out.println("Start Timer : \nsets : " + nbSetsStr + " | timeWork : " + timeWorkStr + " | timeRest : " + timeRestStr);
            TimerPopup timerPopup = new TimerPopup(this, Integer.parseInt(nbSetsStr), timeWorkStr, timeRestStr);
            timerPopup.build();
        }
    }

    private boolean checkStartTimer() {
        if (checkNbSet() && checkWorkTime() && checkRestTime()) {
            return true;
        } else {
            if (!checkNbSet()) nbSetsET.setHintTextColor(getResources().getColor(R.color.redDeep));
            if (!checkWorkTime()) timeWorkET.setHintTextColor(getResources().getColor(R.color.redDeep));
            if (!checkRestTime()) timeRestET.setHintTextColor(getResources().getColor(R.color.redDeep));
            return false;
        }
    }

    private boolean checkNbSet() { return nbSetsET.getText().length() != 0; }
    private boolean checkWorkTime() { return timeWorkET.getText().length() != 0; }
    private boolean checkRestTime() { return timeRestET.getText().length() != 0; }

    /*@Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) AboutScreen.hideNavigationBar(this);
    }*/
}
