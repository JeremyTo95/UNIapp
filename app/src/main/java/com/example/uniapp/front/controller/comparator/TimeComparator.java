package com.example.uniapp.front.controller.comparator;

import com.example.uniapp.front.model.data.Race;

import java.util.Comparator;

public class TimeComparator implements Comparator<Race> {
    private float t1;
    private float t2;

    @Override
    public int compare(Race o1, Race o2) {
        t1 = o1.getTime();
        t2 = o2.getTime();
        if (t1 > t2) return 1;
        else if (t1 == t2) return 0;
        else return -1;
    }
}
