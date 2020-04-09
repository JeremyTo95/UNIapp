package com.example.uniapp.controllers.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uniapp.R;
//import com.example.uniapp.controllers.activities.DetailTrainingActivity;
import com.example.uniapp.controllers.activities.MainActivity;
import com.example.uniapp.models.MarketTimes;
import com.example.uniapp.views.popup.TrainingDetailPopup;
import com.example.uniapp.models.database.dao.race.Race;
import com.example.uniapp.models.database.dao.training.Training;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class RvTrainingAdapter extends RecyclerView.Adapter<RvTrainingAdapter.MyViewHolder> {
    private Context context;
    private List<Training> allTrainings;

    public RvTrainingAdapter(Context context, List<Training> allTrainings) { this.context = context; this.allTrainings = allTrainings; }

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
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                TrainingDetailPopup trainingDetailPopup = new TrainingDetailPopup(context, training);
                trainingDetailPopup.build();
                /*Intent intent = new Intent(v.getContext(), DetailTrainingActivity.class);
                intent.putExtra("EXTRA_TRAINING_SELECTED", (Serializable) training);
                intent.putExtra("EXTRA_ALL_RACES", (Serializable) allRaces);
                v.getContext().startActivity(intent);*/
            }
        });
    }

    @Override
    public int getItemCount() { return allTrainings.size(); }

    public void removeItem(int position) {
        allTrainings.remove(position);
        notifyItemRemoved(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView date_sizePool_title;
        private TextView serie_content;
        private LineChart lineChart;
        private ArrayList<Button> stars;

        private MyViewHolder(@NonNull View itemView) {
            super(itemView);

            date_sizePool_title = (TextView) itemView.findViewById(R.id.rv_training_items_date_sizePool_title);
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

        private void display(Training training) {
            String text = "";

            date_sizePool_title.setText(training.getDate());
            for (int i = 0; i < training.getTrainingBlockList().size(); i++)
                text += training.getTrainingBlockList().get(i).getNbSet() + " x " + training.getTrainingBlockList().get(i).getDistance() + Race.convertShortSwim(training.getTrainingBlockList().get(i).getSwim()) + " Z" + training.getTrainingBlockList().get(i).getZone() + "\n";
            text = text.substring(0, text.length() - 1);
            serie_content.setText(text);

            for (int i = 0; i < stars.size(); i++) stars.get(i).setClickable(false);
            for (int i = 0; i < training.getDifficulty(); i++) stars.get(i).setCompoundDrawablesWithIntrinsicBounds(this.itemView.getResources().getDrawable(R.drawable.ic_radio_button_checked_white_24dp), null, null, null);

            configureAndShowLineChart(lineChart, true);
        }

        private void configureAndShowLineChart(LineChart lineChart, boolean isAnimation) {
            lineChart.clear();
            lineChart.setScaleEnabled(false);

            LineData lineData = setupTrainingLineData();
            configureLineChart(lineData, isAnimation);
        }

        private LineData setupTrainingLineData() {
            int[] colors = {R.color.greenLight, R.color.redLight, R.color.blueDeep, R.color.orangeLight, R.color.orangeLight};
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();

            for (int i = 0; i < allTrainings.get(getAdapterPosition()).getTrainingBlockList().size(); i++) {
                ArrayList<Entry> yValues = setupTimesLineChart(allTrainings.get(getAdapterPosition()), i);
                LineDataSet lineDataSet = new LineDataSet(yValues, allTrainings.get(getAdapterPosition()).getTrainingBlockList().get(i).getDistance() + "" + Race.convertShortSwim(allTrainings.get(getAdapterPosition()).getTrainingBlockList().get(i).getSwim()));
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
            return data;
        }

        private ArrayList<Entry> setupTimesLineChart(Training training, int setIndex) {
            ArrayList<Entry> result = new ArrayList<>();
            int cpt = 0;
            if (training.getTrainingBlockList().get(setIndex).getTimes() != null) {
                for (int i = 0; i < training.getTrainingBlockList().get(setIndex).getTimes().size(); i++) {
                    result.add(new Entry(cpt, training.getTrainingBlockList().get(setIndex).getTimes().get(i)));
                    cpt++;
                }
            }
            return result;
        }

        private void configureLineChart(LineData lineData, boolean isAnimation) {
            if (isAnimation) lineChart.animateXY(400, 800, Easing.EaseInOutQuad);
            lineChart.notifyDataSetChanged();
            lineChart.setData(lineData);
            lineChart.getDescription().setEnabled(false); //ok
            lineChart.setDrawGridBackground(false); //ok
            lineChart.getAxisLeft().setEnabled(false);
            lineChart.getAxisLeft().setSpaceTop(40);
            lineChart.getAxisLeft().setSpaceBottom(40);
            lineChart.getAxisLeft().setAxisMinimum(0.0f);
            lineChart.getAxisRight().setEnabled(false);
            lineChart.getXAxis().setEnabled(false);
            lineChart.setTouchEnabled(false);
            lineChart.getLegend().setTextColor(itemView.getResources().getColor(R.color.colorText));
        }
    }
}
