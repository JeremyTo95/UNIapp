package com.example.uniapp.front.view.popup;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.os.Build;
import android.view.Window;
import android.widget.TextView;

import com.example.uniapp.back.room.RoomDataBase;
import com.example.uniapp.front.controller.global.AboutScreen;
import com.example.uniapp.front.model.market.MarketRaces;
import com.example.uniapp.front.model.market.MarketSwim;
import com.example.uniapp.front.model.market.MarketTimes;
import com.example.uniapp.R;
import com.example.uniapp.front.model.data.Race;
import com.example.uniapp.front.controller.comparator.TimeComparator;

import java.util.Collections;
import java.util.List;


public class CompetitionDetailPopup extends Dialog {
    private RoomDataBase roomDataBase;
    private Activity activity;
    private Race race;
    private Race raceUp;
    private Race raceDown;
    private List<Race> subListRaces;

    private TextView bigTitle;
    private TextView swimmerTitle;
    private TextView performanceTitle;
    private TextView club;
    private TextView time;

    public CompetitionDetailPopup(Activity activity, List<Race> subListRaces, Race race) {
        super(activity, R.style.Theme_AppCompat_Dialog);
        this.activity = activity;
        this.subListRaces = subListRaces;
        this.race         = race;
        this.roomDataBase = RoomDataBase.getDatabase(activity.getApplicationContext());
        setContentView(R.layout.popup_race_detail);
        if (getWindow() != null) {
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setNavigationBarColor(getContext().getResources().getColor(R.color.backgroundColorDark));
            }
        }
        setupUIElements();
        updateColors();
    }


    private void setupUIElements() {
        Collections.sort(subListRaces, new TimeComparator());
        raceUp = MarketRaces.getBestTime(subListRaces, 1);
        raceDown = MarketRaces.getBestTime(subListRaces, 2);

        bigTitle               = findViewById(R.id.fragment_detail_race_time_bigtitle);
        swimmerTitle           = findViewById(R.id.fragment_detail_race_time_swimmer_title);
        performanceTitle       = findViewById(R.id.fragment_detail_race_time_performance_title);
        club                   = findViewById(R.id.fragment_detail_race_time_club);
        time                   = findViewById(R.id.fragment_detail_race_time_time);
        TextView fullname      = findViewById(R.id.fragment_detail_race_time_name);
        TextView birthday      = findViewById(R.id.fragment_detail_race_time_birthday);
        TextView date_city     = findViewById(R.id.fragment_detail_race_time_date_and_city);
        TextView level         = findViewById(R.id.fragment_detail_race_time_level_race);
        TextView distance_swim = findViewById(R.id.fragment_detail_race_time_distance_swim);
        TextView diff          = findViewById(R.id.fragment_detail_race_time_diff);
        TextView points        = findViewById(R.id.fragment_detail_race_time_points);

        fullname.setText(getSwimmerET());
        birthday.setText(getBirthdayET());
        club.setText(getClubET());
        diff.setText(getDiffET());
        date_city.setText(getDateCityET());
        level.setText(getLevelET());
        distance_swim.setText(getDistanceSwimET());
        time.setText(MarketTimes.fetchFloatToTime(race.getTime()));
        if (roomDataBase.pointFFNDAO().getNb() != 0 && race.getDistance() != 25) points.setText(getPointFFNET());
        else points.setText(" ");
        diff.setTextColor(getContext().getResources().getColor((diff.getText().toString().charAt(1) != '+') ? R.color.greenDeep : R.color.redDeep));
    }

    private String getSwimmerET()      { return roomDataBase.userDAO().getUser().getFirstname() + " " + roomDataBase.userDAO().getUser().getLastname(); }
    private String getBirthdayET()     { return roomDataBase.userDAO().getUser().getBirthday(); }
    private String getClubET()         { return race.getClub(); }
    private String getDiffET()         { return MarketTimes.compareTwoTimes(raceDown.getTime(), race.getTime(), raceUp.getTime()); }
    private String getDateCityET()     { return ("Le " + race.getDate() + "à " + race.getCity()); }
    private String getLevelET()        { return "Niveau " + race.getLevel(); }
    private String getDistanceSwimET() { return race.getDistance() + " " + MarketSwim.convertShortSwim(race.getSwim()); }
    private String getPointFFNET()     { return roomDataBase.pointFFNDAO().getPointsFFNByGenderDistanceSwimTime(roomDataBase.userDAO().getUser().getGender(), race.getDistance(), race.getSwim(), race.getTime()).getPoint() + " points FFN"; }

    private void updateColors() {
        bigTitle.setTextColor(MarketSwim.getCurrentColor(getContext(), race.getSwim()));
        swimmerTitle.setTextColor(MarketSwim.getCurrentColor(getContext(), race.getSwim()));
        performanceTitle.setTextColor(MarketSwim.getCurrentColor(getContext(), race.getSwim()));
        time.setTextColor(MarketSwim.getCurrentColor(getContext(), race.getSwim()));
    }

    public void build() {
        show();
        Window window = getWindow();
        if (window != null) window.setLayout((int) (AboutScreen.getWidth(activity) * 0.95), (int) (AboutScreen.getHeight(activity) * 0.95));
    }

    public Race getRace() { return race; }
    public TextView getClub() { return club; }
    public TextView getTime() { return time; }

    public void setRace(Race race) { this.race = race; }
    public void setClub(TextView club) { this.club = club; }
    public void setTime(TextView time) { this.time = time; }
}
