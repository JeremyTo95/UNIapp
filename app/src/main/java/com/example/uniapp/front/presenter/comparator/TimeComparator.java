package com.example.uniapp.front.presenter.comparator;

import com.example.uniapp.front.model.data.Race;

import java.util.Comparator;

public class TimeComparator implements Comparator<Race> {
    @Override
    public int compare(Race o1, Race o2) {
        float t1 = o1.getTime();
        float t2 = o2.getTime();
        return Float.compare(t1, t2);
    }
}
