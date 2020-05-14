package com.example.uniapp.front.view.popup;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uniapp.R;
import com.example.uniapp.front.controller.global.AboutScreen;
import com.example.uniapp.front.model.data.Training;
import com.example.uniapp.front.model.data.TrainingBlock;
import com.example.uniapp.front.model.market.MarketSwim;
import com.example.uniapp.front.model.market.MarketTimes;
import com.example.uniapp.front.view.recyclerview.RvTrainingUpdateTimesAdapter;

import java.util.ArrayList;

public class UpdateTrainingTimesPopup extends Dialog {
    private Activity                 activity;
    private Training                 training;
    private ArrayList<TrainingBlock> trainingBlockArrayList;
    private int                      position;
    private float                    timeRef;
    private Button                   confirmed;

    public UpdateTrainingTimesPopup(Activity activity, Training training, int position, Float timeRef) {
        super(activity, R.style.Theme_AppCompat_Dialog);
        this.activity = activity;
        this.training = training;
        this.position = position;
        this.timeRef  =  timeRef;
        setContentView(R.layout.popup_update_training_times);
        if (getWindow() != null) getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        trainingBlockArrayList = new ArrayList<>();
        trainingBlockArrayList.addAll(training.getTrainingBlockList());
        TextView titleSerie = findViewById(R.id.popup_update_training_times_title_serie);
        RecyclerView recyclerView = findViewById(R.id.popup_update_training_times_rv);

        titleSerie.setText(getTitleSerie());

        RvTrainingUpdateTimesAdapter rvTrainingUpdateTimesAdapter = new RvTrainingUpdateTimesAdapter(training.getTrainingBlockList().get(position));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(rvTrainingUpdateTimesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTrainingUpdateTimesAdapter.notifyDataSetChanged();

        Button denied = findViewById(R.id.popup_update_training_denied);
        confirmed = findViewById(R.id.popup_update_training_confirmed);

        denied.setOnClickListener(v -> dismiss());
    }

    public void build() {
        show();
        Window window = getWindow();
        if (window != null) window.setLayout((int) (AboutScreen.getWidth(activity) * 0.95), (int) (AboutScreen.getHeight(activity) * 0.95));
    }

    private String getTitleSerie() {
        return training.getTrainingBlockList().get(position).getNbSet() + " x " + training.getTrainingBlockList().get(position).getDistance() + "" + MarketSwim.convertShortSwim(training.getTrainingBlockList().get(position).getSwim()) + " Z" + training.getTrainingBlockList().get(position).getZone() + " : " + MarketTimes.fetchFloatToTime(timeRef);
    }

    public ArrayList<TrainingBlock> getTrainingBlockArrayList() { return trainingBlockArrayList; }
    public Button getConfirmed() { return confirmed; }
}
