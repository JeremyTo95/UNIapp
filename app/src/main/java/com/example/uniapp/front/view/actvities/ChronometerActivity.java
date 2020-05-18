package com.example.uniapp.front.view.actvities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.uniapp.front.presenter.presenter_activity.ChronometerPresenter;
import com.example.uniapp.front.presenter.global.AboutScreen;
import com.example.uniapp.front.model.market.MarketTimes;
import com.example.uniapp.R;
import com.example.uniapp.front.view.recyclerview.RvChronometerAdapter;

public class ChronometerActivity extends AppCompatActivity implements View.OnClickListener {
    private ChronometerPresenter presenter;

    private ConstraintLayout startStopBtn;
    private LinearLayout spaceResetLep;
    private ImageView anchorChrono;

    private TextView chronoTV;
    private TextView chronoLapTV;
    private TextView chronoDiffTV;
    private Button   resetBtn;

    private RecyclerView allChronosRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AboutScreen.setupThemeApp(this);
        setContentView(R.layout.activity_chronometer);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        presenter = new ChronometerPresenter(this);
        presenter.onStart();

        startStopBtn.setOnClickListener(this);
        resetBtn.setOnClickListener(this);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action  = event.getAction();
        int keyCode = event.getKeyCode();

        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP && action == KeyEvent.ACTION_DOWN) {
            presenter.lap();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN && action == KeyEvent.ACTION_DOWN) {
            presenter.startStop();
            return true;
        } else return super.dispatchKeyEvent(event);
    }

    public void setupUIElements() {
        startStopBtn      = findViewById(R.id.activity_chronometer_btn_start);
        resetBtn          = findViewById(R.id.activity_chronometer_btn_reset);
        anchorChrono      = findViewById(R.id.activity_chronometer_anchor);
        spaceResetLep     = findViewById(R.id.activity_chronometer_linear_layout_options);
        chronoTV          = findViewById(R.id.activity_chronometer_chrono);
        chronoLapTV       = findViewById(R.id.activity_chronometer_lap);
        chronoDiffTV      = findViewById(R.id.activity_chronometer_diff);
        allChronosRV      = findViewById(R.id.activity_chronometer_rv);

        anchorChrono.setImageDrawable(getResources().getDrawable((AboutScreen.isNightMode(this)) ? R.drawable.anchor_chrono_dark : R.drawable.anchor_chrono_light));

        initChronoUI();
    }

    @Override
    public void onClick(View v) {
        if (v.getTag().equals("reset")) presenter.reset();
    }

    public void rotateAnchorStopping(int timeForRoundInS) {
        anchorChrono.setRotation(presenter.getAnchorRotation(timeForRoundInS));
    }

    public String getChronoTVText() {
        return chronoTV.getText().toString();
    }

    public void setChronoTVText(String text) {
        chronoTV.setText(text);
    }

    public void setChronoDiffText(double currentDiffChrono) {
        chronoDiffTV.setText(MarketTimes.getDiffTime(currentDiffChrono));
        if (currentDiffChrono > 0) chronoDiffTV.setTextColor(getResources().getColor(R.color.redDeep));
        else chronoDiffTV.setTextColor(getResources().getColor(R.color.greenDeep));
    }

    public void setChronoLapText(long currentLapChrono) {
        chronoLapTV.setText(MarketTimes.convertLongMilliToTime(currentLapChrono));
    }

    public void initChronoUI() {
        chronoTV.setText(R.string.hint_time);
        chronoLapTV.setText(R.string.hint_time);
        chronoDiffTV.setText(R.string.hint_diff);
        chronoDiffTV.setTextColor(getResources().getColor(R.color.greenDeep));
        hideResetButton();
        rotateAnchorStopping(presenter.getPeriodeAnchor());
    }

    public void hideResetButton() {
        spaceResetLep.setVisibility(View.GONE);
    }

    public void showResetButton() {
        spaceResetLep.setVisibility(View.VISIBLE);
    }

    public void updateLapChronoRV() {
        allChronosRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        RvChronometerAdapter rvChronometerAdapter = new RvChronometerAdapter(getApplicationContext(), presenter.getAllChronos(), presenter.getLapChronos(), presenter.getDiffChronos());
        allChronosRV.setAdapter(rvChronometerAdapter);
        allChronosRV.setNestedScrollingEnabled(false);
        rvChronometerAdapter.notifyDataSetChanged();
    }
}
