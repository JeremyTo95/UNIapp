package com.example.uniapp.controllers.adapters.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uniapp.R;
import com.example.uniapp.models.MarketTimes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RvChronometerAdapter extends RecyclerView.Adapter<RvChronometerAdapter.MyViewHolder> {
    private Context context;
    private List<Long> allChronos;

    public RvChronometerAdapter(Context context, List<Long> allChronos) {
        this.context = context;
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
        private TextView lapChronoTV;
        private TextView diffTV;
        private long lapChrono;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            lapTV        = itemView.findViewById(R.id.laptv_chrono);
            chronoTV     = itemView.findViewById(R.id.chrono_chrono);
            lapChronoTV  = itemView.findViewById(R.id.lap_chrono);
            diffTV       = itemView.findViewById(R.id.diff_chrono);
        }

        public void display(int position) {
            if (position == allChronos.size() - 1) lapChrono = allChronos.get(0);
            else lapChrono = (allChronos.get(allChronos.size() - position - 1) - allChronos.get(allChronos.size() - position - 2));

            lapTV.setText("L A P  " + (allChronos.size() - position));
            chronoTV.setText(MarketTimes.convertLongMilliToTime(allChronos.get(allChronos.size() - position - 1)));
            lapChronoTV.setText(MarketTimes.convertLongMilliToTime(lapChrono));
            diffTV.setText(getDiffLaps(position));
            if (diffTV.getText().toString().charAt(1) == '-') diffTV.setTextColor(context.getResources().getColor(R.color.greenDeep));
            else                                              diffTV.setTextColor(context.getResources().getColor(R.color.redDeep));
        }

        private String getDiffLaps(int position) {
            String result = "";
            long diff;
            long diff1 = 0;
            long diff2 = 0;

            if (position == allChronos.size() - 1) diff1 = 0;
            else diff1 = (allChronos.get(allChronos.size() - position - 1) - allChronos.get(allChronos.size() - position - 2));

            if (position == allChronos.size() - 1) diff2 = diff1;
            else if (position == allChronos.size() - 2) diff2 = allChronos.get(allChronos.size() - 1) - 0 - lapChrono ;
            else diff2 = (allChronos.get(allChronos.size() - position - 2) - allChronos.get(allChronos.size() - position - 3));

            diff = diff2 - diff1;

            double time = (double) diff / 100;

            if (time == 0)     result = "(-0.00)";
            else if (time > 0) result = "(-" + time + ")";
            else               result = "(+" + time*(-1) + ")";

            return result;
        }
    }
}
