package com.example.uniapp.front.controller.controller_fragment;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.uniapp.R;
import com.example.uniapp.back.room.RoomDataBase;
import com.example.uniapp.back.sharedpreferences.SharedPrefManager;
import com.example.uniapp.front.controller.global.AboutScreen;
import com.example.uniapp.front.controller.global.Controller;
import com.example.uniapp.front.model.data.Race;
import com.example.uniapp.front.model.data.User;
import com.example.uniapp.front.model.market.MarketSwim;
import com.example.uniapp.front.view.fragments.SettingsFragment;

public class SettingsController extends Controller {
    private SettingsFragment view;
    private Activity         activity;
    private Context          context;
    private RoomDataBase     roomDataBase;

    private String gender;
    private String firstname;
    private String lastname;
    private int    weight;
    private int    height;
    private String birth;
    private String club;
    private String city;
    private String spe;

    public SettingsController(SettingsFragment view) {
        super(view);
        this.view         = view;
        this.activity     = view.getActivity();
        this.context      = view.getContext();
        this.roomDataBase = RoomDataBase.getDatabase(context);
    }

    @Override
    public void onStart() {
        super.onStart();
        gender = "Homme";
        spe    = "butterfly";
        setupInfoUser();
        updateUIElements();
        disableUpdateUserBtn();
    }

    private void setupInfoUser() {
        if (roomDataBase.userDAO().getNb() != 0) {
            showUserInfo();
        }
    }

    public void manageTheme() {
        if (SharedPrefManager.loadThemeMode(context) == 0) Toast.makeText(context, "Dark Mode Activé", Toast.LENGTH_SHORT).show();
        else if (SharedPrefManager.loadThemeMode(context) == 1) Toast.makeText(context, "Light Mode Activé", Toast.LENGTH_SHORT).show();
        else Toast.makeText(context,  "Mode Automatique Activé", Toast.LENGTH_SHORT).show();
        SharedPrefManager.savedThemeMode((SharedPrefManager.loadThemeMode(context)+1)%3);
        restartApp();
    }

    public void importDataRaces() {
        Race.startAsyncTaskLoadingRace(view.getActivity());
    }

    private void loadUserInfosFromDB() {
        gender    = roomDataBase.userDAO().getUser().getGender();
        firstname = roomDataBase.userDAO().getUser().getFirstname();
        lastname  = roomDataBase.userDAO().getUser().getLastname();
        weight    = roomDataBase.userDAO().getUser().getWeight();
        height    = roomDataBase.userDAO().getUser().getHeight();
        birth     = roomDataBase.userDAO().getUser().getBirthday();
        club      = roomDataBase.userDAO().getUser().getClub();
        city      = roomDataBase.userDAO().getUser().getCityTraining();
        spe       = roomDataBase.userDAO().getUser().getSpe();
    }

    private void showUserInfo() {
        loadUserInfosFromDB();

        view.getGenderSpinner().setSelection((gender.equals("homme")) ? 1 : 0);
        view.getFirstnameEditText().setText(firstname);
        view.getLastnameEditText().setText(lastname);
        view.getWeightEditText().setText(getWeightET());
        view.getHeightEditText().setText(getHeightET());
        view.getBirthEditText().setText(birth);
        view.getClubEditText().setText(club);
        view.getCityEditText().setText(city);

        if (spe.equals("butterfly"))    view.getSpeSpinner().setSelection(0);
        if (spe.equals("backstroke"))   view.getSpeSpinner().setSelection(1);
        if (spe.equals("breaststroke")) view.getSpeSpinner().setSelection(2);
        if (spe.equals("freestyle"))    view.getSpeSpinner().setSelection(3);
        if (spe.equals("IM"))           view.getSpeSpinner().setSelection(4);
    }

    private void updateUIElements() {
        updateGender();
        updateSpeciality();
    }

    private void updateGender() {
        view.getGenderSpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getSelectedItem().toString().toLowerCase();
            }
        });
    }

    private void updateSpeciality() {
        view.getSpeSpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spe = MarketSwim.convertSwimFromFrenchToEnglish(parent.getSelectedItem().toString().toLowerCase());
            }
        });
    }

    public ArrayAdapter<CharSequence> getGenderAdapter() {
        if (AboutScreen.isNightMode(activity)) return ArrayAdapter.createFromResource(context, R.array.genders, R.layout.dropdown_item_dark);
        else return ArrayAdapter.createFromResource(context, R.array.genders, R.layout.dropdown_item_light);
    }

    public ArrayAdapter<CharSequence> getSpeAdapter() {
        if (AboutScreen.isNightMode(activity)) return ArrayAdapter.createFromResource(context, R.array.swims, R.layout.dropdown_item_dark);
        else return ArrayAdapter.createFromResource(context, R.array.swims, R.layout.dropdown_item_light);
    }

    private void disableUpdateUserBtn() {
        view.getUpdateUserBtn().setEnabled(false);
        view.getUpdateUserBtn().setBackground(view.getResources().getDrawable(R.color.transparent));
        view.getUpdateUserBtn().setTextColor(AboutScreen.getColorByThemeAttr(context, R.attr.textColor, R.color.textColorDark));
    }

    public void enableUpdateUserBtn() {
        view.getUpdateUserBtn().setEnabled(true);
        view.getUpdateUserBtn().setBackground(view.getResources().getDrawable(R.drawable.sh_button));
        view.getUpdateUserBtn().setTextColor(view.getResources().getColor(R.color.textButtonColor));
    }

    private void loadUserInfosFromInputs() {
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
    }

    private boolean checkInputUpdateUser() {
        loadUserInfosFromInputs();
        if (checkWeight() && checkHeight() && checkBirth() && checkClub() && checkCity()) {
            return true;
        } else {
            if (!checkWeight()) view.getWeightEditText().setHintTextColor(view.getResources().getColor(R.color.redDeep));
            if (!checkHeight()) view.getHeightEditText().setHintTextColor(view.getResources().getColor(R.color.redDeep));
            if (!checkBirth())  view.getBirthEditText().setHintTextColor(view.getResources().getColor(R.color.redDeep));
            if (!checkClub())   view.getClubEditText().setHintTextColor(view.getResources().getColor(R.color.redDeep));
            if (!checkCity())   view.getCityEditText().setHintTextColor(view.getResources().getColor(R.color.redDeep));
            return false;
        }
    }

    public void setupUpdateUser() {
        if (checkInputUpdateUser()) {
            User user = new User(gender, firstname, lastname, birth, height, weight, club, spe, city, roomDataBase.userDAO().getUser().getMykey());
            roomDataBase.userDAO().deleteAll();
            roomDataBase.userDAO().insert(user);
            disableUpdateUserBtn();
            Toast.makeText(context, "Utilisateur mis à jour", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(context, "ATTENTION : Utilisateur non mis à jour", Toast.LENGTH_SHORT).show();
    }

    private boolean checkWeight()    { return (weight             > 0); }
    private boolean checkHeight()    { return (height             > 0); }
    private boolean checkBirth()     { return (birth.length()     > 0); }
    private boolean checkClub()      { return (club.length()      > 0); }
    private boolean checkCity()      { return (city.length()      > 0); }

    public String getGender() { return gender; }
    public int getHeight() { return height; }
    public String getClub() { return club; }
    public String getCity() { return city; }

    public void setGender(String gender) { this.gender = gender; }
    public void setHeight(int height) { this.height = height; }
    public void setClub(String club) { this.club = club; }
    public void setCity(String city) { this.city = city; }

    private String getWeightET() {
        return weight + "kg";
    }

    private String getHeightET() {
        return height + "cm";
    }
}
