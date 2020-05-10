package com.example.uniapp.front.view.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uniapp.R;
import com.example.uniapp.front.model.market.MarketTimes;

import java.util.List;

public class RvChronometerAdapter extends RecyclerView.Adapter<RvChronometerAdapter.MyViewHolder> {
    private Context context;
    private List<Long> chrono;
    private List<Long> lap;
    private List<Double> diff;

    public RvChronometerAdapter(Context context, List<Long> chrono, List<Long> lap, List<Double> diff) {
        this.context = context;
        this.chrono = chrono;
        this.lap = lap;
        this.diff = diff;
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
        return chrono.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView lapTV;
        private TextView chronoTV;
        private TextView lapChronoTV;
        private TextView diffTV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            lapTV        = itemView.findViewById(R.id.laptv_chrono);
            chronoTV     = itemView.findViewById(R.id.chrono_chrono);
            lapChronoTV  = itemView.findViewById(R.id.lap_chrono);
            diffTV       = itemView.findViewById(R.id.diff_chrono);
        }

        public void display(int position) {
            lapTV.setText("L A P  " + (chrono.size() - position));
            chronoTV.setText(MarketTimes.convertLongMilliToTime(chrono.get(chrono.size() - position - 1)));
            lapChronoTV.setText(MarketTimes.convertLongMilliToTime(lap.get(lap.size() - position - 1)));
            printDiff(diff.get(diff.size() - position - 1));
        }

        private void printDiff(double diffChrono) {
            String diffStr = MarketTimes.getDiffTime(diffChrono);
            diffTV.setText(diffStr);

            if (diffChrono > 0) diffTV.setTextColor(context.getResources().getColor(R.color.redDeep));
            else diffTV.setTextColor(context.getResources().getColor(R.color.greenDeep));

        }
    }
}
