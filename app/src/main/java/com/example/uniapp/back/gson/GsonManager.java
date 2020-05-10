package com.example.uniapp.back.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonManager {
    public static Gson gsonInstance;

    public static Gson getGsonInstance() {
        if (gsonInstance == null) {
            gsonInstance = new GsonBuilder().setLenient().create();
        }
        return gsonInstance;
    }
}
