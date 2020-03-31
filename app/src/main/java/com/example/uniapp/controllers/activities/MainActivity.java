package com.example.uniapp.controllers.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.uniapp.R;
import com.example.uniapp.controllers.fragments.CompetitionsFragment;
import com.example.uniapp.controllers.fragments.MainFragment;
import com.example.uniapp.controllers.fragments.SettingsFragment;
import com.example.uniapp.controllers.fragments.StatisticsFragment;
import com.example.uniapp.controllers.fragments.TrainingsFragment;
import com.example.uniapp.models.MarketRaces;
import com.example.uniapp.models.MarketTrainings;
import com.example.uniapp.models.Race;
import com.example.uniapp.models.Training;
import com.example.uniapp.views.AboutScreen;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Race> allRaces;
    private List<Training> allTrainings;
    private BottomNavigationView mBottomNavigationView;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navbar_custom_competition_btn:
                    configureAndShowFragment(new CompetitionsFragment(allRaces));
                    return true;
                case R.id.navbar_custom_training_btn:
                    configureAndShowFragment(new TrainingsFragment());
                    return true;
                case R.id.navbar_custom_home_btn:
                    configureAndShowFragment(new MainFragment());
                    return true;
                case R.id.navbar_custom_statistic_btn:
                    configureAndShowFragment(new StatisticsFragment());
                    return true;
                case R.id.navbar_custom_settings_btn:
                    configureAndShowFragment(new SettingsFragment());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allRaces     = MarketRaces.initAllTimes();
        allTrainings = MarketTrainings.initAllTrainings();

        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.navbar);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        configureAndShowFragment(new MainFragment());
    }

    public void configureAndShowFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.activty_main_fragment_layout, fragment).commit();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) AboutScreen.hideSystemUI(this);
    }
}
