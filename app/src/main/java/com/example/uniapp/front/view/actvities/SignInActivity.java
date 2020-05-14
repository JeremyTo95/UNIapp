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
import com.example.uniapp.front.controller.controller_activity.SignInController;
import com.example.uniapp.front.controller.global.AboutScreen;
import com.example.uniapp.front.model.market.MarketSwim;
import com.example.uniapp.R;
import com.example.uniapp.front.controller.textwatcher.TextWatcherDate;
import com.example.uniapp.front.controller.textwatcher.TextWatcherHeight;
import com.example.uniapp.front.controller.textwatcher.TextWatcherWeight;
import com.example.uniapp.front.model.data.User;

public class SignInActivity extends AppCompatActivity implements TextWatcher {
    private User newUser;
    private SignInController controller;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AboutScreen.setupThemeApp(this);
        setContentView(R.layout.activity_sign_in);
        controller = new SignInController(this);
        controller.onStart();
    }

    public void setupUIElements() {
        genderSpinner      = findViewById(R.id.activity_sign_in_gender);
        firstnameEditText  = findViewById(R.id.activity_sign_in_firstname);
        lastnameEditText   = findViewById(R.id.activity_sign_in_lastname);
        weightEditText     = findViewById(R.id.activity_sign_in_weight);
        heightEditText     = findViewById(R.id.activity_sign_in_height);
        birthEditText      = findViewById(R.id.activity_sign_in_birth);
        clubEditText       = findViewById(R.id.activity_sign_in_club);
        cityEditText       = findViewById(R.id.activity_sign_in_city);
        speSpinner         = findViewById(R.id.activity_sign_in_spe);
        key_1              = findViewById(R.id.activity_sign_in_key1);
        key_2              = findViewById(R.id.activity_sign_in_key2);
        key_3              = findViewById(R.id.activity_sign_in_key3);
        confirmedBtn       = findViewById(R.id.activity_sign_in_confirmed);
        progressBar        = findViewById(R.id.activity_sign_in_progress_bar);
        loadingText        = findViewById(R.id.activity_sign_in_loading_textview);

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

        confirmedBtn.setOnClickListener(v -> {
            hideKeybaord(v);
            if (defineUserProfil()) {
                progressBar.setVisibility(View.VISIBLE);
                loadingText.setVisibility(View.VISIBLE);
                SharedPrefManager.savedUser(newUser);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean defineUserProfil() {
        if (checkInputUpdateUser()) {
            newUser = controller.getNewUser();
            confirmedBtn.setBackground(getApplicationContext().getResources().getDrawable(R.color.transparent));
            return true;
        } else {
            Toast.makeText(getApplicationContext(), "Utitlisateur non enregistré", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void updateUIElements() {
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
                controller.setGender(parent.getSelectedItem().toString().toLowerCase());
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
        controller.loadInputs();

        if (controller.checkFirstname() && controller.checkLastname() && controller.checkWeight() && controller.checkHeight() && controller.checkBirth() && controller.checkClub() && controller.checkCity() && controller.checkKey1() && controller.checkKey2() && controller.checkKey3()) {
            return true;
        } else {
            if (!controller.checkFirstname()) firstnameEditText.setHintTextColor(getApplicationContext().getResources().getColor(R.color.redDeep));
            if (!controller.checkLastname())  lastnameEditText.setHintTextColor(getApplicationContext().getResources().getColor(R.color.redDeep));
            if (!controller.checkWeight())    weightEditText.setHintTextColor(getApplicationContext().getResources().getColor(R.color.redDeep));
            if (!controller.checkHeight())    heightEditText.setHintTextColor(getApplicationContext().getResources().getColor(R.color.redDeep));
            if (!controller.checkBirth())     birthEditText.setHintTextColor(getApplicationContext().getResources().getColor(R.color.redDeep));
            if (!controller.checkClub())      firstnameEditText.setHintTextColor(getApplicationContext().getResources().getColor(R.color.redDeep));
            if (!controller.checkClub())      clubEditText.setHintTextColor(getApplicationContext().getResources().getColor(R.color.redDeep));
            if (!controller.checkCity())      cityEditText.setHintTextColor(getApplicationContext().getResources().getColor(R.color.redDeep));
            if (!controller.checkKey1())      key_1.setHintTextColor(getApplicationContext().getResources().getColor(R.color.redDeep));
            if (!controller.checkKey2())      key_2.setHintTextColor(getApplicationContext().getResources().getColor(R.color.redDeep));
            if (!controller.checkKey3())      key_3.setHintTextColor(getApplicationContext().getResources().getColor(R.color.redDeep));
            disableConfirmeButton();
            return false;
        }
    }

    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }

    public EditText getFirstnameEditText() { return firstnameEditText; }
    public EditText getLastnameEditText() { return lastnameEditText; }
    public EditText getWeightEditText() { return weightEditText; }
    public EditText getHeightEditText() { return heightEditText; }
    public EditText getBirthEditText() { return birthEditText; }
    public EditText getClubEditText() { return clubEditText; }
    public EditText getCityEditText() { return cityEditText; }
    public EditText getKey_1() { return key_1; }
    public EditText getKey_2() { return key_2; }
    public EditText getKey_3() { return key_3; }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { }
    @Override
    public void afterTextChanged(Editable s) { enableConfirmeButton(); }
}
