package com.example.uniapp.back.gson;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonManager {
    private static Gson gsonInstance;

    public static Gson getGsonInstance() {
        if (gsonInstance == null) {
            Log.i("Gson", "new Gson is loading");
            gsonInstance = new GsonBuilder().setLenient().create();
        }
        return gsonInstance;
    }
}
