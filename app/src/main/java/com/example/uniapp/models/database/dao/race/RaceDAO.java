package com.example.uniapp.models.database.dao.race;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RaceDAO {
    @Query("SELECT * FROM race")
    List<Race> getAllRaces();

    @Insert
    void insertRace(Race race);

    @Delete
    void delete(Race race);

    @Query("DELETE FROM race")
    void deleteAll();
}
