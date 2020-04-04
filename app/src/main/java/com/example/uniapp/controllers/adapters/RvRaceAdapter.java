package com.example.uniapp.controllers.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uniapp.R;
import com.example.uniapp.models.MarketTimes;
import com.example.uniapp.views.popup.CompetitionDetailPopup;
import com.example.uniapp.models.MarketRaces;
import com.example.uniapp.models.database.dao.race.Race;

import java.util.List;

public class RvRaceAdapter extends RecyclerView.Adapter<RvRaceAdapter.MyViewHolder> {
    private List<Race> races;
    private Context context;

    public RvRaceAdapter(Context context, List<Race> races) { this.context = context; this.races = races; }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.rv_race_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final Race race = races.get(position);
        final Race raceUp;
        final Race raceDown;

        List<Race> subList  = races.subList(position, races.size());
        raceUp = MarketRaces.getBestTime(subList, 1);
        raceDown = MarketRaces.getBestTime(subList, 2);

        holder.display(raceDown, races.get(position), raceUp);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompetitionDetailPopup competitionDetailPopup = new CompetitionDetailPopup(context, races.subList(position, races.size()), race);
                competitionDetailPopup.build();
            }
        });
    }

    @Override
    public int getItemCount() {
        return races.size();
    }

    public void removeItem(int position) {
        races.remove(position);
        notifyItemRemoved(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView city;
        private TextView date;
        private TextView time;
        private TextView diff;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            city = (TextView)itemView.findViewById(R.id.rv_race_item_competition_name);
            date = (TextView)itemView.findViewById(R.id.rv_race_item_date);
            time = (TextView)itemView.findViewById(R.id.rv_race_item_time);
            diff = (TextView)itemView.findViewById(R.id.rv_race_item_diff);
        }

        public void display(Race raceDown, Race race, Race raceUp) {
            city.setText(String.valueOf(race.getCity()) + "\nNiveau : " + race.getLevel());
            date.setText("Le " + String.valueOf(race.getDate()));
            time.setText(String.valueOf(race.getTime()));
            time.setTextColor(itemView.getResources().getColor(Race.getCurrentColor(race.getSwim())));

            diff.setText(MarketTimes.compareTwoTimes(raceDown.getTime(), race.getTime(), raceUp.getTime()));
            diff.setTextColor(itemView.getResources().getColor((diff.getText().toString().charAt(1) != '+') ? R.color.greenDeep : R.color.redDeep));
        }
    }
}
