package com.example.uniapp.views;

import com.example.uniapp.R;
import com.example.uniapp.controllers.activities.MainActivity;
import com.example.uniapp.models.MarketRaces;
import com.example.uniapp.models.MarketTimes;
import com.example.uniapp.models.database.dao.race.Race;

import java.util.List;

public class SwimCards {
    private String titleSwim;
    private String time50, time100, time200;
    private String time400, time800, time1500;
    private int gradientBackground;
    private int colorText;

    public SwimCards(List<Race> allTimes, String titleSwim, int poolSize) {
        this.titleSwim = titleSwim;
        if (titleSwim == "butterfly")    loadButterfly(allTimes, poolSize);
        if (titleSwim == "backstroke")   loadBackstroke(allTimes, poolSize);
        if (titleSwim == "breaststroke") loadBreaststroke(allTimes, poolSize);
        if (titleSwim == "freestyle")    loadFreestyle(allTimes, poolSize);
        if (titleSwim == "IM")           loadIM(allTimes, poolSize);
    }

    public void loadButterfly(List<Race> allTimes, int poolSize) {
        List<Race> times50   = MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,50, "butterfly");
        List<Race> times100  = MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,100, "butterfly");
        List<Race> times200  = MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,200,"butterfly");
        Race best50          = MarketRaces.getBestTime(times50, 1);
        Race best100         = MarketRaces.getBestTime(times100, 1);
        Race best200         = MarketRaces.getBestTime(times200, 1);
        titleSwim            = "P a p i l l o n";
        time50               = MarketTimes.fetchFloatToTime(best50.getTime());
        time100              = MarketTimes.fetchFloatToTime(best100.getTime());
        time200              = MarketTimes.fetchFloatToTime(best200.getTime());
        colorText            = R.color.colorSecondary;
    }

    public void loadBackstroke(List<Race> allTimes, int poolSize) {
        List<Race> times50   = MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,50, "backstroke");
        List<Race> times100  = MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,100, "backstroke");
        List<Race> times200  = MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,200,"backstroke");
        Race best50          = MarketRaces.getBestTime(times50, 1);
        Race best100         = MarketRaces.getBestTime(times100, 1);
        Race best200         = MarketRaces.getBestTime(times200, 1);
        titleSwim            = "D o s";
        time50               = MarketTimes.fetchFloatToTime(best50.getTime());
        time100              = MarketTimes.fetchFloatToTime(best100.getTime());
        time200              = MarketTimes.fetchFloatToTime(best200.getTime());
        colorText            = R.color.greenLight;
    }

    public void loadBreaststroke(List<Race> allTimes, int poolSize) {
        List<Race> times50   = MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,50, "breaststroke");
        List<Race> times100  = MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,100, "breaststroke");
        List<Race> times200  = MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,200,"breaststroke");
        Race best50          = MarketRaces.getBestTime(times50, 1);
        Race best100         = MarketRaces.getBestTime(times100, 1);
        Race best200         = MarketRaces.getBestTime(times200, 1);
        titleSwim            = "B r a s s e";
        time50               = MarketTimes.fetchFloatToTime(best50.getTime());
        time100              = MarketTimes.fetchFloatToTime(best100.getTime());
        time200              = MarketTimes.fetchFloatToTime(best200.getTime());
        colorText            = R.color.orangeLight;
    }

    public void loadFreestyle(List<Race> allTimes, int poolSize) {
        List<Race> times50   = MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,50, "freestyle");
        List<Race> times100  = MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,100, "freestyle");
        List<Race> times200  = MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,200,"freestyle");
        List<Race> times400  = MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,400, "freestyle");
        List<Race> times800  = MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,800,"freestyle");
        List<Race> times1500 = MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,1500,"freestyle");
        Race best50          = MarketRaces.getBestTime(times50, 1);
        Race best100         = MarketRaces.getBestTime(times100, 1);
        Race best200         = MarketRaces.getBestTime(times200, 1);
        Race best400         = MarketRaces.getBestTime(times400, 1);
        Race best800         = MarketRaces.getBestTime(times800, 1);
        Race best1500        = MarketRaces.getBestTime(times1500, 1);
        titleSwim            = "N a g e  L i b r e";
        time50               = MarketTimes.fetchFloatToTime(best50.getTime());
        time100              = MarketTimes.fetchFloatToTime(best100.getTime());
        time200              = MarketTimes.fetchFloatToTime(best200.getTime());
        time400              = MarketTimes.fetchFloatToTime(best400.getTime());
        time800              = MarketTimes.fetchFloatToTime(best800.getTime());
        time1500             = MarketTimes.fetchFloatToTime(best1500.getTime());
        colorText            = R.color.redLight;
    }

    public void loadIM(List<Race> allTimes, int poolSize) {
        Race best100;
        Race best200;
        Race best400;
        if (poolSize == 25) {
            List<Race> times100 = MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize, 100,"IM");
            List<Race> times200 = MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize, 200, "IM");
            List<Race> times400 = MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize, 400, "IM");
            best100             = MarketRaces.getBestTime(times100, 1);
            best200             = MarketRaces.getBestTime(times200, 1);
            best400             = MarketRaces.getBestTime(times400, 1);
            time100             = MarketTimes.fetchFloatToTime(best100.getTime());
        } else {
            List<Race> times200 = MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize, 200, "IM");
            List<Race> times400 = MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize, 400, "IM");
            best200             = MarketRaces.getBestTime(times200, 1);
            best400             = MarketRaces.getBestTime(times400, 1);
        }
        titleSwim = "4  N a g e s";
        time200   = MarketTimes.fetchFloatToTime(best200.getTime());
        time400   = MarketTimes.fetchFloatToTime(best400.getTime());
        colorText = R.color.blueLight;
    }

    public void setTitleSwim(String titleSwim) { this.titleSwim = titleSwim; }
    public void setTime50(String time50) { this.time50 = time50; }
    public void setTime100(String time100) { this.time100 = time100; }
    public void setTime200(String time200) { this.time200 = time200; }
    public void setTime400(String time400) { this.time400 = time400; }
    public void setTime800(String time800) { this.time800 = time800; }
    public void setTime1500(String time1500) { this.time1500 = time1500; }
    public void setGradientBackground(int gradientBackground) { this.gradientBackground = gradientBackground; }
    public void setColorText(int colorText) { this.colorText = colorText; }

    public String getTitleSwim() { return titleSwim; }
    public String getTime50() { return time50; }
    public String getTime100() { return time100; }
    public String getTime200() { return time200; }
    public String getTime400() { return time400; }
    public String getTime800() { return time800; }
    public String getTime1500() { return time1500; }
    public int getGradientBackground() { return gradientBackground; }
    public int getColorText() { return colorText; }

}
