package com.example.uniapp.controllers.adapters.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uniapp.R;
import com.example.uniapp.models.MarketRaces;
import com.example.uniapp.models.MarketTimes;

import java.util.List;
import java.util.zip.Inflater;

public class RvChronometerAdapter extends RecyclerView.Adapter<RvChronometerAdapter.MyViewHolder> {
    private List<Long> allChronos;

    public RvChronometerAdapter(List<Long> allChronos) {
        this.allChronos = allChronos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.rv_chronometer_chrono, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.display(position);
    }

    @Override
    public int getItemCount() {
        return allChronos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView lapTV;
        private TextView chronoTV;
        private TextView diffTV;
        private double diffFloat;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            lapTV    = itemView.findViewById(R.id.lap_chrono);
            chronoTV = itemView.findViewById(R.id.chrono_chrono);
            diffTV   = itemView.findViewById(R.id.diff_chrono);
        }

        public void display(int position) {
            System.out.print("size : " + allChronos.size() + " | position : " + position);
            if (position == allChronos.size() - 1) {
                diffFloat = 0.0f;
            } else {
                diffFloat = (double) (allChronos.get(position) - allChronos.get(position + 1))/100;
            }
            System.out.println(" | diff : " + diffFloat);
            lapTV.setText("L A P  " + (allChronos.size() - position));
            chronoTV.setText(MarketTimes.convertLongMilliToTime(allChronos.get(allChronos.size() - position - 1)));
            diffTV.setText("(" + diffFloat + ")");
            System.out.println("END");
        }
    }
}
