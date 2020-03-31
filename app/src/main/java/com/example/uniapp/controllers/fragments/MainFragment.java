package com.example.uniapp.controllers.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.uniapp.R;
import com.example.uniapp.controllers.activities.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainFragment extends Fragment {
    // TODO: Proposer les outils tels que : Convertisseur de temps en zone
    public MainFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_main, container, false);
        return result;
    }
}
