/*package com.example.uniapp.controllers.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

import com.example.uniapp.R;
import com.example.uniapp.controllers.fragments.TrainingDetailFragment;
import com.example.uniapp.views.AboutScreen;

public class DetailTrainingActivity extends AppCompatActivity {
    private TrainingDetailFragment trainingDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_detail);
        configureAndShowFragment(new TrainingDetailFragment());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void configureAndShowFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.activity_detail_training, fragment).commit();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) AboutScreen.hideSystemUI(this);
    }
}*/
