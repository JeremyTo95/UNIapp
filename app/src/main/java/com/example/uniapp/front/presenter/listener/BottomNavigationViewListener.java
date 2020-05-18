package com.example.uniapp.front.presenter.listener;

import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.uniapp.R;
import com.example.uniapp.front.view.fragments.CompetitionsFragment;
import com.example.uniapp.front.view.fragments.GadgetsFragment;
import com.example.uniapp.front.view.fragments.HomeFragment;
import com.example.uniapp.front.view.fragments.SettingsFragment;
import com.example.uniapp.front.view.fragments.TrainingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationViewListener implements BottomNavigationView.OnNavigationItemSelectedListener {
    private AppCompatActivity activity;
    private boolean isActive;

    public BottomNavigationViewListener(AppCompatActivity activity, boolean isActive) {
        this.activity = activity;
        this.isActive = isActive;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (isActive) {
            switch (menuItem.getItemId()) {
                case R.id.navbar_custom_competition_btn:
                    configureAndShowFragment(new CompetitionsFragment());
                    return true;
                case R.id.navbar_custom_training_btn:
                    configureAndShowFragment(new TrainingsFragment());
                    return true;
                case R.id.navbar_custom_home_btn:
                    configureAndShowFragment(new HomeFragment());
                    return true;
                case R.id.navbar_custom_statistic_btn:
                    configureAndShowFragment(new GadgetsFragment());
                    return true;
                case R.id.navbar_custom_settings_btn:
                    configureAndShowFragment(new SettingsFragment());
                    return true;
            }
        } else {
            System.out.println("disable");
            Toast.makeText(activity.getApplicationContext(), "Utiliser les gestes de navigations", Toast.LENGTH_SHORT).show();
            return false;
        }
        return false;
    }

    private void configureAndShowFragment(Fragment fragment) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.activty_main_fragment_layout, fragment).commit();
    }
}
