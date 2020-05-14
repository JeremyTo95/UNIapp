package com.example.uniapp.front.controller.comparator;

import com.example.uniapp.front.model.data.Training;

import java.util.ArrayList;
import java.util.Comparator;

public class TrainingDateComparator implements Comparator<Training> {
    private int getYearValue(String str) {
        String[] splitString = str.split("/");
        String year = splitString[2];
        return Integer.parseInt(year) * 365;
    }
    private int getMonthValue(String str) {
        String[] splitString = str.split("/");
        String month = splitString[1];
        return Integer.parseInt(month) * 31;
    }
    private int getDayValue(String str) {
        String[] splitString = str.split("/");
        String day = splitString[0];
        return Integer.parseInt(day);
    }
    @Override
    public int compare(Training o1, Training o2) {
        int cptO1 = getDayValue(o1.getDate()) + getMonthValue(o1.getDate()) + getYearValue(o1.getDate());
        int cptO2 = getDayValue(o2.getDate()) + getMonthValue(o2.getDate()) + getYearValue(o2.getDate());

        return Integer.compare(cptO2, cptO1);
    }
}
