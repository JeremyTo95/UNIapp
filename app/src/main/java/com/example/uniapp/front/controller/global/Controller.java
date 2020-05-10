package com.example.uniapp.front.controller.global;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;

import com.example.uniapp.back.room.RoomDataBase;
import com.example.uniapp.front.view.actvities.MainActivity;

public abstract class Controller {
    private RoomDataBase roomDataBase;
    private Activity activity;
    private Fragment fragment;
    private Context context;

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

    public void lockUI() {
        AboutScreen.lockUI((MainActivity) activity, false);
    }

    public void unlockUI() {
        AboutScreen.unlockUI((MainActivity) activity);
    }
}
