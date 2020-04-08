package com.example.uniapp.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.uniapp.controllers.activities.MainActivity;
import com.example.uniapp.models.database.AppDataBase;
import com.example.uniapp.models.database.dao.pointFFN.PointFFN;
import com.example.uniapp.models.database.dao.pointFFN.PointFFNAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImportPointsFFNTask extends AsyncTask<Void, Void, Void> {
    private ProgressBar progressBar;

    public ImportPointsFFNTask(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Log.e("IN BACKGROUND", "start");
        //loadPointsFFN();
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.e("IN PRE EXECUTE", "start");
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.e("IN POST EXECUTE", "start");
        progressBar.setVisibility(View.GONE);
    }

    private void loadPointsFFN() {
        if (MainActivity.appDataBase.pointFFNDAO().getNbPoint() != 0) {
            MainActivity.pointFFNList = MainActivity.appDataBase.pointFFNDAO().getAllPoints();
        } else {
            PointFFN.makePointFFNApiCall();
        }
    }
}
