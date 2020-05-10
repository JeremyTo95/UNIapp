package com.example.uniapp.back.api;

import com.example.uniapp.front.model.data.Race;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RaceAPI {
    @GET("master/tourari_jeremy.json")
    Call<List<Race>> getResponseRaceJeremyTourari();


    @GET("master/peuffier_arthur.json")
    Call<List<Race>> getResponseRaceArthurPeuffier();


    @GET("master/noirbent_christophe.json")
    Call<List<Race>> getResponseRaceChristopheNoirbent();


    @GET("master/bencherqui_younes.json")
    Call<List<Race>> getResponseRaceYounesBencherqui();


    @GET("master/valenza_dylan.json")
    Call<List<Race>> getResponseRaceDylanValenza();


    @GET("master/andre_baptiste.json")
    Call<List<Race>> getResponseRaceBaptisteAndre();
}
