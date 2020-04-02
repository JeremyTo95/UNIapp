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

    public static String convertCompetitionTimeToZoneTime(String time, int zone) {
        float timeFloat = Race.fetchTimeToFloat(time);
        return Race.fetchFloatToTime(timeFloat/convertZoneToPercent(zone));
    }

    public static float convertZoneToPercent(int zone) {
        if (zone == 1) return 0.60f;
        else if (zone == 2) return 0.65f;
        else if (zone == 3) return 0.75f;
        else if (zone == 4) return 0.80f;
        else if (zone == 5) return 0.85f;
        else if (zone == 6) return 0.90f;
        else if (zone == 7) return 0.95f;
        else return 0;
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
