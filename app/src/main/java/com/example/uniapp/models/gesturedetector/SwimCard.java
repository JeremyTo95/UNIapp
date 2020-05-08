package com.example.uniapp.models.gesturedetector;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.uniapp.R;
import com.example.uniapp.controllers.activities.MainActivity;
import com.example.uniapp.models.markets.MarketRaces;
import com.example.uniapp.models.markets.MarketSwim;
import com.example.uniapp.models.markets.MarketTimes;
import com.example.uniapp.models.database.dao.race.Race;
import com.example.uniapp.views.AboutScreen;

import java.util.List;

public class SwimCard extends LinearLayout {
    private Context context;
    private String  swim;
    private int     sizePool;

    private String titleSwim;
    private String time50, time100, time200;
    private String time400, time800, time1500;
    private int    colorText;

    private View rootView;
    private TextView title;
    private LinearLayout rightSide;
    private TextView time1, time2, time3;
    private TextView time4, time5, time6;


    public SwimCard(Context context, String swim, int sizePool) {
        super(context);
        this.context  = context;
        this.swim     = swim;
        this.sizePool = sizePool;

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
        List<Race> times50   = (List<Race>) MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,50, "butterfly");
        List<Race> times100  = (List<Race>) MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,100, "butterfly");
        List<Race> times200  = (List<Race>) MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,200,"butterfly");
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
        List<Race> times50   = (List<Race>) MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,50, "backstroke");
        List<Race> times100  = (List<Race>) MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,100, "backstroke");
        List<Race> times200  = (List<Race>) MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,200,"backstroke");
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
        List<Race> times50   = (List<Race>) MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,50, "breaststroke");
        List<Race> times100  = (List<Race>) MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,100, "breaststroke");
        List<Race> times200  = (List<Race>) MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,200,"breaststroke");
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
        List<Race> times50   = (List<Race>) MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,50, "freestyle");
        List<Race> times100  = (List<Race>) MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,100, "freestyle");
        List<Race> times200  = (List<Race>) MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,200,"freestyle");
        List<Race> times400  = (List<Race>) MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,400, "freestyle");
        List<Race> times800  = (List<Race>) MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,800,"freestyle");
        List<Race> times1500 = (List<Race>) MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize,1500,"freestyle");
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
            List<Race> times100 = (List<Race>) MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize, 100,"IM");
            List<Race> times200 = (List<Race>) MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize, 200, "IM");
            List<Race> times400 = (List<Race>) MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize, 400, "IM");
            best100             = MarketRaces.getBestTime(times100, 1);
            best200             = MarketRaces.getBestTime(times200, 1);
            best400             = MarketRaces.getBestTime(times400, 1);
            time100             = MarketTimes.fetchFloatToTime(best100.getTime());
        } else {
            List<Race> times200 = (List<Race>) MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize, 200, "IM");
            List<Race> times400 = (List<Race>) MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(poolSize, 400, "IM");
            best200             = MarketRaces.getBestTime(times200, 1);
            best400             = MarketRaces.getBestTime(times400, 1);
        }
        titleSwim = "4  N a g e s";
        time200   = MarketTimes.fetchFloatToTime(best200.getTime());
        time400   = MarketTimes.fetchFloatToTime(best400.getTime());
        colorText = AboutScreen.getColorByThemeAttr(context, R.attr.blueLightColor, R.color.blueLight);;
    }

    public String getSwim() { return swim; }

    public static class OnGestureDetector extends GestureDetector.SimpleOnGestureListener {
        private HorizontalScrollView horizontalScrollView;
        private List<SwimCard>       swimItem;
        private int                  indexSwimCard;

        private static final int SWIPE_MIN_DISTANCE        = 5;
        private static final int SWIPE_THRESHOLD_VELOCITY  = 100;

        public OnGestureDetector(List<SwimCard> swimItem, HorizontalScrollView horizontalScrollView, String swim) {
            this.swimItem             = swimItem;
            this.horizontalScrollView = horizontalScrollView;
            indexSwimCard = MarketSwim.getSwimIndex(swim);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                //right to left
                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    indexSwimCard = (indexSwimCard < (swimItem.size() - 1)) ? indexSwimCard + 1 : swimItem.size() - 1;
                    horizontalScrollView.smoothScrollTo(indexSwimCard * horizontalScrollView.getMeasuredWidth(), 0);
                    System.out.println(indexSwimCard);
                    return true;
                }
                //left to right
                else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    indexSwimCard = (indexSwimCard > 0)? indexSwimCard - 1 : 0;
                    horizontalScrollView.smoothScrollTo(indexSwimCard * horizontalScrollView.getMeasuredWidth(), 0);
                    System.out.println(indexSwimCard);
                    return true;
                }
            } catch (Exception e) {
                // nothing
            }
            return false;
        }


        public List<SwimCard> getSwimItem() { return swimItem; }
        public int getIndexSwimCard() { return indexSwimCard; }
    }
}
