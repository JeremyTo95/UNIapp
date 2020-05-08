package com.example.uniapp.views.popup.competition;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.os.Build;
import android.view.Window;
import android.widget.TextView;

import com.example.uniapp.R;
import com.example.uniapp.controllers.activities.MainActivity;
import com.example.uniapp.models.markets.MarketRaces;
import com.example.uniapp.models.markets.MarketSwim;
import com.example.uniapp.models.markets.MarketTimes;
import com.example.uniapp.models.database.dao.race.Race;
import com.example.uniapp.views.AboutScreen;
import com.example.uniapp.views.comparators.TimeComparator;

import java.util.Collections;
import java.util.List;


public class CompetitionDetailPopup extends Dialog {
    private Activity activity;
    private Race race;
    private Race raceUp;
    private Race raceDown;
    private List<Race> subListRaces;

    private TextView bigTitle;
    private TextView swimmerTitle;
    private TextView performanceTitle;
    private TextView fullname;
    private TextView birthday;
    private TextView club;
    private TextView date_city;
    private TextView level;
    private TextView distance_swim;
    private TextView time;
    private TextView points;
    private TextView diff;

    public CompetitionDetailPopup(Activity activity, List<Race> subListRaces, Race race) {
        super(activity, R.style.Theme_AppCompat_Dialog);
        this.activity = activity;
        this.subListRaces = subListRaces;
        this.race         = race;
        setContentView(R.layout.popup_race_detail);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getContext().getResources().getColor(R.color.backgroundColorDark));
        }
        setupUIElements();
        updateColors();
    }


    private void setupUIElements() {
        Collections.sort(subListRaces, new TimeComparator());
        raceUp   = MarketRaces.getBestTime(subListRaces, 1);
        raceDown = MarketRaces.getBestTime(subListRaces, 2);

        bigTitle         = (TextView) findViewById(R.id.fragment_detail_race_time_bigtitle);
        swimmerTitle     = (TextView) findViewById(R.id.fragment_detail_race_time_swimmer_title);
        performanceTitle = (TextView) findViewById(R.id.fragment_detail_race_time_performance_title);
        fullname         = (TextView) findViewById(R.id.fragment_detail_race_time_name);
        birthday         = (TextView) findViewById(R.id.fragment_detail_race_time_birthday);
        club             = (TextView) findViewById(R.id.fragment_detail_race_time_club);
        date_city        = (TextView) findViewById(R.id.fragment_detail_race_time_date_and_city);
        level            = (TextView) findViewById(R.id.fragment_detail_race_time_level_race);
        distance_swim    = (TextView) findViewById(R.id.fragment_detail_race_time_distance_swim);
        time             = (TextView) findViewById(R.id.fragment_detail_race_time_time);
        diff             = (TextView) findViewById(R.id.fragment_detail_race_time_diff);
        points           = (TextView) findViewById(R.id.fragment_detail_race_time_points);

        fullname.setText(MainActivity.appDataBase.userDAO().getUser().getFirstname() + " " + MainActivity.appDataBase.userDAO().getUser().getLastname());
        birthday.setText(MainActivity.appDataBase.userDAO().getUser().getBirthday());
        club.setText(race.getClub());
        diff.setText(MarketTimes.compareTwoTimes(raceDown.getTime(), race.getTime(), raceUp.getTime()));
        diff.setTextColor(getContext().getResources().getColor((diff.getText().toString().charAt(1) != '+') ? R.color.greenDeep : R.color.redDeep));
        date_city.setText("Le " + race.getDate() + " à " + race.getCity());
        level.setText("Niveau " + race.getLevel());
        distance_swim.setText(race.getDistance() + " " + MarketSwim.convertShortSwim(race.getSwim()));
        time.setText(MarketTimes.fetchFloatToTime(race.getTime()));
        if (MainActivity.appDataBase.pointFFNDAO().getNb() != 0 && race.getDistance() != 25)
            points.setText(MainActivity.appDataBase.pointFFNDAO().getPointsFFNByGenderDistanceSwimTime(MainActivity.appDataBase.userDAO().getUser().getGender(), race.getDistance(), race.getSwim(), race.getTime()).getPoint() + " points FFN");
        else
            points.setText(" ");
    }

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
    public TextView getFullname() { return fullname; }
    public TextView getBirthday() { return birthday; }
    public TextView getClub() { return club; }
    public TextView getDate_city() { return date_city; }
    public TextView getLevel() { return level; }
    public TextView getDistance_swim() { return distance_swim; }
    public TextView getPoints() { return points; }
    public TextView getTime() { return time; }
    public TextView getDiff() { return diff; }
    public TextView getBigTitle() { return bigTitle; }
    public TextView getSwimmerTitle() { return swimmerTitle; }
    public TextView getPerformanceTitle() { return performanceTitle; }

    public void setBigTitle(TextView bigTitle) { this.bigTitle = bigTitle; }
    public void setSwimmerTitle(TextView swimmerTitle) { this.swimmerTitle = swimmerTitle; }
    public void setPerformanceTitle(TextView performanceTitle) { this.performanceTitle = performanceTitle; }
    public void setRace(Race race) { this.race = race; }
    public void setFullname(TextView fullname) { this.fullname = fullname; }
    public void setBirthday(TextView birthday) { this.birthday = birthday; }
    public void setClub(TextView club) { this.club = club; }
    public void setDate_city(TextView date_city) { this.date_city = date_city; }
    public void setLevel(TextView level) { this.level = level; }
    public void setDistance_swim(TextView distance_swim) { this.distance_swim = distance_swim; }
    public void setPoints(TextView points) { this.points = points; }
    public void setTime(TextView time) { this.time = time; }
    public void setDiff(TextView diff) { this.diff = diff; }
}
