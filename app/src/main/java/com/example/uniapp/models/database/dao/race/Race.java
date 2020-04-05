package com.example.uniapp.models.database.dao.race;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.uniapp.R;
import com.example.uniapp.controllers.activities.MainActivity;
import com.example.uniapp.models.database.AppDataBase;
import com.example.uniapp.models.database.dao.pointFFN.PointFFN;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

    public static void makeRaceApiCall(final Context context) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppDataBase.URL_DATA)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        final RaceAPI raceAPI = retrofit.create(RaceAPI.class);

        Call<List<Race>> call = raceAPI.getResponseRace();
        call.enqueue(new Callback<List<Race>>() {
            @Override
            public void onFailure(Call<List<Race>> call, Throwable t) { Toast.makeText(context, "API call failed : failure", Toast.LENGTH_SHORT).show(); }
            @Override
            public void onResponse(Call<List<Race>> call, Response<List<Race>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Race> raceList = response.body();
                    Log.e("SIZE RACE", raceList.size() + "races found");
                    if (MainActivity.appDataBase.raceDAO().getNbRaces() == 0) {
                        Toast.makeText(context, "Races are loading...", Toast.LENGTH_LONG).show();
                        for (int i = 0; i < raceList.size(); i++) {
                            //System.out.println("point : " + pointFFNList.get(i).getPoint() + " distance : " + pointFFNList.get(i).getDistance() + " swim : " + pointFFNList.get(i).getSwim() + " time : " + pointFFNList.get(i).getTime());
                            MainActivity.appDataBase.raceDAO().insertRace(raceList.get(i));
                        }
                    } else {
                        Toast.makeText(context,  raceList.size() + " are already loaded", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(context,  raceList.size() + " points loaded", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "API call failed : " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
