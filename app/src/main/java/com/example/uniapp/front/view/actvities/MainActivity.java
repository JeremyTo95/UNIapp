package com.example.uniapp.front.view.actvities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

//import com.example.uniapp.front.controller.asynctask.ImportPointsFFNTask;
import com.example.uniapp.front.controller.global.AboutScreen;
import com.example.uniapp.front.controller.controller_activity.MainController;
import com.example.uniapp.front.controller.listener.BottomNavigationViewListener;
import com.example.uniapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private MainController       controller;
    private LinearLayout         linearLayoutLoading;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AboutScreen.setupThemeApp(this);
        setContentView(R.layout.activity_main);

        controller = new MainController(this);
        controller.onStart();
    }

    public void setupUIElements() {
        linearLayoutLoading  = findViewById(R.id.glb_loading);
        bottomNavigationView = findViewById(R.id.navbar);
        bottomNavigationView.setSelectedItemId(R.id.navbar_custom_home_btn);
    }

    public void lockUI(boolean isLoading) {
        bottomNavigationView.setEnabled(false);
        bottomNavigationView.setFocusable(false);
        bottomNavigationView.setFocusableInTouchMode(false);
        bottomNavigationView.setClickable(false);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationViewListener(this, false));
        if (isLoading) linearLayoutLoading.setVisibility(View.VISIBLE);
    }

    public void unlockUI() {
        bottomNavigationView.setEnabled(true);
        bottomNavigationView.setFocusable(true);
        bottomNavigationView.setFocusableInTouchMode(true);
        bottomNavigationView.setClickable(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationViewListener(this, true));
        linearLayoutLoading.setVisibility(View.GONE);
    }
}
