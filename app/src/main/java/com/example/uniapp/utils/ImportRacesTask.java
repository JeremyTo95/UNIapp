package com.example.uniapp.utils;

import android.app.Activity;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.uniapp.controllers.activities.MainActivity;
import com.example.uniapp.models.database.AppDataBase;
import com.example.uniapp.models.database.dao.race.Race;
import com.example.uniapp.models.database.dao.race.RaceAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImportRacesTask extends AsyncTask<Void, Void, Void> {
    private WeakReference<Activity> weakReference;

    public ImportRacesTask(Activity activity) {
        weakReference = new WeakReference<>(activity);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppDataBase.URL_DATA)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        final RaceAPI raceAPI = retrofit.create(RaceAPI.class);

        Call<List<Race>> call = raceAPI.getResponseRace();
        call.enqueue(new Callback<List<Race>>() {
            @Override
            public void onFailure(Call<List<Race>> call, Throwable t) { Log.e("ERROR", "API call failed : failure"); }
            @Override
            public void onResponse(Call<List<Race>> call, Response<List<Race>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Race> raceList = response.body();
                    System.out.println("nbRaces to load : " + raceList.size());
                    for (int i = 0; i < raceList.size(); i++) {
                        MainActivity.appDataBase.raceDAO().insertRace(raceList.get(i));
                    }
                    System.out.println("nbRaces loaded : " + raceList.size());
                }
            }
        });
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Activity activity = weakReference.get();
        if (activity == null)
            return;
    }
}
