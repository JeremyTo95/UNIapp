package com.example.uniapp.models.database;

import androidx.room.TypeConverter;

import com.example.uniapp.models.TrainingBlock;

import java.util.ArrayList;
import java.util.List;

public class Converters {
    @TypeConverter
    public static List<TrainingBlock> fromString(String value) {
        List<TrainingBlock> trainingBlockList = new ArrayList<TrainingBlock>();
        return trainingBlockList;
    }

    @TypeConverter
    public static String fromListTrainingBlock(List<TrainingBlock> trainingBlockList) {
        String value = "";
        return value;
    }
}
