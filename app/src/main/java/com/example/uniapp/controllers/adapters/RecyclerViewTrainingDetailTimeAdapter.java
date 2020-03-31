package com.example.uniapp.controllers.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uniapp.R;

import java.util.Arrays;
import java.util.List;

public class RecyclerViewTrainingDetailTimeAdapter extends RecyclerView.Adapter<RecyclerViewTrainingDetailTimeAdapter.MyViewHolder> {
    private List<String> allTimes;

    public RecyclerViewTrainingDetailTimeAdapter(List<String> allTimes) { this.allTimes = allTimes; }

    @NonNull
    @Override
    public RecyclerViewTrainingDetailTimeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.rv_training_detail_time_items, parent, false);

        System.out.println(Arrays.toString(allTimes.toArray()));
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewTrainingDetailTimeAdapter.MyViewHolder holder, int position) {
        System.out.println(position);
        holder.display(position);
    }

    @Override
    public int getItemCount() { return allTimes.size(); }

    class MyViewHolder extends  RecyclerView.ViewHolder {
        private TextView desc;
        private EditText time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            desc = (TextView) itemView.findViewById(R.id.rv_training_detail_time_text_view);
            time = (EditText) itemView.findViewById(R.id.rv_training_detail_time_edit_text);
        }

        public void display(int index) {
            desc.setText("Temps " + (index + 1) + " : ");
            time.setText(allTimes.get(index));
        }
    }

}
