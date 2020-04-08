package com.example.uniapp.models.database.dao.pointFFN;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

@Dao
public interface PointFFNDAO {
    @Query("SELECT * FROM pointFFN")
    List<PointFFN> getAllPoints();

    @Query("SELECT * FROM pointFFN WHERE swim = :swim AND distance = :distance AND gender = :gender AND time >= :time LIMIT 1" )
    PointFFN getPointsFFNByGenderDistanceSwimTime(String gender, int distance, String swim, float time);

    @Query("SELECT count(*) FROM pointFFN")
    int getNbPoint();

    @Insert
    void insertPointFFN(PointFFN pointFFN);

    @Delete
    void deletePointFFN(PointFFN pointFFN);
}
