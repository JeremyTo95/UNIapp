package com.example.uniapp.models.database.dao.training;

import android.util.Log;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.json.JSONException;

import java.util.List;

@Dao
public interface TrainingDAO {
    @Query("SELECT * FROM training")
    List<Training> getAllTrainings();

    @Query("SELECT count(*) FROM training")
    int getNbTraining();

    @Update
    void updateTraining(Training training);

    @Insert
    void insertTraining(Training training);

    @Delete
    void deleteTraining(Training training);

    @Query("DELETE FROM training")
    void deleteAll();
}
