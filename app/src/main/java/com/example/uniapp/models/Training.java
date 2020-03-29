package com.example.uniapp.models;

import java.util.List;
import java.util.UUID;

public class Training {
    private UUID id;
    private List<String> swims;
    private List<Integer> sets;
    private List<Integer> distance;
    private List<String> times;
    private int difficulty;
    private int sizePool;
    private String date;
    private String city;

    public Training(UUID id, String date, String city, int sizePool, List<String> swims, List<Integer> sets, List<Integer> distance, List<String> times, int difficulty) {
        this.id = id;
        this.date = date;
        this.city = city;
        this.sizePool = sizePool;
        this.swims = swims;
        this.sets = sets;
        this.distance = distance;
        this.times = times;
        this.difficulty = difficulty;
    }

    public int getTotalSets() {
        int tot = 0;
        for (int i = 0; i < sets.size(); i++) {
            tot+= sets.get(i);
        }
        return tot;
    }

    public int getTotalDistance() {
        int tot = 0;
        for (int i = 0; i < sets.size(); i++) {
            tot += distance.get(i) * sets.get(i);
        }
        return tot;
    }

    public UUID getId() { return id; }
    public int getSizePool() { return sizePool; }
    public String getDate() { return date; }
    public String getCity() { return city; }
    public List<String> getSwims() { return swims; }
    public List<Integer> getSets() { return sets; }
    public List<Integer> getDistance() { return distance; }
    public List<String> getTimes() { return times; }
    public int getDifficulty() { return difficulty; }

    public void setId(UUID id) { this.id = id; }
    public void setSizePool(int sizePool) { this.sizePool = sizePool; }
    public void setDate(String date) { this.date = date; }
    public void setCity(String city) { this.city = city; }
    public void setSwims(List<String> swims) { this.swims = swims; }
    public void setSets(List<Integer> sets) { this.sets = sets; }
    public void setDistance(List<Integer> distance) { this.distance = distance; }
    public void setTimes(List<String> times) { this.times = times; }
    public void setDifficulty(int difficulty) { this.difficulty = difficulty; }
}
