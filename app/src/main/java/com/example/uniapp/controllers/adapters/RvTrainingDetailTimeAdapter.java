package com.example.uniapp.controllers.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uniapp.R;

import java.util.List;

public class RvTrainingDetailTimeAdapter extends RecyclerView.Adapter<RvTrainingDetailTimeAdapter.MyViewHolder> {
    private List<String> allTimes;
    private Context      context;

    public RvTrainingDetailTimeAdapter(Context context, List<String> allTimes) { this.context = context; this.allTimes = allTimes; }

    @NonNull
    @Override
    public RvTrainingDetailTimeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.rv_training_detail_time_items, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RvTrainingDetailTimeAdapter.MyViewHolder holder, int position) {
        holder.display(position);
    }

    @Override
    public int getItemCount() { return allTimes.size(); }

    class MyViewHolder extends  RecyclerView.ViewHolder {
        private TextView desc;
        private TextView time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            desc = (TextView) itemView.findViewById(R.id.rv_training_detail_time_text_view);
            time = (TextView) itemView.findViewById(R.id.rv_training_detail_time_edit_text);
        }

        public void display(int index) {
            desc.setText("t" + (index + 1) + " : ");
            time.setText(allTimes.get(index));
        }
    }

}
