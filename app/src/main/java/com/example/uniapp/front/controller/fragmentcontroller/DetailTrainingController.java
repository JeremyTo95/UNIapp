package com.example.uniapp.front.controller.fragmentcontroller;

import android.content.Context;

import com.example.uniapp.back.room.RoomDataBase;
import com.example.uniapp.front.controller.global.Controller;
import com.example.uniapp.front.model.data.Training;
import com.example.uniapp.front.model.market.MarketRaces;
import com.example.uniapp.front.model.market.MarketSwim;
import com.example.uniapp.front.model.market.MarketTimes;
import com.example.uniapp.front.view.fragments.TrainingDetailFragment;

import java.util.ArrayList;
import java.util.List;

public class DetailTrainingController extends Controller {
    private RoomDataBase           roomDataBase;
    private TrainingDetailFragment view;
    private Context                context;

    private Training training;

    public DetailTrainingController(TrainingDetailFragment view, Training training) {
        super(view);
        this.view         = view;
        this.context      = view.getContext();
        this.roomDataBase = RoomDataBase.getDatabase(context);
        this.training     = training;
    }

    @Override
    public void onStart() {
        super.onStart();
        view.setupUIElements();
        view.updateHeaderInfo();
        view.fillDifficulty();
        view.updateTrainingList();
    }

    public String getTitleTraining() {
        return "Le " + training.getDate() + " à " + training.getCity() + " (" + training.getSizePool() + "m)";
    }

    public String getRaceZoneStr() {
        StringBuilder raceZoneTimeStr = new StringBuilder();
        List<Float> bestTimes         = getCompetitionRaceTime();

        for (int i = 0; i < training.getTrainingBlockList().size(); i++) {
            raceZoneTimeStr.append(training.getTrainingBlockList().get(i).getDistance())
                    .append(MarketSwim.convertShortSwim(training.getTrainingBlockList().get(i).getSwim()))
                    .append(" : ")
                    .append(MarketTimes.fetchFloatToTime(bestTimes.get(i)))
                    .append("\n");
        }
        raceZoneTimeStr.delete(raceZoneTimeStr.length() - 1, raceZoneTimeStr.length() - 1);

        return raceZoneTimeStr.toString();
    }

    public String getTrainingZoneTimeStr() {
        StringBuilder trainingZoneTimeStr = new StringBuilder();
        List<Float> bestTimes = getCompetitionRaceTime();

        for (int i = 0; i < training.getTrainingBlockList().size(); i++) {
            trainingZoneTimeStr.append("Z")
                    .append(training.getTrainingBlockList().get(i).getZone())
                    .append(" ")
                    .append(training.getTrainingBlockList().get(i).getDistance())
                    .append(MarketSwim.convertShortSwim(training.getTrainingBlockList().get(i).getSwim()))
                    .append(" : ")
                    .append(MarketTimes.convertCompetitionTimeToZoneTime(bestTimes.get(i), training.getTrainingBlockList().get(i).getZone()))
                    .append("\n");
        }
        trainingZoneTimeStr.delete(trainingZoneTimeStr.length() - 1, trainingZoneTimeStr.length() - 1);

        return trainingZoneTimeStr.toString();
    }

    public int getTrainingDifficulty() {
        return training.getDifficulty();
    }

    private List<Float> getCompetitionRaceTime() {
        List<Float> result = new ArrayList<Float>();
        for (int i = 0; i < training.getTrainingBlockList().size(); i++)
            result.add(MarketRaces.getBestTime(roomDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(training.getSizePool(), training.getTrainingBlockList().get(i).getDistance(), training.getTrainingBlockList().get(i).getSwim()), 1).getTime());
        return result;
    }

    public Training getTraining() { return training; }
}
