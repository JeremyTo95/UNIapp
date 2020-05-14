package com.example.uniapp.front.view.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.uniapp.R;
import com.example.uniapp.front.controller.controller_fragment.SettingsController;
import com.example.uniapp.front.controller.textwatcher.TextWatcherDate;
import com.example.uniapp.front.controller.textwatcher.TextWatcherHeight;
import com.example.uniapp.front.controller.textwatcher.TextWatcherWeight;


public class SettingsFragment extends Fragment implements View.OnClickListener, TextWatcher {
    private SettingsController controller;

    private View     layoutInflater;

    private Spinner  genderSpinner;
    private TextView firstnameEditText;
    private TextView lastnameEditText;
    private EditText weightEditText;
    private EditText heightEditText;
    private EditText birthEditText;
    private EditText clubEditText;
    private EditText cityEditText;
    private Spinner  speSpinner;
    private Button   updateUserBtn;

    public SettingsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutInflater     = inflater.inflate(R.layout.fragment_settings, container, false);
        controller = new SettingsController(this);

        setupUIElements();
        controller.onStart();

        return layoutInflater;
    }

    private void setupUIElements() {
        genderSpinner      = layoutInflater.findViewById(R.id.fragment_settings_gender);
        firstnameEditText  = layoutInflater.findViewById(R.id.fragment_settings_firstname);
        lastnameEditText   = layoutInflater.findViewById(R.id.fragment_settings_lastname);
        weightEditText     = layoutInflater.findViewById(R.id.fragment_settings_weight);
        heightEditText     = layoutInflater.findViewById(R.id.fragment_settings_height);
        birthEditText      = layoutInflater.findViewById(R.id.fragment_settings_birth);
        clubEditText       = layoutInflater.findViewById(R.id.fragment_settings_club);
        cityEditText       = layoutInflater.findViewById(R.id.fragment_settings_city);
        speSpinner         = layoutInflater.findViewById(R.id.fragment_settings_spe);
        updateUserBtn      = layoutInflater.findViewById(R.id.fragment_settings_update);
        Button importRaces = layoutInflater.findViewById(R.id.fragment_settings_races);
        Button saveDataBtn = layoutInflater.findViewById(R.id.fragment_settings_save_data);

        firstnameEditText.addTextChangedListener(this);
        lastnameEditText.addTextChangedListener(this);
        weightEditText.addTextChangedListener(this);
        heightEditText.addTextChangedListener(this);
        birthEditText.addTextChangedListener(this);
        clubEditText.addTextChangedListener(this);
        cityEditText.addTextChangedListener(this);

        updateUserBtn.setOnClickListener(this);
        importRaces.setOnClickListener(this);
        saveDataBtn.setOnClickListener(this);

        birthEditText.addTextChangedListener(new  TextWatcherDate(birthEditText));
        weightEditText.addTextChangedListener(new TextWatcherWeight(weightEditText));
        heightEditText.addTextChangedListener(new TextWatcherHeight(heightEditText));

        ArrayAdapter<CharSequence> genderDropdownAdapter = controller.getGenderAdapter();
        genderDropdownAdapter.setDropDownViewResource(R.layout.dropdown_all_items);
        genderSpinner.setAdapter(genderDropdownAdapter);

        ArrayAdapter<CharSequence> speDropdownAdapter = controller.getSpeAdapter();
        speDropdownAdapter.setDropDownViewResource(R.layout.dropdown_all_items);
        speSpinner.setAdapter(speDropdownAdapter);
    }

    @Override
    public void onClick(View v) {
         if (v.getTag().equals("updateBtn")) {
             controller.setupUpdateUser();
             controller.hideKeybaord(v);
         }
        else if (v.getTag().equals("importDataRaceBtn")) controller.importDataRaces();
        else if (v.getTag().equals("themeManagement"))   controller.manageTheme();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { }
    @Override
    public void afterTextChanged(Editable s) {
        controller.enableUpdateUserBtn();
    }

    public Spinner getGenderSpinner() { return genderSpinner; }
    public TextView getFirstnameEditText() { return firstnameEditText; }
    public TextView getLastnameEditText() { return lastnameEditText; }
    public EditText getWeightEditText() { return weightEditText; }
    public EditText getHeightEditText() { return heightEditText; }
    public EditText getBirthEditText() { return birthEditText; }
    public EditText getClubEditText() { return clubEditText; }
    public EditText getCityEditText() { return cityEditText; }
    public Spinner getSpeSpinner() { return speSpinner; }
    public Button getUpdateUserBtn() { return updateUserBtn; }
}
