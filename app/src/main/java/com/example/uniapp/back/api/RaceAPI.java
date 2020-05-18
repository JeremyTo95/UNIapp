package com.example.uniapp.back.api;

import com.example.uniapp.front.model.data.Race;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RaceAPI {
    @GET("master/{name}")
    Call<List<Race>> getResponseRace(@Path("name") String username);
}
