package com.example.uniapp.models.database.dao.training;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.uniapp.models.database.dao.trainingblock.TrainingBlock;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Entity(tableName = "training")
public class Training implements Serializable {
    @NonNull
    @PrimaryKey
    private String id;

    @ColumnInfo(name = "difficulty")
    private int difficulty;

    @ColumnInfo(name = "size_pool")
    private int sizePool;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "city")
    private String city;

    @ColumnInfo(name = "trainingBlock")
    private List<TrainingBlock> trainingBlockList;

    @NonNull
    @Override
    public String toString() {
        return String.format("id : %s | difficulty : %d | sizePool : %d | date : %s | city : %s | trainingBlock : %s", id, difficulty, sizePool, date, city, Arrays.toString(trainingBlockList.toArray()));
    }

    public Training(String id, int difficulty, int sizePool, String date, String city, List<TrainingBlock> trainingBlockList) {
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

    public String getId() { return id; }
    public int getSizePool() { return sizePool; }
    public String getDate() { return date; }
    public String getCity() { return city; }
    public int getDifficulty() { return difficulty; }
    public List<TrainingBlock> getTrainingBlockList() { return trainingBlockList; }

    public void setId(String id) { this.id = id; }
    public void setSizePool(int sizePool) { this.sizePool = sizePool; }
    public void setDate(String date) { this.date = date; }
    public void setCity(String city) { this.city = city; }
    public void setDifficulty(int difficulty) { this.difficulty = difficulty; }
    public void setTrainingBlockList(List<TrainingBlock> trainingBlockList) { this.trainingBlockList = trainingBlockList; }
}
