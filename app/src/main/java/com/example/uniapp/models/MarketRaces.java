package com.example.uniapp.models;

import android.util.Log;

import com.example.uniapp.models.database.dao.race.Race;
import com.example.uniapp.views.comparators.RaceDateComparator;
import com.example.uniapp.views.comparators.TimeComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class MarketRaces {

    public MarketRaces() { }

    public static Race getBestTime(List<Race> allTimes, int position) {
        Race bestTime;

        if (allTimes != null && allTimes.size() != 0 && (position - 1) >= 0 && allTimes.size() > (position - 1)) {
            Collections.sort(allTimes, new TimeComparator());
            bestTime = allTimes.get(position - 1);
            Collections.sort(allTimes, new RaceDateComparator());
        }
        else if (allTimes != null && allTimes.size() != 0) {
            Collections.sort(allTimes, new TimeComparator());
            bestTime = allTimes.get(0);
            Collections.sort(allTimes, new RaceDateComparator());
        } else {
            bestTime = new Race();
            bestTime.setTime(0.0f);
        }
        return bestTime;
    }

    public static void sortRacesByDate(List<Race> allTimes) {
        RaceDateComparator raceDateComparator = new RaceDateComparator();
        Collections.sort(allTimes, raceDateComparator);
    }
}
