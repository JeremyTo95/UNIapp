package com.example.uniapp.controllers.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uniapp.R;
import com.example.uniapp.controllers.activities.DetailRaceTimeActivity;
import com.example.uniapp.models.MarketRaces;
import com.example.uniapp.models.Race;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewRaceAdapter extends RecyclerView.Adapter<RecyclerViewRaceAdapter.MyViewHolder> {
    private List<Race> mRaces;

    public RecyclerViewRaceAdapter(List<Race> races) {
        mRaces = races;
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
        final Race race = mRaces.get(position);
        final Race raceUp;
        final Race raceDown;

        List<Race> subList  = mRaces.subList(position, mRaces.size());
        raceUp = MarketRaces.getBestTime(subList, 1);
        raceDown = MarketRaces.getBestTime(subList, 2);

        if (Race.fetchTimeToFloat(race.getTime()) > Race.fetchTimeToFloat(raceUp.getTime()))
            holder.display(mRaces.get(position), raceUp);
        else
            holder.display(mRaces.get(position), raceDown);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailRaceTimeActivity.class);
                intent.putExtra("EXTRA_RACE_SELECTED", (Serializable) race);
                intent.putExtra("EXTRA_SUBLIST_RACES", new ArrayList<Race>(mRaces.subList(position, mRaces.size())));
                v.getContext().startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return mRaces.size();
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

        public void display(Race race, Race raceBis) {
            city.setText(String.valueOf(race.getCity()) + "\nNiveau : " + race.getLevel());
            date.setText("Le " + String.valueOf(race.getDate()));
            time.setText(String.valueOf(race.getTime()));
            time.setTextColor(itemView.getResources().getColor(Race.getCurrentColor(race.getSwim())));

            if (Race.fetchTimeToFloat(raceBis.getTime()) < Race.fetchTimeToFloat(race.getTime())) {
                diff.setTextColor(this.itemView.getResources().getColor(R.color.redDeep));
                diff.setText(String.format("+%.2fs", (Race.fetchTimeToFloat(race.getTime()) - Race.fetchTimeToFloat(raceBis.getTime()))));
            }
            else {
                diff.setTextColor(this.itemView.getResources().getColor(R.color.greenDeep));
                diff.setText(String.format("-%.2fs", (Race.fetchTimeToFloat(raceBis.getTime()) - Race.fetchTimeToFloat(race.getTime()))));
            }
        }

        public void add(int position, Race race) {
            mRaces.add(position, race);
            notifyItemInserted(position);
        }

        public void remove(int position) {
            mRaces.remove(position);
            notifyItemRemoved(position);
        }
    }
}
