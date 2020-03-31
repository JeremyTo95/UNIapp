package com.example.uniapp.controllers.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.uniapp.R;
import com.example.uniapp.models.MarketRaces;
import com.example.uniapp.models.Race;
import com.example.uniapp.views.TimeComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class RaceDetailFragment extends Fragment {
    private Race mRace;
    private Race mRaceUp;
    private Race mRaceDown;
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

    public RaceDetailFragment() { }


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view     = inflater.inflate(R.layout.fragment_race_detail, container, false);
        subListRaces  = (ArrayList<Race>) getActivity().getIntent().getSerializableExtra("EXTRA_SUBLIST_RACES");
        mRace = (Race)       getActivity().getIntent().getSerializableExtra("EXTRA_RACE_SELECTED");

        Collections.sort(subListRaces, new TimeComparator());
        mRaceUp = MarketRaces.getBestTime(subListRaces, 1);
        mRaceDown = MarketRaces.getBestTime(subListRaces, 2);

        bigTitle         = (TextView) view.findViewById(R.id.fragment_detail_race_time_bigtitle);
        swimmerTitle     = (TextView) view.findViewById(R.id.fragment_detail_race_time_swimmer_title);
        performanceTitle = (TextView) view.findViewById(R.id.fragment_detail_race_time_performance_title);
        videoTitle       = (TextView) view.findViewById(R.id.fragment_detail_race_time_video_title);
        fullname         = (TextView) view.findViewById(R.id.fragment_detail_race_time_name);
        birthday         = (TextView) view.findViewById(R.id.fragment_detail_race_time_birthday);
        club             = (TextView) view.findViewById(R.id.fragment_detail_race_time_club);
        date_city        = (TextView) view.findViewById(R.id.fragment_detail_race_time_date_and_city);
        level            = (TextView) view.findViewById(R.id.fragment_detail_race_time_level_race);
        distance_swim    = (TextView) view.findViewById(R.id.fragment_detail_race_time_distance_swim);
        time             = (TextView) view.findViewById(R.id.fragment_detail_race_time_time);
        diff             = (TextView) view.findViewById(R.id.fragment_detail_race_time_diff);
        points           = (TextView) view.findViewById(R.id.fragment_detail_race_time_points);

        if (Race.fetchTimeToFloat(mRace.getTime()) > Race.fetchTimeToFloat(mRaceUp.getTime())) {
            diff.setText(String.format("+%.2fs", (Race.fetchTimeToFloat(mRace.getTime())) - Race.fetchTimeToFloat(mRaceUp.getTime())));
            diff.setTextColor(getContext().getResources().getColor(R.color.redDeep));
        } else {
            diff.setText(String.format("-%.2fs", (Race.fetchTimeToFloat(mRaceDown.getTime()) - Race.fetchTimeToFloat(mRace.getTime()))));
            diff.setTextColor(getContext().getResources().getColor(R.color.greenDeep));
        }

        fullname.setText("Jérémy" + " " + "TOURARI");
        birthday.setText("11/11/1999 (21 ans)");
        club.setText("AS HERBLAY NATATION");

        date_city.setText("Le " + mRace.getDate() + " à " + mRace.getCity());
        level.setText("Niveau " + mRace.getLevel());
        System.out.println(mRace.getSwim());
        distance_swim.setText(mRace.getDistanceRace() + " " + Race.convertShortSwim(mRace.getSwim()));
        time.setText(mRace.getTime());
        System.out.println(mRace.getPointFFN());
        points.setText(String.valueOf(mRace.getPointFFN()) + " points");

        updateColors();

        return view;
    }

    private void updateColors() {
        bigTitle.setTextColor(getActivity().getResources().getColor(Race.getCurrentColor(mRace.getSwim())));
        swimmerTitle.setTextColor(getActivity().getResources().getColor(Race.getCurrentColor(mRace.getSwim())));
        performanceTitle.setTextColor(getActivity().getResources().getColor(Race.getCurrentColor(mRace.getSwim())));
        videoTitle.setTextColor(getActivity().getResources().getColor(Race.getCurrentColor(mRace.getSwim())));
        time.setTextColor(getActivity().getResources().getColor(Race.getCurrentColor(mRace.getSwim())));
    }


    public Race getRace() { return mRace; }
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
    public void setRace(Race race) { this.mRace = race; }
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
