package com.example.uniapp.views.comparators;

import com.example.uniapp.models.MarketRaces;
import com.example.uniapp.models.Race;

import java.util.Comparator;

public class TimeComparator implements Comparator<Race> {
    private float t1;
    private float t2;

    @Override
    public int compare(Race o1, Race o2) {
        t1 = Race.fetchTimeToFloat(o1.getTime());
        t2 = Race.fetchTimeToFloat(o2.getTime());
        if (t1 > t2) return 1;
        else if (t1 == t2) return 0;
        else return -1;
    }
}
