package com.example.uniapp.front.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.uniapp.R;
import com.example.uniapp.front.controller.controller_fragment.TimerController;
import com.example.uniapp.front.controller.textwatcher.TextWatcherTimer;

public class TimerFragment extends Fragment {
    private View layoutInflater;
    private TimerController controller;

    private EditText nbSetsET;
    private EditText timeWorkET;
    private EditText timeRestET;
    private Button startButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutInflater = inflater.inflate(R.layout.fragment_timer, container, false);
        controller = new TimerController(this);
        controller.onStart();

        return layoutInflater;
    }

    public void setupUIElements() {
        nbSetsET    = (EditText) layoutInflater.findViewById(R.id.activity_timer_set);
        timeWorkET  = (EditText) layoutInflater.findViewById(R.id.activity_timer_work_time);
        timeRestET  = (EditText) layoutInflater.findViewById(R.id.activity_timer_rest_time);
        startButton = (Button)   layoutInflater.findViewById(R.id.activity_timer_button);

        timeWorkET.addTextChangedListener(new TextWatcherTimer(timeWorkET));
        timeRestET.addTextChangedListener(new TextWatcherTimer(timeRestET));

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { controller.startTimer(); }
        });

        controller.lockUI(null);
    }

    public EditText getNbSetsET() { return nbSetsET; }
    public EditText getTimeWorkET() { return timeWorkET; }
    public EditText getTimeRestET() { return timeRestET; }

}
