package com.example.uniapp.models.database.dao.training;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.uniapp.models.database.AppDataBase;
import com.example.uniapp.models.database.dao.ElementRepertories;
import com.example.uniapp.models.database.dao.pointFFN.PointFFN;
import com.example.uniapp.models.database.dao.pointFFN.PointFFNDAO;

import java.util.List;

public class TrainingRepository extends ElementRepertories {
    private TrainingDAO trainingDAO;
    private List<Training> trainingList;

    public TrainingRepository(Application application) {
        AppDataBase appDataBase = AppDataBase.getDatabase(application);
        trainingDAO = appDataBase.trainingDAO();
        trainingList = trainingDAO.getAllTrainings();
    }

    public List<Training> getAllTrainings() {
        return trainingList;
    }

    @Override
    public int getNbElement() { return trainingDAO.getNb(); }

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
