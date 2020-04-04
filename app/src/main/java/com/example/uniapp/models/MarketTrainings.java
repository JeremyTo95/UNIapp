package com.example.uniapp.models;

import com.example.uniapp.models.database.dao.training.Training;
import com.example.uniapp.views.comparators.TrainingDateComparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class MarketTrainings {
    private List<Training> allTrainings;

    public MarketTrainings() { }

    public static ArrayList<Training> initAllTrainings() {
        ArrayList<Training> allTrainings      = new ArrayList<Training>();

        List<TrainingBlock> trainingBlockList = new ArrayList<TrainingBlock>();
        trainingBlockList.add(new TrainingBlock(1, "freestyle", 100, Arrays.asList(new String[] {"01:00:20"}), 7));
        trainingBlockList.add(new TrainingBlock(2, "butterfly", 50,  Arrays.asList(new String[] {"00:30.20", "00:32.40"}), 7));
        allTrainings.add(new Training(UUID.randomUUID().toString(), 3, 25, "20/02/2020", "HERBLAY", trainingBlockList));

        trainingBlockList = new ArrayList<TrainingBlock>();
        trainingBlockList.add(new TrainingBlock(10, "freestyle", 100, Arrays.asList(new String[] {"01:15:00", "01:15:00", "01:15:00", "01:15:00", "01:16:00", "01:16:00", "01:15:00", "01:15:00", "01:16:00", "01:16:00"}), 3));
        allTrainings.add(new Training(UUID.randomUUID().toString(), 3, 25, "20/02/2020", "HERBLAY", trainingBlockList));

        trainingBlockList = new ArrayList<TrainingBlock>();
        trainingBlockList.add(new TrainingBlock(1, "freestyle", 100, Arrays.asList(new String[] {"01:00:20"}), 7));
        trainingBlockList.add(new TrainingBlock(2, "butterfly", 50,  Arrays.asList(new String[] {"00:30.20", "00:32.40"}), 7));
        allTrainings.add(new Training(UUID.randomUUID().toString(), 3, 25, "20/02/2020", "HERBLAY", trainingBlockList));

        trainingBlockList = new ArrayList<TrainingBlock>();
        trainingBlockList.add(new TrainingBlock(20, "butterfly", 50,  Arrays.asList(new String[] {"00:35.90", "00:35.90", "00:36.90", "00:37.90", "00:36.90", "00:37.90", "00:36.90", "00:37.90", "00:36.90", "00:37.90", "00:37.90", "00:37.90", "00:37.90", "00:38.90", "00:38.90", "00:39.90", "00:39.90", "00:38.90", "00:38.90", "00:36.90"}), 3));
        allTrainings.add(new Training(UUID.randomUUID().toString(), 3, 50, "20/02/2020", "HERBLAY", trainingBlockList));

        trainingBlockList = new ArrayList<TrainingBlock>();

        trainingBlockList.add(new TrainingBlock(1, "freestyle", 100, Arrays.asList(new String[] {"01:00:20"}), 6));
        trainingBlockList.add(new TrainingBlock(2, "butterfly", 50,  Arrays.asList(new String[] {"00:30.20", "00:32.40"}), 6));
        trainingBlockList.add(new TrainingBlock(2, "freestyle", 50,  Arrays.asList(new String[] {"00:28.90", "00:29.50"}), 6));
        allTrainings.add(new Training(UUID.randomUUID().toString(), 3, 25, "20/02/2020", "HERBLAY", trainingBlockList));

        return allTrainings;
    }

    public static void addTraining(List<Training> allTrainings, Training training) {
        allTrainings.add(training);
        sortTrainingsByDate(allTrainings);
    }

    public static void removeTrainings(List<Training> allTrainings, Training training) {
        for (int i = 0; i < allTrainings.size(); i++) {
            if (allTrainings.get(i).getId().equals(training.getId())) {
                allTrainings.remove(i);
                i = allTrainings.size();
            }
        }
    }

    public static void sortTrainingsByDate(List<Training> allTrainings) {
        TrainingDateComparator trainingDateComparator = new TrainingDateComparator();
        Collections.sort(allTrainings, trainingDateComparator);
    }

    public static List<Training> getTrainingsBySizePoolSwimDifficulty(List<Training> allTrainings, int sizePool, String swim, int difficulty) {
        List<Training> trainings = new ArrayList<>();

        for (int i = 0; i < allTrainings.size(); i++) {
            if (allTrainings.get(i).getSizePool() == sizePool) {
                if (allTrainings.get(i).getDifficulty() == difficulty || difficulty == 0) {
                    for (int j = 0; j < allTrainings.get(i).getTrainingBlockList().size(); j++) {
                        System.out.println(j);
                        if (allTrainings.get(i).getTrainingBlockList().get(j).getSwim().equals(swim) || swim.equals("all")) {
                            trainings.add(allTrainings.get(i));
                            j = allTrainings.get(i).getTrainingBlockList().size();
                        }
                    }
                }
            }
        }

        return trainings;
    }
}
