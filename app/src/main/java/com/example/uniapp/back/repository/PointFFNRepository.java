package com.example.uniapp.back.repository;

import android.content.Context;

import com.example.uniapp.back.dao.PointFFNDAO;
import com.example.uniapp.back.executor.AppExecutors;
import com.example.uniapp.back.room.RoomDataBase;
import com.example.uniapp.front.model.data.PointFFN;

import java.util.ArrayList;
import java.util.List;

public class PointFFNRepository extends ElementRepertory {
    private RoomDataBase roomDataBase;
    private AppExecutors appExecutors;
    private PointFFNDAO  pointFFNDAO;

    private List<PointFFN> pointFFNList;
    private PointFFN pointFFN;
    private int pointFFnListSize;

    public PointFFNRepository(Context context) {
        roomDataBase = RoomDataBase.getDatabase(context);
        appExecutors = AppExecutors.getInstance(context);
        pointFFNDAO  = roomDataBase.pointFFNDAO();
    }

    public List<PointFFN> getAllPoints() {
        return pointFFNDAO.getAllPoints();
    }

    public int getNb() {
        return pointFFNDAO.getNb();
    }

    public PointFFN getPointsFFNByGenderDistanceSwimTime(String gender, int distance, String swim, Float time) {
        return pointFFNDAO.getPointsFFNByGenderDistanceSwimTime(gender, distance, swim, time);
    }

    public void deleteAll() {
        pointFFNDAO.deleteAll();
    }

    public void insert (final PointFFN pointFFN) {
        pointFFNDAO.insert(pointFFN);
    }
}
