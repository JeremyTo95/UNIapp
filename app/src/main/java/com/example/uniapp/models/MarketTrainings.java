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

    public MarketTrainings() { }

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
