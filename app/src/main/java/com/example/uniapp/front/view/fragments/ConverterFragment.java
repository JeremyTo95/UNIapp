package com.example.uniapp.front.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.uniapp.R;
import com.example.uniapp.front.controller.global.AboutScreen;
import com.example.uniapp.front.controller.textwatcher.TextWatcherChrono;
import com.example.uniapp.front.model.market.MarketTimes;
import com.example.uniapp.front.view.actvities.MainActivity;

public class ConverterFragment extends Fragment implements View.OnClickListener {
    private View layoutInflater;

    private LinearLayout linearLayout;
    private Spinner zoneSelected;
    private EditText inputTimeET;
    private Button converterBtn;
    private TextView outputTimeTV;

    private String inputTimeStr;
    private String outputTimeStr;
    private String zoneSelectedStr;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutInflater = inflater.inflate(R.layout.fragment_converter, container, false);

        setupUIElements();
        updateZone();

        return layoutInflater;
    }

    private void setupUIElements() {
        linearLayout  = (LinearLayout) layoutInflater.findViewById(R.id.activity_converter_main_layout);
        zoneSelected  = (Spinner)      layoutInflater.findViewById(R.id.activity_converter_zone_selected);
        inputTimeET   = (EditText)     layoutInflater.findViewById(R.id.activity_converter_input_time);
        converterBtn  = (Button)       layoutInflater.findViewById(R.id.activity_converter_convert_btn);
        outputTimeTV  = (TextView)     layoutInflater.findViewById(R.id.activity_converter_output_time);
        converterBtn.setOnClickListener(this);
        inputTimeET.setOnClickListener(this);
        updateInputTime();

        ArrayAdapter<CharSequence> zoneSelectedAdapter;
        if (AboutScreen.isNightMode(getActivity())) zoneSelectedAdapter = ArrayAdapter.createFromResource(getContext(), R.array.zones, R.layout.dropdown_big_item_dark);
        else zoneSelectedAdapter = ArrayAdapter.createFromResource(getContext(), R.array.zones, R.layout.dropdown_big_item_light);
        zoneSelectedAdapter.setDropDownViewResource(R.layout.dropdown_all_big_items);
        zoneSelected.setAdapter(zoneSelectedAdapter);

        zoneSelectedStr = "Z1";
        ((MainActivity) getActivity()).lockUI(false, null);
    }

    @Override
    public void onClick(View v) {
        if (v.getTag().equals("inputTime")) inputTimeET.clearComposingText();
        if (v.getTag().equals("converterButton")) {
            convertTime();
            hideKeybaord(v);
        }
    }





    private void updateInputTime() {
        inputTimeET.addTextChangedListener(new TextWatcherChrono(inputTimeET));
    }

    private void updateZone() {
        zoneSelected.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                zoneSelectedStr = parent.getItemAtPosition(position).toString();
            }
        });
    }

    private void convertTime() {
        if (inputTimeET.getText().toString().length() == 0) {
            inputTimeET.setHintTextColor(getResources().getColor(R.color.redDeep));
        } else {
            Toast.makeText(getActivity(), "Conversion terminé", Toast.LENGTH_SHORT).show();
            inputTimeStr = MarketTimes.convertTimeToFormat(inputTimeET.getText().toString());
            outputTimeStr = MarketTimes.convertCompetitionTimeToZoneTime(MarketTimes.fetchTimeToFloat(inputTimeStr), Integer.parseInt(zoneSelectedStr.substring(1)));
            System.out.println("input : " + inputTimeStr + " | output : " + outputTimeStr);
            outputTimeTV.setText("Temps " + zoneSelectedStr + " : " + outputTimeStr);
            outputTimeTV.setVisibility(View.VISIBLE);
        }
    }

    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }
}
