package com.example.uniapp.models.database.dao.race;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.uniapp.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(tableName = "race")
public class Race implements Serializable {
    @NonNull
    @PrimaryKey
    private String id;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "city")
    private String city;

    @ColumnInfo(name = "country")
    private String country;

    @ColumnInfo(name = "club")
    private String club;

    @ColumnInfo(name = "distance_race")
    private int distanceRace;

    @ColumnInfo(name = "sizePool")
    private int poolSize;

    @ColumnInfo(name = "time")
    private String time;

    @ColumnInfo(name = "swim")
    private String swim;

    @ColumnInfo(name = "pointFFN")
    private int pointFFN;

    @ColumnInfo(name = "level")
    private String level;


    public Race(String id, String date, String city, String country, String club, int distanceRace, int poolSize, String swim, String time, int pointFFN, String level) {
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

    public void setId(String id) { this.id = id; }
    public void setDate(String date) { this.date = date; }
    public void setCity(String city) { this.city = city; }
    public void setClub(String club) { this.club = club; }
    public void setDistanceRace(int distanceRace) { this.distanceRace = distanceRace; }
    public void setPoolSize(int poolSize) { this.poolSize = poolSize; }
    public void setSwim(String swim) { this.swim = swim; }
    public void setTime(String time) { this.time = time; }
    public void setCountry(String country) { this.country = country; }
    public void setLevel(String level) { this.level = level; }
    public void setPointFFN(int pointFFN) { this.pointFFN = pointFFN; }

    public String getId() { return id; }
    public String getDate() { return date; }
    public String getCity() { return city; }
    public String getClub() { return club; }
    public int    getDistanceRace() { return distanceRace; }
    public int    getPoolSize() { return poolSize; }
    public String getSwim() { return swim; }
    public String getTime() { return time; }
    public String getCountry() { return country; }
    public String getLevel() { return level; }
    public int    getPointFFN() { return pointFFN; }
}
