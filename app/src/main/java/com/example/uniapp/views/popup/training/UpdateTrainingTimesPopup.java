package com.example.uniapp.views.popup.training;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uniapp.R;
import com.example.uniapp.controllers.adapters.recyclerview.RvTrainingUpdateTimesAdapter;
import com.example.uniapp.models.MarketRaces;
import com.example.uniapp.models.MarketTimes;
import com.example.uniapp.models.database.dao.race.Race;
import com.example.uniapp.models.database.dao.training.Training;
import com.example.uniapp.models.database.dao.trainingblock.TrainingBlock;
import com.example.uniapp.views.AboutScreen;

import java.util.ArrayList;
import java.util.Arrays;

import static android.view.View.*;

public class UpdateTrainingTimesPopup extends Dialog {
    private Training training;
    private int position;
    private Float timeRef;
    private ArrayList<TrainingBlock> trainingBlockArrayList;
    private TextView titleSerie;
    private RecyclerView recyclerView;
    private RvTrainingUpdateTimesAdapter rvTrainingUpdateTimesAdapter;
    private Button denied;
    private Button confirmed;

    public UpdateTrainingTimesPopup(@NonNull Context context, Training training, int position, Float timeRef) {
        super(context);
        this.training = training;
        this.position = position;
        this.timeRef = timeRef;
        setContentView(R.layout.popup_update_training_times);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        trainingBlockArrayList = new ArrayList<TrainingBlock>();
        for (int i = 0; i < training.getTrainingBlockList().size(); i++) trainingBlockArrayList.add(training.getTrainingBlockList().get(i));
        titleSerie   = findViewById(R.id.popup_update_training_times_title_serie);
        recyclerView = findViewById(R.id.popup_update_training_times_rv);

        titleSerie.setText(training.getTrainingBlockList().get(position).getNbSet() + " x " + training.getTrainingBlockList().get(position).getDistance() + "" + Race.convertShortSwim(training.getTrainingBlockList().get(position).getSwim()) + " Z" + training.getTrainingBlockList().get(position).getZone() + " : " + MarketTimes.fetchFloatToTime(timeRef));

        rvTrainingUpdateTimesAdapter = new RvTrainingUpdateTimesAdapter(getContext(), training.getTrainingBlockList().get(position));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(rvTrainingUpdateTimesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTrainingUpdateTimesAdapter.notifyDataSetChanged();

        denied    = findViewById(R.id.popup_update_training_denied);
        confirmed = findViewById(R.id.popup_update_training_confirmed);

        denied.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void build() {
        show();
        Window window = getWindow();
        if (window != null) window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }


    public ArrayList<TrainingBlock> getTrainingBlockArrayList() { return trainingBlockArrayList; }
    public Button getConfirmed() { return confirmed; }
    public void setConfirmed(Button confirmed) { this.confirmed = confirmed; }
}
