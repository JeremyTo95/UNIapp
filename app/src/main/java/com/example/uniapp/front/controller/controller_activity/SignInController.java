package com.example.uniapp.front.controller.controller_activity;

import com.example.uniapp.front.controller.global.Controller;
import com.example.uniapp.front.model.data.User;
import com.example.uniapp.front.view.actvities.SignInActivity;

public class SignInController extends Controller {
    private SignInActivity view;

    private String gender;
    private String firstname;
    private String lastname;
    private int    weight;
    private int    height;
    private String birth;
    private String club;
    private String city;
    private String spe;
    private String key1;
    private String key2;
    private String key3;

    public SignInController(SignInActivity view) {
        super(view);
        this.view    = view;
    }

    @Override
    public void onStart() {
        super.onStart();
        gender = "Homme";
        spe    = "butterfly";
        view.setupUIElements();
        view.updateUIElements();
    }

    private String getKey() {
        return key1 + "-" + key2 + "-" + key3;
    }

    public User getNewUser() {
        return new User(gender, firstname, lastname, birth, height, weight, club, spe, city, getKey());
    }

    public void loadInputs() {
        String tmp;
        firstname = view.getFirstnameEditText().getText().toString();
        lastname  = view.getLastnameEditText().getText().toString();
        birth     = view.getBirthEditText().getText().toString();
        tmp       = view.getHeightEditText().getText().toString().replaceAll("[a-zA-Z]", "");
        if (tmp.length() != 0) height = Integer.parseInt(tmp);
        else height = 0;
        tmp       = view.getWeightEditText().getText().toString().replaceAll("[a-zA-Z]", "");
        if (tmp.length() != 0) weight = Integer.parseInt(tmp);
        else weight = 0;
        club      = view.getClubEditText().getText().toString();
        city      = view.getCityEditText().getText().toString();
        key1      = view.getKey_1().getText().toString();
        key2      = view.getKey_2().getText().toString();
        key3      = view.getKey_3().getText().toString();
    }

    public boolean checkFirstname() { return (firstname.length() > 0); }
    public boolean checkLastname()  { return (lastname.length()  > 0); }
    public boolean checkWeight()    { return (weight             > 0); }
    public boolean checkHeight()    { return (height             > 0); }
    public boolean checkBirth()     { return (birth.length()     > 0); }
    public boolean checkClub()      { return (club.length()      > 0); }
    public boolean checkCity()      { return (city.length()      > 0); }
    public boolean checkKey1()      { return (key1.length()      > 0); }
    public boolean checkKey2()      { return (key2.length()      > 0); }
    public boolean checkKey3()      { return (key3.length()      > 0); }

    public void setGender(String gender) { this.gender = gender; }
}
