package com.example.uniapp.front.view.recyclerview;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uniapp.R;
import com.example.uniapp.front.model.data.TrainingBlock;
import com.example.uniapp.front.model.market.MarketTimes;
import com.example.uniapp.front.controller.textwatcher.TextWatcherChrono;

import java.util.ArrayList;

public class RvTrainingUpdateTimesAdapter extends RecyclerView.Adapter<RvTrainingUpdateTimesAdapter.MyViewHolder> {
    private TrainingBlock trainingBlock;
    private ArrayList<Float> times;

    public RvTrainingUpdateTimesAdapter(TrainingBlock trainingBlock) {
        this.trainingBlock = trainingBlock;
        times              = new ArrayList<>();
        times.addAll(trainingBlock.getTimes());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.rv_popup_update_training_times, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.display(position);
        trainingBlock.setTimes(times);
    }

    @Override
    public int getItemCount() {
        return trainingBlock.getTimes().size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private EditText editText;

        private MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.rv_popup_update_training_times_time_text_view);
            editText = itemView.findViewById(R.id.rv_popup_update_training_times_time_edit_text);
        }

        private void display(int position) {
            textView.setText(getTime(position));
            if (trainingBlock.getTimes().get(position) != 0.0f) editText.setText(MarketTimes.fetchFloatToTime(trainingBlock.getTimes().get(position)));
            updateTime(position);
        }

        private String getTime(int position) {
            return "T E M P S  " + (position + 1) + " : ";
        }

        private void updateTime(int position) {
            editText.addTextChangedListener(new TextWatcherChrono(editText));
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) { }
                @Override
                public void afterTextChanged(Editable s) { trainingBlock.setTime(MarketTimes.fetchTimeToFloat(MarketTimes.convertTimeToFormat(editText.getText().toString())), position); }
            });
        }
    }
}
