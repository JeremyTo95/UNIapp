package com.example.uniapp.front.view.actvities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uniapp.back.sharedpreferences.SharedPrefManager;
import com.example.uniapp.front.controller.global.AboutScreen;
import com.example.uniapp.front.model.market.MarketSwim;
import com.example.uniapp.R;
import com.example.uniapp.front.controller.textwatcher.TextWatcherDate;
import com.example.uniapp.front.controller.textwatcher.TextWatcherHeight;
import com.example.uniapp.front.controller.textwatcher.TextWatcherWeight;
import com.example.uniapp.front.model.data.User;

public class SignInActivity extends AppCompatActivity implements TextWatcher {
    private User newUser;

    private Spinner     genderSpinner;
    private EditText    firstnameEditText;
    private EditText    lastnameEditText;
    private EditText    weightEditText;
    private EditText    heightEditText;
    private EditText    birthEditText;
    private EditText    clubEditText;
    private EditText    cityEditText;
    private Spinner     speSpinner;
    private Button      confirmedBtn;
    private ProgressBar progressBar;
    private TextView    loadingText;
    private EditText    key_1;
    private EditText    key_2;
    private EditText    key_3;

    private String gender;
    private String firstname;
    private String lastname;
    private int    weight;
    private int    height;
    private String birth;
    private String club;
    private String city;
    private String spe;
    private String key;
    private String key1;
    private String key2;
    private String key3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AboutScreen.setupThemeApp(this);
        setContentView(R.layout.activity_sign_in);


        setupUIElements();
        updateUIElements();
    }

    private void setupUIElements() {
        genderSpinner      = (Spinner)     findViewById(R.id.activity_sign_in_gender);
        firstnameEditText  = (EditText)    findViewById(R.id.activity_sign_in_firstname);
        lastnameEditText   = (EditText)    findViewById(R.id.activity_sign_in_lastname);
        weightEditText     = (EditText)    findViewById(R.id.activity_sign_in_weight);
        heightEditText     = (EditText)    findViewById(R.id.activity_sign_in_height);
        birthEditText      = (EditText)    findViewById(R.id.activity_sign_in_birth);
        clubEditText       = (EditText)    findViewById(R.id.activity_sign_in_club);
        cityEditText       = (EditText)    findViewById(R.id.activity_sign_in_city);
        speSpinner         = (Spinner)     findViewById(R.id.activity_sign_in_spe);
        key_1              = (EditText)    findViewById(R.id.activity_sign_in_key1);
        key_2              = (EditText)    findViewById(R.id.activity_sign_in_key2);
        key_3              = (EditText)    findViewById(R.id.activity_sign_in_key3);
        confirmedBtn       = (Button)      findViewById(R.id.activity_sign_in_confirmed);
        progressBar        = (ProgressBar) findViewById(R.id.activity_sign_in_progress_bar);
        loadingText        = (TextView)    findViewById(R.id.activity_sign_in_loading_textview);

        firstnameEditText.addTextChangedListener(this);
        lastnameEditText.addTextChangedListener(this);
        weightEditText.addTextChangedListener(this);
        heightEditText.addTextChangedListener(this);
        birthEditText.addTextChangedListener(this);
        clubEditText.addTextChangedListener(this);
        key_1.addTextChangedListener(this);
        key_2.addTextChangedListener(this);
        key_3.addTextChangedListener(this);
        weightEditText.addTextChangedListener(new TextWatcherWeight(weightEditText));
        heightEditText.addTextChangedListener(new TextWatcherHeight(heightEditText));
        birthEditText.addTextChangedListener(new TextWatcherDate(birthEditText));

        gender    = "Homme";
        spe       = "butterfly";

        progressBar.setVisibility(View.GONE);
        loadingText.setVisibility(View.GONE);

        ArrayAdapter<CharSequence> genderDropdownAdapter;
        if (AboutScreen.isNightMode(this)) genderDropdownAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.genders, R.layout.dropdown_item_dark);
        else genderDropdownAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.genders, R.layout.dropdown_item_light);
        genderDropdownAdapter.setDropDownViewResource(R.layout.dropdown_all_items);
        genderSpinner.setAdapter(genderDropdownAdapter);

        ArrayAdapter<CharSequence> speDropdownAdapter;
        if (AboutScreen.isNightMode(this)) speDropdownAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.swims, R.layout.dropdown_item_dark);
        else speDropdownAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.swims, R.layout.dropdown_item_light);
        speDropdownAdapter.setDropDownViewResource(R.layout.dropdown_all_items);
        speSpinner.setAdapter(speDropdownAdapter);

        confirmedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeybaord(v);
                if (defineUserProfil()) {
                    progressBar.setVisibility(View.VISIBLE);
                    loadingText.setVisibility(View.VISIBLE);
                    SharedPrefManager.savedUser(newUser);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private boolean defineUserProfil() {
        if (checkInputUpdateUser()) {
            key = key1 + "-" + key2 + "-" + key3;
            newUser = new User(gender, firstname, lastname, birth, height, weight, club, spe, city, key);
            confirmedBtn.setBackground(getApplicationContext().getResources().getDrawable(R.color.transparent));
            return true;
        } else {
            Toast.makeText(getApplicationContext(), "Utitlisateur non enregistré", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void updateUIElements() {
        updateGender();
        updateSpeciality();
    }

    private void updateSpeciality() {
        speSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MarketSwim.convertSwimFromFrenchToEnglish(parent.getSelectedItem().toString().toLowerCase());
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

    private void enableConfirmeButton() {
        confirmedBtn.setEnabled(true);
        confirmedBtn.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.sh_button));
        confirmedBtn.setTextColor(getResources().getColor(R.color.textButtonColor));
    }

    private void disableConfirmeButton() {
        confirmedBtn.setEnabled(false);
        confirmedBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
        if (AboutScreen.isNightMode(this)) confirmedBtn.setTextColor(getResources().getColor(R.color.textColorDark));
        else confirmedBtn.setTextColor(getResources().getColor(R.color.textColorLight));
    }

    private boolean checkInputUpdateUser() {
        loadInputs();

        if (checkFirstname() && checkLastname() && checkWeight() && checkHeight() && checkBirth() && checkClub() && checkCity() && checkKey1() && checkKey2() && checkKey3()) {
            return true;
        } else {
            if (!checkFirstname()) firstnameEditText.setHintTextColor(getApplicationContext().getResources().getColor(R.color.redDeep));
            if (!checkLastname())  lastnameEditText.setHintTextColor(getApplicationContext().getResources().getColor(R.color.redDeep));
            if (!checkWeight())    weightEditText.setHintTextColor(getApplicationContext().getResources().getColor(R.color.redDeep));
            if (!checkHeight())    heightEditText.setHintTextColor(getApplicationContext().getResources().getColor(R.color.redDeep));
            if (!checkBirth())     birthEditText.setHintTextColor(getApplicationContext().getResources().getColor(R.color.redDeep));
            if (!checkClub())      firstnameEditText.setHintTextColor(getApplicationContext().getResources().getColor(R.color.redDeep));
            if (!checkClub())      clubEditText.setHintTextColor(getApplicationContext().getResources().getColor(R.color.redDeep));
            if (!checkCity())      cityEditText.setHintTextColor(getApplicationContext().getResources().getColor(R.color.redDeep));
            if (!checkKey1())      key_1.setHintTextColor(getApplicationContext().getResources().getColor(R.color.redDeep));
            if (!checkKey2())      key_2.setHintTextColor(getApplicationContext().getResources().getColor(R.color.redDeep));
            if (!checkKey3())      key_3.setHintTextColor(getApplicationContext().getResources().getColor(R.color.redDeep));
            disableConfirmeButton();
            return false;
        }
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
        key1      = key_1.getText().toString();
        key2      = key_2.getText().toString();
        key3      = key_3.getText().toString();
    }

    private boolean checkFirstname() { return (firstname.length() > 0); }
    private boolean checkLastname()  { return (lastname.length()  > 0); }
    private boolean checkWeight()    { return (weight             > 0); }
    private boolean checkHeight()    { return (height             > 0); }
    private boolean checkBirth()     { return (birth.length()     > 0); }
    private boolean checkClub()      { return (club.length()      > 0); }
    private boolean checkCity()      { return (city.length()      > 0); }
    private boolean checkKey1()      { return (key1.length()      > 0); }
    private boolean checkKey2()      { return (key2.length()      > 0); }
    private boolean checkKey3()      { return (key3.length()      > 0); }

    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { }
    @Override
    public void afterTextChanged(Editable s) { enableConfirmeButton(); }
}
