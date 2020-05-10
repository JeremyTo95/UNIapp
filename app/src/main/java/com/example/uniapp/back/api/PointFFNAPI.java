package com.example.uniapp.back.api;

import com.example.uniapp.front.model.data.PointFFN;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PointFFNAPI {
    @GET("master/dataPoints.json")
    Call<List<PointFFN>> getResponsePointsFFN();
}
