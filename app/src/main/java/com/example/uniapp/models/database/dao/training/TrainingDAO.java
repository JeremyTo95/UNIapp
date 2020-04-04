package com.example.uniapp.models.database.dao.training;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TrainingDAO {
    @Query("SELECT * FROM training")
    List<Training> getAllTrainings();

    @Insert
    void insertTraining(Training training);

    @Delete
    void deleteTraining(Training training);

    @Query("DELETE FROM training")
    void deleteAll();
}
