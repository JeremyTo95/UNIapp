package com.example.uniapp.utils;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.example.uniapp.controllers.activities.MainActivity;
import com.example.uniapp.models.database.AppDataBase;
import com.example.uniapp.models.database.dao.race.Race;
import com.example.uniapp.models.database.dao.race.RaceAPI;
import com.example.uniapp.models.database.dao.user.User;
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
    private User user;

    public ImportRacesTask(Activity activity, User user) {
        weakReference = new WeakReference<>(activity);
        this.user = user;
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

        Log.e("HERE", "In async race");
        Call<List<Race>> call = null;
        if (user.getFirstname().equals("Jeremy") && user.getLastname().equals("Tourari"))      call = raceAPI.getResponseRaceJeremyTourari();
        if (user.getFirstname().equals("Younes") && user.getLastname().equals("Bencherqui"))   call = raceAPI.getResponseRaceYounesBencherqui();
        if (user.getFirstname().equals("Arthur") && user.getLastname().equals("Peuffier"))     call = raceAPI.getResponseRaceArthurPeuffier();
        if (user.getFirstname().equals("Christophe") && user.getLastname().equals("Noirbent")) call = raceAPI.getResponseRaceChristopheNoirbent();
        if (user.getFirstname().equals("Dylan") && user.getLastname().equals("Valenza"))       call = raceAPI.getResponseRaceDylanValenza();
        if (user.getFirstname().equals("Baptiste") && user.getLastname().equals("Andre"))      call = raceAPI.getResponseRaceBaptisteAndre();
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
