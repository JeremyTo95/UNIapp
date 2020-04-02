package com.example.uniapp.controllers.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uniapp.R;
import com.example.uniapp.models.MarketRaces;
import com.example.uniapp.models.Race;
import com.example.uniapp.models.Training;
import com.example.uniapp.models.TrainingBlock;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class RvTrainingDetailAdapter extends RecyclerView.Adapter<RvTrainingDetailAdapter.MyViewHolder> {
    private Context context;
    private List<Race> allRaces;
    private Training training;
    private int sizePool;

    public RvTrainingDetailAdapter(Context context, List<Race> allRaces, Training training) {
        this.context  = context;
        this.allRaces = allRaces;
        this.training = training;
        sizePool      = training.getSizePool();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
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
        private TextView serieSubtitle;
        private TextView serieZone;
        private LineChart lineChart;
        private RecyclerView recyclerViewTimes;

        private int[] colors = {R.color.greenLight, R.color.blueDeep, R.color.redLight, R.color.orangeLight, R.color.orangeLight};

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            serieSubtitle     = (TextView)     itemView.findViewById(R.id.rv_detail_training_items_serie_subtitle);
            serieZone         = (TextView)     itemView.findViewById(R.id.rv_detail_training_items_serie_zones);
            lineChart         = (LineChart)    itemView.findViewById(R.id.rv_detail_training_items_line_chart);
            recyclerViewTimes = (RecyclerView) itemView.findViewById(R.id.rv_detail_training_items_recyclerview);
        }

        @SuppressLint("SetTextI18n")
        public void display(int indexSerie) {
            TrainingBlock trainingBlock = training.getTrainingBlockList().get(indexSerie);
            serieSubtitle.setText(trainingBlock.getNbSet() + " x " + trainingBlock.getDistance() + Race.convertShortSwim(trainingBlock.getSwim()));
            serieZone.setText("Z O N E  " + trainingBlock.getZone());
            configureAndShowLineChart(lineChart, indexSerie, trainingBlock, true);
            showRecyclerViewTrainingDetailTime(trainingBlock.getTimes());
        }

        private void showRecyclerViewTrainingDetailTime(List<String> allTimes) {
            RvTrainingDetailTimeAdapter rvTrainingDetailTimeAdapter = new RvTrainingDetailTimeAdapter(itemView.getContext(), allTimes);
            recyclerViewTimes.setHasFixedSize(true);
            recyclerViewTimes.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerViewTimes.setAdapter(rvTrainingDetailTimeAdapter);
            recyclerViewTimes.setNestedScrollingEnabled(false);
            rvTrainingDetailTimeAdapter.notifyDataSetChanged();


        }

        private void configureAndShowLineChart(final LineChart lineChart, int indexSerie, TrainingBlock trainingBlock, boolean isAnimation) {
            lineChart.clear();
            lineChart.setScaleEnabled(false);
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.clear();

            LineDataSet refLineData  = setupRefLineTime(trainingBlock, indexSerie);
            LineDataSet realLineData = setupRealLineTime(trainingBlock, indexSerie);
            dataSets.add(refLineData);
            dataSets.add(realLineData);

            LineData data = new LineData(dataSets);

            data.setHighlightEnabled(true);
            if (isAnimation) lineChart.animateXY(400, 800, Easing.EaseInOutQuad);
            lineChart.notifyDataSetChanged();
            lineChart.setData(data);
            lineChart.setDrawGridBackground(false); //ok
            lineChart.getAxisLeft().setEnabled(false);
            lineChart.getAxisLeft().setSpaceTop(40);
            lineChart.getAxisLeft().setSpaceBottom(40);
            lineChart.getAxisRight().setEnabled(false);
            lineChart.getXAxis().setEnabled(false);
            lineChart.getDescription().setEnabled(false);
            lineChart.setTouchEnabled(false);
            lineChart.setHighlightPerDragEnabled(true);
            lineChart.setHighlightPerTapEnabled(true);
            lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onNothingSelected() { }
                @Override
                public void onValueSelected(Entry e, Highlight h) {
                    lineChart.highlightValue(h);
                }
            });
            lineChart.getLegend().setTextColor(itemView.getResources().getColor(R.color.colorText));
        }

        private ArrayList<Entry> setupTimesLineChart(TrainingBlock trainingBlock) {
            int cpt = 0;
            ArrayList<Entry> result = new ArrayList<>();
            for (int i = 0; i < trainingBlock.getTimes().size(); i++) {
                result.add(new Entry(cpt, Race.fetchTimeToFloat(trainingBlock.getTimes().get(i))));
                cpt++;
            }
            return result;
        }

        private LineDataSet setupRefLineTime(TrainingBlock trainingBlock, int indexSerie) {
            ArrayList<Entry> refTimes = new ArrayList<>();
            Race bestTime = MarketRaces.getBestTime(MarketRaces.getRacesByPoolSizeDistanceRaceSwimRace(allRaces, sizePool, trainingBlock.getDistance(), trainingBlock.getSwim()), 1);
            for (int i = 0; i < trainingBlock.getNbSet(); i++) {
                refTimes.add(new Entry(i, Race.fetchTimeToFloat(Training.convertCompetitionTimeToZoneTime(bestTime.getTime(), trainingBlock.getZone()))));
            }
            LineDataSet refLineTime = new LineDataSet(refTimes, "Temps visé");
            refLineTime.setLineWidth(1.5f);
            refLineTime.setCircleRadius(3f);
            refLineTime.setCircleHoleRadius(1.5f);
            refLineTime.setColor(this.itemView.getResources().getColor(colors[(indexSerie+2)%colors.length]));
            refLineTime.setCircleColor(this.itemView.getResources().getColor(colors[(indexSerie+2)%colors.length]));
            refLineTime.setHighLightColor(this.itemView.getResources().getColor(colors[(indexSerie+2)%colors.length]));
            refLineTime.setDrawValues(false);
            refLineTime.setDrawFilled(true);
            refLineTime.setFillColor(this.itemView.getResources().getColor(colors[(indexSerie+2)%colors.length]));
            refLineTime.setFillAlpha(100);
            return refLineTime;
        }

        private LineDataSet setupRealLineTime(TrainingBlock trainingBlock, int indexSerie) {
            ArrayList<Entry> yValues = setupTimesLineChart(trainingBlock);
            LineDataSet realLineData = new LineDataSet(yValues, "Temps réalisé");
            realLineData.setLineWidth(1.5f);
            realLineData.setCircleRadius(3f);
            realLineData.setCircleHoleRadius(1.5f);
            realLineData.setColor(this.itemView.getResources().getColor(colors[indexSerie%colors.length]));
            realLineData.setCircleColor(this.itemView.getResources().getColor(colors[indexSerie%colors.length]));
            realLineData.setHighLightColor(this.itemView.getResources().getColor(colors[indexSerie%colors.length]));
            realLineData.setDrawValues(false);
            realLineData.setDrawFilled(true);
            realLineData.setFillColor(this.itemView.getResources().getColor(colors[indexSerie%colors.length]));
            realLineData.setFillAlpha(100);
            return realLineData;
        }
    }
}