package com.example.uniapp.back.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.uniapp.front.model.data.Training;

import java.util.List;

@Dao
public interface TrainingDAO {
    @Query("SELECT * FROM training")
    List<Training> getAllTrainings();

    @Query("SELECT count(*) FROM training")
    int getNb();

    @Update
    void updateTraining(Training training);

    @Insert
    void insertTraining(Training training);

    @Delete
    void deleteTraining(Training training);

    @Query("DELETE FROM training")
    void deleteAll();
}
