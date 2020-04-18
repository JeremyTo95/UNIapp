package com.example.uniapp.controllers.adapters.recyclerview;

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
import com.example.uniapp.models.MarketTimes;
import com.example.uniapp.models.database.dao.trainingblock.TrainingBlock;

import java.util.ArrayList;
import java.util.Arrays;

public class RvTrainingUpdateTimesAdapter extends RecyclerView.Adapter<RvTrainingUpdateTimesAdapter.MyViewHolder> {
    private Context context;
    private TrainingBlock trainingBlock;
    private ArrayList<Float> times;

    public RvTrainingUpdateTimesAdapter(Context context, TrainingBlock trainingBlock) {
        this.context = context;
        this.trainingBlock = trainingBlock;
        times = new ArrayList<Float>();
        for (int i = 0; i < trainingBlock.getTimes().size(); i++)
            times.add(trainingBlock.getTimes().get(i));
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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.rv_popup_update_training_times_time_text_view);
            editText = (EditText) itemView.findViewById(R.id.rv_popup_update_training_times_time_edit_text);
        }

        public void display(int position) {
            textView.setText("T E M P S  " + (position + 1) + " : ");
            if (trainingBlock.getTimes().get(position) != 0.0f) editText.setText(MarketTimes.fetchFloatToTime(trainingBlock.getTimes().get(position)));
            updateTime(position);
            System.out.println(Arrays.toString(trainingBlock.getTimes().toArray()));
        }

        private void updateTime(int position) {
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                @Override
                public void afterTextChanged(Editable s) {
                    // times.set(position, MarketTimes.fetchTimeToFloat(MarketTimes.convertTimeToFormat(editText.getText().toString())));
                    trainingBlock.setTime(MarketTimes.fetchTimeToFloat(MarketTimes.convertTimeToFormat(editText.getText().toString())), position);
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    editText.removeTextChangedListener(this);
                    int timeInt = Integer.parseInt(editText.getText().toString().replaceAll("[:.]", "")); // permet de retirer les premiers zéros du String
                    String text = String.valueOf(timeInt);
                    int textSize = text.length();

                    if (textSize >= 5) {
                        text = new StringBuilder(text).insert(textSize - 2, ".").insert(textSize - 4, ":").toString();
                        editText.setText(text);
                        editText.setSelection(text.length());
                    } else if (textSize > 2) {
                        text = new StringBuilder(text).insert(textSize - 2, ".").toString();
                        editText.setText(text);
                        editText.setSelection(text.length());
                    } else {
                        for (int i = 0; i < 3 - textSize; i++) text = new StringBuilder(text).insert(i, "0").toString();
                        text = new StringBuilder(text).insert(text.length() - 2, ".").toString();
                        editText.setText(text);
                        editText.setSelection(text.length());
                    }
                    System.out.println(Arrays.toString(trainingBlock.getTimes().toArray()));
                    editText.addTextChangedListener(this);
                }
            });
        }
    }
}