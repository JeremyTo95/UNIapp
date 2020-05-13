package com.example.uniapp.back.room;

import androidx.room.TypeConverter;

import com.example.uniapp.back.gson.GsonManager;
import com.example.uniapp.front.model.data.TrainingBlock;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Converters {
    @TypeConverter
    public static List<TrainingBlock> toTrainingBlockList(String inputJSON) {
        JSONArray trainingBlockArrayJSON;
        List<TrainingBlock> trainingBlockList = new ArrayList<TrainingBlock>();
        try {
            trainingBlockArrayJSON = new JSONArray(inputJSON);
            for (int i = 0; i < trainingBlockArrayJSON.length(); i++) {
                JSONObject jsonObject = trainingBlockArrayJSON.getJSONObject(i);
                trainingBlockList.add(GsonManager.getGsonInstance().fromJson(jsonObject.toString(), TrainingBlock.class));
            }
            return trainingBlockList;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return trainingBlockList;
    }

    @TypeConverter
    public static String fromListTrainingBlock(List<TrainingBlock> trainingBlockList) {
        String inputJSON = "[";
        for (int i = 0; i < trainingBlockList.size(); i++) {
            if (i != trainingBlockList.size() - 1) inputJSON += GsonManager.getGsonInstance().toJson(trainingBlockList.get(i)) + ",";
            else inputJSON += GsonManager.getGsonInstance().toJson(trainingBlockList.get(i));
        }
        inputJSON += "]";

        return inputJSON;
    }
}
