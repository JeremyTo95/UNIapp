package com.example.uniapp.models.database.dao.trainingblock;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TrainingBlock implements Serializable {
    @ColumnInfo(name = "nbset")
    private int nbSet;

    @ColumnInfo(name = "swim")
    private String swim;

    @ColumnInfo(name = "distance")
    private int distance;

    @ColumnInfo(name = "times")
    private List<Float> times;

    @ColumnInfo(name = "zone")
    private int zone;

    public TrainingBlock(int nbSet, String swim, int distance, List<Float> times, int zone) {
        this.nbSet = nbSet;
        this.swim = swim;
        this.distance = distance;
        this.times = times;
        this.zone = zone;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("nbSet : %d | swim : %s | distance : %d | times : %s | zone : %d", nbSet, swim, distance, Arrays.toString(times.toArray()), zone);
    }

    public void addTime(int index, Float time) {
        times.add(index, time);
    }

    public void setNbSet(int nbSet) { this.nbSet = nbSet; }
    public void setSwim(String swim) { this.swim = swim; }
    public void setDistance(int distance) { this.distance = distance; }
    public void setTimes(Float t, int index) {
        ArrayList<Float> allTimes = new ArrayList<>();
        for (int i = 0; i < times.size(); i++) allTimes.add(times.get(i));
        allTimes.set(index, t);
        times = allTimes;
    }

    public void setZone(int zone) { this.zone = zone; }
    public int getNbSet() { return nbSet; }
    public int getDistance() { return distance; }
    public int getZone() { return zone; }
    public String getSwim() { return swim; }
    public List<Float> getTimes() { return times; }
}
