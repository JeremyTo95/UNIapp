package com.example.uniapp.controllers.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.uniapp.R;

/*
TODO: STATISTIC --> TOOLS
      CHRONO AVEC TEMPS DE PASSAGE ET SAUVEGARDE DES TEMPS
      OUTILS DE CONVERSION DES TEMPS EN ZONE DE VITESSE
 */

public class StatisticsFragment extends Fragment {
    private View layoutInflater;

    public StatisticsFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutInflater = inflater.inflate(R.layout.fragment_tools, container, false);

        return layoutInflater;
    }
}
