package com.example.uniapp.controllers.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

public class RecyclerViewTrainingAdapter extends RecyclerView.Adapter<RecyclerViewTrainingAdapter.MyViewHolder> {
    private List<Training> allTrainings;

    public RecyclerViewTrainingAdapter(List<Training> allTrainings) { this.allTrainings = allTrainings; }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.rv_training_items, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Training training = allTrainings.get(position);
        holder.display(training);
    }

    @Override
    public int getItemCount() { return allTrainings.size(); }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView date_sizePool_title;
        private TextView serie_title;
        private TextView serie_content;
        private LineChart lineChart;
        private LineDataSet lineDataSet;
        private ArrayList<Button> stars;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            date_sizePool_title = (TextView) itemView.findViewById(R.id.rv_training_items_date_sizePool_title);
            serie_title         = (TextView) itemView.findViewById(R.id.rv_training_items_serie_title);
            serie_content       = (TextView) itemView.findViewById(R.id.rv_training_items_serie_content);
            lineChart           = (LineChart) itemView.findViewById(R.id.rv_training_items_line_chart);
            stars               = new ArrayList<Button>();
            for (int i = 0; i < 5; i++) {
                if (i == 0) stars.add((Button) itemView.findViewById(R.id.rv_training_items_difficulty_star_1));
                if (i == 1) stars.add((Button) itemView.findViewById(R.id.rv_training_items_difficulty_star_2));
                if (i == 2) stars.add((Button) itemView.findViewById(R.id.rv_training_items_difficulty_star_3));
                if (i == 3) stars.add((Button) itemView.findViewById(R.id.rv_training_items_difficulty_star_4));
                if (i == 4) stars.add((Button) itemView.findViewById(R.id.rv_training_items_difficulty_star_5));
            }
        }

        public void display(Training training) {
            String text = "";

            date_sizePool_title.setText(training.getDate());
            for (int i = 0; i < training.getSets().size(); i++)
                text += training.getSets().get(i) + "x" + training.getDistance().get(i) + Race.convertShortSwim(training.getSwims().get(i)) + "\n";
            text = text.substring(0, text.length() - 1);
            serie_content.setText(text);

            for (int i = 0; i < training.getDifficulty(); i++) {
                stars.get(i).setCompoundDrawablesWithIntrinsicBounds(this.itemView.getResources().getDrawable(R.drawable.ic_radio_button_checked_white_24dp), null, null, null);
            }
            configureAndShowLineChart(lineChart, true);

        }

        private void configureAndShowLineChart(LineChart lineChart, boolean isAnimation) {
            lineChart.clear();
            lineChart.setScaleEnabled(false);
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.clear();

            int[] colors = {R.color.greenLight, R.color.redLight, R.color.blueDeep, R.color.orangeLight, R.color.orangeLight};

            for (int i = 0; i < allTrainings.get(getAdapterPosition()).getSets().size(); i++) {
                int startIndex = 0;
                int endIndex = 0;
                for (int j = 0; j < i; j++) {
                    startIndex += allTrainings.get(getAdapterPosition()).getSets().get(j);
                }
                endIndex = startIndex + allTrainings.get(getAdapterPosition()).getSets().get(i);
                ArrayList<Entry> yValues = setupTimesLineChart(i, startIndex, endIndex);
                lineDataSet = new LineDataSet(yValues, allTrainings.get(getAdapterPosition()).getDistance().get(i) + "" + Race.convertShortSwim(allTrainings.get(getAdapterPosition()).getSwims().get(i)));
                lineDataSet.setLineWidth(1.5f);
                lineDataSet.setCircleRadius(3f);
                lineDataSet.setCircleHoleRadius(1.5f);
                lineDataSet.setColor(this.itemView.getResources().getColor(colors[i%colors.length]));
                lineDataSet.setCircleColor(this.itemView.getResources().getColor(colors[i%colors.length]));
                lineDataSet.setHighLightColor(this.itemView.getResources().getColor(colors[i%colors.length]));
                lineDataSet.setDrawValues(false);
                lineDataSet.setDrawFilled(true);
                lineDataSet.setFillColor(this.itemView.getResources().getColor(colors[i%colors.length]));
                lineDataSet.setFillAlpha(100);
                dataSets.add(lineDataSet);
            }

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
            ArrayList<Entry> result = new ArrayList<>();
            int cpt = 0;

            for (int i = startIndex; i < endIndex; i++) {
                result.add(new Entry(cpt, Race.fetchTimeToFloat(allTrainings.get(this.getAdapterPosition()).getTimes().get(i))));
                cpt++;
            }
            return result;
        }
    }
}
