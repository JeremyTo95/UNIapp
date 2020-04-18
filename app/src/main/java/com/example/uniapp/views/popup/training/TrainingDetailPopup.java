package com.example.uniapp.views.popup.training;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.uniapp.R;
import com.example.uniapp.controllers.activities.MainActivity;
import com.example.uniapp.controllers.adapters.recyclerview.RvTrainingDetailAdapter;
import com.example.uniapp.models.MarketRaces;
import com.example.uniapp.models.MarketTimes;
import com.example.uniapp.models.database.dao.race.Race;
import com.example.uniapp.models.database.dao.training.Training;

import java.util.ArrayList;
import java.util.List;

public class TrainingDetailPopup extends Dialog {
    private Training training;
    public static Button updateBtn;

    public TrainingDetailPopup(@NonNull Context context, Training training) {
        super(context);
        setContentView(R.layout.popup_training_detail);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        this.training = training;
        updateTextView();
        fillDifficulty();
        updateRecyclerViewTrainingList();
    }

    private void updateRecyclerViewTrainingList() {
        RecyclerView serieRecyclerView = (RecyclerView) findViewById(R.id.fragment_training_detail_graphs);
        RvTrainingDetailAdapter serieRecyclerViewAdapter = new RvTrainingDetailAdapter(getContext(), training);
        serieRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        serieRecyclerView.setAdapter(serieRecyclerViewAdapter);
        serieRecyclerView.setNestedScrollingEnabled(false);
        serieRecyclerView.setHasFixedSize(true);
        serieRecyclerView.setItemViewCacheSize(20);
        serieRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void updateTextView() {
        TextView date_city_sizePool = (TextView) findViewById(R.id.fragment_detail_race_time_date_and_city);
        TextView competitionTitle   = (TextView) findViewById(R.id.fragment_detail_training_competition_title);
        TextView zoneTitle          = (TextView) findViewById(R.id.fragment_detail_training_zone_title);
        TextView competitionTime    = (TextView) findViewById(R.id.fragment_detail_training_swims_competition_times);
        TextView zoneTime           = (TextView) findViewById(R.id.fragment_detail_training_swims_zone_times);

        updateBtn = (Button)   findViewById(R.id.popup_training_detail_update_button);
        updateBtn.setVisibility(View.GONE);

        date_city_sizePool.setText("Le " + training.getDate() + " à " + training.getCity() + " (" + training.getSizePool() + ")");
        competitionTitle.setText("C O M P E T I T I O N");
        zoneTitle.setText("Z O N E");

        String competitionTimeStr  = "";
        String trainingZoneTimeStr = "";
        List<Float> bestTimes = getCompetitionRaceTime();
        for (int i = 0; i < training.getTrainingBlockList().size(); i++) {
            competitionTimeStr  += training.getTrainingBlockList().get(i).getDistance() + Race.convertShortSwim(training.getTrainingBlockList().get(i).getSwim()) + " : " + bestTimes.get(i) + "\n";
            trainingZoneTimeStr += "Z" + training.getTrainingBlockList().get(i).getZone() + " " + training.getTrainingBlockList().get(i).getDistance() + Race.convertShortSwim(training.getTrainingBlockList().get(i).getSwim()) + " : " + MarketTimes.convertCompetitionTimeToZoneTime(bestTimes.get(i), training.getTrainingBlockList().get(i).getZone()) + "\n";
        }
        competitionTimeStr  = competitionTimeStr.substring(0, competitionTimeStr.length() - 1);
        trainingZoneTimeStr = trainingZoneTimeStr.substring(0, trainingZoneTimeStr.length() - 1);
        competitionTime.setText(competitionTimeStr);
        zoneTime.setText(trainingZoneTimeStr);
    }

    private void fillDifficulty() {
        List<Button> difficultyStars = new ArrayList<Button>();
        int[] idStars = {
                R.id.fragment_training_detail_difficulty_star_1,
                R.id.fragment_training_detail_difficulty_star_2,
                R.id.fragment_training_detail_difficulty_star_3,
                R.id.fragment_training_detail_difficulty_star_4,
                R.id.fragment_training_detail_difficulty_star_5
        };
        for (int i = 0; i < 5; i++) difficultyStars.add((Button) findViewById(idStars[i]));
        for (int i = 0; i < difficultyStars.size(); i++) difficultyStars.get(i).setEnabled(false);
        for (int i = 0; i < training.getDifficulty(); i++) {
            difficultyStars.get(i).setEnabled(false);
            difficultyStars.get(i).setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.drawable.ic_radio_button_checked_white_24dp), null, null, null);
        }
    }

    private List<Float> getCompetitionRaceTime() {
        List<Float> result = new ArrayList<Float>();
        for (int i = 0; i < training.getTrainingBlockList().size(); i++)
            result.add(MarketRaces.getBestTime((List<Race>) MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(training.getSizePool(), training.getTrainingBlockList().get(i).getDistance(), training.getTrainingBlockList().get(i).getSwim()), 1).getTime());
        return result;
    }

    public void build() {
        show();
        Window window = getWindow();
        if (window != null) window.setLayout(GridLayout.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.MATCH_PARENT);
    }

    public Button getUpdateBtn() { return updateBtn; }
    public void setUpdateBtn(Button updateBtn) { this.updateBtn = updateBtn; }
}