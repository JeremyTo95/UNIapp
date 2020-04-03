package com.example.uniapp.models;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class Training implements Serializable {
    private UUID id;
    private int difficulty;
    private int sizePool;
    private String date;
    private String city;
    private List<TrainingBlock> trainingBlockList;

    public Training(UUID id, int difficulty, int sizePool, String date, String city, List<TrainingBlock> trainingBlockList) {
        this.id = id;
        this.difficulty = difficulty;
        this.sizePool = sizePool;
        this.date = date;
        this.city = city;
        this.trainingBlockList = trainingBlockList;
    }

    public static int getStartIndexFromSetIndex(List<Integer> allSets, int setIndex) {
        int startIndex = 0;
        for (int i = 0; i < setIndex; i++) startIndex += allSets.get(i);
        System.out.println("startIndex : " + startIndex);
        return startIndex;
    }

    public static int getEndIndexFromSetIndex(List<Integer> allSets, int setIndex) {
        return getStartIndexFromSetIndex(allSets, setIndex) + allSets.get(setIndex);
    }

    public UUID getId() { return id; }
    public int getSizePool() { return sizePool; }
    public String getDate() { return date; }
    public String getCity() { return city; }
    public int getDifficulty() { return difficulty; }
    public List<TrainingBlock> getTrainingBlockList() { return trainingBlockList; }

    public void setId(UUID id) { this.id = id; }
    public void setSizePool(int sizePool) { this.sizePool = sizePool; }
    public void setDate(String date) { this.date = date; }
    public void setCity(String city) { this.city = city; }
    public void setDifficulty(int difficulty) { this.difficulty = difficulty; }
    public void setTrainingBlockList(List<TrainingBlock> trainingBlockList) { this.trainingBlockList = trainingBlockList; }
}
