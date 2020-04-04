package com.example.uniapp.controllers.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.uniapp.R;
import com.example.uniapp.controllers.activities.MainActivity;
import com.example.uniapp.models.database.dao.user.User;

import java.util.List;


public class SettingsFragment extends Fragment implements View.OnClickListener {
    private View     layoutInflater;

    private EditText firstnameEditText;
    private EditText lastnameEditText;
    private EditText weightEditText;
    private EditText heightEditText;
    private EditText birthEditText;
    private EditText clubEditText;
    private EditText cityEditText;
    private Spinner speSpinner;
    private Button updateUserBtn;
    private Button   importDataRacesBtn;
    private Button   saveDataBtn;

    private String firstname;
    private String lastname;
    private int    weight;
    private int    height;
    private String birth;
    private String club;
    private String city;
    private String spe;

    public SettingsFragment() { }

    //TOTO: AJOUTER LES RECORDS DU CLUB

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutInflater = inflater.inflate(R.layout.fragment_settings, container, false);

        setupUIElements();
        updateUIElements();

        return layoutInflater;
    }

    private void setupUIElements() {
        firstnameEditText  = (EditText) layoutInflater.findViewById(R.id.fragment_settings_firstname);
        lastnameEditText   = (EditText) layoutInflater.findViewById(R.id.fragment_settings_lastname);
        weightEditText     = (EditText) layoutInflater.findViewById(R.id.fragment_settings_weight);
        heightEditText     = (EditText) layoutInflater.findViewById(R.id.fragment_settings_height);
        birthEditText      = (EditText) layoutInflater.findViewById(R.id.fragment_settings_birth);
        clubEditText       = (EditText) layoutInflater.findViewById(R.id.fragment_settings_club);
        cityEditText       = (EditText) layoutInflater.findViewById(R.id.fragment_settings_city);
        speSpinner         = (Spinner)  layoutInflater.findViewById(R.id.fragment_settings_spe);
        updateUserBtn      = (Button)   layoutInflater.findViewById(R.id.fragment_settings_update);
        importDataRacesBtn = (Button)   layoutInflater.findViewById(R.id.fragment_settings_import_data_race);
        saveDataBtn        = (Button)   layoutInflater.findViewById(R.id.fragment_settings_save_data);

        firstname = firstnameEditText.getText().toString();
        lastname  = lastnameEditText.getText().toString();
        weight    = 0;
        height    = 0;
        birth     = birthEditText.getText().toString();
        club      = clubEditText.getText().toString();
        city      = cityEditText.getText().toString();
        spe       = "butterfly";

        ArrayAdapter<CharSequence> speDropdownAdapter = ArrayAdapter.createFromResource(getContext(), R.array.swims, R.layout.dropdown_item);
        speDropdownAdapter.setDropDownViewResource(R.layout.dropdown_item);
        speSpinner.setAdapter(speDropdownAdapter);

        updateUserBtn.setOnClickListener(this);
        importDataRacesBtn.setOnClickListener(this);
        saveDataBtn.setOnClickListener(this);
    }

    private void updateUIElements() {
        updateFirstname();
        updateLastname();
        updateWeight();
        updateHeight();
        updateBirth();
        updateClub();
        updateCity();
        updateSpeciality();
    }

    @Override
    public void onClick(View v) {
             if (v.getTag().equals("updateBtn"))         setupUpdateUser();
        else if (v.getTag().equals("importDataRaceBtn")) importDataRaces();
        else if (v.getTag().equals("saveDataBtn"))       saveData();
    }

    private void setupUpdateUser() {
        Log.e("Ep", "It works but...");
        System.out.println("first  : " + firstname);
        System.out.println("last   : " + lastname);
        System.out.println("weight : " + weight);
        System.out.println("height : " + height);
        System.out.println("birth  : " + birth);
        System.out.println("club   : " + club);
        System.out.println("city   : " + city);
        System.out.println("spe    : " + spe);
        if (checkInputUpdateUser()) {
            User user = new User(firstname, lastname, birth, height, weight, club, spe, city);
            MainActivity.appDataBase.userDAO().deleteAll();
            MainActivity.appDataBase.userDAO().insert(user);
            MainActivity.user = MainActivity.appDataBase.userDAO().getAll().get(0);
            Toast.makeText(getActivity(), "New user has been saved", Toast.LENGTH_SHORT);
            Log.e("E", "It works ! (" + MainActivity.appDataBase.userDAO().getAll().size() + ")");
        }
    }

    private void importDataRaces() {
        //TODO: Requête vers JSON sur serveur
        //      Stockage des données sur base de donnée locale
    }

    private void saveData() {
        // Pour le momment ça va montrer la base tmtc
        List<User> allUsers = MainActivity.appDataBase.userDAO().getAll();
        for (int i = 0; i < allUsers.size(); i++) {
            System.out.println(allUsers.get(i).toString());
        }
    }

    private void updateBirth() {
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
            }
        });
    }

    private void updateFirstname() {
        firstnameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) { firstname = firstnameEditText.getText().toString(); }
        });
    }

    private void updateLastname() {
        lastnameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) { lastname = lastnameEditText.getText().toString(); }
        });
    }

    private void updateWeight() {
        weightEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) { weight = Integer.parseInt(weightEditText.getText().toString()); }
        });
    }

    private void updateHeight() {
        heightEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) { height = Integer.parseInt(heightEditText.getText().toString()); }
        });
    }

    private void updateClub() {
        clubEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) { club = clubEditText.getText().toString(); }
        });
    }

    private void updateCity() {
        cityEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) { city = cityEditText.getText().toString(); }
        });
    }

    private void updateSpeciality() {
        speSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spe = parent.getSelectedItem().toString();
            }
        });
    }

    private boolean checkInputUpdateUser() {
        if (checkFirstname() && checkLastname() && checkWeight() && checkHeight() && checkBirth() && checkClub() && checkCity()) {
            return true;
        } else {
            if (!checkFirstname()) firstnameEditText.setHintTextColor(getResources().getColor(R.color.redDeep));
            if (!checkLastname())  lastnameEditText.setHintTextColor(getResources().getColor(R.color.redDeep));
            if (!checkWeight())    weightEditText.setHintTextColor(getResources().getColor(R.color.redDeep));
            if (!checkHeight())    heightEditText.setHintTextColor(getResources().getColor(R.color.redDeep));
            if (!checkBirth())     birthEditText.setHintTextColor(getResources().getColor(R.color.redDeep));
            if (!checkClub())      firstnameEditText.setHintTextColor(getResources().getColor(R.color.redDeep));
            if (!checkClub())      clubEditText.setHintTextColor(getResources().getColor(R.color.redDeep));
            if (!checkCity())      cityEditText.setHintTextColor(getResources().getColor(R.color.redDeep));
            return false;
        }
    }

    private boolean checkFirstname() { return (firstname.length() > 0); }
    private boolean checkLastname()  { return (lastname.length()  > 0); }
    private boolean checkWeight()    { return (weight             > 0); }
    private boolean checkHeight()    { return (height             > 0); }
    private boolean checkBirth()     { return (birth.length()     > 0); }
    private boolean checkClub()      { return (club.length()      > 0); }
    private boolean checkCity()      { return (city.length()      > 0); }
}
