package com.example.uniapp.models.database.dao.pointFFN;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PointFFNAPI {
    @GET("master/dataPoints_v2.json")
    Call<List<PointFFN>> getResponsePointsFFN();
}
