package com.example.uniapp.front.controller.global;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.uniapp.back.room.RoomDataBase;
import com.example.uniapp.front.view.actvities.MainActivity;

public abstract class Controller {
    private RoomDataBase roomDataBase;
    private Activity     activity;
    private Fragment     fragment;
    private Context      context;

    public Controller(Activity activity) {
        this.activity     = activity;
        this.context      = activity.getApplicationContext();
        this.roomDataBase = RoomDataBase.getDatabase(context);
    }

    public Controller(Fragment fragment) {
        this.fragment     = fragment;
        this.activity     = fragment.getActivity();
        this.context      = fragment.getContext();
        this.roomDataBase = RoomDataBase.getDatabase(context);
    }

    public void onStart() {}

    public void lockUI(String loadingText) {
        AboutScreen.lockUI((MainActivity) activity, false, loadingText);
    }

    public void unlockUI() {
        AboutScreen.unlockUI((MainActivity) activity);
    }

    public void hideKeybaord(View v) {
        AboutScreen.hideKeybaord(activity, v);
    }

    protected void restartApp() {
        Intent i = new Intent(context, MainActivity.class);
        activity.startActivity(i);
        activity.finish();
    }

    public RoomDataBase getRoomDataBase() { return roomDataBase; }
    public Activity getActivity()         { return activity; }
    public Fragment getFragment()         { return fragment; }
    public Context  getContext()          { return context; }
}
