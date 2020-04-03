package com.example.uniapp.views.comparators;

import com.example.uniapp.models.MarketRaces;
import com.example.uniapp.models.MarketTimes;
import com.example.uniapp.models.Race;

import java.util.Comparator;

public class TimeComparator implements Comparator<Race> {
    private float t1;
    private float t2;

    @Override
    public int compare(Race o1, Race o2) {
        t1 = MarketTimes.fetchTimeToFloat(o1.getTime());
        t2 = MarketTimes.fetchTimeToFloat(o2.getTime());
        if (t1 > t2) return 1;
        else if (t1 == t2) return 0;
        else return -1;
    }
}
