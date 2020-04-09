package com.example.uniapp.utils;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.SortedList;

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
    private WeakReference<Activity> weakReference;
    private LinearLayout linearLayout;

    public ImportPointsFFNTask(Activity activity, LinearLayout linearLayout) {
        weakReference = new WeakReference<>(activity);
        this.linearLayout = linearLayout;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Log.e("IN BACKGROUND", "start");
        MainActivity.appDataBase.pointFFNDAO().deleteAllPointFFN();
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(AppDataBase.URL_DATA).addConverterFactory(GsonConverterFactory.create(gson)).build();
        PointFFNAPI pointFFNAPI = retrofit.create(PointFFNAPI.class);
        Call<List<PointFFN>> call = pointFFNAPI.getResponsePointsFFN();

        call.enqueue(new Callback<List<PointFFN>>() {
            @Override
            public void onResponse(Call<List<PointFFN>> call, Response<List<PointFFN>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<PointFFN> pointFFNList = response.body();
                    System.out.println("nbPointsFFN to load : " + pointFFNList.size());
                    for (int i = 0; i < pointFFNList.size(); i++) {
                        MainActivity.appDataBase.pointFFNDAO().insertPointFFN(pointFFNList.get(i));
                    }
                    linearLayout.setVisibility(View.GONE);
                    System.out.println("nbPointsFFN loaded : " + MainActivity.appDataBase.pointFFNDAO().getNb());
                }
            }

            @Override
            public void onFailure(Call<List<PointFFN>> call, Throwable t) {
                Log.e("ERREUR", "didn't connect to pointFFN API");
            }
        });

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        linearLayout.setVisibility(View.VISIBLE);
        Log.e("IN PRE EXECUTE", "start");
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Activity activity = weakReference.get();
        if(activity == null) {
            return;
        }
        Log.e("IN POST EXECUTE", "start");
    }
}