package com.example.uniapp.models.database;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static SharedPrefManager sharedPrefManager;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public SharedPrefManager() {
        //sharedPrefManager = context.getSharedPreferences("quicksave", Context.MODE_PRIVATE);
        sharedPrefManager = new SharedPrefManager();
    }

    public static SharedPrefManager getSharedPref(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("quicksave", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
        return sharedPrefManager;
    }

    public static void savedThemeMode(int state) {
        //SharedPreferences.Editor editor = sharedPrefManager.edit();
        editor.putInt("DarkMode", state);
        editor.commit();
    }

    public static int loadThemeMode() {
        return sharedPreferences.getInt("DarkMode", 0);
    }
}
