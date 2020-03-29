package com.example.uniapp.models;

import com.example.uniapp.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Race implements Serializable {
    private UUID id;
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

    public Race(UUID id, String date, String city, String country, String club, int distanceRace, int poolSize, String swim, String time, int pointFFN, String level, int age, String description) {
        this.id = id;
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

    public Race() { }

    public static String convertSwimFromEnglishToFrench(String swim) {
        switch (swim.toLowerCase()) {
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

    public static String convertShortSwim(String swim) {
        System.out.println(swim);
        if (swim.equals("butterfly")) return "pap";
        else if (swim.equals("backstroke")) return "dos";
        else if (swim.equals("breaststroke")) return "br";
        else if (swim.equals("freestyle")) return "NL";
        else if (swim.equals("IM")) return "4N";
        else return "JSP";
    }

    public static String convertSwimFromFrenchToEnglish(String swim) {
        swim = swim.toLowerCase();
        if (swim.equals("papillon")) return "butterfly";
        else if (swim.equals("dos")) return "backstroke";
        else if (swim.equals("brasse")) return "breaststroke";
        else if (swim.equals("nage libre")) return "freestyle";
        else if (swim.equals("4 nages")) return "IM";
        else return "JSP";
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

    public static float fetchTimeToFloat(String time) {
        float myTime = 0.0f;
        if (time.length() == 8) {
            myTime += Float.parseFloat(String.valueOf(time.charAt(0) )) * 10 * 60;
            myTime += Float.parseFloat(String.valueOf(time.charAt(1))) * 60;
            myTime += Float.parseFloat(String.valueOf(time.charAt(3))) * 10;
            myTime += Float.parseFloat(String.valueOf(time.charAt(4)));
            myTime += Float.parseFloat(String.valueOf(time.charAt(6))) / 10;
            myTime += Float.parseFloat(String.valueOf(time.charAt(7))) / 100;
        }
        return myTime;
    }

    public void setId(UUID id) { this.id = id; }
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
    public void setPointFFN(int pointFFN) { this.pointFFN = pointFFN; }

    public UUID   getId() { return id; }
    public String getDate() { return date; }
    public String getCity() { return city; }
    public String getClub() { return club; }
    public int    getDistanceRace() { return distanceRace; }
    public int    getPoolSize() { return poolSize; }
    public String getSwim() { return swim; }
    public String getDescription() { return description; }
    public String getTime() { return time; }
    public String getCountry() { return country; }
    public int    getAge() { return age; }
    public String getLevel() { return level; }
    public int    getPointFFN() { return pointFFN; }
}
