package com.example.uniapp.front.view.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.uniapp.R;
import com.example.uniapp.front.controller.controller_fragment.ConverterController;
import com.example.uniapp.front.controller.textwatcher.TextWatcherChrono;

public class ConverterFragment extends Fragment implements View.OnClickListener {
    private View layoutInflater;
    private ConverterController controller;

    private Spinner zoneSelected;
    private EditText inputTimeET;
    private TextView outputTimeTV;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutInflater = inflater.inflate(R.layout.fragment_converter, container, false);
        controller = new ConverterController(this);
        controller.onStart();

        return layoutInflater;
    }

    public void setupUIElements() {
        Button converterBtn;
        zoneSelected = layoutInflater.findViewById(R.id.activity_converter_zone_selected);
        inputTimeET  = layoutInflater.findViewById(R.id.activity_converter_input_time);
        outputTimeTV = layoutInflater.findViewById(R.id.activity_converter_output_time);
        converterBtn = layoutInflater.findViewById(R.id.activity_converter_convert_btn);
        converterBtn.setOnClickListener(this);
        inputTimeET.setOnClickListener(this);
        updateInputTime();

        ArrayAdapter<CharSequence> zoneSelectedAdapter = controller.getSelectedAdapter();
        zoneSelectedAdapter.setDropDownViewResource(R.layout.dropdown_all_big_items);
        zoneSelected.setAdapter(zoneSelectedAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getTag().equals("inputTime")) inputTimeET.clearComposingText();
        if (v.getTag().equals("converterButton")) {
            controller.convertTime();
            controller.hideKeybaord(v);
        }
    }

    private void updateInputTime() {
        inputTimeET.addTextChangedListener(new TextWatcherChrono(inputTimeET));
        inputTimeET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { controller.setInputTimeStr(inputTimeET.getText().toString()); }
        });
    }

    public void updateZone() {
        zoneSelected.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                controller.setZoneSelectedStr(parent.getItemAtPosition(position).toString());
            }
        });
    }


    public EditText getInputTimeET() { return inputTimeET; }
    public TextView getOutputTimeTV() { return outputTimeTV; }
    public void setOutputText(String text) {
        outputTimeTV.setText(text);
    }
}
