package com.example.uniapp.front.view.actvities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

//import com.example.uniapp.back.asynctask.ImportPointsFFNTask;
import com.example.uniapp.front.presenter.global.AboutScreen;
import com.example.uniapp.front.presenter.presenter_activity.MainPresenter;
import com.example.uniapp.front.presenter.listener.BottomNavigationViewListener;
import com.example.uniapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private LinearLayout         linearLayoutLoading;
    private TextView             loadingText;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AboutScreen.setupThemeApp(this);
        setContentView(R.layout.activity_main);

        MainPresenter presenter = new MainPresenter(this);
        presenter.onStart();
    }

    public void setupUIElements() {
        linearLayoutLoading  = findViewById(R.id.glb_loading);
        loadingText          = findViewById(R.id.activity_main_loading_textview);
        bottomNavigationView = findViewById(R.id.navbar);
        bottomNavigationView.setSelectedItemId(R.id.navbar_custom_home_btn);
    }

    public void lockUI(boolean isLoading, String loadingTextStr) {
        bottomNavigationView.setEnabled(false);
        bottomNavigationView.setFocusable(false);
        bottomNavigationView.setFocusableInTouchMode(false);
        bottomNavigationView.setClickable(false);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationViewListener(this, false));
        if (isLoading) {
            linearLayoutLoading.setVisibility(View.VISIBLE);
            if (loadingTextStr != null) loadingText.setText(loadingTextStr);
        }
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
