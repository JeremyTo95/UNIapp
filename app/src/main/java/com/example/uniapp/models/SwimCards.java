package com.example.uniapp.models;

import android.graphics.drawable.Drawable;

import com.example.uniapp.R;

import java.util.List;

public class SwimCards {
    private String titleSwim;
    private String time50, time100, time200;
    private String time400, time800, time1500;
    private int gradientBackground;
    private int colorText;

    private List<RaceTime> allTimes;

    public SwimCards(List<RaceTime> allTimes, String titleSwim, int poolSize) {
        this.titleSwim = titleSwim;

        switch (titleSwim) {
            case "butterfly":
                loadButterfly(allTimes, poolSize);
                break;

            case "backstroke":
                loadBackstroke(allTimes, poolSize);
                break;

            case "breaststroke":
                loadBreaststroke(allTimes, poolSize);
                break;

            case "freestyle":
                loadFreestyle(allTimes, poolSize);
                break;

            case "IM":
                load4N(allTimes, poolSize);
                break;

            default:
                break;
        }
    }

    public void loadButterfly(List<RaceTime> allTimes, int poolSize) {
        List<RaceTime> times50   = MarketRaceTime.getRacesByPoolSizeDistanceRaceSwimRace(allTimes, poolSize,50, "butterfly");
        List<RaceTime> times100  = MarketRaceTime.getRacesByPoolSizeDistanceRaceSwimRace(allTimes, poolSize,100, "butterfly");
        List<RaceTime> times200  = MarketRaceTime.getRacesByPoolSizeDistanceRaceSwimRace(allTimes, poolSize,200,"butterfly");
        RaceTime best50  = MarketRaceTime.getBestTime(times50, 1);
        RaceTime best100 = MarketRaceTime.getBestTime(times100, 1);
        RaceTime best200 = MarketRaceTime.getBestTime(times200, 1);
        titleSwim = "P a p i l l o n";
        time50  = best50.getTime();
        time100 = best100.getTime();
        time200 = best200.getTime();
        gradientBackground = R.drawable.sh_gradient_blue;
        colorText = R.color.colorSecondary;
    }

    public void loadBackstroke(List<RaceTime> allTimes, int poolSize) {
        List<RaceTime> times50   = MarketRaceTime.getRacesByPoolSizeDistanceRaceSwimRace(allTimes, poolSize,50, "backstroke");
        List<RaceTime> times100  = MarketRaceTime.getRacesByPoolSizeDistanceRaceSwimRace(allTimes, poolSize,100, "backstroke");
        List<RaceTime> times200  = MarketRaceTime.getRacesByPoolSizeDistanceRaceSwimRace(allTimes, poolSize,200,"backstroke");
        RaceTime best50  = MarketRaceTime.getBestTime(times50, 1);
        RaceTime best100 = MarketRaceTime.getBestTime(times100, 1);
        RaceTime best200 = MarketRaceTime.getBestTime(times200, 1);
        titleSwim = "D o s";
        time50  = best50.getTime();
        time100 = best100.getTime();
        time200 = best200.getTime();
        gradientBackground = R.drawable.sh_gradient_green;
        colorText = R.color.greenLight;
    }

    public void loadBreaststroke(List<RaceTime> allTimes, int poolSize) {
        List<RaceTime> times50   = MarketRaceTime.getRacesByPoolSizeDistanceRaceSwimRace(allTimes, poolSize,50, "breaststroke");
        List<RaceTime> times100  = MarketRaceTime.getRacesByPoolSizeDistanceRaceSwimRace(allTimes, poolSize,100, "breaststroke");
        List<RaceTime> times200  = MarketRaceTime.getRacesByPoolSizeDistanceRaceSwimRace(allTimes, poolSize,200,"breaststroke");
        RaceTime best50  = MarketRaceTime.getBestTime(times50, 1);
        RaceTime best100 = MarketRaceTime.getBestTime(times100, 1);
        RaceTime best200 = MarketRaceTime.getBestTime(times200, 1);
        titleSwim = "B r a s s e";
        time50   = best50.getTime();
        time100  = best100.getTime();
        time200  = best200.getTime();
        gradientBackground = R.drawable.sh_gradient_orange;
        colorText = R.color.orangeLight;
    }

    public void loadFreestyle(List<RaceTime> allTimes, int poolSize) {
        List<RaceTime> times50   = MarketRaceTime.getRacesByPoolSizeDistanceRaceSwimRace(allTimes, poolSize,50, "freestyle");
        List<RaceTime> times100  = MarketRaceTime.getRacesByPoolSizeDistanceRaceSwimRace(allTimes, poolSize,100, "freestyle");
        List<RaceTime> times200  = MarketRaceTime.getRacesByPoolSizeDistanceRaceSwimRace(allTimes, poolSize,200,"freestyle");
        List<RaceTime> times400  = MarketRaceTime.getRacesByPoolSizeDistanceRaceSwimRace(allTimes, poolSize,400, "freestyle");
        List<RaceTime> times800  = MarketRaceTime.getRacesByPoolSizeDistanceRaceSwimRace(allTimes, poolSize,800,"freestyle");
        List<RaceTime> times1500 = MarketRaceTime.getRacesByPoolSizeDistanceRaceSwimRace(allTimes, poolSize,1500,"freestyle");
        RaceTime best50   = MarketRaceTime.getBestTime(times50, 1);
        RaceTime best100  = MarketRaceTime.getBestTime(times100, 1);
        RaceTime best200  = MarketRaceTime.getBestTime(times200, 1);
        RaceTime best400  = MarketRaceTime.getBestTime(times400, 1);
        RaceTime best800  = MarketRaceTime.getBestTime(times800, 1);
        RaceTime best1500 = MarketRaceTime.getBestTime(times1500, 1);
        titleSwim = "N a g e  L i b r e";
        time50   = best50.getTime();
        time100  = best100.getTime();
        time200  = best200.getTime();
        time400  = best400.getTime();
        time800  = best800.getTime();
        time1500 = best1500.getTime();
        gradientBackground = R.drawable.sh_gradient_red;
        colorText = R.color.redLight;
    }

    public void load4N(List<RaceTime> allTimes, int poolSize) {
        RaceTime best100;
        RaceTime best200;
        RaceTime best400;
        if (poolSize == 25) {
            List<RaceTime> times100 = MarketRaceTime.getRacesByPoolSizeDistanceRaceSwimRace(allTimes, poolSize, 100,"IM");
            List<RaceTime> times200 = MarketRaceTime.getRacesByPoolSizeDistanceRaceSwimRace(allTimes, poolSize, 200, "IM");
            List<RaceTime> times400 = MarketRaceTime.getRacesByPoolSizeDistanceRaceSwimRace(allTimes, poolSize, 400, "IM");
            best100 = MarketRaceTime.getBestTime(times100, 1);
            best200 = MarketRaceTime.getBestTime(times200, 1);
            best400 = MarketRaceTime.getBestTime(times400, 1);
            time100 = best100.getTime();
        } else {
            List<RaceTime> times200 = MarketRaceTime.getRacesByPoolSizeDistanceRaceSwimRace(allTimes, poolSize, 200, "IM");
            List<RaceTime> times400 = MarketRaceTime.getRacesByPoolSizeDistanceRaceSwimRace(allTimes, poolSize, 400, "IM");
            best200 = MarketRaceTime.getBestTime(times200, 1);
            best400 = MarketRaceTime.getBestTime(times400, 1);
        }
        titleSwim = "4  N a g e s";
        time200 = best200.getTime();
        time400 = best400.getTime();
        gradientBackground = R.drawable.sh_gradient_blue;
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
