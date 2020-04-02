package com.example.uniapp.models;

import java.io.Serializable;
import java.util.List;

public class TrainingBlock implements Serializable {
    private int nbSet;
    private String swim;
    private int distance;
    private List<String> times;
    private int zone;

    public TrainingBlock(int nbSet, String swim, int distance, List<String> times, int zone) {
        this.nbSet = nbSet;
        this.swim = swim;
        this.distance = distance;
        this.times = times;
        this.zone = zone;
    }

    public void setNbSet(int nbSet) { this.nbSet = nbSet; }
    public void setSwim(String swim) { this.swim = swim; }
    public void setDistance(int distance) { this.distance = distance; }
    public void setTimes(List<String> times) { this.times = times; }
    public void setZone(int zone) { this.zone = zone; }

    public int getNbSet() { return nbSet; }
    public int getDistance() { return distance; }
    public int getZone() { return zone; }
    public String getSwim() { return swim; }
    public List<String> getTimes() { return times; }
}