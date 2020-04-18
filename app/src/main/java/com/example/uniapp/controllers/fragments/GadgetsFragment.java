package com.example.uniapp.controllers.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.uniapp.controllers.activities.ConverterActivity;
import com.example.uniapp.R;
import com.example.uniapp.controllers.activities.ChronometerActivity;

/*
TODO: STATISTIC --> TOOLS
      CHRONO AVEC TEMPS DE PASSAGE ET SAUVEGARDE DES TEMPS
      OUTILS DE CONVERSION DES TEMPS EN ZONE DE VITESSE
 */

public class GadgetsFragment extends Fragment implements View.OnClickListener {
    private View layoutInflater;

    private LinearLayout chronometer;
    private LinearLayout converter;

    public GadgetsFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutInflater = inflater.inflate(R.layout.fragment_tools, container, false);

        setupUIElements();

        return layoutInflater;
    }

    private void setupUIElements() {
        chronometer = layoutInflater.findViewById(R.id.fragment_gadget_chronometer);
        converter   = layoutInflater.findViewById(R.id.fragment_gadget_converter);
        chronometer.setOnClickListener(this);
        converter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
             if (v.getTag().equals("chronometerBtn")) goToChronometer();
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
}
