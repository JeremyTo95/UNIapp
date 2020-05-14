package com.example.uniapp.front.view.popup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.InputFilter;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.uniapp.R;
import com.example.uniapp.front.controller.global.AboutScreen;
import com.example.uniapp.front.controller.textwatcher.TextWatcherChrono;
import com.example.uniapp.front.controller.textwatcher.TextWatcherDate;
import com.example.uniapp.front.model.market.MarketTimes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddRacePopup extends Dialog {
    private Activity activity;

    private String city;
    private String date;
    private int distanceRace;
    private int sizePool;
    private float time;
    private String swim;
    private String level;
    private String country;

    private TextView subtitleDescription;
    private EditText dateEditText;
    private EditText cityEditText;
    private EditText timeEditText;
    private Spinner  selectLevelCompetition;
    private EditText countryEditText;
    private Button   deniedButton;
    private Button   confirmedButton;

    public AddRacePopup(final Activity activity) {
        super(activity, R.style.Theme_AppCompat_Dialog);
        setContentView(R.layout.popup_add_race);
        if (getWindow() != null) getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.activity = activity;

        @SuppressLint("SimpleDateFormat")
        DateFormat dateFormat  = new SimpleDateFormat("dd/MM/yyyy");
        Date today             = Calendar.getInstance().getTime();
        date                   = dateFormat.format(today);
        subtitleDescription    = findViewById(R.id.fragment_competition_add_race_popup_swim_description);
        dateEditText           = findViewById(R.id.race_popup_edit_text_date);
        cityEditText           = findViewById(R.id.race_popup_edit_text_city);
        timeEditText           = findViewById(R.id.race_popup_edit_text_time);
        selectLevelCompetition = findViewById(R.id.race_popup_spinner_level);
        countryEditText        = findViewById(R.id.race_popup_edit_text_country);
        deniedButton           = findViewById(R.id.fragment_competition_add_race_popup_denied);
        confirmedButton        = findViewById(R.id.fragment_competition_add_race_popup_confirmed);
        dateEditText.setText(date);

        ArrayAdapter<CharSequence> levelDropdownAdapter = ArrayAdapter.createFromResource(getContext(), R.array.competition_level, R.layout.dropdown_item_auto);
        levelDropdownAdapter.setDropDownViewResource(R.layout.dropdown_all_items);
        selectLevelCompetition.setAdapter(levelDropdownAdapter);

        updateInputDateFormatEditText();
        updateInputCityFormatEditText();
        updateInputTimeFormatEditText();
        updateInputCountryFormatEditText();
        updateInputLevelFormatEditText();

        onClickConfirmedButton();
        onClickDeniedButton();

    }

    private void  updateInputDateFormatEditText() {
        dateEditText.addTextChangedListener(new TextWatcherDate(dateEditText));
    }

    private void updateInputCityFormatEditText() {
        cityEditText.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
    }

    private void updateInputTimeFormatEditText() {
        timeEditText.addTextChangedListener(new TextWatcherChrono(timeEditText));
    }

    private void updateInputLevelFormatEditText() {
        selectLevelCompetition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { level = parent.getItemAtPosition(position).toString(); }
        });
    }

    private void updateInputCountryFormatEditText() {
        countryEditText.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
    }

    private boolean checkInputFormatDate() {
        String text = getDateEditText().getText().toString();
        int textSize = text.length();
        if (textSize == 10) {
            return (0 < Integer.parseInt(text.substring(0, 2)) && Integer.parseInt(text.substring(0, 2)) <= 31)
                    && (0 < Integer.parseInt(text.substring(3, 5)) && Integer.parseInt(text.substring(3, 5)) <= 12)
                    && (1970 < Integer.parseInt(text.substring(6, 10)) && Integer.parseInt(text.substring(6, 10)) <= Calendar.getInstance().get(Calendar.YEAR));
        } else return false;
    }

    public boolean checkInputFormatTime() {
        String text = getTimeEditText().getText().toString();
        int textSize = text.length();
        if (textSize != 0) {
            if (Integer.parseInt(text.replaceAll("[:.]", "")) == 0) return false;
            else if (textSize != 8) {
                for (int i = textSize; i < 8; i++) {
                    if      (8 - i == 3) text = new StringBuilder(text).insert(0, ":").toString();
                    else if (8 - i == 6) text = new StringBuilder(text).insert(0, ".").toString();
                    else                 text = new StringBuilder(text).insert(0, "0").toString();
                }
                time = MarketTimes.fetchTimeToFloat(text);
                return true;
            } else return true;
        } else return false;
    }

    public boolean isEnableConfirmed() {
        boolean checkDate = checkInputFormatDate();
        boolean checkTime = checkInputFormatTime();

        if (checkDate && checkTime && city.length() > 0 && country.length() > 0) {
            dismiss();
            return true;
        } else {
            if (!checkDate)  {
                getDateEditText().getText().clear();
                getDateEditText().setHint("Erreur de Format");
                getDateEditText().setHintTextColor(getLayoutInflater().getContext().getResources().getColor(R.color.redDeep));
            }
            if (!checkTime) {
                getTimeEditText().setHint("Champ vide");
                getTimeEditText().setTextColor(getLayoutInflater().getContext().getResources().getColor(R.color.redDeep));
                getTimeEditText().setHintTextColor(getLayoutInflater().getContext().getResources().getColor(R.color.redDeep));
            }
            if (city.length() == 0) {
                getCityEditText().getText().clear();
                getCityEditText().setHint("Champ vide");
                getCityEditText().setHintTextColor(getLayoutInflater().getContext().getResources().getColor(R.color.redDeep));
            }
            return false;
        }
    }

    private void onClickConfirmedButton() {
        confirmedButton.setOnClickListener(v -> {
            setDate(getDateEditText().getText().toString());
            setCity(getCityEditText().getText().toString());
            setCountry(getCountryEditText().getText().toString());
            setTime(MarketTimes.fetchTimeToFloat(getTimeEditText().getText().toString()));
            checkInputFormatTime();
            isEnableConfirmed();
        });
    }

    private void onClickDeniedButton() {
        getDeniedButton().setOnClickListener(v -> dismiss());
    }

    public void build() {
        show();
        Window window = getWindow();
        if (window != null) window.setLayout((int) (AboutScreen.getWidth(activity) * 0.8), LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private Button getDeniedButton() { return deniedButton; }
    public Button getConfirmedButton() { return confirmedButton; }
    public String getCity() { return city; }
    public String getDate() { return date; }
    public int getDistanceRace() { return distanceRace; }
    public int getSizePool() { return sizePool; }
    public float getTime() { return time; }
    public String getSwim() { return swim; }
    public String getLevel() { return level; }
    public TextView getSubtitleDescription() { return subtitleDescription; }
    public EditText getDateEditText() { return dateEditText; }
    public EditText getCityEditText() { return cityEditText; }
    public EditText getTimeEditText() { return timeEditText; }
    public String getCountry() { return country; }
    public EditText getCountryEditText() { return countryEditText; }

    public void setCity(String city) { this.city = city; }
    public void setDate(String date) { this.date = date; }
    public void setDistanceRace(int distanceRace) { this.distanceRace = distanceRace; }
    public void setSizePool(int sizePool) { this.sizePool = sizePool; }
    public void setTime(float time) { this.time = time; }
    public void setSwim(String swim) { this.swim = swim; }
    public void setCountry(String country) { this.country = country; }
}