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

public class RecyclerViewTrainingDetailAdapter extends RecyclerView.Adapter<RecyclerViewTrainingDetailAdapter.MyViewHolder> {
    private List<String>  allSwims;
    private List<Integer> allSets;
    private List<Integer> allDistances;
    private List<String>  allTimes;
    private List<Integer> allZones;
    private int sizePool;

    private Context context;


    public RecyclerViewTrainingDetailAdapter(Context context, Training training) {
        this.context = context;
        allSwims     = training.getSwims();
        allSets      = training.getSets();
        allDistances = training.getDistance();
        allTimes     = training.getTimes();
        allZones     = training.getZones();
        sizePool     = training.getSizePool();
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
    public int getItemCount() { return allSets.size(); }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView serieSubtitle;
        private LineChart lineChart;
        private RecyclerView recyclerViewTimes;

        private int[] colors = {R.color.greenLight, R.color.blueDeep, R.color.redLight, R.color.orangeLight, R.color.orangeLight};

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            serieSubtitle     = (TextView)     itemView.findViewById(R.id.rv_detail_training_items_serie_subtitle);
            lineChart         = (LineChart)    itemView.findViewById(R.id.rv_detail_training_items_line_chart);
            recyclerViewTimes = (RecyclerView) itemView.findViewById(R.id.rv_detail_training_items_recyclerview);
        }

        @SuppressLint("SetTextI18n")
        public void display(int indexSerie) {
            int startIndex = Training.getStartIndexFromSetIndex(allSets, indexSerie);
            int endIndex   = Training.getEndIndexFromSetIndex(allSets, indexSerie);

            serieSubtitle.setText(allSets.get(indexSerie) + " x " + allDistances.get(indexSerie) + Race.convertShortSwim(allSwims.get(indexSerie)));
            configureAndShowLineChart(lineChart, indexSerie, startIndex, endIndex, true);
            showRecyclerViewTrainingDetailTime(allTimes.subList(startIndex, endIndex));
        }

        private void showRecyclerViewTrainingDetailTime(List<String> allTimes) {
            RecyclerViewTrainingDetailTimeAdapter recyclerViewTrainingDetailTimeAdapter = new RecyclerViewTrainingDetailTimeAdapter(itemView.getContext(), allTimes);
            recyclerViewTimes.setHasFixedSize(true);
            recyclerViewTimes.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerViewTimes.setAdapter(recyclerViewTrainingDetailTimeAdapter);
            recyclerViewTimes.setNestedScrollingEnabled(false);
            recyclerViewTrainingDetailTimeAdapter.notifyDataSetChanged();
        }

        private void configureAndShowLineChart(final LineChart lineChart, int indexSerie, int startIndex, int endIndex, boolean isAnimation) {
            lineChart.clear();
            lineChart.setScaleEnabled(false);
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.clear();

            LineDataSet refLineData  = setupRefLineTime(indexSerie);
            LineDataSet realLineData = setupRealLineTime(indexSerie, startIndex, endIndex);
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
            lineChart.setTouchEnabled(true);
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

        private ArrayList<Entry> setupTimesLineChart(List<String> allTimes, int startIndex, int endIndex) {
            int cpt = 0;
            ArrayList<Entry> result = new ArrayList<>();
            for (int i = startIndex; i < endIndex; i++) {
                result.add(new Entry(cpt, Race.fetchTimeToFloat(allTimes.get(i))));
                cpt++;
            }
            return result;
        }

        private LineDataSet setupRefLineTime(int indexSerie) {
            ArrayList<Entry> refTimes = new ArrayList<>();
            List<Race> allTimes = MarketRaces.initAllTimes();
            Race bestTime = MarketRaces.getBestTime(MarketRaces.getRacesByPoolSizeDistanceRaceSwimRace(allTimes, sizePool, allDistances.get(indexSerie), allSwims.get(indexSerie)), 1);
            for (int i = 0; i < allSets.get(indexSerie); i++) {
                refTimes.add(new Entry(i, Race.fetchTimeToFloat(Training.convertCompetitionTimeToZoneTime(bestTime.getTime(), allZones.get(indexSerie)))));
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

        private LineDataSet setupRealLineTime(int indexSerie, int startIndex, int endIndex) {
            ArrayList<Entry> yValues = setupTimesLineChart(allTimes, startIndex, endIndex);
            LineDataSet realLineData = new LineDataSet(yValues, allDistances.get(indexSerie) + "" + Race.convertShortSwim(allSwims.get(indexSerie)));
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
