package com.example.uniapp.front.model.data;

import android.app.Activity;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.example.uniapp.back.repository.PointFFNRepository;
import com.example.uniapp.front.controller.asynctask.ImportPointsFFNTask;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;

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

    public static void startAsyncTaskLoadingPointsFFN(Activity activity) {
        Log.e("Function", "IN");
        ImportPointsFFNTask importPointsFFNTask = new ImportPointsFFNTask(activity);
        importPointsFFNTask.execute();
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
}
