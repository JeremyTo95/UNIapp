package com.example.uniapp.models;

import com.example.uniapp.views.RaceDateComparator;
import com.example.uniapp.views.TrainingDateComparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class MarketTrainings {
    private List<Training> allTrainings;

    public MarketTrainings() { }

    public static ArrayList<Training> initTrainings() {
        ArrayList<Training> allTrainings = new ArrayList<Training>();

        allTrainings.add(new Training(UUID.randomUUID(), "20/02/2020", "HERBLAY", 25, Arrays.asList(new String[] {"freestyle", "butterfly"}), Arrays.asList(new Integer[] {1, 2}), Arrays.asList(new Integer[] {100, 50}), Arrays.asList(new String[] {"01:00:20", "00:30.20", "00:32.40"}), 3, Arrays.asList(new Integer[] {7, 7})));
        allTrainings.add(new Training(UUID.randomUUID(), "15/12/2019", "HERBLAY", 25, Arrays.asList(new String[] {"freestyle"}),              Arrays.asList(new Integer[] {10}),   Arrays.asList(new Integer[] {100}),     Arrays.asList(new String[] {"01:15:00", "01:15:00", "01:15:00", "01:15:00", "01:16:00", "01:16:00", "01:15:00", "01:15:00", "01:16:00", "01:16:00"}), 4, Arrays.asList(new Integer[] {3})));
        allTrainings.add(new Training(UUID.randomUUID(), "20/03/2020", "HERBLAY", 25, Arrays.asList(new String[] {"freestyle", "butterfly"}), Arrays.asList(new Integer[] {1, 2}), Arrays.asList(new Integer[] {100, 50}), Arrays.asList(new String[] {"01:00:20", "00:30.20", "00:32.40"}), 3, Arrays.asList(new Integer[] {5, 7})));
        allTrainings.add(new Training(UUID.randomUUID(), "09/01/2020", "HERBLAY", 50, Arrays.asList(new String[] {"butterfly"}),              Arrays.asList(new Integer[] {20}),   Arrays.asList(new Integer[] {50}),      Arrays.asList(new String[] {"00:35.90", "00:35.90", "00:36.90", "00:37.90", "00:36.90", "00:37.90", "00:36.90", "00:37.90", "00:36.90", "00:37.90", "00:37.90", "00:37.90", "00:37.90", "00:38.90", "00:38.90", "00:39.90", "00:39.90", "00:38.90", "00:38.90", "00:36.90"}), 5, Arrays.asList(new Integer[] {3})));
        allTrainings.add(new Training(UUID.randomUUID(), "03/01/2020", "HERBLAY", 25, Arrays.asList(new String[] {"freestyle", "freestyle"}), Arrays.asList(new Integer[] {2, 4}), Arrays.asList(new Integer[] {100, 50}), Arrays.asList(new String[] {"01:00:20", "01:02:40", "00:27.24", "00:28.90", "00:30.20", "00:29.40"}), 5, Arrays.asList(new Integer[] {6, 6})));
        allTrainings.add(new Training(UUID.randomUUID(), "20/02/2020", "HERBLAY", 25, Arrays.asList(new String[] {"freestyle", "butterfly", "freestyle"}), Arrays.asList(new Integer[] {1, 2, 2}), Arrays.asList(new Integer[] {100, 50, 50}), Arrays.asList(new String[] {"01:00:20", "00:30.20", "00:32.40", "00:28.90", "00:29.50"}), 4, Arrays.asList(new Integer[] {6, 6, 6})));

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
        List<Training> cpyAllTrainings = allTrainings;

        for (int i = 0; i < cpyAllTrainings.size(); i++) {
            for (int j = 0; j < cpyAllTrainings.get(i).getSwims().size(); j++) {
                if (cpyAllTrainings.get(i).getSizePool() == sizePool) {
                    if (cpyAllTrainings.get(i).getSwims().get(j).equals(swim) || swim.equals("all")) {
                        if (difficulty == 0 || cpyAllTrainings.get(i).getDifficulty() == difficulty) {
                            trainings.add(cpyAllTrainings.get(i));
                            j = cpyAllTrainings.get(i).getSwims().size();
                        }
                    }
                }
            }
        }

        return trainings;
    }
}
