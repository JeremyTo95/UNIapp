package com.example.uniapp.front.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

import com.example.uniapp.front.controller.global.AboutScreen;
import com.example.uniapp.front.controller.fragmentcontroller.SettingsController;
import com.example.uniapp.R;
import com.example.uniapp.front.controller.textwatcher.TextWatcherDate;
import com.example.uniapp.front.controller.textwatcher.TextWatcherHeight;
import com.example.uniapp.front.controller.textwatcher.TextWatcherWeight;


public class SettingsFragment extends Fragment implements View.OnClickListener, TextWatcher {
    private SettingsController settingsController;

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
    private Button   importRaces;
    private Button   saveDataBtn;

    public SettingsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutInflater     = inflater.inflate(R.layout.fragment_settings, container, false);
        settingsController = new SettingsController(this);

        setupUIElements();
        settingsController.onStart();

        return layoutInflater;
    }

    private void setupUIElements() {
        genderSpinner     = (Spinner)  layoutInflater.findViewById(R.id.fragment_settings_gender);
        firstnameEditText = (TextView) layoutInflater.findViewById(R.id.fragment_settings_firstname);
        lastnameEditText  = (TextView) layoutInflater.findViewById(R.id.fragment_settings_lastname);
        weightEditText    = (EditText) layoutInflater.findViewById(R.id.fragment_settings_weight);
        heightEditText    = (EditText) layoutInflater.findViewById(R.id.fragment_settings_height);
        birthEditText     = (EditText) layoutInflater.findViewById(R.id.fragment_settings_birth);
        clubEditText      = (EditText) layoutInflater.findViewById(R.id.fragment_settings_club);
        cityEditText      = (EditText) layoutInflater.findViewById(R.id.fragment_settings_city);
        speSpinner        = (Spinner)  layoutInflater.findViewById(R.id.fragment_settings_spe);
        updateUserBtn     = (Button)   layoutInflater.findViewById(R.id.fragment_settings_update);
        importRaces       = (Button)   layoutInflater.findViewById(R.id.fragment_settings_races);
        saveDataBtn       = (Button)   layoutInflater.findViewById(R.id.fragment_settings_save_data);

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

        ArrayAdapter<CharSequence> genderDropdownAdapter;
        if (AboutScreen.isNightMode(getActivity())) genderDropdownAdapter = ArrayAdapter.createFromResource(getContext(), R.array.genders, R.layout.dropdown_item_dark);
        else genderDropdownAdapter = ArrayAdapter.createFromResource(getContext(), R.array.genders, R.layout.dropdown_item_light);
        genderDropdownAdapter.setDropDownViewResource(R.layout.dropdown_all_items);
        genderSpinner.setAdapter(genderDropdownAdapter);

        ArrayAdapter<CharSequence> speDropdownAdapter;
        if (AboutScreen.isNightMode(getActivity())) speDropdownAdapter = ArrayAdapter.createFromResource(getContext(), R.array.swims, R.layout.dropdown_item_dark);
        else speDropdownAdapter = ArrayAdapter.createFromResource(getContext(), R.array.swims, R.layout.dropdown_item_light);
        speDropdownAdapter.setDropDownViewResource(R.layout.dropdown_all_items);
        speSpinner.setAdapter(speDropdownAdapter);
    }

    @Override
    public void onClick(View v) {
         if (v.getTag().equals("updateBtn")) {
             settingsController.setupUpdateUser();
             AboutScreen.hideKeybaord(getActivity(), v);
         }
        else if (v.getTag().equals("importDataRaceBtn")) settingsController.importDataRaces();
        else if (v.getTag().equals("themeManagement"))   settingsController.manageTheme();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { }
    @Override
    public void afterTextChanged(Editable s) {
        settingsController.enableUpdateUserBtn();
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
