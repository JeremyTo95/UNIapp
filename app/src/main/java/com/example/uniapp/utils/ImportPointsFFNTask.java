package com.example.uniapp.utils;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.example.uniapp.controllers.activities.MainActivity;
import com.example.uniapp.models.database.AppDataBase;
import com.example.uniapp.models.database.dao.pointFFN.PointFFN;
import com.example.uniapp.models.database.dao.pointFFN.PointFFNAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImportPointsFFNTask extends AsyncTask<Void, Void, Void> {
    private WeakReference<Activity> weakReference;


    public ImportPointsFFNTask(Activity activity) {
        weakReference = new WeakReference<>(activity);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Log.e("IN BACKGROUND", "start");

        MainActivity.pointFFNRepository.deleteAll();
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(AppDataBase.URL_DATA).addConverterFactory(GsonConverterFactory.create(gson)).build();
        PointFFNAPI pointFFNAPI = retrofit.create(PointFFNAPI.class);
        Call<List<PointFFN>> call = pointFFNAPI.getResponsePointsFFN();

        call.enqueue(new retrofit2.Callback<List<PointFFN>>() {
            @Override
            public void onResponse(Call<List<PointFFN>> call, Response<List<PointFFN>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<PointFFN> pointFFNList = response.body();
                    System.out.println("nbPointsFFN to load : " + pointFFNList.size());
                    for (int i = 0; i < pointFFNList.size(); i++) {
                        MainActivity.pointFFNRepository.insert(pointFFNList.get(i));
                    }
                    System.out.println("nbPointsFFN loaded : " + MainActivity.pointFFNRepository.getNbElement());
                }
            }

            @Override
            public void onFailure(Call<List<PointFFN>> call, Throwable t) { Log.e("ERREUR","didn't connect to pointFFN API"); }
        });

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
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