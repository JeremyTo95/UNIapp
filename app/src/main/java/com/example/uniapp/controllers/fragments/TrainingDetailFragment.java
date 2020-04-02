package com.example.uniapp.controllers.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.uniapp.R;
import com.example.uniapp.controllers.adapters.RvTrainingDetailAdapter;
import com.example.uniapp.models.MarketRaces;
import com.example.uniapp.models.Race;
import com.example.uniapp.models.Training;

import java.util.ArrayList;
import java.util.List;

public class TrainingDetailFragment extends Fragment {
    private View layoutInflater;
    private Training training;
    private List<Race> allRaces;

    public TrainingDetailFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutInflater = inflater.inflate(R.layout.fragment_training_detail, container, false);
        training       = (Training) getActivity().getIntent().getSerializableExtra("EXTRA_TRAINING_SELECTED");
        allRaces       = (List<Race>) getActivity().getIntent().getSerializableExtra("EXTRA_ALL_RACES");

        updateTextView();
        fillDifficulty();
        updateRecyclerViewTrainingList();

        return layoutInflater;
    }

    private void updateRecyclerViewTrainingList() {
        RecyclerView serieRecyclerView = (RecyclerView) layoutInflater.findViewById(R.id.fragment_training_detail_graphs);
        RvTrainingDetailAdapter serieRecyclerViewAdapter = new RvTrainingDetailAdapter(getContext(), allRaces, training);
        serieRecyclerView.setLayoutManager(new LinearLayoutManager(layoutInflater.getContext()));
        serieRecyclerView.setAdapter(serieRecyclerViewAdapter);
        serieRecyclerView.setNestedScrollingEnabled(false);
        serieRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void updateTextView() {
        TextView date_city_sizePool = (TextView) layoutInflater.findViewById(R.id.fragment_detail_race_time_date_and_city);
        TextView competitionTitle = (TextView) layoutInflater.findViewById(R.id.fragment_detail_training_competition_title);
        TextView zoneTitle = (TextView) layoutInflater.findViewById(R.id.fragment_detail_training_zone_title);
        TextView competitionTime = (TextView) layoutInflater.findViewById(R.id.fragment_detail_training_swims_competition_times);
        TextView zoneTime = (TextView) layoutInflater.findViewById(R.id.fragment_detail_training_swims_zone_times);

        date_city_sizePool.setText("Le " + training.getDate() + " à " + training.getCity() + " ("+training.getSizePool() + ")");
        competitionTitle.setText("C O M P E T I T I O N");
        zoneTitle.setText("Z O N E");
        String competitionTimeStr  = "";
        String trainingZoneTimeStr = "";
        List<String> bestTimes = getCompetitionRaceTime();
        for (int i = 0; i < training.getTrainingBlockList().size(); i++) {
            competitionTimeStr  += training.getTrainingBlockList().get(i).getDistance() + Race.convertShortSwim(training.getTrainingBlockList().get(i).getSwim()) + " : " + bestTimes.get(i) + "\n";
            trainingZoneTimeStr += "Z" + training.getTrainingBlockList().get(i).getZone() + " " + training.getTrainingBlockList().get(i).getDistance() + Race.convertShortSwim(training.getTrainingBlockList().get(i).getSwim()) + " : " + Training.convertCompetitionTimeToZoneTime(bestTimes.get(i), training.getTrainingBlockList().get(i).getZone()) + "\n";
        }
        competitionTimeStr = competitionTimeStr.substring(0, competitionTimeStr.length() - 1);
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
        for (int i = 0; i < 5; i++) difficultyStars.add((Button) layoutInflater.findViewById(idStars[i]));
        for (int i = 0; i < difficultyStars.size(); i++) difficultyStars.get(i).setEnabled(false);
        for (int i = 0; i < training.getDifficulty(); i++) {
            difficultyStars.get(i).setEnabled(false);
            difficultyStars.get(i).setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_radio_button_checked_white_24dp), null, null, null);
        }
    }

    private List<String> getCompetitionRaceTime() {
        List<String> result = new ArrayList<String>();
        for (int i = 0; i < training.getTrainingBlockList().size(); i++)
            result.add(MarketRaces.getBestTime(MarketRaces.getRacesByPoolSizeDistanceRaceSwimRace(allRaces, training.getSizePool(), training.getTrainingBlockList().get(i).getDistance(), training.getTrainingBlockList().get(i).getSwim()), 1).getTime());
        return result;
    }
}
