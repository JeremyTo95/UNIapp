package com.example.uniapp.back.repository;

import com.example.uniapp.back.dao.RaceDAO;
import com.example.uniapp.back.room.RoomDataBase;
import com.example.uniapp.front.model.data.Race;

import java.util.List;

public class RaceRepository extends ElementRepertory {
    private RaceDAO raceDAO;
    private List<Race> raceList;

    public RaceRepository(RoomDataBase roomDataBase) {
        raceDAO  = roomDataBase.raceDAO();
        raceList = raceDAO.getAllRaces();
    }

    public List<Race> getRaces() {
        return raceList;
    }

    @Override
    public int getNb() { return raceList.size(); }

    public List<Race> getRacesByPoolSizeDistanceRaceSwimRace(int sizePool, int distance, String swim) {
        return raceDAO.getRacesByPoolSizeDistanceRaceSwimRace(sizePool, distance, swim);
    }

    public void insert (final Race race) {
        raceDAO.insert(race);
    }

    public void delete (final Race race) {
        raceDAO.delete(race);
    }
}
