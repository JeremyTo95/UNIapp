package com.example.uniapp.controllers.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uniapp.R;
import com.example.uniapp.models.RaceTime;

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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.display(mRaceTimes.get(position));
    }

    @Override
    public int getItemCount() {
        return mRaceTimes.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView city;
        private TextView date;
        private TextView time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            city = (TextView)itemView.findViewById(R.id.rv_race_item_competition_name);
            date = (TextView)itemView.findViewById(R.id.rv_race_item_date);
            time = (TextView)itemView.findViewById(R.id.rv_race_item_time);
        }

        public void display(RaceTime raceTime) {
            city.setText(String.valueOf(raceTime.getCity()) + "\nNiveau : " + raceTime.getLevel());
            date.setText("Le " + String.valueOf(raceTime.getDate()));
            time.setText(String.valueOf(raceTime.getTime()));
            time.setTextColor(itemView.getResources().getColor(RaceTime.getCurrentColor(raceTime.getSwim())));
        }
    }
}
