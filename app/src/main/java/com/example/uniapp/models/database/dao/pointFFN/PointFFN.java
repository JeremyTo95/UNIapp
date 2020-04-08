package com.example.uniapp.models.database.dao.pointFFN;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.example.uniapp.controllers.activities.MainActivity;
import com.example.uniapp.models.database.AppDataBase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Entity(tableName = "pointFFN", primaryKeys = {"point", "distance", "swim", "time", "gender"})
public class PointFFN implements Serializable {

    @NonNull
    @ColumnInfo(name = "point")
    private int point;

    @NonNull
    @ColumnInfo(name = "distance")
    private int distance;

    @NonNull
    @ColumnInfo(name = "swim")
    private String swim;

    @NonNull
    @ColumnInfo(name = "time")
    private float time;

    @NonNull
    @ColumnInfo(name = "gender")
    private String gender;

    public PointFFN(int point, int distance, @NonNull String swim, @NonNull float time, @NonNull String gender) {
        this.point = point;
        this.distance = distance;
        this.swim = swim;
        this.time = time;
        this.gender = gender;
    }

    public int getPoint() { return point; }
    public int getDistance() { return distance; }
    public String getSwim() { return swim; }
    public float getTime() { return time; }
    public String getGender() { return gender; }

    public void setPoint(int point) { this.point = point; }
    public void setDistance(int distance) { this.distance = distance; }
    public void setSwim(String swim) { this.swim = swim; }
    public void setTime(float time) { this.time = time; }
    public void setGender(String gender) { this.gender = gender; }

    public static void makePointFFNApiCall() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppDataBase.URL_DATA)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        final PointFFNAPI pointFFNAPI = retrofit.create(PointFFNAPI.class);

        Call<List<PointFFN>> call = pointFFNAPI.getResponsePointsFFN();
        call.enqueue(new Callback<List<PointFFN>>() {
            @Override
            public void onFailure(Call<List<PointFFN>> call, Throwable t) { }
            @Override
            public void onResponse(Call<List<PointFFN>> call, Response<List<PointFFN>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<PointFFN> pointFFNList = response.body();
                    System.out.println("size : " + pointFFNList.size());
                    if (MainActivity.appDataBase.pointFFNDAO().getAllPoints().size() == 0) {
                        for (int i = 0; i < pointFFNList.size(); i++) {
                            //System.out.println("insert");
                            //points.add(pointFFNList.get(i));
                            MainActivity.appDataBase.pointFFNDAO().insertPointFFN(pointFFNList.get(i));
                        }
                        //System.out.println("size points : " + points.size());
                    }
                }
            }
        });
    }
}
