package com.example.uniapp.controllers.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uniapp.R;
import com.example.uniapp.models.Race;
import com.example.uniapp.models.Training;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewTrainingDetailAdapter extends RecyclerView.Adapter<RecyclerViewTrainingDetailAdapter.MyViewHolder> {
    private List<String>  allSwims;
    private List<Integer> allSets;
    private List<Integer> allDistances;
    private List<String>  allTimes;
    private List<Integer> allZones;

    public RecyclerViewTrainingDetailAdapter(Training training) {
        allSwims     = training.getSwims();
        allSets      = training.getSets();
        allDistances = training.getDistance();
        allTimes     = training.getTimes();
        allZones     = training.getZones();
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
    public int getItemCount() { return allSets.size(); }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView serie;
        private LineChart lineChart;
        private RecyclerView recyclerViewTimes;

        private RecyclerViewTrainingDetailTimeAdapter recyclerViewTrainingDetailTimeAdapter;
        private LineDataSet lineDataSet;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            serie             = (TextView)     itemView.findViewById(R.id.rv_detail_training_items_serie_subtitle);
            lineChart         = (LineChart)    itemView.findViewById(R.id.rv_detail_training_items_line_chart);
            recyclerViewTimes = (RecyclerView) itemView.findViewById(R.id.rv_detail_training_items_recyclerview);
        }

        @SuppressLint("SetTextI18n")
        public void display(int indexSerie) {
            int startIndex = Training.getStartIndexFromSetIndex(allSets, indexSerie);
            int endIndex   = Training.getEndIndexFromSetIndex(allSets, indexSerie);

            serie.setText(allSets.get(indexSerie) + " x " + allDistances.get(indexSerie) + Race.convertShortSwim(allSwims.get(indexSerie)));
            configureAndShowLineChart(lineChart, indexSerie, startIndex, endIndex, true);
            showRecyclerViewTrainingDetailTime(allTimes.subList(startIndex, endIndex));
        }

        private void configureAndShowLineChart(LineChart lineChart, int indexSerie, int startIndex, int endIndex, boolean isAnimation) {
            lineChart.clear();
            lineChart.setScaleEnabled(false);
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.clear();

            int[] colors = {R.color.greenLight, R.color.redLight, R.color.blueDeep, R.color.orangeLight, R.color.orangeLight};

            ArrayList<Entry> yValues = setupTimesLineChart(indexSerie, startIndex, endIndex);
            lineDataSet = new LineDataSet(yValues, allDistances.get(indexSerie) + "" + Race.convertShortSwim(allSwims.get(indexSerie)));
            lineDataSet.setLineWidth(1.5f);
            lineDataSet.setCircleRadius(3f);
            lineDataSet.setCircleHoleRadius(1.5f);
            lineDataSet.setColor(this.itemView.getResources().getColor(colors[indexSerie%colors.length]));
            lineDataSet.setCircleColor(this.itemView.getResources().getColor(colors[indexSerie%colors.length]));
            lineDataSet.setHighLightColor(this.itemView.getResources().getColor(colors[indexSerie%colors.length]));
            lineDataSet.setDrawValues(false);
            lineDataSet.setDrawFilled(true);
            lineDataSet.setFillColor(this.itemView.getResources().getColor(colors[indexSerie%colors.length]));
            lineDataSet.setFillAlpha(100);
            dataSets.add(lineDataSet);


            LineData data = new LineData(dataSets);

            data.setHighlightEnabled(false);
            if (isAnimation) lineChart.animateXY(400, 800, Easing.EaseInOutQuad);
            lineChart.notifyDataSetChanged();
            lineChart.setData(data);
            lineChart.getDescription().setEnabled(false); //ok
            lineChart.setDrawGridBackground(false); //ok
            lineChart.getAxisLeft().setEnabled(false);
            lineChart.getAxisLeft().setSpaceTop(40);
            lineChart.getAxisLeft().setSpaceBottom(40);
            lineChart.getAxisRight().setEnabled(false);
            lineChart.getXAxis().setEnabled(false);
            lineChart.getLegend().setTextColor(itemView.getResources().getColor(R.color.colorText));
        }

        private ArrayList<Entry> setupTimesLineChart(int setIndex, int startIndex, int endIndex) {
            int cpt = 0;
            ArrayList<Entry> result = new ArrayList<>();
            for (int i = startIndex; i < endIndex; i++) {
                result.add(new Entry(cpt, Race.fetchTimeToFloat(allTimes.get(i))));
                cpt++;
            }
            return result;
        }

        private void showRecyclerViewTrainingDetailTime(List<String> allTimes) {
            recyclerViewTrainingDetailTimeAdapter = new RecyclerViewTrainingDetailTimeAdapter(allTimes);
            recyclerViewTimes.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            recyclerViewTimes.setAdapter(recyclerViewTrainingDetailTimeAdapter);
            recyclerViewTimes.setNestedScrollingEnabled(true);
            recyclerViewTrainingDetailTimeAdapter.notifyDataSetChanged();
        }
    }
}
