package com.example.uniapp.front.controller.global;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uniapp.R;
import com.example.uniapp.back.sharedpreferences.SharedPrefManager;
import com.example.uniapp.front.view.actvities.MainActivity;

@SuppressLint("Registered")
public class AboutScreen extends AppCompatActivity {
    public static int getWidth(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public static int getHeight(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    public static int getColorByThemeAttr(Context context, int attr, int defaultColor) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        boolean got = theme.resolveAttribute(attr, typedValue, true);
        return got ? typedValue.data : defaultColor;
    }

    public static boolean isNightMode(Activity activity) {
        System.out.println(SharedPrefManager.loadThemeMode(activity.getApplicationContext()));
        if (SharedPrefManager.loadThemeMode(activity.getApplicationContext()) == 1) return true;
        else if (SharedPrefManager.loadThemeMode(activity.getApplicationContext()) == 2) return false;
        else return SharedPrefManager.loadThemeMode(activity.getApplicationContext()) == 0 && (activity.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
    }

    public static void setupDefaultNightMode(Activity activity) {
        int currentNightMode = activity.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                activity.setTheme(R.style.LightTheme);
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                activity.setTheme(R.style.DarkTheme);
                break;
        }
    }

    public static void setupThemeApp(Activity activity) {
        if (SharedPrefManager.loadThemeMode(activity.getApplicationContext()) == 0) AboutScreen.setupDefaultNightMode(activity);
        else if (SharedPrefManager.loadThemeMode(activity.getApplicationContext()) == 1) activity.setTheme(R.style.DarkTheme);
        else activity.setTheme(R.style.LightTheme);
    }

    public static void lockUI(MainActivity activity, boolean isAnimation, String loadingText) {
        activity.lockUI(isAnimation, loadingText);
    }

    public static void unlockUI(MainActivity activity) {
        activity.unlockUI();
    }

    public static void hideKeybaord(Activity activity, View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }
}
