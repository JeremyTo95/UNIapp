package com.example.uniapp.front.model.data;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.example.uniapp.back.asynctask.ImportPointsFFNTask;

import java.io.Serializable;

@Entity(tableName = "pointFFN", primaryKeys = {"point", "distance", "swim", "time", "gender"})
public class PointFFN implements Serializable {

    @ColumnInfo(name = "point")
    private int point;

    @ColumnInfo(name = "distance")
    private int distance;

    @NonNull
    @ColumnInfo(name = "swim")
    private String swim;

    @ColumnInfo(name = "time")
    private float time;

    @NonNull
    @ColumnInfo(name = "gender")
    private String gender;

    public PointFFN(int point, int distance, @NonNull String swim, float time, @NonNull String gender) {
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

    @NonNull
    public String getSwim() { return swim; }
    @NonNull
    public String getGender() { return gender; }
    public int getPoint() { return point; }
    public int getDistance() { return distance; }
    public float getTime() { return time; }

    public void setDistance(int distance) { this.distance = distance; }
    public void setSwim(@NonNull String swim) { this.swim = swim; }
    public void setTime(float time) { this.time = time; }
    public void setGender(@NonNull String gender) { this.gender = gender; }
}
