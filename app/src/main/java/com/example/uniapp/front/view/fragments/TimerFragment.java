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
import com.example.uniapp.front.presenter.presenter_fragment.TimerPresenter;
import com.example.uniapp.front.presenter.textwatcher.TextWatcherTimer;

public class TimerFragment extends Fragment {
    private View layoutInflater;
    private TimerPresenter presenter;

    private EditText nbSetsET;
    private EditText timeWorkET;
    private EditText timeRestET;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutInflater = inflater.inflate(R.layout.fragment_timer, container, false);
        presenter = new TimerPresenter(this);
        presenter.onStart();

        return layoutInflater;
    }

    public void setupUIElements() {
        nbSetsET           = layoutInflater.findViewById(R.id.activity_timer_set);
        timeWorkET         = layoutInflater.findViewById(R.id.activity_timer_work_time);
        timeRestET         = layoutInflater.findViewById(R.id.activity_timer_rest_time);
        Button startButton = layoutInflater.findViewById(R.id.activity_timer_button);

        timeWorkET.addTextChangedListener(new TextWatcherTimer(timeWorkET));
        timeRestET.addTextChangedListener(new TextWatcherTimer(timeRestET));

        startButton.setOnClickListener(v -> presenter.startTimer());

        presenter.lockUI(null);
    }

    public EditText getNbSetsET() { return nbSetsET; }
    public EditText getTimeWorkET() { return timeWorkET; }
    public EditText getTimeRestET() { return timeRestET; }

}
