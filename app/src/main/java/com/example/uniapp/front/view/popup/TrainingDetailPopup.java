package com.example.uniapp.front.view.popup;

import android.app.Activity;
import android.app.Dialog;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.uniapp.back.room.RoomDataBase;
import com.example.uniapp.front.model.market.MarketRaces;
import com.example.uniapp.front.model.market.MarketSwim;
import com.example.uniapp.front.model.market.MarketTimes;
import com.example.uniapp.R;
import com.example.uniapp.front.model.data.Race;
import com.example.uniapp.front.view.recyclerview.RvTrainingDetailAdapter;
import com.example.uniapp.front.model.data.Training;
import com.example.uniapp.front.view.actvities.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class TrainingDetailPopup extends Dialog {
    private RoomDataBase roomDataBase;
    private Activity activity;
    private Training training;
    public static Button updateBtn;

    public TrainingDetailPopup(Activity activity, Training training) {
        super(activity, R.style.Theme_AppCompat_Dialog);
        setContentView(R.layout.popup_training_detail);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        this.activity     = activity;
        this.training     = training;
        this.roomDataBase = RoomDataBase.getDatabase(activity.getApplicationContext());
        updateTextView();
        fillDifficulty();
        updateRecyclerViewTrainingList();
    }

    private void updateRecyclerViewTrainingList() {
        RecyclerView serieRecyclerView = (RecyclerView) findViewById(R.id.fragment_training_detail_graphs);
        RvTrainingDetailAdapter serieRecyclerViewAdapter = new RvTrainingDetailAdapter(activity, training);
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
            competitionTimeStr  += training.getTrainingBlockList().get(i).getDistance() + MarketSwim.convertShortSwim(training.getTrainingBlockList().get(i).getSwim()) + " : " + bestTimes.get(i) + "\n";
            trainingZoneTimeStr += "Z" + training.getTrainingBlockList().get(i).getZone() + " " + training.getTrainingBlockList().get(i).getDistance() + MarketSwim.convertShortSwim(training.getTrainingBlockList().get(i).getSwim()) + " : " + MarketTimes.convertCompetitionTimeToZoneTime(bestTimes.get(i), training.getTrainingBlockList().get(i).getZone()) + "\n";
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
        for (int i = 0; i < difficultyStars.size(); i++) {
            difficultyStars.get(i).setEnabled(false);
            difficultyStars.get(i).setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.drawable.ic_radio_button_unchecked_black_24dp), null, null, null);
        }
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            for (int i = 0; i < training.getDifficulty(); i++)
                difficultyStars.get(i).setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.drawable.ic_radio_button_checked_white_24dp), null, null, null);
            for (int i = training.getDifficulty(); i < difficultyStars.size(); i++)
                difficultyStars.get(i).setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.drawable.ic_radio_button_unchecked_white_24dp), null, null, null);
        } else {
            for (int i = 0; i < training.getDifficulty(); i++)
                difficultyStars.get(i).setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.drawable.ic_radio_button_checked_black_24dp), null, null, null);
            for (int i = training.getDifficulty(); i < difficultyStars.size(); i++)
                difficultyStars.get(i).setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.drawable.ic_radio_button_unchecked_black_24dp), null, null, null);
        }
    }

    private List<Float> getCompetitionRaceTime() {
        List<Float> result = new ArrayList<Float>();
        for (int i = 0; i < training.getTrainingBlockList().size(); i++)
            result.add(MarketRaces.getBestTime((List<Race>) roomDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(training.getSizePool(), training.getTrainingBlockList().get(i).getDistance(), training.getTrainingBlockList().get(i).getSwim()), 1).getTime());
        return result;
    }

    public void build() {
        show();
        Window window = getWindow();
        if (window != null) window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    public Button getUpdateBtn() { return updateBtn; }
    public void setUpdateBtn(Button updateBtn) { this.updateBtn = updateBtn; }
}
