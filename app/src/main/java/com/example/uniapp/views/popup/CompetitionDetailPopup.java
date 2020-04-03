package com.example.uniapp.views.popup;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.annotation.NonNull;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.example.uniapp.R;
import com.example.uniapp.models.MarketRaces;
import com.example.uniapp.models.MarketTimes;
import com.example.uniapp.models.Race;
import com.example.uniapp.views.comparators.TimeComparator;

import java.util.Collections;
import java.util.List;


public class CompetitionDetailPopup extends Dialog {
    private Race race;
    private Race raceUp;
    private Race raceDown;
    private List<Race> subListRaces;

    private TextView bigTitle;
    private TextView swimmerTitle;
    private TextView performanceTitle;
    private TextView videoTitle;
    private TextView fullname;
    private TextView birthday;
    private TextView club;
    private TextView date_city;
    private TextView level;
    private TextView distance_swim;
    private TextView time;
    private TextView points;
    private TextView diff;

    public CompetitionDetailPopup(@NonNull Context context, List<Race> subListRaces, Race race) {
        super(context);
        this.subListRaces = subListRaces;
        this.race         = race;
        setContentView(R.layout.popup_race_detail);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
        videoTitle       = (TextView) findViewById(R.id.fragment_detail_race_time_video_title);
        fullname         = (TextView) findViewById(R.id.fragment_detail_race_time_name);
        birthday         = (TextView) findViewById(R.id.fragment_detail_race_time_birthday);
        club             = (TextView) findViewById(R.id.fragment_detail_race_time_club);
        date_city        = (TextView) findViewById(R.id.fragment_detail_race_time_date_and_city);
        level            = (TextView) findViewById(R.id.fragment_detail_race_time_level_race);
        distance_swim    = (TextView) findViewById(R.id.fragment_detail_race_time_distance_swim);
        time             = (TextView) findViewById(R.id.fragment_detail_race_time_time);
        diff             = (TextView) findViewById(R.id.fragment_detail_race_time_diff);
        points           = (TextView) findViewById(R.id.fragment_detail_race_time_points);

        fullname.setText("Jérémy" + " " + "TOURARI");
        birthday.setText("11/11/1999 (21 ans)");
        club.setText("AS HERBLAY NATATION");
        diff.setText(MarketTimes.compareTwoTimes(raceDown.getTime(), race.getTime(), raceUp.getTime()));
        diff.setTextColor(getContext().getResources().getColor((diff.getText().toString().charAt(1) != '+') ? R.color.greenDeep : R.color.redDeep));
        date_city.setText("Le " + race.getDate() + " à " + race.getCity());
        level.setText("Niveau " + race.getLevel());
        distance_swim.setText(race.getDistanceRace() + " " + Race.convertShortSwim(race.getSwim()));
        time.setText(race.getTime());
        points.setText(race.getPointFFN() + " points");
    }

    private void updateColors() {
        bigTitle.setTextColor(getContext().getResources().getColor(Race.getCurrentColor(race.getSwim())));
        swimmerTitle.setTextColor(getContext().getResources().getColor(Race.getCurrentColor(race.getSwim())));
        performanceTitle.setTextColor(getContext().getResources().getColor(Race.getCurrentColor(race.getSwim())));
        videoTitle.setTextColor(getContext().getResources().getColor(Race.getCurrentColor(race.getSwim())));
        time.setTextColor(getContext().getResources().getColor(Race.getCurrentColor(race.getSwim())));
    }

    public void build() {
        show();
        Window window = getWindow();
        if (window != null) window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
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
    public TextView getVideoTitle() { return videoTitle; }

    public void setBigTitle(TextView bigTitle) { this.bigTitle = bigTitle; }
    public void setSwimmerTitle(TextView swimmerTitle) { this.swimmerTitle = swimmerTitle; }
    public void setPerformanceTitle(TextView performanceTitle) { this.performanceTitle = performanceTitle; }
    public void setVideoTitle(TextView videoTitle) { this.videoTitle = videoTitle; }
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
