package com.example.uniapp.controllers.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.uniapp.R;
import com.example.uniapp.controllers.fragments.TrainingDetailFragment;

public class DetailTrainingActivity extends AppCompatActivity {
    private TrainingDetailFragment trainingDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_detail);
        configureAndShowFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void configureAndShowFragment() {
        trainingDetailFragment = (TrainingDetailFragment) getSupportFragmentManager().findFragmentById(R.id.activity_detail_training);

        if (trainingDetailFragment == null) {
            trainingDetailFragment = new TrainingDetailFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.activity_detail_training, trainingDetailFragment).commit();
        }
    }

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
