package com.example.uniapp.front.model.graphicsitem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.uniapp.back.room.RoomDataBase;
import com.example.uniapp.front.controller.global.AboutScreen;
import com.example.uniapp.R;
import com.example.uniapp.front.model.data.Race;
import com.example.uniapp.front.model.market.MarketRaces;
import com.example.uniapp.front.model.market.MarketTimes;

import java.util.List;

public class SwimCard extends LinearLayout {
    private RoomDataBase roomDataBase;
    private Context      context;
    private String       swim;
    private int          sizePool;

    private String titleSwim;
    private String time50, time100, time200;
    private String time400, time800, time1500;
    private int    colorText;

    private View rootView;
    private TextView title;
    private ConstraintLayout rightSide;
    private TextView time1, time2, time3;
    private TextView time4, time5, time6;


    public SwimCard(Context context, String swim, int sizePool) {
        super(context);
        this.context      = context;
        this.swim         = swim;
        this.sizePool     = sizePool;
        this.roomDataBase = RoomDataBase.getDatabase(context);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(layoutParams);
        rootView = inflate(context, R.layout.pv_swim_items, this);
        setupSwimFeature();
        setupUIElements();
        setupSwimCard();
    }

    private void setupSwimFeature() {
        if (swim.equals("butterfly"))         loadButterfly(sizePool);
        else if (swim.equals("backstroke"))   loadBackstroke(sizePool);
        else if (swim.equals("breaststroke")) loadBreaststroke(sizePool);
        else if (swim.equals("freestyle"))    loadFreestyle(sizePool);
        else if (swim.equals("IM"))           loadIM(sizePool);
    }

    private void setupUIElements() {
        title     = rootView.findViewById(R.id.swim_items_name);
        rightSide = rootView.findViewById(R.id.swim_items_p2);
        time1     = rootView.findViewById(R.id.swim_items_d1);
        time2     = rootView.findViewById(R.id.swim_items_d2);
        time3     = rootView.findViewById(R.id.swim_items_d3);
        time4     = rootView.findViewById(R.id.swim_items_d4);
        time5     = rootView.findViewById(R.id.swim_items_d5);
        time6     = rootView.findViewById(R.id.swim_items_d6);
    }

    private void setupSwimCard() {
        title.setText(titleSwim);
        title.setTextColor(colorText);
        setupSwimItemElement();

    }
    private void setupSwimItemElement() {
        switch (titleSwim) {
            case "P a p i l l o n":
            case "D o s":
            case "B r a s s e":
                rightSide.setVisibility(LinearLayout.GONE);
                time1.setText("50m   : " + time50);
                time2.setText("100m  : " + time100);
                time3.setText("200m  : " + time200);
                break;

            case "N a g e  L i b r e":
                time1.setText("50m   : " + time50);
                time2.setText("100m  : " + time100);
                time3.setText("200m  : " + time200);
                time4.setText("400m  : " + time400);
                time5.setText("800m  : " + time800);
                time6.setText("1500m : " + time1500);
                break;

            case "4  N a g e s":
                rightSide.setVisibility(LinearLayout.GONE);
                if (sizePool == 25) {
                    time1.setText("100m  : " + time100);
                    time2.setText("200m  : " + time200);
                    time3.setText("400m  : " + time400);
                } else {
                    time1.setText("200m  : " + time200);
                    time2.setText("400m  : " + time400);
                    time3.setText("");
                }
                break;
        }
    }

    public void loadButterfly(int poolSize) {
        List<Race> times50   = roomDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,50, "butterfly");
        List<Race> times100  = roomDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,100, "butterfly");
        List<Race> times200  = roomDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,200,"butterfly");
        Race best50          = MarketRaces.getBestTime(times50, 1);
        Race best100         = MarketRaces.getBestTime(times100, 1);
        Race best200         = MarketRaces.getBestTime(times200, 1);
        titleSwim            = "P a p i l l o n";
        time50               = MarketTimes.fetchFloatToTime(best50.getTime());
        time100              = MarketTimes.fetchFloatToTime(best100.getTime());
        time200              = MarketTimes.fetchFloatToTime(best200.getTime());
        colorText            = AboutScreen.getColorByThemeAttr(context, R.attr.secondaryColor, R.color.colorSecondaryDark);
    }

    public void loadBackstroke(int poolSize) {
        List<Race> times50   = roomDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,50, "backstroke");
        List<Race> times100  = roomDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,100, "backstroke");
        List<Race> times200  = roomDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,200,"backstroke");
        Race best50          = MarketRaces.getBestTime(times50, 1);
        Race best100         = MarketRaces.getBestTime(times100, 1);
        Race best200         = MarketRaces.getBestTime(times200, 1);
        titleSwim            = "D o s";
        time50               = MarketTimes.fetchFloatToTime(best50.getTime());
        time100              = MarketTimes.fetchFloatToTime(best100.getTime());
        time200              = MarketTimes.fetchFloatToTime(best200.getTime());
        colorText            = AboutScreen.getColorByThemeAttr(context, R.attr.greenLightColor, R.color.greenLight);
    }

    public void loadBreaststroke(int poolSize) {
        List<Race> times50   = roomDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,50, "breaststroke");
        List<Race> times100  = roomDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,100, "breaststroke");
        List<Race> times200  = roomDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,200,"breaststroke");
        Race best50          = MarketRaces.getBestTime(times50, 1);
        Race best100         = MarketRaces.getBestTime(times100, 1);
        Race best200         = MarketRaces.getBestTime(times200, 1);
        titleSwim            = "B r a s s e";
        time50               = MarketTimes.fetchFloatToTime(best50.getTime());
        time100              = MarketTimes.fetchFloatToTime(best100.getTime());
        time200              = MarketTimes.fetchFloatToTime(best200.getTime());
        colorText            = AboutScreen.getColorByThemeAttr(context, R.attr.orangeLightColor, R.color.orangeLight);
    }

    public void loadFreestyle(int poolSize) {
        List<Race> times50   = roomDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,50, "freestyle");
        List<Race> times100  = roomDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,100, "freestyle");
        List<Race> times200  = roomDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,200,"freestyle");
        List<Race> times400  = roomDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,400, "freestyle");
        List<Race> times800  = roomDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,800,"freestyle");
        List<Race> times1500 = roomDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,1500,"freestyle");
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
        colorText            = AboutScreen.getColorByThemeAttr(context, R.attr.redLightColor, R.color.redLight);
    }

    public void loadIM(int poolSize) {
        Race best100;
        Race best200;
        Race best400;
        if (poolSize == 25) {
            List<Race> times100 = roomDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize, 100,"IM");
            List<Race> times200 = roomDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize, 200, "IM");
            List<Race> times400 = roomDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize, 400, "IM");
            best100             = MarketRaces.getBestTime(times100, 1);
            best200             = MarketRaces.getBestTime(times200, 1);
            best400             = MarketRaces.getBestTime(times400, 1);
            time100             = MarketTimes.fetchFloatToTime(best100.getTime());
        } else {
            List<Race> times200 = roomDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize, 200, "IM");
            List<Race> times400 = roomDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize, 400, "IM");
            best200             = MarketRaces.getBestTime(times200, 1);
            best400             = MarketRaces.getBestTime(times400, 1);
        }
        titleSwim = "4  N a g e s";
        time200   = MarketTimes.fetchFloatToTime(best200.getTime());
        time400   = MarketTimes.fetchFloatToTime(best400.getTime());
        colorText = AboutScreen.getColorByThemeAttr(context, R.attr.blueLightColor, R.color.blueLight);;
    }

    public String getSwim() { return swim; }
}
