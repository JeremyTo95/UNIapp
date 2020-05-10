package com.example.uniapp.back.repository;

import com.example.uniapp.back.dao.PointFFNDAO;
import com.example.uniapp.back.room.RoomDataBase;
import com.example.uniapp.front.model.data.PointFFN;

import java.util.List;

public class PointFFNRepository extends ElementRepertory {
    private PointFFNDAO pointFFNDAO;
    private List<PointFFN> pointFFNList;

    public PointFFNRepository(RoomDataBase roomDatabase) {
        pointFFNDAO  = roomDatabase.pointFFNDAO();
        pointFFNList = pointFFNDAO.getAllPoints();
    }

    public List<PointFFN> getAllPoints() {
        return pointFFNList;
    }

    public int getNb() { return pointFFNDAO.getNb(); }

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
