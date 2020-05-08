package com.example.uniapp.controllers.adapters.recyclerview;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uniapp.R;
import com.example.uniapp.controllers.activities.MainActivity;
import com.example.uniapp.models.markets.MarketRaces;
import com.example.uniapp.models.markets.MarketSwim;
import com.example.uniapp.models.markets.MarketTimes;
import com.example.uniapp.models.markets.MarketTrainings;
import com.example.uniapp.models.SetupLineChart;
import com.example.uniapp.models.database.dao.race.Race;
import com.example.uniapp.models.database.dao.training.Training;
import com.example.uniapp.models.database.dao.trainingblock.TrainingBlock;
import com.example.uniapp.views.AboutScreen;
import com.example.uniapp.views.popup.training.UpdateTrainingTimesPopup;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;
import java.util.List;

public class RvTrainingDetailAdapter extends RecyclerView.Adapter<RvTrainingDetailAdapter.MyViewHolder> {
    private Activity activity;
    private Training training;
    private int sizePool;

    public RvTrainingDetailAdapter(Activity activity, Training training) {
        this.activity = activity;
        this.training = training;
        sizePool      = training.getSizePool();
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
        private Race          bestTime;
        private TextView      serieSubtitle;
        private TextView      serieZone;
        private LineChart     lineChart;
        private Button        updateBtn;
        private Float         timeReference;
        private List<List<Float>> timeValues;
        private List<String> legendValues;
        private RecyclerView  recyclerViewTimes;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            serieSubtitle     = (TextView)     itemView.findViewById(R.id.rv_detail_training_items_serie_subtitle);
            serieZone         = (TextView)     itemView.findViewById(R.id.rv_detail_training_items_serie_zones);
            lineChart         = (LineChart)    itemView.findViewById(R.id.rv_detail_training_items_line_chart);
            updateBtn         = (Button)       itemView.findViewById(R.id.rv_training_detail_item_update_btn);
            recyclerViewTimes = (RecyclerView) itemView.findViewById(R.id.rv_detail_training_items_recyclerview);
        }

        public void display(final int indexSerie) {
            trainingBlock = training.getTrainingBlockList().get(indexSerie);
            bestTime      = MarketRaces.getBestTime(MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(training.getSizePool(), trainingBlock.getDistance(), trainingBlock.getSwim()), 1);
            timeReference = MarketTimes.fetchTimeToFloat(MarketTimes.convertCompetitionTimeToZoneTime((bestTime.getTime()), trainingBlock.getZone()));

            serieSubtitle.setText(trainingBlock.getNbSet() + " x " + trainingBlock.getDistance() + MarketSwim.convertShortSwim(trainingBlock.getSwim()));
            serieZone.setText("Z O N E  " + trainingBlock.getZone());

            updateGraphicsElements();
            if (AboutScreen.isNightMode(activity)) updateBtn.setCompoundDrawablesWithIntrinsicBounds(activity.getResources().getDrawable(R.drawable.ic_create_white_24dp), null, null, null);
            else updateBtn.setCompoundDrawablesWithIntrinsicBounds(activity.getResources().getDrawable(R.drawable.ic_create_black_24dp), null, null, null);

            updateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UpdateTrainingTimesPopup updateTrainingTimesPopup = new UpdateTrainingTimesPopup(activity, training, indexSerie, timeReference);
                    updateTrainingTimesPopup.build();
                    updateTrainingTimesPopup.getConfirmed().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            System.out.println(updateTrainingTimesPopup.getTrainingBlockArrayList().toArray());
                            training.setTrainingBlockList(updateTrainingTimesPopup.getTrainingBlockArrayList());
                            updateTrainingTimesPopup.dismiss();
                            MainActivity.appDataBase.trainingDAO().updateTraining(training);
                            System.out.println(training);
                            updateGraphicsElements();
                        }
                    });
                }
            });
        }

        private void updateGraphicsElements() {
            timeValues   = new ArrayList<List<Float>>();
            legendValues = new ArrayList<String>();
            timeValues.add(trainingBlock.getTimes());
            timeValues.add(MarketTrainings.getRefLine(timeReference, trainingBlock.getTimes().size()));
            legendValues.add("Temps réalisé");
            legendValues.add("Temps référent");
            SetupLineChart.configureMyLinesChart(activity, timeValues, lineChart, legendValues, true);
            showRecyclerViewTrainingDetailTime();
        }

        private void showRecyclerViewTrainingDetailTime() {
            List<Race> subRacesList = MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(training.getSizePool(), trainingBlock.getDistance(), trainingBlock.getSwim());
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
