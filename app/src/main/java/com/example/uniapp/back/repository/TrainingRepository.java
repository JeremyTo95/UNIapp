package com.example.uniapp.back.repository;

import android.app.Application;

import com.example.uniapp.back.dao.TrainingDAO;
import com.example.uniapp.back.room.RoomDataBase;
import com.example.uniapp.front.model.data.Training;

import java.util.List;

public class TrainingRepository extends ElementRepertory {
    private TrainingDAO trainingDAO;
    private List<Training> trainingList;

    public TrainingRepository(RoomDataBase roomDataBase) {
        trainingDAO  = roomDataBase.trainingDAO();
        trainingList = trainingDAO.getAllTrainings();
    }

    public List<Training> getAllTrainings() {
        return trainingList;
    }

    @Override
    public int getNb() { return trainingDAO.getNb(); }

    public void insert (final Training training) {
        System.out.println("Before : " + trainingDAO.getNb());
        trainingDAO.insertTraining(training);
        System.out.println("After  : " + trainingDAO.getNb());
        System.out.println("AfterBis : " + trainingDAO.getNb());
    }

    public void update (final Training training) {
        trainingDAO.updateTraining(training);
    }

    public void delete (final Training training) {
        trainingDAO.deleteTraining(training);
    }
}
