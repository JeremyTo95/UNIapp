package com.example.uniapp.models;

import android.util.Log;

import java.util.List;

public class MarketTimes {

    public static int convertTimeToInt(String time) { return Integer.parseInt(time.replaceAll("[:.]", "").toString()); }
    public static String convertIntToTime(int time) { return String.valueOf(time/10000%100) + ":" + String.valueOf(time/100%100) + "." + String.valueOf(time%100); }

    public static String convertCompetitionTimeToZoneTime(float time, int zone) {
        float timeFloat = time;
        return fetchFloatToTime(timeFloat/convertZoneToPercent(zone));
    }

    public static float convertZoneToPercent(int zone) {
        if (zone == 1) return 0.60f;
        else if (zone == 2) return 0.65f;
        else if (zone == 3) return 0.75f;
        else if (zone == 4) return 0.80f;
        else if (zone == 5) return 0.85f;
        else if (zone == 6) return 0.90f;
        else if (zone == 7) return 0.95f;
        else return 0;
    }

    public static String compareTwoTimes(float raceDown, float race, float raceUp) {
        String result = "";
        if (race > raceUp) result = String.format("(+%.2fs)", (race - raceUp));
        else result = String.format("(-%.2fs)", (raceDown - race));
        return result;
    }

    public static float fetchTimeToFloat(String time) {
        float myTime = 0.0f;
        if (time.length() == 8) {
            myTime += Float.parseFloat(String.valueOf(time.charAt(0))) * 10 * 60;
            myTime += Float.parseFloat(String.valueOf(time.charAt(1))) *  1 * 60;
            myTime += Float.parseFloat(String.valueOf(time.charAt(3))) * 10;
            myTime += Float.parseFloat(String.valueOf(time.charAt(4))) *  1;
            myTime += Float.parseFloat(String.valueOf(time.charAt(6))) / 10;
            myTime += Float.parseFloat(String.valueOf(time.charAt(7))) / 100;
        }
        return myTime;
    }

    public static String fetchFloatToTime(float time) {
        int myTime = 0;
        myTime = (int) (time * 100);
        String ms  = String.valueOf(myTime%100);
        String sec = String.valueOf((myTime/100%60));
        String min = String.valueOf(myTime/6000);

        if (min.length() < 2) min = "0" + min;
        if (sec.length() < 2) sec = "0" + sec;
        if (ms.length() < 2)  ms = "0" + ms;

        return (min + ":" + sec + ":" + ms);
    }

    public static String convertTimeToFormat(String time) {
        Log.e("M", time);
        String newTime = time;
        int timeSize = time.length();
        if (timeSize != 8 && timeSize != 0) {
            for (int i = timeSize; i < 8; i++) {
                Log.e("M", newTime);
                if      (8 - i == 3) newTime = new StringBuilder(newTime).insert(0, ":").toString();
                else if (8 - i == 6) newTime = new StringBuilder(newTime).insert(0, ".").toString();
                else                 newTime = new StringBuilder(newTime).insert(0, "0").toString();
            }
        }
        return newTime;
    }

    public static String getBestTimes(List<String> allTimes) {
        String bestTime = allTimes.get(0);
        for (int i = 0; i < allTimes.size(); i++) {
            if (convertTimeToInt(bestTime) > convertTimeToInt(allTimes.get(i))) bestTime = allTimes.get(i);
        }
        return bestTime;
    }

    public static long convertTimeToLongMilli(String timeStr) {
        long timeLong = 0;
        int min = Integer.parseInt(String.valueOf(timeStr.charAt(timeStr.length() - 8))) * 10 + Integer.parseInt(String.valueOf(timeStr.charAt(timeStr.length() - 7)));
        int sec = Integer.parseInt(String.valueOf(timeStr.charAt(timeStr.length() - 5))) * 10 + Integer.parseInt(String.valueOf(timeStr.charAt(timeStr.length() - 4)));
        int mil = Integer.parseInt(String.valueOf(timeStr.charAt(timeStr.length() - 2))) * 10 + Integer.parseInt(String.valueOf(timeStr.charAt(timeStr.length() - 1)));

        // System.out.println("min : " + min + " | sec : " + sec + " | mil : " + mil);

        timeLong = mil + sec * 100 + min * 60 * 100;

        return timeLong;
    }

    public static String convertLongMilliToTime(long timeLong) {
        int mil = (int) timeLong%100;
        int sec = (int) (timeLong/100)%60;
        int min = (int) (timeLong/6000);

        // System.out.println("min : " + min + " | sec : " + sec + " | mil : " + mil);

        String milStr = (mil < 10) ? ("0" + mil) : String.valueOf(mil);
        String secStr = (sec < 10) ? ("0" + sec) : String.valueOf(sec);
        String minStr = (min < 10) ? ("0" + min) : String.valueOf(min);

        return (minStr + ":" + secStr + "." + milStr);
    }

    public static Long convertTimerToLong(String timer) {
        String[] timerSplit = timer.split(":");
        long time = 0;// Integer.parseInt(String.valueOf(timerSplit[0])) * 60;
        for (int i = 0; i < timerSplit.length; i++) {
            time += Integer.parseInt(String.valueOf(timerSplit[i])) * ((long) Math.pow(60, (timerSplit.length - (i + 1))));
        }
        return time;
    }

    public static String converLongToTimer(Long time) {
        String min = (String.valueOf(time/60).length() == 1) ? ("0" + time/60) : String.valueOf(time/60);
        String sec = (String.valueOf(time%60).length() == 1) ? ("0" + time%60) : String.valueOf(time%60);
        return min + ":" + sec;
    }
}
