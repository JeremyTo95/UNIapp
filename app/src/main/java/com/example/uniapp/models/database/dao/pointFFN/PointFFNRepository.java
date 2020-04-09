package com.example.uniapp.models.database.dao.pointFFN;

import android.app.Application;
import android.view.View;

import androidx.lifecycle.LiveData;

import com.example.uniapp.models.database.AppDataBase;
import com.example.uniapp.models.database.dao.ElementRepertories;

import java.util.List;

public class PointFFNRepository extends ElementRepertories {
    private PointFFNDAO pointFFNDAO;
    private List<PointFFN> pointFFNList;

    public PointFFNRepository(Application application) {
        AppDataBase appDataBase = AppDataBase.getDatabase(application);
        pointFFNDAO = appDataBase.pointFFNDAO();
        pointFFNList = pointFFNDAO.getAllPoints();
    }

    public List<PointFFN> getAllPoints() {
        return pointFFNList;
    }

    public int getNbElement() {
        return ((List<PointFFN>) pointFFNList).size();
    }

    public PointFFN getPointsFFNByGenderDistanceSwimTime(String gender, int distance, String swim, Float time) {
        return (PointFFN) pointFFNDAO.getPointsFFNByGenderDistanceSwimTime(gender, distance, swim, time);
    }

    public void deleteAll() {
        AppDataBase.dataWriterExecutor.execute(new Runnable() {
            @Override
            public void run() {
                pointFFNDAO.deleteAllPointFFN();
            }
        });
    }

    public void insert (final PointFFN pointFFN) {
        AppDataBase.dataWriterExecutor.execute(new Runnable() {
           @Override
           public void run() {
            pointFFNDAO.insertPointFFN(pointFFN);
           }
        });
    }
}
