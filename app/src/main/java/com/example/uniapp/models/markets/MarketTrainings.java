package com.example.uniapp.models.markets;

import com.example.uniapp.models.database.dao.training.Training;
import com.example.uniapp.models.database.dao.trainingblock.TrainingBlock;
import com.example.uniapp.views.comparators.TrainingDateComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public static List<Float> getFloatTimes(TrainingBlock trainingBlock) {
        List<Float> allTimes = new ArrayList<>();
        for (int i = 0; i < trainingBlock.getTimes().size(); i++)
            allTimes.add(trainingBlock.getTimes().get(i));

        return allTimes;
    }

    public static List<Float> getRefLine(float ref, int nbSets) {
        List<Float> refLineData = new ArrayList<Float>();
        for (int i = 0; i < nbSets; i++) refLineData.add(ref);

        return refLineData;
    }

    public static List<String> getLegendTraining(List<TrainingBlock> allTrainingBlock) {
        List<String> legendList = new ArrayList<>();
        for (int i = 0; i < allTrainingBlock.size(); i++)
            legendList.add(allTrainingBlock.get(i).getDistance() + "" + MarketSwim.convertShortSwim(allTrainingBlock.get(i).getSwim()));


        return legendList;
    }
}
