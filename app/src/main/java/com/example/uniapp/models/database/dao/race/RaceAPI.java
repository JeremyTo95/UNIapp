package com.example.uniapp.models.database.dao.race;

import com.example.uniapp.models.database.dao.pointFFN.PointFFN;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RaceAPI {
    @GET("master/my_times.json")
    Call<List<Race>> getResponseRace();
}
