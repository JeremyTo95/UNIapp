package com.example.uniapp.controllers.adapters.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uniapp.R;
import com.example.uniapp.controllers.activities.MainActivity;
import com.example.uniapp.models.markets.MarketSwim;
import com.example.uniapp.models.markets.MarketTimes;
import com.example.uniapp.views.popup.competition.CompetitionDetailPopup;
import com.example.uniapp.models.markets.MarketRaces;
import com.example.uniapp.models.database.dao.race.Race;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class RvRaceAdapter extends RecyclerView.Adapter<RvRaceAdapter.MyViewHolder> {
    private List<Race> races;
    private Activity   activity;
    private Context    context;
    private Race       raceDeleted;
    private int        raceDeletedPosition;

    public RvRaceAdapter(Activity activity, Context context, List<Race> races) {
        this.activity = activity;
        this.context = context;
        this.races = races;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.rv_race_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final Race race;
        final Race raceUp;
        final Race raceDown;

        List<Race> subList = races.subList(position, races.size());
        race               = races.get(position);
        raceUp             = MarketRaces.getBestTime(subList, 1);
        raceDown           = MarketRaces.getBestTime(subList, 2);

        holder.display(raceDown, races.get(position), raceUp);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompetitionDetailPopup competitionDetailPopup = new CompetitionDetailPopup(activity, races.subList(position, races.size()), race);
                competitionDetailPopup.build();
            }
        });
    }

    @Override
    public int getItemCount() {
        return races.size();
    }

    public void removeItem(int position) {
        raceDeleted = races.get(position);
        raceDeletedPosition = position;
        MainActivity.appDataBase.raceDAO().delete(races.get(position));
        races.remove(position);
        notifyItemRemoved(position);
        showUndoSnackbar();
    }

    private void showUndoSnackbar() {
        View view = activity.findViewById(R.id.activty_main_fragment_layout);
        Snackbar snackbar = Snackbar.make(view, "Course supprimée", Snackbar.LENGTH_LONG);
        snackbar.setAction("Annuler", v -> undoDelete());
        snackbar.show();
    }

    public void undoDelete() {
        MainActivity.appDataBase.raceDAO().insertRace(raceDeleted);
        races.add(raceDeletedPosition, raceDeleted);
        notifyItemInserted(raceDeletedPosition);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView city;
        private TextView date;
        private TextView time;
        private TextView diff;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            city  = (TextView) itemView.findViewById(R.id.rv_race_item_competition_name);
            date  = (TextView) itemView.findViewById(R.id.rv_race_item_date);
            time  = (TextView) itemView.findViewById(R.id.rv_race_item_time);
            diff  = (TextView) itemView.findViewById(R.id.rv_race_item_diff);
        }

        public void display(Race raceDown, Race race, Race raceUp) {
            city.setText(String.valueOf(race.getCity()) + "\nNiveau : " + race.getLevel());
            date.setText("Le " + String.valueOf(race.getDate()));
            time.setText(String.valueOf(MarketTimes.fetchFloatToTime(race.getTime())));
            time.setTextColor(MarketSwim.getCurrentColor(context, race.getSwim()));

            diff.setText(MarketTimes.compareTwoTimes(raceDown.getTime(), race.getTime(), raceUp.getTime()));
            diff.setTextColor(itemView.getResources().getColor((diff.getText().toString().charAt(1) != '+') ? R.color.greenDeep : R.color.redDeep));
        }
    }
}
