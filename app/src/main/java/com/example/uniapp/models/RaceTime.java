package com.example.uniapp.models;

import com.example.uniapp.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RaceTime implements Serializable {
    private String date;
    private String city;
    private String country;
    private String club;
    private int distanceRace;
    private int poolSize;
    private String time;
    private String swim;
    private int pointFFN;
    private String level;
    private int age;
    private String description;

    public RaceTime(String date, String city, String country, String club, int distanceRace, int poolSize, String swim, String time, int pointFFN, String level, int age, String description) {
        this.date = date;
        this.city = city;
        this.country = country;
        this.club = club;
        this.distanceRace = distanceRace;
        this.poolSize = poolSize;
        this.swim = swim;
        this.time = time;
        this.pointFFN = pointFFN;
        this.level = level;
        this.age = age;
        this.description = description;
    }

    public RaceTime() { }

    public static String convertSwim(String swim) {
        switch (swim) {
            case "butterfly":
                return "Papillon";
            case "backstroke":
                return "Dos";
            case "breaststroke":
                return "Brasse";
            case "freestyle":
                return "Nage Libre";
            case "IM":
                return "4 Nages";
        }
        return "SaisPas";
    }

    public static int getCurrentColor(String swim) {
        switch (swim) {
            case "butterfly":
                return R.color.colorSecondary;
            case "backstroke":
                return R.color.greenLight;
            case "breaststroke":
                return R.color.orangeLight;
            case "freestyle":
                return R.color.redLight;
            case "IM":
                return R.color.blueLight;
            default:
                return -1;
        }
    }

    public void setDate(String date) { this.date = date; }
    public void setCity(String city) { this.city = city; }
    public void setClub(String club) { this.club = club; }
    public void setDistanceRace(int distanceRace) { this.distanceRace = distanceRace; }
    public void setPoolSize(int poolSize) { this.poolSize = poolSize; }
    public void setSwim(String swim) { this.swim = swim; }
    public void setDescription(String description) { this.description = description; }
    public void setTime(String time) { this.time = time; }
    public void setCountry(String country) { this.country = country; }
    public void setAge(int age) { this.age = age; }
    public void setLevel(String level) { this.level = level; }

    public String getDate() { return date; }
    public String getCity() { return city; }
    public String getClub() { return club; }
    public int getDistanceRace() { return distanceRace; }
    public int getPoolSize() { return poolSize; }
    public String getSwim() { return swim; }
    public String getDescription() { return description; }
    public String getTime() { return time; }
    public String getCountry() { return country; }
    public int getAge() { return age; }
    public String getLevel() { return level; }
}
