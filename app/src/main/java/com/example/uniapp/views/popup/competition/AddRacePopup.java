package com.example.uniapp.views.popup.competition;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.uniapp.R;
import com.example.uniapp.models.MarketTimes;
import com.example.uniapp.models.database.dao.race.Race;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddRacePopup extends Dialog {
    private String title;
    private String city;
    private String date;
    private int distanceRace;
    private int sizePool;
    private float time;
    private String swim;
    private int pointFFN;
    private String level;
    private String country;

    private TextView titleTextView;
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
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        DateFormat dateFormat  = new SimpleDateFormat("dd/MM/yyyy");
        Date today             = Calendar.getInstance().getTime();
        date                   = dateFormat.format(today);
        titleTextView          = (TextView) findViewById(R.id.fragment_competition_add_race_popup_title);
        subtitleDescription    = (TextView) findViewById(R.id.fragment_competition_add_race_popup_swim_description);
        dateEditText           = (EditText) findViewById(R.id.race_popup_edit_text_date);
        cityEditText           = (EditText) findViewById(R.id.race_popup_edit_text_city);
        timeEditText           = (EditText) findViewById(R.id.race_popup_edit_text_time);
        selectLevelCompetition = (Spinner)  findViewById(R.id.race_popup_spinner_level);
        countryEditText        = (EditText) findViewById(R.id.race_popup_edit_text_country);
        deniedButton           = (Button)   findViewById(R.id.fragment_competition_add_race_popup_denied);
        confirmedButton        = (Button)   findViewById(R.id.fragment_competition_add_race_popup_confirmed);
        confirmedButton.setTextColor(activity.getResources().getColor(R.color.colorText));

        dateEditText.setText(date);

        ArrayAdapter<CharSequence> levelDropdownAdapter = ArrayAdapter.createFromResource(getContext(), R.array.competition_level, R.layout.dropdown_item);
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
        dateEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) { checkInputFormatDate(); }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = dateEditText.getText().toString();
                int textSize = text.length();

                if ((textSize == 3 && text.charAt(textSize - 1) != '/') || (textSize == 6 && text.charAt(textSize - 1) != '/')) {
                    dateEditText.setText(new StringBuilder(text).insert(textSize - 1, "/").toString());
                    dateEditText.setSelection(dateEditText.getText().length());
                }
            }
        });
    }

    private void updateInputCityFormatEditText() {
        cityEditText.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
    }

    private void updateInputTimeFormatEditText() {
        timeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                timeEditText.removeTextChangedListener(this);
                int timeInt = Integer.parseInt(timeEditText.getText().toString().replaceAll("[:.]", "")); // permet de retirer les premiers zéros du String
                String text = String.valueOf(timeInt);
                int textSize = text.length();

                if (textSize >= 5) {
                    text = new StringBuilder(text).insert(textSize - 2, ".").insert(textSize - 4, ":").toString();
                    timeEditText.setText(text);
                    timeEditText.setSelection(text.length());
                } else if (textSize > 2) {
                    text = new StringBuilder(text).insert(textSize - 2, ".").toString();
                    timeEditText.setText(text);
                    timeEditText.setSelection(text.length());
                } else {
                    for (int i = 0; i < 3 - textSize; i++) {
                        text = new StringBuilder(text).insert(i, "0").toString();
                    }
                    text = new StringBuilder(text).insert(text.length() - 2, ".").toString();
                    timeEditText.setText(text);
                    timeEditText.setSelection(text.length());
                }
                timeEditText.addTextChangedListener(this);
            }
        });
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
            if (Integer.parseInt(text.replaceAll("[:.]", "").toString()) == 0) return false;
            else if (textSize != 8 && textSize != 0) {
                for (int i = textSize; i < 8; i++) {
                    if      (8 - i == 3) text = new StringBuilder(text).insert(0, ":").toString();
                    else if (8 - i == 6) text = new StringBuilder(text).insert(0, ".").toString();
                    else                 text = new StringBuilder(text).insert(0, "0").toString();
                }
                time = MarketTimes.fetchTimeToFloat(text);
                return true;
            } else return false;
        } else return false;
    }

    public boolean isEnableConfirmed() {
        boolean checkDate = checkInputFormatDate();
        boolean checkTime = checkInputFormatTime();

        if (checkDate && checkTime && city.length() > 0 && country.length() > 0) {
            System.out.println("date    : "+  date);
            System.out.println("city    : "+  city);
            System.out.println("time    : "+  time);
            System.out.println("level   : "+  level);
            System.out.println("country : "+  level);
            dismiss();
            return true;
        } else {
            if (!checkDate)  {
                getDateEditText().getText().clear();
                getDateEditText().setHint("Erreur de Format");
                getDateEditText().setHintTextColor(getLayoutInflater().getContext().getResources().getColor(R.color.redDeep));
            }
            if (!checkTime) {
                getTimeEditText().getText().toString().replaceAll("[.:0-9]", "");
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
        confirmedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(getDateEditText().getText().toString());
                setCity(getCityEditText().getText().toString());
                setCountry(getCountryEditText().getText().toString());
                setTime(MarketTimes.fetchTimeToFloat(getTimeEditText().getText().toString()));
                checkInputFormatTime();
                isEnableConfirmed();
            }
        });
    }

    private void onClickDeniedButton() {
        getDeniedButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void build() {
        show();
        Window window = getWindow();
        if (window != null) window.setLayout(900, 1500);
        titleTextView.setText(title);
    }

    public Button getDeniedButton() { return deniedButton; }
    public Button getConfirmedButton() { return confirmedButton; }
    public String getTitle() { return title; }
    public String getCity() { return city; }
    public String getDate() { return date; }
    public int getDistanceRace() { return distanceRace; }
    public int getSizePool() { return sizePool; }
    public float getTime() { return time; }
    public String getSwim() { return swim; }
    public int getPointFFN() { return pointFFN; }
    public String getLevel() { return level; }
    public TextView getTitleTextView() { return titleTextView; }
    public TextView getSubtitleDescription() { return subtitleDescription; }
    public EditText getDateEditText() { return dateEditText; }
    public EditText getCityEditText() { return cityEditText; }
    public EditText getTimeEditText() { return timeEditText; }
    //public EditText getLevelEditText() { return levelEditText; }
    public Spinner getSelectLevelCompetition() { return selectLevelCompetition; }
    public String getCountry() { return country; }
    public EditText getCountryEditText() { return countryEditText; }

    public void setDateEditText(EditText dateEditText) { this.dateEditText = dateEditText; }
    public void setCityEditText(EditText cityEditText) { this.cityEditText = cityEditText; }
    public void setTimeEditText(EditText timeEditText) { this.timeEditText = timeEditText; }
    //public void setLevelEditText(EditText levelEditText) { this.levelEditText = levelEditText; }
    public void setTitle(String title) { this.title = title; }
    public void setCity(String city) { this.city = city; }
    public void setDate(String date) { this.date = date; }
    public void setDistanceRace(int distanceRace) { this.distanceRace = distanceRace; }
    public void setSizePool(int sizePool) { this.sizePool = sizePool; }
    public void setTime(float time) { this.time = time; }
    public void setSwim(String swim) { this.swim = swim; }
    public void setPointFFN(int pointFFN) { this.pointFFN = pointFFN; }
    public void setLevel(String level) { this.level = level; }
    public void setTitleTextView(TextView titleTextView) { this.titleTextView = titleTextView; }
    public void setSubtitleDescription(TextView subtitleDescription) { this.subtitleDescription = subtitleDescription; }
    public void setSelectLevelCompetition(Spinner selectLevelCompetition) { this.selectLevelCompetition = selectLevelCompetition; }
    public void setCountry(String country) { this.country = country; }
    public void setCountryEditText(EditText countryEditText) { this.countryEditText = countryEditText; }
}