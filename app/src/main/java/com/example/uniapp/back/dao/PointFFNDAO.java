package com.example.uniapp.back.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.uniapp.front.model.data.PointFFN;

import java.util.List;

@Dao
public interface PointFFNDAO {
    @Query("SELECT * FROM pointFFN")
    List<PointFFN> getAllPoints();

    @Query("SELECT * FROM pointFFN WHERE swim = :swim AND distance = :distance AND gender = :gender AND time >= :time LIMIT 1" )
    PointFFN getPointsFFNByGenderDistanceSwimTime(String gender, int distance, String swim, float time);

    @Query("SELECT count(*) FROM pointFFN")
    int getNb();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PointFFN pointFFN);

    @Delete
    void deletePointFFN(PointFFN pointFFN);

    @Query("DELETE FROM pointFFN")
    void deleteAll();
}
