package com.example.uniapp.controllers.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.uniapp.R;
import com.example.uniapp.controllers.fragments.DetailRaceTimeFragment;

public class DetailRaceTimeActivity extends AppCompatActivity {
    private DetailRaceTimeFragment mDetailRaceTimeFragment;
    public static final String EXTRA_BUTTON_TAG = "com.example.openclassroom3.controllers.activities.DetailActivities.EXTRA_BUTTON_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_race_time);
        configureAndShowFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void configureAndShowFragment() {
        mDetailRaceTimeFragment = (DetailRaceTimeFragment) getSupportFragmentManager().findFragmentById(R.id.activity_detail_race_time);

        if (mDetailRaceTimeFragment == null) {
            mDetailRaceTimeFragment = new DetailRaceTimeFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.activity_detail_race_time, mDetailRaceTimeFragment).commit();
        }
    }

    /*  Mise en place de l'application en pleine écran pour un meilleur rendu  */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) hideSystemUI();
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
        );
    }

    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

}
