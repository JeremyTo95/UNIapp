package com.example.uniapp.front.view.recyclerview;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.uniapp.front.view.actvities.DetailTrainingActivity;
import com.example.uniapp.back.room.RoomDataBase;
import com.example.uniapp.front.presenter.global.AboutScreen;
import com.example.uniapp.R;
import com.example.uniapp.front.model.graphicsitem.BuildLineChart;
import com.example.uniapp.front.model.data.Training;
import com.example.uniapp.front.model.market.MarketSwim;
import com.example.uniapp.front.model.market.MarketTrainings;
import com.example.uniapp.front.view.fragments.TrainingDetailFragment;
import com.github.mikephil.charting.charts.LineChart;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class RvTrainingAdapter extends RecyclerView.Adapter<RvTrainingAdapter.MyViewHolder> {
    private Activity           activity;
    private RoomDataBase       roomDataBase;
    private List<Training>     allTrainings;
    private List<List<Float>>  inputTimes;
    private List<List<String>> legendValues;

    private Training trainingDeleted;
    private int      trainingDeletedPosition;

    public RvTrainingAdapter(Activity activity, List<Training> allTrainings) {
        this.activity     = activity;
        this.allTrainings = allTrainings;
        this.roomDataBase = RoomDataBase.getDatabase(activity.getApplicationContext());
    }

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
        inputTimes              = new ArrayList<>();
        legendValues            = new ArrayList<>();
        for (int i = 0; i < training.getTrainingBlockList().size(); i++) {
            inputTimes.add(training.getTrainingBlockList().get(i).getTimes());
            legendValues.add(MarketTrainings.getLegendTraining(training.getTrainingBlockList()));
        }
        holder.display(training);
        holder.itemView.setOnClickListener(v -> {
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            TrainingDetailFragment trainingDetailFragment = new TrainingDetailFragment(training);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.activty_main_fragment_layout, trainingDetailFragment).addToBackStack(null).commit();
        });
    }

    public void removeItem(int position) {
        trainingDeleted         = allTrainings.get(position);
        trainingDeletedPosition = position;
        roomDataBase.trainingDAO().deleteTraining(allTrainings.get(position));
        allTrainings.remove(position);
        notifyItemRemoved(position);
        showUndoSnackbar();
    }

    private void showUndoSnackbar() {
        View view = activity.findViewById(R.id.activty_main_fragment_layout);
        Snackbar snackbar = Snackbar.make(view, "Entrainement supprimé", Snackbar.LENGTH_LONG);
        snackbar.setAction("Annuler", v -> undoDelete());
        snackbar.show();
    }

    private void undoDelete() {
        roomDataBase.trainingDAO().insertTraining(trainingDeleted);
        allTrainings.add(trainingDeletedPosition, trainingDeleted);
        notifyItemInserted(trainingDeletedPosition);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() { return allTrainings.size(); }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView date_sizePool_title;
        private TextView serie_content;
        private LineChart lineChart;
        private ArrayList<Button> stars;

        private MyViewHolder(@NonNull View itemView) {
            super(itemView);

            date_sizePool_title = itemView.findViewById(R.id.rv_training_items_date_sizePool_title);
            serie_content       = itemView.findViewById(R.id.rv_training_items_serie_content);
            lineChart           = itemView.findViewById(R.id.rv_training_items_line_chart);
            stars               = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                if (i == 0) stars.add(itemView.findViewById(R.id.rv_training_items_difficulty_star_1));
                if (i == 1) stars.add(itemView.findViewById(R.id.rv_training_items_difficulty_star_2));
                if (i == 2) stars.add(itemView.findViewById(R.id.rv_training_items_difficulty_star_3));
                if (i == 3) stars.add(itemView.findViewById(R.id.rv_training_items_difficulty_star_4));
                if (i == 4) stars.add(itemView.findViewById(R.id.rv_training_items_difficulty_star_5));
            }
        }

        private void display(Training training) {
            StringBuilder text = new StringBuilder();
            date_sizePool_title.setText(training.getDate());

            for (int i = 0; i < training.getTrainingBlockList().size(); i++)
                text.append(training.getTrainingBlockList().get(i).getNbSet()).append(" x ").append(training.getTrainingBlockList().get(i).getDistance()).append(MarketSwim.convertShortSwim(training.getTrainingBlockList().get(i).getSwim())).append(" Z").append(training.getTrainingBlockList().get(i).getZone()).append("\n");

            text.delete(text.length() - 1, text.length() - 1);
            serie_content.setText(text.toString());

            for (int i = 0; i < stars.size(); i++) stars.get(i).setClickable(false);
            if (AboutScreen.isNightMode(activity)) {
                for (int i = 0; i < stars.size(); i++) stars.get(i).setCompoundDrawablesWithIntrinsicBounds(itemView.getResources().getDrawable(R.drawable.ic_radio_button_unchecked_white_24dp), null, null, null);
                for (int i = 0; i < training.getDifficulty(); i++) stars.get(i).setCompoundDrawablesWithIntrinsicBounds(itemView.getResources().getDrawable(R.drawable.ic_radio_button_checked_white_24dp), null, null, null);
            } else {
                for (int i = 0; i < stars.size(); i++) stars.get(i).setCompoundDrawablesWithIntrinsicBounds(itemView.getResources().getDrawable(R.drawable.ic_radio_button_unchecked_black_24dp), null, null, null);
                for (int i = 0; i < training.getDifficulty(); i++) stars.get(i).setCompoundDrawablesWithIntrinsicBounds(itemView.getResources().getDrawable(R.drawable.ic_radio_button_checked_black_24dp), null, null, null);
            }

            BuildLineChart.configureMyLinesChart(activity, inputTimes, lineChart, legendValues.get(0), true);
        }
    }
}
