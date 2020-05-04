package com.example.uniapp.controllers.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.example.uniapp.models.database.dao.race.Race;
import com.example.uniapp.models.database.dao.user.User;
import com.example.uniapp.views.AboutScreen;


public class SettingsFragment extends Fragment implements View.OnClickListener {
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

        return layoutInflater;
    }

    private void setupUIElements() {
        genderSpinner      = (Spinner)  layoutInflater.findViewById(R.id.fragment_settings_gender);
        firstnameEditText  = (TextView) layoutInflater.findViewById(R.id.fragment_settings_firstname);
        lastnameEditText   = (TextView) layoutInflater.findViewById(R.id.fragment_settings_lastname);
        weightEditText     = (EditText) layoutInflater.findViewById(R.id.fragment_settings_weight);
        heightEditText     = (EditText) layoutInflater.findViewById(R.id.fragment_settings_height);
        birthEditText      = (EditText) layoutInflater.findViewById(R.id.fragment_settings_birth);
        clubEditText       = (EditText) layoutInflater.findViewById(R.id.fragment_settings_club);
        cityEditText       = (EditText) layoutInflater.findViewById(R.id.fragment_settings_city);
        speSpinner         = (Spinner)  layoutInflater.findViewById(R.id.fragment_settings_spe);
        updateUserBtn      = (Button)   layoutInflater.findViewById(R.id.fragment_settings_update);
        importRaces        = (Button)   layoutInflater.findViewById(R.id.fragment_settings_races);
        saveDataBtn        = (Button)   layoutInflater.findViewById(R.id.fragment_settings_save_data);

        gender    = "Homme";
        firstname = firstnameEditText.getText().toString();
        lastname  = lastnameEditText.getText().toString();
        weight    = 0;
        height    = 0;
        birth     = birthEditText.getText().toString();
        club      = clubEditText.getText().toString();
        city      = cityEditText.getText().toString();
        spe       = "butterfly";


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

        if (MainActivity.appDataBase.userDAO().getNb() != 0) {
            genderSpinner.setSelection((MainActivity.appDataBase.userDAO().getUser().getGender() == "homme") ? 1 : 0);
            firstnameEditText.setText(MainActivity.appDataBase.userDAO().getUser().getFirstname());
            lastnameEditText.setText(MainActivity.appDataBase.userDAO().getUser().getLastname());
            weightEditText.setText(String.valueOf(MainActivity.appDataBase.userDAO().getUser().getWeight()) + "kg");
            heightEditText.setText(String.valueOf(MainActivity.appDataBase.userDAO().getUser().getHeight()) + "cm");
            birthEditText.setText(MainActivity.appDataBase.userDAO().getUser().getBirthday());
            clubEditText.setText(MainActivity.appDataBase.userDAO().getUser().getClub());
            cityEditText.setText(MainActivity.appDataBase.userDAO().getUser().getCityTraining());
            System.out.println(MainActivity.appDataBase.userDAO().getUser().getGender());
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

        updateUserBtn.setOnClickListener(this);
        importRaces.setOnClickListener(this);
        saveDataBtn.setOnClickListener(this);
    }

    private void updateUIElements() {
        updateGender();
        updateWeight();
        updateHeight();
        updateBirth();
        updateClub();
        updateCity();
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
        Log.e("Ep", "It works but...");
        System.out.println("gender : " + gender);
        System.out.println("first  : " + firstname);
        System.out.println("last   : " + lastname);
        System.out.println("weight : " + weight);
        System.out.println("height : " + height);
        System.out.println("birth  : " + birth);
        System.out.println("club   : " + club);
        System.out.println("city   : " + city);
        System.out.println("spe    : " + spe);
        if (checkInputUpdateUser()) {
            User user = new User(gender, firstname, lastname, birth, height, weight, club, spe, city, MainActivity.appDataBase.userDAO().getUser().getMykey());
            MainActivity.appDataBase.userDAO().deleteAll();
            MainActivity.appDataBase.userDAO().insert(user);
            Toast.makeText(getContext(), "Utilisateur mis à jour", Toast.LENGTH_SHORT).show();
            updateUserBtn.setBackground(getResources().getDrawable(R.color.transparent));
            Log.e("E", "It works ! (" + MainActivity.appDataBase.userDAO().getNb() + "user)");
        } else {
            Toast.makeText(getContext(), "ATTENTION : Utilisateur non mis à jour", Toast.LENGTH_SHORT).show();
        }
    }

    private void importDataRaces() {
        MainActivity.appDataBase.raceDAO().deleteAll();
        Race.startAsyncTaskLoadingRace(this.getActivity(), MainActivity.appDataBase.userDAO().getUser());
        Toast.makeText(getContext(), "Actualisation des temps...", Toast.LENGTH_SHORT).show();
    }

    private void manageTheme() {
        if (MainActivity.sharedPrefManager.loadThemeMode() == 0)
            Toast.makeText(getContext(),  "Dark Mode Activé", Toast.LENGTH_SHORT).show();
        else if (MainActivity.sharedPrefManager.loadThemeMode() == 1)
            Toast.makeText(getContext(),  "Light Mode Activé", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getContext(),  "Automatique Mode Activé", Toast.LENGTH_SHORT).show();
        MainActivity.sharedPrefManager.savedThemeMode((MainActivity.sharedPrefManager.loadThemeMode()+1)%3);
        restartApp();
    }

    private void restartApp() {
        Intent i = new Intent(getContext(), MainActivity.class);
        startActivity(i);
        getActivity().finish();
    }

    private void updateBirth() {
        birthEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                birthEditText.setText("");
            }
        });
        birthEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) { birth = birthEditText.getText().toString(); }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = birthEditText.getText().toString();
                int textSize = text.length();

                if ((textSize == 3 && text.charAt(textSize - 1) != '/') || (textSize == 6 && text.charAt(textSize - 1) != '/')) {
                    birthEditText.setText(new StringBuilder(text).insert(textSize - 1, "/").toString());
                    birthEditText.setSelection(birthEditText.getText().length());
                }
                updateUserBtn.setBackground(getResources().getDrawable(R.drawable.sh_button));
            }
        });
    }

    private void updateWeight() {
        weightEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weightEditText.setText("");
            }
        });
        weightEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                weightEditText.removeTextChangedListener(this);
                if (weightEditText.getText().length() >= 1 && !weightEditText.getText().toString().equals("kg")) {
                    String text = weightEditText.getText().toString().replaceAll("[a-zA-Z ]", "") + "kg";
                    weightEditText.setText(text);
                    weightEditText.setSelection(text.length() - 2);
                    weight = Integer.parseInt(weightEditText.getText().toString().replaceAll("[a-zA-Z ]", ""));
                } else if (weightEditText.getText().toString().equals("kg")) {
                    weightEditText.setText("");
                }
                weightEditText.addTextChangedListener(this);
                updateUserBtn.setBackground(getResources().getDrawable(R.drawable.sh_button));
            }
        });
    }

    private void updateHeight() {
        heightEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heightEditText.setText("");
            }
        });
        heightEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                heightEditText.removeTextChangedListener(this);
                if (heightEditText.getText().length() >= 1 && !heightEditText.getText().toString().equals("cm")) {
                    String text = heightEditText.getText().toString().replaceAll("[a-zA-Z ]", "") + "cm";
                    heightEditText.setText(text);
                    heightEditText.setSelection(text.length() - 2);
                    height = Integer.parseInt(heightEditText.getText().toString().replaceAll("[a-zA-Z ]", ""));
                } else if (heightEditText.getText().toString().equals("cm")) {
                    heightEditText.setText("");
                }
                heightEditText.addTextChangedListener(this);
                updateUserBtn.setBackground(getResources().getDrawable(R.drawable.sh_button));
            }
        });
    }

    private void updateClub() {
        clubEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clubEditText.setText("");
            }
        });
        clubEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                club = clubEditText.getText().toString();
                updateUserBtn.setBackground(getResources().getDrawable(R.drawable.sh_button));
            }
        });
    }

    private void updateCity() {
        cityEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityEditText.setText("");
            }
        });
        cityEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                city = cityEditText.getText().toString();
                updateUserBtn.setBackground(getResources().getDrawable(R.drawable.sh_button));
            }
        });
    }

    private void updateSpeciality() {
        speSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spe = Race.convertSwimFromFrenchToEnglish(parent.getSelectedItem().toString().toLowerCase());
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

    private boolean checkWeight()    { return (weight             > 0); }
    private boolean checkHeight()    { return (height             > 0); }
    private boolean checkBirth()     { return (birth.length()     > 0); }
    private boolean checkClub()      { return (club.length()      > 0); }
    private boolean checkCity()      { return (city.length()      > 0); }
}
