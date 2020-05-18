package com.example.uniapp.front.view.recyclerview;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uniapp.back.room.RoomDataBase;
import com.example.uniapp.front.presenter.global.AboutScreen;
import com.example.uniapp.R;
import com.example.uniapp.front.model.graphicsitem.BuildLineChart;
import com.example.uniapp.front.model.data.Race;
import com.example.uniapp.front.model.data.Training;
import com.example.uniapp.front.model.data.TrainingBlock;
import com.example.uniapp.front.model.market.MarketRaces;
import com.example.uniapp.front.model.market.MarketSwim;
import com.example.uniapp.front.model.market.MarketTimes;
import com.example.uniapp.front.model.market.MarketTrainings;
import com.example.uniapp.front.view.popup.UpdateTrainingTimesPopup;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;
import java.util.List;

public class RvTrainingDetailAdapter extends RecyclerView.Adapter<RvTrainingDetailAdapter.MyViewHolder> {
    private RoomDataBase roomDataBase;
    private Activity     activity;
    private Training     training;

    public RvTrainingDetailAdapter(Activity activity, Training training) {
        this.activity     = activity;
        this.training     = training;
        this.roomDataBase = RoomDataBase.getDatabase(activity.getApplicationContext());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.rv_training_detail_items, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.display(position);
    }

    @Override
    public int getItemCount() { return training.getTrainingBlockList().size(); }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TrainingBlock trainingBlock;
        private TextView      serieSubtitle;
        private TextView      serieZone;
        private LineChart     lineChart;
        private Button        updateBtn;
        private Float         timeReference;
        private RecyclerView  recyclerViewTimes;

        MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            serieSubtitle     = itemView.findViewById(R.id.rv_detail_training_items_serie_subtitle);
            serieZone         = itemView.findViewById(R.id.rv_detail_training_items_serie_zones);
            lineChart         = itemView.findViewById(R.id.rv_detail_training_items_line_chart);
            updateBtn         = itemView.findViewById(R.id.rv_training_detail_item_update_btn);
            recyclerViewTimes = itemView.findViewById(R.id.rv_detail_training_items_recyclerview);
        }

        private String getSerieSubtitle() { return trainingBlock.getNbSet() + " x " + trainingBlock.getDistance() + MarketSwim.convertShortSwim(trainingBlock.getSwim()); }
        private String getSerieZone()     { return "Z O N E  " + trainingBlock.getZone(); }

        private void display(final int indexSerie) {
            trainingBlock = training.getTrainingBlockList().get(indexSerie);
            Race bestTime = MarketRaces.getBestTime(roomDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(training.getSizePool(), trainingBlock.getDistance(), trainingBlock.getSwim()), 1);
            timeReference = MarketTimes.fetchTimeToFloat(MarketTimes.convertCompetitionTimeToZoneTime((bestTime.getTime()), trainingBlock.getZone()));

            serieSubtitle.setText(getSerieSubtitle());
            serieZone.setText(getSerieZone());

            updateGraphicsElements();
            if (AboutScreen.isNightMode(activity)) updateBtn.setCompoundDrawablesWithIntrinsicBounds(activity.getResources().getDrawable(R.drawable.ic_create_white_24dp), null, null, null);
            else updateBtn.setCompoundDrawablesWithIntrinsicBounds(activity.getResources().getDrawable(R.drawable.ic_create_black_24dp), null, null, null);

            updateBtn.setOnClickListener(v -> {
                UpdateTrainingTimesPopup updateTrainingTimesPopup = new UpdateTrainingTimesPopup(activity, training, indexSerie, timeReference);
                updateTrainingTimesPopup.build();
                updateTrainingTimesPopup.getConfirmed().setOnClickListener(v1 -> {
                    training.setTrainingBlockList(updateTrainingTimesPopup.getTrainingBlockArrayList());
                    updateTrainingTimesPopup.dismiss();
                    roomDataBase.trainingDAO().updateTraining(training);
                    updateGraphicsElements();
                });
            });
        }

        private void updateGraphicsElements() {
            List<List<Float>> timeValues = new ArrayList<>();
            List<String> legendValues = new ArrayList<>();
            timeValues.add(trainingBlock.getTimes());
            timeValues.add(MarketTrainings.getRefLine(timeReference, trainingBlock.getTimes().size()));
            legendValues.add("Temps réalisé");
            legendValues.add("Temps référent");
            BuildLineChart.configureMyLinesChart(activity, timeValues, lineChart, legendValues, true);
            showRecyclerViewTrainingDetailTime();
        }

        private void showRecyclerViewTrainingDetailTime() {
            List<Race> subRacesList = roomDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(training.getSizePool(), trainingBlock.getDistance(), trainingBlock.getSwim());
            float timeRef           = MarketRaces.getBestTime(subRacesList, 1).getTime();
            float timeRefStr        = MarketTimes.fetchTimeToFloat(MarketTimes.convertCompetitionTimeToZoneTime(timeRef, trainingBlock.getZone()));
            RvTrainingDetailTimeAdapter rvTrainingDetailTimeAdapter = new RvTrainingDetailTimeAdapter(itemView.getContext(), trainingBlock, timeRefStr);
            recyclerViewTimes.setHasFixedSize(true);
            recyclerViewTimes.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerViewTimes.setAdapter(rvTrainingDetailTimeAdapter);
            recyclerViewTimes.setNestedScrollingEnabled(false);
            rvTrainingDetailTimeAdapter.notifyDataSetChanged();
        }
    }
}
