package com.example.uniapp.controllers.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uniapp.R;
import com.example.uniapp.controllers.activities.MainActivity;
import com.example.uniapp.models.markets.MarketSwim;
import com.example.uniapp.models.database.SharedPrefManager;
import com.example.uniapp.models.database.dao.race.Race;
import com.example.uniapp.models.database.dao.user.User;
import com.example.uniapp.models.textwatcher.TextWatcherDate;
import com.example.uniapp.models.textwatcher.TextWatcherHeight;
import com.example.uniapp.models.textwatcher.TextWatcherWeight;
import com.example.uniapp.views.AboutScreen;


public class SettingsFragment extends Fragment implements View.OnClickListener, TextWatcher {
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

    private String gender;
    private String firstname;
    private String lastname;
    private int    weight;
    private int    height;
    private String birth;
    private String club;
    private String city;
    private String spe;

    public SettingsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutInflater = inflater.inflate(R.layout.fragment_settings, container, false);

        setupUIElements();
        updateUIElements();
        disableUpdateUserBtn();

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

        birthEditText.addTextChangedListener(new TextWatcherDate(birthEditText));
        weightEditText.addTextChangedListener(new TextWatcherWeight(weightEditText));
        heightEditText.addTextChangedListener(new TextWatcherHeight(heightEditText));

        gender = "Homme";
        spe    = "butterfly";

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

        if (MainActivity.appDataBase.userDAO().getNb() != 0) { setupUserInfo(); }
    }

    private void setupUserInfo() {
        genderSpinner.setSelection((MainActivity.appDataBase.userDAO().getUser().getGender() == "homme") ? 1 : 0);
        firstnameEditText.setText(MainActivity.appDataBase.userDAO().getUser().getFirstname());
        lastnameEditText.setText(MainActivity.appDataBase.userDAO().getUser().getLastname());
        weightEditText.setText(String.valueOf(MainActivity.appDataBase.userDAO().getUser().getWeight()) + "kg");
        heightEditText.setText(String.valueOf(MainActivity.appDataBase.userDAO().getUser().getHeight()) + "cm");
        birthEditText.setText(MainActivity.appDataBase.userDAO().getUser().getBirthday());
        clubEditText.setText(MainActivity.appDataBase.userDAO().getUser().getClub());
        cityEditText.setText(MainActivity.appDataBase.userDAO().getUser().getCityTraining());

        if (MainActivity.appDataBase.userDAO().getUser().getSpe().equals("butterfly")) speSpinner.setSelection(0);
        if (MainActivity.appDataBase.userDAO().getUser().getSpe().equals("backstroke")) speSpinner.setSelection(1);
        if (MainActivity.appDataBase.userDAO().getUser().getSpe().equals("breaststroke")) speSpinner.setSelection(2);
        if (MainActivity.appDataBase.userDAO().getUser().getSpe().equals("freestyle")) speSpinner.setSelection(3);
        if (MainActivity.appDataBase.userDAO().getUser().getSpe().equals("IM")) speSpinner.setSelection(4);

        gender    = MainActivity.appDataBase.userDAO().getUser().getGender();
        firstname = MainActivity.appDataBase.userDAO().getUser().getFirstname();
        lastname  = MainActivity.appDataBase.userDAO().getUser().getLastname();
        weight    = MainActivity.appDataBase.userDAO().getUser().getWeight();
        height    = MainActivity.appDataBase.userDAO().getUser().getHeight();
        birth     = MainActivity.appDataBase.userDAO().getUser().getBirthday();
        club      = MainActivity.appDataBase.userDAO().getUser().getClub();
        city      = MainActivity.appDataBase.userDAO().getUser().getCityTraining();
        spe       = MainActivity.appDataBase.userDAO().getUser().getSpe();
    }

    private void updateUIElements() {
        updateGender();
        updateSpeciality();
    }

    @Override
    public void onClick(View v) {
         if (v.getTag().equals("updateBtn")) {
             setupUpdateUser();
             hideKeybaord(v);
         }
        else if (v.getTag().equals("importDataRaceBtn")) importDataRaces();
        else if (v.getTag().equals("themeManagement"))          manageTheme();
    }

    private void setupUpdateUser() {
        if (checkInputUpdateUser()) {
            User user = new User(gender, firstname, lastname, birth, height, weight, club, spe, city, MainActivity.appDataBase.userDAO().getUser().getMykey());
            MainActivity.appDataBase.userDAO().deleteAll();
            MainActivity.appDataBase.userDAO().insert(user);
            disableUpdateUserBtn();
            Toast.makeText(getContext(), "Utilisateur mis à jour", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(getContext(), "ATTENTION : Utilisateur non mis à jour", Toast.LENGTH_SHORT).show();

    }

    private void disableUpdateUserBtn() {
        updateUserBtn.setEnabled(false);
        updateUserBtn.setBackground(getResources().getDrawable(R.color.transparent));
        updateUserBtn.setTextColor(AboutScreen.getColorByThemeAttr(getContext(), R.attr.textColor, R.color.textColorDark));
    }

    private void enableUpdateUserBtn() {
        updateUserBtn.setEnabled(true);
        updateUserBtn.setBackground(getResources().getDrawable(R.drawable.sh_button));
        updateUserBtn.setTextColor(getResources().getColor(R.color.textButtonColor));
    }

    private void importDataRaces() {
        MainActivity.appDataBase.raceDAO().deleteAll();
        Race.startAsyncTaskLoadingRace(this.getActivity(), MainActivity.appDataBase.userDAO().getUser());
        Toast.makeText(getContext(), "Temps réinitialisé...", Toast.LENGTH_SHORT).show();
    }

    private void manageTheme() {
             if (MainActivity.sharedPrefManager.loadThemeMode() == 0) Toast.makeText(getContext(),  "Dark Mode Activé", Toast.LENGTH_SHORT).show();
        else if (MainActivity.sharedPrefManager.loadThemeMode() == 1) Toast.makeText(getContext(),  "Light Mode Activé", Toast.LENGTH_SHORT).show();
        else Toast.makeText(getContext(),  "Mode Automatique Activé", Toast.LENGTH_SHORT).show();
        SharedPrefManager.savedThemeMode((SharedPrefManager.loadThemeMode()+1)%3);
        restartApp();
    }

    private void restartApp() {
        Intent i = new Intent(getContext(), MainActivity.class);
        startActivity(i);
        getActivity().finish();
    }

    private void updateSpeciality() {
        speSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spe = MarketSwim.convertSwimFromFrenchToEnglish(parent.getSelectedItem().toString().toLowerCase());
            }
        });
    }

    private void updateGender() {
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getSelectedItem().toString().toLowerCase();
            }
        });
    }

    private boolean checkInputUpdateUser() {
        loadInputs();
        if (checkWeight() && checkHeight() && checkBirth() && checkClub() && checkCity()) {
            return true;
        } else {
            if (!checkWeight())    weightEditText.setHintTextColor(getResources().getColor(R.color.redDeep));
            if (!checkHeight())    heightEditText.setHintTextColor(getResources().getColor(R.color.redDeep));
            if (!checkBirth())     birthEditText.setHintTextColor(getResources().getColor(R.color.redDeep));
            if (!checkClub())      firstnameEditText.setHintTextColor(getResources().getColor(R.color.redDeep));
            if (!checkClub())      clubEditText.setHintTextColor(getResources().getColor(R.color.redDeep));
            if (!checkCity())      cityEditText.setHintTextColor(getResources().getColor(R.color.redDeep));
            return false;
        }
    }

    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }

    private void loadInputs() {
        String tmp;
        firstname = firstnameEditText.getText().toString();
        lastname  = lastnameEditText.getText().toString();
        birth     = birthEditText.getText().toString();
        tmp       = heightEditText.getText().toString().replaceAll("[a-zA-Z]", "").toString();
        if (tmp.length() != 0) height = Integer.parseInt(tmp);
        else height = 0;
        tmp       = weightEditText.getText().toString().replaceAll("[a-zA-Z]", "").toString();
        if (tmp.length() != 0) weight = Integer.parseInt(tmp);
        else weight = 0;
        club      = clubEditText.getText().toString();
        city      = cityEditText.getText().toString();
    }

    private boolean checkWeight()    { return (weight             > 0); }
    private boolean checkHeight()    { return (height             > 0); }
    private boolean checkBirth()     { return (birth.length()     > 0); }
    private boolean checkClub()      { return (club.length()      > 0); }
    private boolean checkCity()      { return (city.length()      > 0); }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { }
    @Override
    public void afterTextChanged(Editable s) { enableUpdateUserBtn(); }
}
