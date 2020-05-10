package com.example.uniapp.front.view.popup;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uniapp.front.controller.global.AboutScreen;
import com.example.uniapp.front.model.market.MarketSwim;
import com.example.uniapp.front.model.market.MarketTimes;
import com.example.uniapp.R;
import com.example.uniapp.front.view.recyclerview.RvTrainingUpdateTimesAdapter;
import com.example.uniapp.front.model.data.Training;
import com.example.uniapp.front.model.data.TrainingBlock;

import java.util.ArrayList;

public class UpdateTrainingTimesPopup extends Dialog {
    private Activity activity;
    private Training training;
    private int position;
    private Float timeRef;
    private ArrayList<TrainingBlock> trainingBlockArrayList;
    private TextView titleSerie;
    private RecyclerView recyclerView;
    private RvTrainingUpdateTimesAdapter rvTrainingUpdateTimesAdapter;
    private Button denied;
    private Button confirmed;

    public UpdateTrainingTimesPopup(Activity activity, Training training, int position, Float timeRef) {
        super(activity, R.style.Theme_AppCompat_Dialog);
        this.activity = activity;
        this.training = training;
        this.position = position;
        this.timeRef = timeRef;
        setContentView(R.layout.popup_update_training_times);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        trainingBlockArrayList = new ArrayList<TrainingBlock>();
        for (int i = 0; i < training.getTrainingBlockList().size(); i++) trainingBlockArrayList.add(training.getTrainingBlockList().get(i));
        titleSerie   = findViewById(R.id.popup_update_training_times_title_serie);
        recyclerView = findViewById(R.id.popup_update_training_times_rv);

        titleSerie.setText(training.getTrainingBlockList().get(position).getNbSet() + " x " + training.getTrainingBlockList().get(position).getDistance() + "" + MarketSwim.convertShortSwim(training.getTrainingBlockList().get(position).getSwim()) + " Z" + training.getTrainingBlockList().get(position).getZone() + " : " + MarketTimes.fetchFloatToTime(timeRef));

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
        if (window != null) window.setLayout((int) (AboutScreen.getWidth(activity) * 0.95), (int) (AboutScreen.getHeight(activity) * 0.95));
    }


    public ArrayList<TrainingBlock> getTrainingBlockArrayList() { return trainingBlockArrayList; }
    public Button getConfirmed() { return confirmed; }
    public void setConfirmed(Button confirmed) { this.confirmed = confirmed; }
}
