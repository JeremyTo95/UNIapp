package com.example.uniapp.controllers.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uniapp.R;
import com.example.uniapp.controllers.activities.DetailRaceTimeActivity;
import com.example.uniapp.controllers.fragments.DetailRaceTimeFragment;
import com.example.uniapp.models.MarketRaceTime;
import com.example.uniapp.models.RaceTime;
import com.example.uniapp.views.DateComparator;
import com.example.uniapp.views.TimeComparator;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private List<RaceTime> mRaceTimes;

    public RecyclerViewAdapter(List<RaceTime> raceTimes) {
        mRaceTimes = raceTimes;
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
        final RaceTime raceTime = mRaceTimes.get(position);
        final RaceTime raceTimeUp;
        final RaceTime raceTimeDown;

        List<RaceTime> subList  = mRaceTimes.subList(position, mRaceTimes.size());
        raceTimeUp   = MarketRaceTime.getBestTime(subList, 1);
        raceTimeDown = MarketRaceTime.getBestTime(subList, 2);

        if (MarketRaceTime.fetchTimeToFloat(raceTime.getTime()) > MarketRaceTime.fetchTimeToFloat(raceTimeUp.getTime()))
            holder.display(mRaceTimes.get(position), raceTimeUp);
        else
            holder.display(mRaceTimes.get(position), raceTimeDown);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailRaceTimeActivity.class);
                intent.putExtra("EXTRA_RACE_SELECTED", (Serializable) raceTime);
                intent.putExtra("EXTRA_SUBLIST_RACES", new ArrayList<RaceTime>(mRaceTimes.subList(position, mRaceTimes.size())));
                v.getContext().startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return mRaceTimes.size();
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

        public void display(RaceTime raceTime, RaceTime raceTimeBis) {
            city.setText(String.valueOf(raceTime.getCity()) + "\nNiveau : " + raceTime.getLevel());
            date.setText("Le " + String.valueOf(raceTime.getDate()));
            time.setText(String.valueOf(raceTime.getTime()));
            time.setTextColor(itemView.getResources().getColor(RaceTime.getCurrentColor(raceTime.getSwim())));

            if (MarketRaceTime.fetchTimeToFloat(raceTimeBis.getTime()) < MarketRaceTime.fetchTimeToFloat(raceTime.getTime())) {
                diff.setTextColor(this.itemView.getResources().getColor(R.color.redDeep));
                diff.setText(String.format("+%.2fs", (MarketRaceTime.fetchTimeToFloat(raceTime.getTime()) - MarketRaceTime.fetchTimeToFloat(raceTimeBis.getTime()))));
            }
            else {
                diff.setTextColor(this.itemView.getResources().getColor(R.color.greenDeep));
                diff.setText(String.format("-%.2fs", (MarketRaceTime.fetchTimeToFloat(raceTimeBis.getTime()) - MarketRaceTime.fetchTimeToFloat(raceTime.getTime()))));
            }
        }

        public void add(int position, RaceTime raceTime) {
            mRaceTimes.add(position, raceTime);
            notifyItemInserted(position);
        }

        public void remove(int position) {
            mRaceTimes.remove(position);
            notifyItemRemoved(position);
        }
    }
}
