package com.example.uniapp.models.database.dao.race;

import android.app.Activity;
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
import com.example.uniapp.utils.ImportRacesTask;
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
        raceDAO.insertRace(race);
    }

    public void delete (final Race race) {
        raceDAO.delete(race);
    }
}
