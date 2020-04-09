package com.example.uniapp.models.database.dao.race;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RaceDAO {
    @Query("SELECT * FROM race")
    List<Race> getAllRaces();

    @Query("SELECT count(*) FROM race")
    int getNbRaces();

    @Query("SELECT * FROM race WHERE sizePool = :poolSize AND distance = :distance AND swim = :swim")
    List<Race> getRacesByPoolSizeDistanceRaceSwimRace(int poolSize, int distance, String swim);

    @Insert
    void insertRace(Race race);

    @Delete
    void delete(Race race);

    @Query("DELETE FROM race")
    void deleteAll();
}
