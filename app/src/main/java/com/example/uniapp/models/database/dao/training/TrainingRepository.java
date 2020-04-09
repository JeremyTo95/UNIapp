package com.example.uniapp.models.database.dao.training;

import android.app.Application;

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
    public int getNbElement() { return trainingList.size(); }

    public void insert (final Training training) {
        AppDataBase.dataWriterExecutor.execute(new Runnable() {
            @Override
            public void run() {
                trainingDAO.insertTraining(training);
            }
        });
    }

    public void update (final Training training) {
        AppDataBase.dataWriterExecutor.execute(new Runnable() {
            @Override
            public void run() {
                trainingDAO.updateTraining(training);
            }
        });
    }

    public void delete (final Training training) {
        AppDataBase.dataWriterExecutor.execute(new Runnable() {
            @Override
            public void run() {
                trainingDAO.deleteTraining(training);
            }
        });
    }
}
