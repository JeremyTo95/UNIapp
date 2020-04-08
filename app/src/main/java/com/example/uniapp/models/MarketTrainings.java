package com.example.uniapp.models;

import com.example.uniapp.models.database.dao.training.Training;
import com.example.uniapp.models.database.dao.trainingblock.TrainingBlock;
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

        /*List<TrainingBlock> trainingBlockList = new ArrayList<TrainingBlock>();
        trainingBlockList.add(new TrainingBlock(1, "freestyle", 100, Arrays.asList(new Float[] {1.0f}), 7));
        trainingBlockList.add(new TrainingBlock(2, "butterfly", 50,  Arrays.asList(new Float[] {0.325f, 0.333f}), 7));
        allTrainings.add(new Training(UUID.randomUUID().toString(), 3, 25, "20/02/2020", "HERBLAY", trainingBlockList));

        trainingBlockList = new ArrayList<TrainingBlock>();
        trainingBlockList.add(new TrainingBlock(10, "freestyle", 100, Arrays.asList(new Float[] {1.15f, 1.15f, 1.15f, 1.15f, 1.16f, 1.16f, 1.16f, 1.17f, 1.17f, 1.17f}), 3));
        allTrainings.add(new Training(UUID.randomUUID().toString(), 3, 25, "20/02/2020", "HERBLAY", trainingBlockList));

        trainingBlockList = new ArrayList<TrainingBlock>();
        trainingBlockList.add(new TrainingBlock(1, "freestyle", 100, Arrays.asList(new Float[] {1.0020f}), 7));
        trainingBlockList.add(new TrainingBlock(2, "butterfly", 50,  Arrays.asList(new Float[] {0.315f, 0.320f}), 7));
        allTrainings.add(new Training(UUID.randomUUID().toString(), 3, 25, "20/02/2020", "HERBLAY", trainingBlockList));

        trainingBlockList = new ArrayList<TrainingBlock>();
        trainingBlockList.add(new TrainingBlock(20, "butterfly", 50,  Arrays.asList(new Float[] {0.3410f, 0.3430f, 0.3460f, 0.3470f, 0.3480f, 0.3500f, 0.3510f, 0.3410f, 0.3530f, 0.3550f, 0.3560f, 0.3570f, 0.3580f, 0.3580f, 0.3590f, 0.3660f, 0.3660f, 0.3670f, 0.3690f, 0.3710f}), 3));
        allTrainings.add(new Training(UUID.randomUUID().toString(), 3, 50, "20/02/2020", "HERBLAY", trainingBlockList));

        trainingBlockList = new ArrayList<TrainingBlock>();

        trainingBlockList.add(new TrainingBlock(1, "freestyle", 100, Arrays.asList(new Float[] {1.05f}), 6));
        trainingBlockList.add(new TrainingBlock(2, "butterfly", 50,  Arrays.asList(new Float[] {0.321f, 0.336f}), 6));
        trainingBlockList.add(new TrainingBlock(2, "freestyle", 50,  Arrays.asList(new Float[] {0.280f, 0.282f}), 6));
        allTrainings.add(new Training(UUID.randomUUID().toString(), 3, 25, "20/02/2020", "HERBLAY", trainingBlockList));*/

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
