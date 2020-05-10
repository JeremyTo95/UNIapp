package com.example.uniapp.back.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.uniapp.front.model.data.Race;

import java.util.List;

@Dao
public interface RaceDAO {
    @Query("SELECT * FROM race")
    List<Race> getAllRaces();

    @Query("SELECT count(*) FROM race")
    int getNb();

    @Query("SELECT * FROM race WHERE sizePool = :poolSize AND distance = :distance AND swim = :swim")
    List<Race> getRacesByPoolSizeDistanceRaceSwimRace(int poolSize, int distance, String swim);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Race race);

    @Delete
    void delete(Race race);

    @Query("DELETE FROM race")
    void deleteAll();
}
