package com.example.uniapp.front.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uniapp.R;
import com.example.uniapp.front.controller.global.AboutScreen;
import com.example.uniapp.front.controller.fragmentcontroller.DetailTrainingController;
import com.example.uniapp.front.model.data.Training;
import com.example.uniapp.front.view.recyclerview.RvTrainingDetailAdapter;

import java.util.ArrayList;
import java.util.List;

public class TrainingDetailFragment extends Fragment {
    private DetailTrainingController controller;
    private View layoutInflater;

    private Training training;
    private RecyclerView serieRecyclerView;
    private RvTrainingDetailAdapter serieRecyclerViewAdapter;
    private TextView date_city_sizePool;
    private TextView competitionTime;
    private TextView zoneTime;
    private List<Integer> idStars;
    private List<Button> difficultyStars;

    public TrainingDetailFragment(Training training) {
        this.training = training;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutInflater = inflater.inflate(R.layout.fragment_detail_training, container, false);
        controller = new DetailTrainingController(this, training);
        controller.onStart();

        return layoutInflater;
    }

    public void updateTrainingList() {
        serieRecyclerViewAdapter = new RvTrainingDetailAdapter(getActivity(), controller.getTraining());

        serieRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        serieRecyclerView.setAdapter(serieRecyclerViewAdapter);
        serieRecyclerView.setNestedScrollingEnabled(false);
        serieRecyclerView.setHasFixedSize(true);
        serieRecyclerView.setItemViewCacheSize(20);

        serieRecyclerViewAdapter.notifyDataSetChanged();
    }

    public void setupUIElements() {
        difficultyStars = new ArrayList<Button>();
        idStars         = new ArrayList<Integer>();

        idStars.add(R.id.fragment_training_detail_difficulty_star_1);
        idStars.add(R.id.fragment_training_detail_difficulty_star_2);
        idStars.add(R.id.fragment_training_detail_difficulty_star_3);
        idStars.add(R.id.fragment_training_detail_difficulty_star_4);
        idStars.add(R.id.fragment_training_detail_difficulty_star_5);

        date_city_sizePool = (TextView)     layoutInflater.findViewById(R.id.fragment_detail_race_time_date_and_city);
        competitionTime    = (TextView)     layoutInflater.findViewById(R.id.fragment_detail_training_swims_competition_times);
        zoneTime           = (TextView)     layoutInflater.findViewById(R.id.fragment_detail_training_swims_zone_times);
        serieRecyclerView  = (RecyclerView) layoutInflater.findViewById(R.id.fragment_training_detail_graphs);

        for (int i = 0; i < 5; i++) difficultyStars.add((Button) layoutInflater.findViewById(idStars.get(i)));
        for (int i = 0; i < difficultyStars.size(); i++) difficultyStars.get(i).setEnabled(false);
        controller.lockUI();
    }

    public void updateHeaderInfo() {
        date_city_sizePool.setText(controller.getTitleTraining());
        competitionTime.setText(controller.getRaceZoneStr());
        zoneTime.setText(controller.getTrainingZoneTimeStr());
    }

    public void fillDifficulty() {
        if (AboutScreen.isNightMode(getActivity())) {
            for (int i = 0; i < controller.getTrainingDifficulty(); i++)
                difficultyStars.get(i).setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.drawable.ic_radio_button_checked_white_24dp), null, null, null);
            for (int i = controller.getTrainingDifficulty(); i < difficultyStars.size(); i++)
                difficultyStars.get(i).setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.drawable.ic_radio_button_unchecked_white_24dp), null, null, null);
        } else {
            for (int i = 0; i < controller.getTrainingDifficulty(); i++)
                difficultyStars.get(i).setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.drawable.ic_radio_button_checked_black_24dp), null, null, null);
            for (int i = controller.getTrainingDifficulty(); i < difficultyStars.size(); i++)
                difficultyStars.get(i).setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.drawable.ic_radio_button_unchecked_black_24dp), null, null, null);
        }
    }
}
