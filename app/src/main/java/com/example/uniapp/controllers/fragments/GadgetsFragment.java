package com.example.uniapp.controllers.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.uniapp.controllers.activities.TimerActivity;
import com.example.uniapp.controllers.activities.ConverterActivity;
import com.example.uniapp.R;
import com.example.uniapp.controllers.activities.ChronometerActivity;
import com.example.uniapp.views.AboutScreen;

/*
TODO: STATISTIC --> TOOLS
      CHRONO AVEC TEMPS DE PASSAGE (--> OK) ET SAUVEGARDE DES TEMPS --> KO
      OUTILS DE CONVERSION DES TEMPS EN ZONE DE VITESSE --> OK
 */

public class GadgetsFragment extends Fragment implements View.OnClickListener {
    private View layoutInflater;

    public GadgetsFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutInflater = inflater.inflate(R.layout.fragment_tools, container, false);
        setupUIElements();

        return layoutInflater;
    }

    private void setupUIElements() {
        LinearLayout chronometer = layoutInflater.findViewById(R.id.fragment_gadget_chronometer);
        LinearLayout timer       = layoutInflater.findViewById(R.id.fragment_gadget_timer);
        LinearLayout converter   = layoutInflater.findViewById(R.id.fragment_gadget_converter);

        ImageView chronoLogo     = layoutInflater.findViewById(R.id.fragment_tools_chrono_logo);
        ImageView timerLogo      = layoutInflater.findViewById(R.id.fragment_tools_timer_logo);
        ImageView converterLogo  = layoutInflater.findViewById(R.id.fragment_tools_converter_logo);

        chronoLogo.setImageDrawable(getResources().getDrawable((AboutScreen.isNightMode(getActivity())) ? R.drawable.ic_timer_white_24dp : R.drawable.ic_timer_black_24dp));
        timerLogo.setImageDrawable(getResources().getDrawable((AboutScreen.isNightMode(getActivity())) ? R.drawable.ic_play_arrow_white_24dp : R.drawable.ic_play_arrow_black_24dp));
        converterLogo.setImageDrawable(getResources().getDrawable((AboutScreen.isNightMode(getActivity())) ? R.drawable.ic_sync_alt_white_24dp : R.drawable.ic_sync_alt_black_24dp));
        chronometer.setOnClickListener(this);
        timer.setOnClickListener(this);
        converter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
             if (v.getTag().equals("chronometerBtn")) goToChronometer();
        else if (v.getTag().equals("timerBtn")) goToTimer();
        else if (v.getTag().equals("converterBtn"))   goToConverter();
    }

    private void goToConverter() {
        Intent intent = new Intent(getContext(), ConverterActivity.class);
        startActivity(intent);
    }

    private void goToChronometer() {
        Intent intent = new Intent(getContext(), ChronometerActivity.class);
        startActivity(intent);
    }

    private void goToTimer() {
        Intent intent = new Intent(getContext(), TimerActivity.class);
        startActivity(intent);
    }
}
