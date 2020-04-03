package com.example.uniapp.controllers.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uniapp.R;
import com.example.uniapp.models.MarketTimes;
import com.example.uniapp.models.Race;
import com.example.uniapp.models.TrainingBlock;

import java.util.Arrays;
import java.util.List;

public class RvTrainingDetailTimeAdapter extends RecyclerView.Adapter<RvTrainingDetailTimeAdapter.MyViewHolder> {
    private Context      context;
    private TrainingBlock trainingBlock;
    private String       timeRef;

    public RvTrainingDetailTimeAdapter(Context context, TrainingBlock trainingBlock, String timeRef) { this.context = context; this.trainingBlock = trainingBlock; this.timeRef = timeRef; }

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
    public int getItemCount() { return trainingBlock.getNbSet(); }

    class MyViewHolder extends  RecyclerView.ViewHolder {
        private TextView desc;
        private EditText time;
        private TextView diff;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            desc = (TextView) itemView.findViewById(R.id.rv_training_detail_time_text_view);
            time = (EditText) itemView.findViewById(R.id.rv_training_detail_time_edit_text);
            diff = (TextView) itemView.findViewById(R.id.rv_training_detail_time_diff_text_view);
        }

        public void display(int index) {
            desc.setText("t" + (index + 1) + " : ");
            if (trainingBlock.getTimes() != null) {
                if (MarketTimes.convertTimeToInt(trainingBlock.getTimes().get(index)) != 0) {
                    time.setText(trainingBlock.getTimes().get(index));
                    updateDiff(diff, timeRef, index);
                }
            }
            updateInputTimeFormatEditText();
            System.out.println(Arrays.toString(trainingBlock.getTimes().toArray()));
            //updateTimeAt(index);
        }

        private void updateInputTimeFormatEditText() {
            time.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                @Override
                public void afterTextChanged(Editable s) { trainingBlock.setTimes(MarketTimes.convertTimeToFormat(time.getText().toString()), getAdapterPosition()); }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    time.removeTextChangedListener(this);

                    int timeInt, textSize;
                    String text;
                    if (time.getText().toString().length() != 0) {
                        timeInt  = Integer.parseInt(time.getText().toString().replaceAll("[:.]", "")); // permet de retirer les premiers zéros du String
                        text     = String.valueOf(timeInt);
                        textSize = text.length();
                        if (textSize >= 5) {
                            text = new StringBuilder(text).insert(textSize - 2, ".").insert(textSize - 4, ":").toString();
                            time.setText(text);
                            time.setSelection(text.length());
                        } else if (textSize > 2) {
                            text = new StringBuilder(text).insert(textSize - 2, ".").toString();
                            time.setText(text);
                            time.setSelection(text.length());
                        } else {
                            for (int i = 0; i < 3 - textSize; i++) {
                                text = new StringBuilder(text).insert(i, "0").toString();
                            }
                            text = new StringBuilder(text).insert(text.length() - 2, ".").toString();
                            time.setText(text);
                            time.setSelection(text.length());
                        }
                    }
                    time.addTextChangedListener(this);
                }
            });
        }

        private void updateDiff(TextView diff, String timeRef, int index) {
            String diffStr = MarketTimes.compareTwoTimes(timeRef, trainingBlock.getTimes().get(index), timeRef);
            diff.setText(diffStr);
            diff.setTextColor(itemView.getResources().getColor((diffStr.charAt(1) == '+') ? R.color.redLight : R.color.greenDeep));
        }
    }

}
