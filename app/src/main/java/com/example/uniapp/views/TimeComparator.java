package com.example.uniapp.views;

import com.example.uniapp.models.MarketRaceTime;
import com.example.uniapp.models.RaceTime;

import java.util.Comparator;

public class TimeComparator implements Comparator<RaceTime> {
    private float t1;
    private float t2;

    @Override
    public int compare(RaceTime o1, RaceTime o2) {
        t1 = MarketRaceTime.fetchTimeToFloat(o1.getTime());
        t2 = MarketRaceTime.fetchTimeToFloat(o2.getTime());
        if (t1 > t2) return 1;
        else if (t1 == t2) return 0;
        else return -1;
    }
}
