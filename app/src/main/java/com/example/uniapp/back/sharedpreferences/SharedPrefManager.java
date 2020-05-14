package com.example.uniapp.back.sharedpreferences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.uniapp.back.gson.GsonManager;
import com.example.uniapp.front.model.data.User;

public class SharedPrefManager {
    private static SharedPrefManager sharedPrefManager;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private SharedPrefManager() {
        sharedPrefManager = new SharedPrefManager();
    }

    @SuppressLint("CommitPrefEdits")
    private static SharedPrefManager getSharedPref(Context context) {
        if (sharedPreferences == null) {
            Log.i("SharedPred", "new shared pref is loading");
            sharedPreferences = context.getSharedPreferences("quicksave", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
        return sharedPrefManager;
    }

    public static void savedThemeMode(int state) {
        editor.putInt("DarkMode", state);
        editor.commit();
    }

    public static int loadThemeMode(Context context) {
        sharedPrefManager = getSharedPref(context);
        return sharedPreferences.getInt("DarkMode", 0);
    }

    public static void savedUser(User user) {
        editor.putString("user", GsonManager.getGsonInstance().toJson(user));
        editor.commit();
    }

    public static User getUser(Context context) {
        sharedPrefManager = getSharedPref(context);
        String userJSON = sharedPreferences.getString("user", "null");
        if (userJSON.equals("null")) return null;
        else return GsonManager.getGsonInstance().fromJson(userJSON, User.class);
    }

    public static void clearUser(Context context) {
        sharedPrefManager = getSharedPref(context);
        editor.remove("user");
        editor.commit();
    }
}
