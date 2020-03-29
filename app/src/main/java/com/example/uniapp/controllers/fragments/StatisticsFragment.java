package com.example.uniapp.controllers.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.uniapp.R;


public class StatisticsFragment extends Fragment {
    private View layoutInflater;
    private int    sizePool;
    private String swim;

    private TextView bigtitle;
    private Spinner  dropdownPool;
    private Spinner  dropdownSwim;
    private TextView speedTimeTitle;

    public StatisticsFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutInflater = inflater.inflate(R.layout.fragment_statistic, container, false);
        sizePool = 25;
        swim     = "Papillon";

        bigtitle       = (TextView) layoutInflater.findViewById(R.id.fragment_statistiques_statistique_title);
        speedTimeTitle = (TextView) layoutInflater.findViewById(R.id.fragment_statistic_speed_time_title);
        //configureAndShowSizePoolDropdown();
        //configureAndShowSwimDropdown();


        return layoutInflater;
    }

    /*private void configureAndShowSizePoolDropdown() {
        dropdownPool = layoutInflater.findViewById(R.id.fragment_statistic_pool_dropdown);
        dropdownPool.setPopupBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(layoutInflater.getContext(), R.array.sizePool, R.layout.dropdown_competition_distance_item);
        adapter.setDropDownViewResource(R.layout.dropdown_competition_distance_item);
        dropdownPool.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        dropdownPool.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String newSizePool = parent.getItemAtPosition(position).toString();
                newSizePool = newSizePool.replaceAll("[a-zA-Z ]", "");
                sizePool = Integer.parseInt(newSizePool);
                speedTimeTitle.setText("T E M P S  " + sizePool/10 + " " + sizePool%10 + " m");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void configureAndShowSwimDropdown() {
        dropdownSwim = layoutInflater.findViewById(R.id.fragment_statistic_swim_dropdown);
        dropdownSwim.setPopupBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(layoutInflater.getContext(), R.array.swims, R.layout.dropdown_competition_distance_item);
        adapter.setDropDownViewResource(R.layout.dropdown_competition_distance_item);
        dropdownSwim.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        dropdownSwim.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                swim = parent.getItemAtPosition(position).toString();
                updateColors();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void updateColors() {
        speedTimeTitle.setTextColor(getResources().getColor(RaceTime.getCurrentColor(RaceTime.convertSwimFromFrenchToEnglish(swim))));
        bigtitle.setTextColor(getResources().getColor(RaceTime.getCurrentColor(RaceTime.convertSwimFromFrenchToEnglish(swim))));
    }*/
}
