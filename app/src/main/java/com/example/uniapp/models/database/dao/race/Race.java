package com.example.uniapp.models.database.dao.race;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.uniapp.R;
import com.example.uniapp.models.database.dao.user.User;
import com.example.uniapp.utils.ImportRacesTask;
import com.example.uniapp.views.AboutScreen;

import java.io.Serializable;

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

    @ColumnInfo(name = "distance")
    private int distance;

    @ColumnInfo(name = "sizePool")
    private int sizePool;

    @ColumnInfo(name = "time")
    private float time;

    @ColumnInfo(name = "swim")
    private String swim;

    @ColumnInfo(name = "level")
    private String level;


    public Race(String id, String date, String city, String country, String club, int distance, int sizePool, String swim, float time, String level) {
        this.id = id;
        this.date = date;
        this.city = city;
        this.country = country;
        this.club = club;
        this.distance = distance;
        this.sizePool = sizePool;
        this.swim = swim;
        this.time = time;
        this.level = level;
    }

    @Ignore
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

    public static int getCurrentColor(Context context, String swim) {
        switch (swim) {
            case "butterfly":
                return AboutScreen.getColorByThemeAttr(context, R.attr.secondaryColor, R.color.colorSecondaryLight);
            case "backstroke":
                return AboutScreen.getColorByThemeAttr(context, R.attr.greenLightColor, R.color.greenLight);
            case "breaststroke":
                return AboutScreen.getColorByThemeAttr(context, R.attr.orangeLightColor, R.color.orangeLight);
            case "freestyle":
                return AboutScreen.getColorByThemeAttr(context, R.attr.redLightColor, R.color.redLight);
            case "IM":
                return AboutScreen.getColorByThemeAttr(context, R.attr.blueLightColor, R.color.blueLight);
            default:
                return -1;
        }
    }

    public static void startAsyncTaskLoadingRace(Activity activity, User user) {
        Log.e("Function", "async task race");
        ImportRacesTask importRacesTask = new ImportRacesTask(activity, user);
        importRacesTask.execute();
    }

    public void setId(String id) { this.id = id; }
    public void setDate(String date) { this.date = date; }
    public void setCity(String city) { this.city = city; }
    public void setDistance(int distance) { this.distance = distance; }
    public void setSizePool(int sizePool) { this.sizePool = sizePool; }
    public void setSwim(String swim) { this.swim = swim; }
    public void setTime(float time) { this.time = time; }
    public void setCountry(String country) { this.country = country; }
    public void setLevel(String level) { this.level = level; }
    public void setClub(String club) { this.club = club; }

    public String getId() { return id; }
    public String getDate() { return date; }
    public String getCity() { return city; }
    public int getDistance() { return distance; }
    public int getSizePool() { return sizePool; }
    public String getSwim() { return swim; }
    public float getTime() { return time; }
    public String getCountry() { return country; }
    public String getLevel() { return level; }
    public String getClub() { return club; }
}
