package com.example.uniapp.models.database;

import androidx.room.TypeConverter;

import com.example.uniapp.models.database.dao.trainingblock.TrainingBlock;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Converters {
    @TypeConverter
    public static List<TrainingBlock> toTrainingBlockList(String inputJSON) {
        // System.out.println("output : " + inputJSON);
        JSONArray trainingBlockArrayJSON      = null;
        List<Float> times                     = new ArrayList<Float>();
        List<TrainingBlock> trainingBlockList = new ArrayList<TrainingBlock>();
        try {
            trainingBlockArrayJSON = new JSONArray(inputJSON);
            JSONArray timesArrayJSON;

            int nbset;
            String swim;
            int distance;
            int zone;

            for (int i = 0; i < trainingBlockArrayJSON.length(); i++) {
                JSONObject jsonObject = trainingBlockArrayJSON.getJSONObject(i);
                nbset    = jsonObject.getInt("nbset");
                swim     = jsonObject.getString("swim");
                distance = jsonObject.getInt("distance");
                zone     = jsonObject.getInt("zone");
                times    = new ArrayList<Float>();

                timesArrayJSON = new JSONArray(jsonObject.getString("times"));
                for (int j = 0; j < timesArrayJSON.length(); j++) {
                    JSONObject timeJSON = timesArrayJSON.getJSONObject(j);
                    times.add(Float.parseFloat(timeJSON.getString("time")));
                }

                /*System.out.println("nbset    : " + nbset);
                System.out.println("swim     : " + swim);
                System.out.println("distance : " + distance);
                System.out.println("times    : " + Arrays.toString(times.toArray()));
                System.out.println("zone     : " + zone);*/


                trainingBlockList.add(new TrainingBlock(nbset, swim, distance, times, zone));
            }
            return trainingBlockList;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return trainingBlockList;
    }

    public static List<Float> initEmptyTime(int newSet) {
        List<Float> allTimes = new ArrayList<Float>();
        for (int i = 0; i < newSet; i++) {
            allTimes.add(0.0f);
        }
        return allTimes;
    }

    @TypeConverter
    public static String fromListTrainingBlock(List<TrainingBlock> trainingBlockList) {
        String inputJSON = "[";
        for (int i = 0; i < trainingBlockList.size(); i++) {
            inputJSON = inputJSON + "" +
                    "{" +
                    "\"nbset\": \"" + trainingBlockList.get(i).getNbSet() + "\"," +
                    "\"swim\": \"" + trainingBlockList.get(i).getSwim() + "\"," +
                    "\"distance\": \"" + trainingBlockList.get(i).getDistance() + "\"," +
                    "\"zone\": \"" + trainingBlockList.get(i).getZone() + "\"," +
                    "\"times\": [";
            for (int j = 0; j < trainingBlockList.get(i).getTimes().size(); j++) {
                inputJSON = inputJSON + "{" +
                        "\"time\": \"" + trainingBlockList.get(i).getTimes().get(j) + "\"";
                if (j == trainingBlockList.get(i).getTimes().size() - 1) inputJSON = inputJSON + "}";
                else inputJSON = inputJSON + "},";
            }
            inputJSON += "]";
            if (i == trainingBlockList.size() - 1) inputJSON = inputJSON + "}";
            else inputJSON = inputJSON + "},";
        }
        inputJSON += "]";

        // System.out.println("InputJSON : " + inputJSON);

        return inputJSON;
    }
}
