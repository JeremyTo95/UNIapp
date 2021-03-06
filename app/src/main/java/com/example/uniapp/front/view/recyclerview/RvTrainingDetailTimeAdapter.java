package com.example.uniapp.front.view.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uniapp.R;
import com.example.uniapp.front.model.data.TrainingBlock;
import com.example.uniapp.front.model.market.MarketTimes;

public class RvTrainingDetailTimeAdapter extends RecyclerView.Adapter<RvTrainingDetailTimeAdapter.MyViewHolder> {
    private Context      context;
    private TrainingBlock trainingBlock;
    private float         timeRef;

    RvTrainingDetailTimeAdapter(Context context, TrainingBlock trainingBlock, float timeRef) {
        this.context = context;
        this.trainingBlock = trainingBlock;
        this.timeRef = timeRef;
    }

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
    public int getItemCount() { return trainingBlock.getTimes().size(); }

    class MyViewHolder extends  RecyclerView.ViewHolder {
        private TextView desc;
        private TextView time;
        private TextView diff;

        private MyViewHolder(@NonNull View itemView) {
            super(itemView);
            desc = itemView.findViewById(R.id.rv_training_detail_time_text_view);
            time = itemView.findViewById(R.id.rv_training_detail_time_content_text_view);
            diff = itemView.findViewById(R.id.rv_training_detail_time_diff_text_view);
        }

        void display(int index) {
            desc.setText(getTimeIndexText(index));
            if (trainingBlock.getTimes() != null) {
                if (trainingBlock.getTimes().get(index) != 0.0f) {
                    time.setText(MarketTimes.fetchFloatToTime(trainingBlock.getTimes().get(index)));
                    updateDiff(diff, timeRef, index);
                }
            }
        }

        private String getTimeIndexText(int index) {
            return "t" + (index + 1) + " : ";
        }

        private void updateDiff(TextView diff, float timeRef, int index) {
            String diffStr = MarketTimes.compareTwoTimes(timeRef, trainingBlock.getTimes().get(index), timeRef);
            diff.setText(diffStr);
            diff.setTextColor(itemView.getResources().getColor((diffStr.charAt(1) == '+') ? R.color.redLight : R.color.greenDeep));
        }
    }

}
