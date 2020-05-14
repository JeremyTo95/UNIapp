package com.example.uniapp.front.model.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        return String.format(getTrainingBlock(), nbSet, swim, distance, Arrays.toString(times.toArray()), zone);
    }

    private String getTrainingBlock() {
        return "nbSet : %d | swim : %s | distance : %d | times : %s | zone : %d";
    }

    public void setSwim(String swim) { this.swim = swim; }
    public void setDistance(int distance) { this.distance = distance; }
    public void setTime(Float t, int index) {
        ArrayList<Float> allTimes = new ArrayList<>(times);
        allTimes.set(index, t);
        times = allTimes;
    }
    public void setTimes(List<Float> times) { this.times = times; }

    public int getNbSet() { return nbSet; }
    public int getDistance() { return distance; }
    public int getZone() { return zone; }
    public String getSwim() { return swim; }
    public List<Float> getTimes() { return times; }
}
