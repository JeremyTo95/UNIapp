package com.example.uniapp.models.database.dao.race;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.example.uniapp.controllers.activities.MainActivity;
import com.example.uniapp.models.database.AppDataBase;
import com.example.uniapp.models.database.dao.ElementRepertories;
import com.example.uniapp.models.database.dao.pointFFN.PointFFN;
import com.example.uniapp.models.database.dao.pointFFN.PointFFNDAO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RaceRepository extends ElementRepertories {
    private RaceDAO raceDAO;
    private List<Race> raceList;

    public RaceRepository(Application application) {
        AppDataBase appDataBase = AppDataBase.getDatabase(application);
        raceDAO = appDataBase.raceDAO();
        raceList = raceDAO.getAllRaces();
    }

    public List<Race> getRaces() {
        return raceList;
    }

    @Override
    public int getNbElement() { return raceList.size(); }

    public List<Race> getRacesByPoolSizeDistanceRaceSwimRace(int sizePool, int distance, String swim) {
        return raceDAO.getRacesByPoolSizeDistanceRaceSwimRace(sizePool, distance, swim);
    }

    public void insert (final Race race) {
        AppDataBase.dataWriterExecutor.execute(new Runnable() {
            @Override
            public void run() {
                raceDAO.insertRace(race);
            }
        });
    }

    public void delete (final Race race) {
        AppDataBase.dataWriterExecutor.execute(new Runnable() {
            @Override
            public void run() {
                raceDAO.delete(race);
            }
        });
    }

    public void makeRaceApiCall(final Context context) {
        AppDataBase.dataWriterExecutor.execute(new Runnable() {
            @Override
            public void run() {
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
                    public void onFailure(Call<List<Race>> call, Throwable t) { Toast.makeText(context, "API call failed : failure", Toast.LENGTH_SHORT).show(); }
                    @Override
                    public void onResponse(Call<List<Race>> call, Response<List<Race>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<Race> raceList = response.body();
                            for (int i = 0; i < raceList.size(); i++) {
                                raceDAO.insertRace(raceList.get(i));
                            }
                        }
                    }
                });
            }
        });
    }
}
