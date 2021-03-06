package com.example.uniapp.front.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.uniapp.front.presenter.global.AboutScreen;
import com.example.uniapp.R;
import com.example.uniapp.front.presenter.presenter_fragment.GadgetPresenter;

public class GadgetsFragment extends Fragment implements View.OnClickListener {
    private View layoutInflater;
    private GadgetPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutInflater = inflater.inflate(R.layout.fragment_gadget, container, false);

        presenter = new GadgetPresenter(this);
        presenter.onStart();

        return layoutInflater;
    }

    public void setupUIElements() {
        LinearLayout chronometer = layoutInflater.findViewById(R.id.fragment_gadget_chronometer);
        LinearLayout timer       = layoutInflater.findViewById(R.id.fragment_gadget_timer);
        LinearLayout converter   = layoutInflater.findViewById(R.id.fragment_gadget_converter);

        ImageView chronoLogo     = layoutInflater.findViewById(R.id.fragment_tools_chrono_logo);
        ImageView timerLogo      = layoutInflater.findViewById(R.id.fragment_tools_timer_logo);
        ImageView converterLogo  = layoutInflater.findViewById(R.id.fragment_tools_converter_logo);

        if (getActivity() != null) {
            chronoLogo.setImageDrawable(getResources().getDrawable((AboutScreen.isNightMode(getActivity())) ? R.drawable.ic_timer_white_24dp : R.drawable.ic_timer_black_24dp));
            timerLogo.setImageDrawable(getResources().getDrawable((AboutScreen.isNightMode(getActivity())) ? R.drawable.ic_play_arrow_white_24dp : R.drawable.ic_play_arrow_black_24dp));
            converterLogo.setImageDrawable(getResources().getDrawable((AboutScreen.isNightMode(getActivity())) ? R.drawable.ic_sync_alt_white_24dp : R.drawable.ic_sync_alt_black_24dp));
        }
        chronometer.setOnClickListener(this);
        timer.setOnClickListener(this);
        converter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
             if (v.getTag().equals("chronometerBtn")) presenter.goToChronometer();
        else if (v.getTag().equals("timerBtn"))       presenter.goToTimer();
        else if (v.getTag().equals("converterBtn"))   presenter.goToConverter();
    }
}
