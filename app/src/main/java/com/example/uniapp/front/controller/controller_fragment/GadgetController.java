package com.example.uniapp.front.controller.controller_fragment;

import android.content.Context;
import android.content.Intent;

import com.example.uniapp.R;
import com.example.uniapp.front.controller.global.Controller;
import com.example.uniapp.front.view.actvities.ChronometerActivity;
import com.example.uniapp.front.view.fragments.ConverterFragment;
import com.example.uniapp.front.view.fragments.GadgetsFragment;
import com.example.uniapp.front.view.fragments.TimerFragment;

public class GadgetController extends Controller {
    private GadgetsFragment view;

    public GadgetController(GadgetsFragment view) {
        super(view);
        this.view = view;
    }

    @Override
    public void onStart() {
        super.onStart();
        unlockUI();
        view.setupUIElements();
    }

    public void goToChronometer() {
        Intent intent = new Intent(getContext(), ChronometerActivity.class);
        view.startActivity(intent);
    }

    public void goToTimer() {
        if (view.getActivity() != null) {
            TimerFragment timerFragment = new TimerFragment();
            view.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.activty_main_fragment_layout, timerFragment).addToBackStack(null).commit();
        }
    }

    public void goToConverter() {
        if (view.getActivity() != null) {
            ConverterFragment converterFragment = new ConverterFragment();
            view.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.activty_main_fragment_layout, converterFragment).addToBackStack(null).commit();
        }
    }
}
