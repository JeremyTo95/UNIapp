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
import com.example.uniapp.models.database.dao.pointFFN.PointFFN;
import com.example.uniapp.models.database.dao.race.Race;
import com.example.uniapp.models.database.dao.user.User;

import java.util.List;


public class SettingsFragment extends Fragment implements View.OnClickListener {
    private View     layoutInflater;

    private Spinner  genderSpinner;
    private EditText firstnameEditText;
    private EditText lastnameEditText;
    private EditText weightEditText;
    private EditText heightEditText;
    private EditText birthEditText;
    private EditText clubEditText;
    private EditText cityEditText;
    private Spinner  speSpinner;
    private Button   updateUserBtn;
    private Button   importDataRacesBtn;
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

    //TOTO: AJOUTER LES RECORDS DU CLUB

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutInflater = inflater.inflate(R.layout.fragment_settings, container, false);

        setupUIElements();
        updateUIElements();

        return layoutInflater;
    }

    private void setupUIElements() {
        genderSpinner      = (Spinner)  layoutInflater.findViewById(R.id.fragment_settings_gender);
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

        gender    = "M";
        firstname = firstnameEditText.getText().toString();
        lastname  = lastnameEditText.getText().toString();
        weight    = 0;
        height    = 0;
        birth     = birthEditText.getText().toString();
        club      = clubEditText.getText().toString();
        city      = cityEditText.getText().toString();
        spe       = "butterfly";

        if (MainActivity.user != null) {
            genderSpinner.setSelection((gender == "M") ? 0 : 1);
            firstnameEditText.setText(MainActivity.user.getFirstname());
            lastnameEditText.setText(MainActivity.user.getLastname());
            weightEditText.setText(String.valueOf(MainActivity.user.getWeight()) + "kg");
            heightEditText.setText(String.valueOf(MainActivity.user.getHeight()) + "cm");
            birthEditText.setText(MainActivity.user.getBirthday());
            clubEditText.setText(MainActivity.user.getClub());
            cityEditText.setText(MainActivity.user.getCityTraining());
            if (MainActivity.user.getSpe().equals("butterfly")) speSpinner.setSelection(0);
            if (MainActivity.user.getSpe().equals("backstroke")) speSpinner.setSelection(1);
            if (MainActivity.user.getSpe().equals("breaststroke")) speSpinner.setSelection(2);
            if (MainActivity.user.getSpe().equals("freestyle")) speSpinner.setSelection(3);
            if (MainActivity.user.getSpe().equals("IM")) speSpinner.setSelection(4);

            gender    = MainActivity.user.getGender();
            firstname = MainActivity.user.getFirstname();
            lastname  = MainActivity.user.getLastname();
            weight    = MainActivity.user.getWeight();
            height    = MainActivity.user.getHeight();
            birth     = MainActivity.user.getBirthday();
            club      = MainActivity.user.getClub();
            city      = MainActivity.user.getCityTraining();
            spe       = MainActivity.user.getSpe();
        }

        ArrayAdapter<CharSequence> genderDropdownAdapter = ArrayAdapter.createFromResource(getContext(), R.array.genders, R.layout.dropdown_item);
        genderDropdownAdapter.setDropDownViewResource(R.layout.dropdown_item);
        genderSpinner.setAdapter(genderDropdownAdapter);

        ArrayAdapter<CharSequence> speDropdownAdapter = ArrayAdapter.createFromResource(getContext(), R.array.swims, R.layout.dropdown_item);
        speDropdownAdapter.setDropDownViewResource(R.layout.dropdown_item);
        speSpinner.setAdapter(speDropdownAdapter);

        updateUserBtn.setOnClickListener(this);
        importDataRacesBtn.setOnClickListener(this);
        saveDataBtn.setOnClickListener(this);
    }

    private void updateUIElements() {
        updateGender();
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
        else if (v.getTag().equals("importPointsFFN"))   importPointFFN();
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
            User user = new User(gender, firstname, lastname, birth, height, weight, club, spe, city);
            MainActivity.appDataBase.userDAO().deleteAll();
            MainActivity.appDataBase.userDAO().insert(user);
            MainActivity.user = MainActivity.appDataBase.userDAO().getAll().get(0);
            Toast.makeText(getContext(), "New user has been saved", Toast.LENGTH_SHORT).show();
            updateUserBtn.setBackground(getResources().getDrawable(R.color.transparent));
            Log.e("E", "It works ! (" + MainActivity.appDataBase.userDAO().getAll().size() + ")");
        } else {
            Toast.makeText(getContext(), "New user hasn't been saved", Toast.LENGTH_SHORT).show();
        }
    }

    private void importDataRaces() {
        //TODO: Requête vers JSON sur serveur
        //      Stockage des données sur base de donnée locale
        Race.makeRaceApiCall(getContext());
    }

    private void importPointFFN() {
        if (MainActivity.appDataBase.pointFFNDAO().getNbPoint() == 0) PointFFN.makePointFFNApiCall(getContext());

        // Pour le moment ça va montrer la base tmtc
        MainActivity.appDataBase.raceDAO().deleteAll();
        List<User> allUsers = MainActivity.appDataBase.userDAO().getAll();
        List<Race> allRaces = MainActivity.appDataBase.raceDAO().getAllRaces();
        System.out.println(MainActivity.appDataBase.raceDAO().getNbRaces());
        //        for (int i = 0; i < allUsers.size(); i++) System.out.println(allUsers.get(i).toString());
//        for (int i = 0; i < allUsers.size(); i++) System.out.println(allRaces.get(i).toString());
        System.out.println(MainActivity.appDataBase.pointFFNDAO().getNbPoint());
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

    private void updateFirstname() {
        firstnameEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { firstnameEditText.setText("");
            }
        });
        firstnameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                firstname = firstnameEditText.getText().toString();
                updateUserBtn.setBackground(getResources().getDrawable(R.drawable.sh_button));
            }
        });
    }

    private void updateLastname() {
        lastnameEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { lastnameEditText.setText("");
            }
        });
        lastnameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                lastname = lastnameEditText.getText().toString();
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
                spe = parent.getSelectedItem().toString();
            }
        });
    }

    private void updateGender() {
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getSelectedItem().toString();
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
