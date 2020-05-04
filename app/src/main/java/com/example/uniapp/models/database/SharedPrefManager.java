package com.example.uniapp.models.database;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private SharedPreferences sharedPreferences;
    public SharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences("quicksave", Context.MODE_PRIVATE);
    }

    public void savedThemeMode(int state) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("DarkMode", state);
        editor.commit();
    }

    public int loadThemeMode() {
        return sharedPreferences.getInt("DarkMode", 0);
    }
}
