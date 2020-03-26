package com.example.uniapp.models;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.uniapp.R;

import java.util.Calendar;

public class CustomPopup extends Dialog {
    private String title;
    private String city;
    private String date;
    private int distanceRace;
    private int sizePool;
    private String time;
    private String swim;
    private int pointFFN;
    private String level;
    private boolean checkDate;

    private TextView titleTextView;
    private TextView subtitleDescription;
    private EditText dateEditText;
    private EditText cityEditText;
    private EditText timeEditText;
    private EditText levelEditText;
    private Button   deniedButton;
    private Button   confirmedButton;

    public CustomPopup(Activity activity) {
        super(activity, R.style.Theme_AppCompat_Dialog);
        setContentView(R.layout.fragment_competition_add_race_popup);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        checkDate = false;
        city      = "MA VILLE";
        title     = "T I T L E";
        titleTextView       = (TextView) findViewById(R.id.fragment_competition_add_race_popup_title);
        subtitleDescription = (TextView) findViewById(R.id.fragment_competition_add_race_popup_swim_description);
        dateEditText        = (EditText) findViewById(R.id.race_popup_edit_text_date);
        cityEditText        = (EditText) findViewById(R.id.race_popup_edit_text_city);
        timeEditText        = (EditText) findViewById(R.id.race_popup_edit_text_time);
        levelEditText       = (EditText) findViewById(R.id.race_popup_edit_text_level);
        deniedButton        = (Button)   findViewById(R.id.fragment_competition_add_race_popup_denied);
        confirmedButton     = (Button)   findViewById(R.id.fragment_competition_add_race_popup_confirmed);
        confirmedButton.setTextColor(activity.getResources().getColor(R.color.colorText));

        updateDateEditText();
        updateCityEditText();
        updateTimeEditText();
        updateLevelEditText();
    }

    public void  updateDateEditText() {
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

    public void updateCityEditText() {
        cityEditText.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
    }

    public void updateTimeEditText() {
        timeEditText.addTextChangedListener(new TextWatcher() {
            boolean isChanged = false;
            String newStr;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { isChanged = false;  }
            @Override
            public void afterTextChanged(Editable s) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                timeEditText.removeTextChangedListener(this);
                String text = timeEditText.getText().toString().replaceAll("[:.]", "");
                int textSize = text.length();

                if (textSize >= 5 && isChanged == false) {
                    //011055
                    //01:10.55
                    text = new StringBuilder(text).insert(textSize - 2, ".").insert(textSize - 4, ":").toString();
                    newStr = text;
                    timeEditText.setText(newStr);
                    timeEditText.setSelection(timeEditText.length());
                    isChanged = true;
                } else if (textSize > 2 && isChanged == false) {
                    System.out.println("text : " + text + " | size : "+  textSize);
                    text = new StringBuilder(text).insert(textSize - 2, ".").toString();
                    System.out.println("text : " + text + " | size : "+  textSize + "\n");
                    newStr = text;
                    timeEditText.setText(newStr);
                    timeEditText.setSelection(timeEditText.length());
                    isChanged = true;
                }
                timeEditText.addTextChangedListener(this);
            }
        });
    }

    public void updateLevelEditText() {
        levelEditText.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
    }

    public void checkInputFormatDate() {
        String text = getDateEditText().getText().toString();
        int textSize = text.length();
        if (textSize == 10) {
            if (
                    (0 < Integer.parseInt(text.substring(0, 2)) && Integer.parseInt(text.substring(0, 2)) <= 31)
                            && (0 < Integer.parseInt(text.substring(3, 5)) &&  Integer.parseInt(text.substring(3, 5)) <= 12)
                            && (1970 < Integer.parseInt(text.substring(6, 10)) && Integer.parseInt(text.substring(6, 10)) <= Calendar.getInstance().get(Calendar.YEAR))
            ) {
                checkDate = true;
                // customPopup.getConfirmedButton().setEnabled(true);
                // customPopup.getConfirmedButton().setTextColor(view.getResources().getColor(RaceTime.getCurrentColor(swim)));
            } else {
                checkDate = false;
                // customPopup.getConfirmedButton().setEnabled(false);
                // customPopup.getConfirmedButton().setTextColor(view.getResources().getColor(R.color.colorText));
            }
        } else checkDate = false;
    }

    public void checkInputFormatTime() {
        /*String text = getTimeEditText().getText().toString();
        int textSize = text.length();
        if (textSize != 8) {
            for (int i = textSize; i < 8; i++) {
                if      (8 - i == 3) text = new StringBuilder(text).insert(0, ":").toString();
                else if (8 - i == 5) text = new StringBuilder(text).insert(0, ".").toString();
                else                 text = new StringBuilder(text).insert(0, "0").toString();
            }
        }
        time = text;*/
    }

    public void isEnableConfirmed() {
        /*if (checkDate == true && city.length() > 0 && time.length() > 0) {
            System.out.println("date  : "+  date);
            System.out.println("city  : "+  city);
            System.out.println("time  : "+  time);
            System.out.println("level : "+  level);
        }*/
    }

    public Button getDeniedButton() { return deniedButton; }
    public Button getConfirmedButton() { return confirmedButton; }
    public String getTitle() { return title; }
    public String getCity() { return city; }
    public String getDate() { return date; }
    public int getDistanceRace() { return distanceRace; }
    public int getSizePool() { return sizePool; }
    public String getTime() { return time; }
    public String getSwim() { return swim; }
    public int getPointFFN() { return pointFFN; }
    public String getLevel() { return level; }
    public TextView getTitleTextView() { return titleTextView; }
    public TextView getSubtitleDescription() { return subtitleDescription; }
    public EditText getDateEditText() { return dateEditText; }
    public EditText getCityEditText() { return cityEditText; }
    public EditText getTimeEditText() { return timeEditText; }
    public EditText getLevelEditText() { return levelEditText; }
    public boolean isCheckDate() { return checkDate; }

    public void setDateEditText(EditText dateEditText) { this.dateEditText = dateEditText; }
    public void setCityEditText(EditText cityEditText) { this.cityEditText = cityEditText; }
    public void setTimeEditText(EditText timeEditText) { this.timeEditText = timeEditText; }
    public void setLevelEditText(EditText levelEditText) { this.levelEditText = levelEditText; }
    public void setTitle(String title) { this.title = title; }
    public void setCity(String city) { this.city = city; }
    public void setDate(String date) { this.date = date; }
    public void setDistanceRace(int distanceRace) { this.distanceRace = distanceRace; }
    public void setSizePool(int sizePool) { this.sizePool = sizePool; }
    public void setTime(String time) { this.time = time; }
    public void setSwim(String swim) { this.swim = swim; }
    public void setPointFFN(int pointFFN) { this.pointFFN = pointFFN; }
    public void setLevel(String level) { this.level = level; }
    public void setTitleTextView(TextView titleTextView) { this.titleTextView = titleTextView; }
    public void setSubtitleDescription(TextView subtitleDescription) { this.subtitleDescription = subtitleDescription; }
    public void setCheckDate(boolean checkDate) { this.checkDate = checkDate; }

    public void build() {
        show();
        Window window = getWindow();
        window.setLayout(900, 1500);
        titleTextView.setText(title);
    }
}
