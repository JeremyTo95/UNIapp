package com.example.uniapp.front.model.market;

import com.example.uniapp.front.model.data.Race;
import com.example.uniapp.front.controller.comparator.RaceDateComparator;
import com.example.uniapp.front.controller.comparator.TimeComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public static List<Float> getFloatTimes(List<Race> allRaces) {
        List<Float> allTimes = new ArrayList<>();
        for (int i = 0; i < allRaces.size(); i++) {
            allTimes.add(allRaces.get(allRaces.size() - (i + 1)).getTime());
        }
        return allTimes;
    }

    public static void sortRacesByDate(List<Race> allTimes) {
        RaceDateComparator raceDateComparator = new RaceDateComparator();
        Collections.sort(allTimes, raceDateComparator);
    }
}
