package com.example.uniapp.controllers.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.uniapp.R;
import com.example.uniapp.controllers.adapters.recyclerview.RvTrainingDetailAdapter;
import com.example.uniapp.models.markets.MarketRaces;
import com.example.uniapp.models.markets.MarketSwim;
import com.example.uniapp.models.markets.MarketTimes;
import com.example.uniapp.models.database.dao.race.Race;
import com.example.uniapp.models.database.dao.training.Training;
import com.example.uniapp.views.AboutScreen;

import java.util.ArrayList;
import java.util.List;

public class DetailTrainingActivity extends AppCompatActivity {
    private Training training;
    private RecyclerView serieRecyclerView;
    private RvTrainingDetailAdapter serieRecyclerViewAdapter;
    private TextView date_city_sizePool;
    private TextView competitionTime;
    private TextView zoneTime;
    private List<Integer> idStars;
    private List<Button> difficultyStars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AboutScreen.setupThemeApp(this);
        setContentView(R.layout.activity_detail_training);
        training = (Training) getIntent().getSerializableExtra("EXTRA_TRAINING_SELECTED");

        setupUIElements();
        updateHeaderInfo();
        fillDifficulty();
        updateTrainingList();
    }

    private void updateTrainingList() {
        serieRecyclerViewAdapter = new RvTrainingDetailAdapter(this, training);

        serieRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        serieRecyclerView.setAdapter(serieRecyclerViewAdapter);
        serieRecyclerView.setNestedScrollingEnabled(false);
        serieRecyclerView.setHasFixedSize(true);
        serieRecyclerView.setItemViewCacheSize(20);

        serieRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void setupUIElements() {
        difficultyStars = new ArrayList<Button>();
        idStars         = new ArrayList<Integer>();

        idStars.add(R.id.fragment_training_detail_difficulty_star_1);
        idStars.add(R.id.fragment_training_detail_difficulty_star_2);
        idStars.add(R.id.fragment_training_detail_difficulty_star_3);
        idStars.add(R.id.fragment_training_detail_difficulty_star_4);
        idStars.add(R.id.fragment_training_detail_difficulty_star_5);

        date_city_sizePool = (TextView) findViewById(R.id.fragment_detail_race_time_date_and_city);
        competitionTime    = (TextView) findViewById(R.id.fragment_detail_training_swims_competition_times);
        zoneTime           = (TextView) findViewById(R.id.fragment_detail_training_swims_zone_times);
        serieRecyclerView = (RecyclerView) findViewById(R.id.fragment_training_detail_graphs);
        for (int i = 0; i < 5; i++) difficultyStars.add((Button) findViewById(idStars.get(i)));

        for (int i = 0; i < difficultyStars.size(); i++) difficultyStars.get(i).setEnabled(false);
    }

    private void updateHeaderInfo() {
        String raceZoneTimeStr     = "";
        String trainingZoneTimeStr = "";
        List<Float> bestTimes = getCompetitionRaceTime();

        for (int i = 0; i < training.getTrainingBlockList().size(); i++) {
            raceZoneTimeStr     = raceZoneTimeStr     + training.getTrainingBlockList().get(i).getDistance() + MarketSwim.convertShortSwim(training.getTrainingBlockList().get(i).getSwim()) + " : " + bestTimes.get(i) + "\n";
            trainingZoneTimeStr = trainingZoneTimeStr + "Z" + training.getTrainingBlockList().get(i).getZone() + " " + training.getTrainingBlockList().get(i).getDistance() + MarketSwim.convertShortSwim(training.getTrainingBlockList().get(i).getSwim()) + " : " + MarketTimes.convertCompetitionTimeToZoneTime(bestTimes.get(i), training.getTrainingBlockList().get(i).getZone()) + "\n";
        }
        raceZoneTimeStr     = raceZoneTimeStr.substring(0, raceZoneTimeStr.length() - 1);
        trainingZoneTimeStr = trainingZoneTimeStr.substring(0, trainingZoneTimeStr.length() - 1);

        date_city_sizePool.setText("Le " + training.getDate() + " à " + training.getCity() + " (" + training.getSizePool() + "m)");
        competitionTime.setText(raceZoneTimeStr);
        zoneTime.setText(trainingZoneTimeStr);
    }

    private void fillDifficulty() {
        if (AboutScreen.isNightMode(this)) {
            for (int i = 0; i < training.getDifficulty(); i++)
                difficultyStars.get(i).setCompoundDrawablesWithIntrinsicBounds(getApplicationContext().getResources().getDrawable(R.drawable.ic_radio_button_checked_white_24dp), null, null, null);
            for (int i = training.getDifficulty(); i < difficultyStars.size(); i++)
                difficultyStars.get(i).setCompoundDrawablesWithIntrinsicBounds(getApplicationContext().getResources().getDrawable(R.drawable.ic_radio_button_unchecked_white_24dp), null, null, null);
        } else {
            for (int i = 0; i < training.getDifficulty(); i++)
                difficultyStars.get(i).setCompoundDrawablesWithIntrinsicBounds(getApplicationContext().getResources().getDrawable(R.drawable.ic_radio_button_checked_black_24dp), null, null, null);
            for (int i = training.getDifficulty(); i < difficultyStars.size(); i++)
                difficultyStars.get(i).setCompoundDrawablesWithIntrinsicBounds(getApplicationContext().getResources().getDrawable(R.drawable.ic_radio_button_unchecked_black_24dp), null, null, null);
        }
    }

    private List<Float> getCompetitionRaceTime() {
        List<Float> result = new ArrayList<Float>();
        for (int i = 0; i < training.getTrainingBlockList().size(); i++)
            result.add(MarketRaces.getBestTime((List<Race>) MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(training.getSizePool(), training.getTrainingBlockList().get(i).getDistance(), training.getTrainingBlockList().get(i).getSwim()), 1).getTime());
        return result;
    }
}
