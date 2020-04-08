package com.example.uniapp.views.popup;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.uniapp.R;
import com.example.uniapp.controllers.activities.MainActivity;
import com.example.uniapp.models.database.dao.user.User;

import org.w3c.dom.Text;

public class SignInPopup extends Dialog {
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

    private String gender;
    private String firstname;
    private String lastname;
    private int    weight;
    private int    height;
    private String birth;
    private String club;
    private String city;
    private String spe;

    public SignInPopup(@NonNull Context context) {
        super(context);
        setContentView(R.layout.popup_sign_in);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setupUIElements();
        updateUIElements();
    }

    private void setupUIElements() {
        genderSpinner      = (Spinner)     findViewById(R.id.popup_sign_in_gender);
        firstnameEditText  = (EditText)    findViewById(R.id.popup_sign_in_firstname);
        lastnameEditText   = (EditText)    findViewById(R.id.popup_sign_in_lastname);
        weightEditText     = (EditText)    findViewById(R.id.popup_sign_in_weight);
        heightEditText     = (EditText)    findViewById(R.id.popup_sign_in_height);
        birthEditText      = (EditText)    findViewById(R.id.popup_sign_in_birth);
        clubEditText       = (EditText)    findViewById(R.id.popup_sign_in_club);
        cityEditText       = (EditText)    findViewById(R.id.popup_sign_in_city);
        speSpinner         = (Spinner)     findViewById(R.id.popup_sign_in_spe);
        confirmedBtn       = (Button)      findViewById(R.id.popup_sign_in_confirmed);
        progressBar        = (ProgressBar) findViewById(R.id.popup_sign_in_progress_bar);
        loadingText        = (TextView)    findViewById(R.id.popup_sign_in_loading_textview);

        progressBar.setVisibility(View.GONE);
        loadingText.setVisibility(View.GONE);

        gender    = "boy";
        firstname = "";
        lastname  = "";
        weight    = 0;
        height    = 0;
        birth     = "";
        club      = "";
        city      = "";
        spe       = "butterfly";

        ArrayAdapter<CharSequence> genderDropdownAdapter = ArrayAdapter.createFromResource(getContext(), R.array.genders, R.layout.dropdown_item);
        genderDropdownAdapter.setDropDownViewResource(R.layout.dropdown_item);
        genderSpinner.setAdapter(genderDropdownAdapter);

        ArrayAdapter<CharSequence> speDropdownAdapter = ArrayAdapter.createFromResource(getContext(), R.array.swims, R.layout.dropdown_item);
        speDropdownAdapter.setDropDownViewResource(R.layout.dropdown_item);
        speSpinner.setAdapter(speDropdownAdapter);

        confirmedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defineUserProfil();
                progressBar.setVisibility(View.VISIBLE);
                loadingText.setVisibility(View.VISIBLE);
            }
        });
    }

    private void defineUserProfil() {
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
            confirmedBtn.setBackground(getContext().getResources().getDrawable(R.color.transparent));
            Log.e("E", "It works ! (" + MainActivity.appDataBase.userDAO().getAll().size() + ")");
        } else {
            Toast.makeText(getContext(), "New user hasn't been saved", Toast.LENGTH_SHORT).show();
        }
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
                confirmedBtn.setBackground(getContext().getResources().getDrawable(R.drawable.sh_button));
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
                confirmedBtn.setBackground(getContext().getResources().getDrawable(R.drawable.sh_button));
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
                confirmedBtn.setBackground(getContext().getResources().getDrawable(R.drawable.sh_button));
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
                confirmedBtn.setBackground(getContext().getResources().getDrawable(R.drawable.sh_button));
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
                confirmedBtn.setBackground(getContext().getResources().getDrawable(R.drawable.sh_button));
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
                confirmedBtn.setBackground(getContext().getResources().getDrawable(R.drawable.sh_button));
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
                confirmedBtn.setBackground(getContext().getResources().getDrawable(R.drawable.sh_button));
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
            if (!checkFirstname()) firstnameEditText.setHintTextColor(getContext().getResources().getColor(R.color.redDeep));
            if (!checkLastname())  lastnameEditText.setHintTextColor(getContext().getResources().getColor(R.color.redDeep));
            if (!checkWeight())    weightEditText.setHintTextColor(getContext().getResources().getColor(R.color.redDeep));
            if (!checkHeight())    heightEditText.setHintTextColor(getContext().getResources().getColor(R.color.redDeep));
            if (!checkBirth())     birthEditText.setHintTextColor(getContext().getResources().getColor(R.color.redDeep));
            if (!checkClub())      firstnameEditText.setHintTextColor(getContext().getResources().getColor(R.color.redDeep));
            if (!checkClub())      clubEditText.setHintTextColor(getContext().getResources().getColor(R.color.redDeep));
            if (!checkCity())      cityEditText.setHintTextColor(getContext().getResources().getColor(R.color.redDeep));
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

    public void build() {
        show();
        Window window = getWindow();
        if (window != null) window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }
}
