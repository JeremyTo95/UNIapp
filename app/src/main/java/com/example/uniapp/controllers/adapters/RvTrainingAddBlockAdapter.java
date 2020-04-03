package com.example.uniapp.controllers.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uniapp.R;

public class RvTrainingAddBlockAdapter extends RecyclerView.Adapter<RvTrainingAddBlockAdapter.MyViewHolder> {
    private int nbBlock;

    public RvTrainingAddBlockAdapter(int nbBlock) { this.nbBlock = nbBlock; }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.popup_add_training_time, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.display();
    }

    @Override
    public int getItemCount() { return 0; }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private EditText nbSetsEditText;
        private EditText distanceEditText;
        private Spinner  swimSpinner;
        private Spinner  zoneSpinner;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nbSetsEditText   = (EditText) itemView.findViewById(R.id.rv_popup_add_training_sets_edit_text);
            distanceEditText = (EditText) itemView.findViewById(R.id.rv_popup_add_training_distance_edit_text);
            swimSpinner      = (Spinner) itemView.findViewById(R.id.rv_popup_add_training_swim_spinner);
            zoneSpinner      = (Spinner) itemView.findViewById(R.id.rv_popup_add_training_zone_spinner);
        }

        public void display() {

        }
    }
}
