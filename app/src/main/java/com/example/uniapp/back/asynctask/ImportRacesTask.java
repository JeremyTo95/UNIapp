package com.example.uniapp.back.asynctask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.example.uniapp.back.api.RaceAPI;
import com.example.uniapp.back.executor.AppExecutors;
import com.example.uniapp.back.gson.GsonManager;
import com.example.uniapp.back.room.RoomDataBase;
import com.example.uniapp.front.controller.global.AboutScreen;
import com.example.uniapp.front.model.data.Race;
import com.example.uniapp.front.model.data.User;
import com.example.uniapp.front.view.actvities.MainActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImportRacesTask extends AsyncTask<Void, Void, Void> {
    private RoomDataBase roomDataBase;
    @SuppressLint("StaticFieldLeak")
    private Activity activity;
    private User     user;

    public ImportRacesTask(Activity activity) {
        this.activity     = activity;
        this.roomDataBase = RoomDataBase.getDatabase(activity.getApplicationContext());
        this.user         = roomDataBase.userDAO().getUser();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RoomDataBase.URL_DATA)
                .addConverterFactory(GsonConverterFactory.create(GsonManager.getGsonInstance()))
                .build();

        final RaceAPI raceAPI = retrofit.create(RaceAPI.class);

        Call<List<Race>> call = null;
        if (user.getFirstname().equals("Jeremy")     && user.getLastname().equals("Tourari"))    call = raceAPI.getResponseRaceJeremyTourari();
        if (user.getFirstname().equals("Younes")     && user.getLastname().equals("Bencherqui")) call = raceAPI.getResponseRaceYounesBencherqui();
        if (user.getFirstname().equals("Arthur")     && user.getLastname().equals("Peuffier"))   call = raceAPI.getResponseRaceArthurPeuffier();
        if (user.getFirstname().equals("Christophe") && user.getLastname().equals("Noirbent"))   call = raceAPI.getResponseRaceChristopheNoirbent();
        if (user.getFirstname().equals("Dylan")      && user.getLastname().equals("Valenza"))    call = raceAPI.getResponseRaceDylanValenza();
        if (user.getFirstname().equals("Baptiste")   && user.getLastname().equals("Andre"))      call = raceAPI.getResponseRaceBaptisteAndre();
        if (call != null) {
            call.enqueue(new Callback<List<Race>>() {
                @Override
                public void onFailure(Call<List<Race>> call, Throwable t) { Log.e("ERROR", "API call failed : failure"); }
                @Override
                public void onResponse(Call<List<Race>> call, Response<List<Race>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Race> raceList = response.body();
                        AppExecutors.getInstance(activity.getApplicationContext()).getDiskIo().execute(() -> {
                            System.out.println("nbRaces to load : " + raceList.size());
                            for (int i = 0; i < raceList.size(); i++) {
                                roomDataBase.raceDAO().insert(raceList.get(i));
                                int indexElementLoaded = i+1;
                                AppExecutors.getInstance(activity.getApplicationContext()).getUIThread().execute(() -> AboutScreen.lockUI((MainActivity) activity, true, indexElementLoaded + " / " + raceList.size() + " courses chargées"));
                            }
                            System.out.println("nbRaces loaded : " + raceList.size());
                            AppExecutors.getInstance(activity.getApplicationContext()).getUIThread().execute(() -> AboutScreen.unlockUI((MainActivity) activity));
                        });
                    }
                }
            });
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
