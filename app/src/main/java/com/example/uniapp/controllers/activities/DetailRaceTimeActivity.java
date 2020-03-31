package com.example.uniapp.controllers.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.uniapp.R;
import com.example.uniapp.controllers.fragments.CompetitionDetailFragment;
import com.example.uniapp.views.AboutScreen;

public class DetailRaceTimeActivity extends AppCompatActivity {
    private CompetitionDetailFragment mCompetitionDetailFragment;
    public static final String EXTRA_BUTTON_TAG = "com.example.openclassroom3.controllers.activities.DetailActivities.EXTRA_BUTTON_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race_detail);
        configureAndShowFragment(new CompetitionDetailFragment());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void configureAndShowFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.activity_detail_race, fragment).commit();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) AboutScreen.hideSystemUI(this);
    }
}
