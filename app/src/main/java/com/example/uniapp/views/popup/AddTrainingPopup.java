package com.example.uniapp.views.popup;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.uniapp.R;
import com.example.uniapp.models.Race;
import com.example.uniapp.models.Training;
import com.example.uniapp.models.TrainingBlock;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddTrainingPopup extends Dialog implements View.OnClickListener {
    private EditText            dateEditText;
    private EditText            cityEditText;
    private List<Button>        btn_difficulty_stars;
    private Spinner             sizePoolDropdown;
    private EditText            setsEditText;
    private EditText            distanceEditText;
    private Spinner             swimDropdown;
    private Spinner             zoneDropdown;
    private Button              addBlock;
    private Button              btn_denied;
    private Button              btn_confirmed;
    private GridLayout          gridLayout;
    private View                newBlock;

    private int     newSet;
    private int     newDistance;
    private int     newDifficulty;
    private int     newSizePool;
    private int     newZone;
    private String  newSwim;
    private String  newDate;
    private String  newCity;

    private List<Training> allTrainings;
    private List<TrainingBlock> trainingBlockList;
    private int nbBlock;

    public AddTrainingPopup(Activity activity, List<Training> allTrainings) {
        super(activity, R.style.Theme_AppCompat_Dialog);
        setContentView(R.layout.popup_add_training);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        this.allTrainings = allTrainings;

        setupUIElements();
        updateInputDateFormatEditText();
        updateInputCityFormatEditText();
        updateDifficulty();
        configureAndShowSizePoolDropdown();
        configureAndShowSwimDropdown();
        configureAndShowZoneDropdown();
    }

    public void setupUIElements() {
        gridLayout              = (GridLayout)   findViewById(R.id.popup_add_training_block_space);
        dateEditText            = (EditText)     findViewById(R.id.popup_add_training_date_edit_text);
        cityEditText            = (EditText)     findViewById(R.id.popup_add_training_city_edit_text);
        sizePoolDropdown        = (Spinner)      findViewById(R.id.popup_add_training_size_pool_dropdown);
        setsEditText            = (EditText)     findViewById(R.id.rv_popup_add_training_sets_edit_text);
        distanceEditText        = (EditText)     findViewById(R.id.rv_popup_add_training_distance_edit_text);
        swimDropdown            = (Spinner)      findViewById(R.id.rv_popup_add_training_swim_spinner);
        zoneDropdown            = (Spinner)      findViewById(R.id.rv_popup_add_training_zone_spinner);
        addBlock                = (Button)       findViewById(R.id.popup_add_training_add_block_btn);
        btn_denied              = (Button)       findViewById(R.id.popup_add_training_denied_btn);
        btn_confirmed           = (Button)       findViewById(R.id.popup_add_training_confirmed_btn);
        DateFormat dateFormat   = new SimpleDateFormat("dd/MM/yyyy");
        Date today              = Calendar.getInstance().getTime();
        int[] idButtons = {
                R.id.fragment_training_difficulty_star_1,
                R.id.fragment_training_difficulty_star_2,
                R.id.fragment_training_difficulty_star_3,
                R.id.fragment_training_difficulty_star_4,
                R.id.fragment_training_difficulty_star_5
        };
        btn_difficulty_stars = new ArrayList<Button>();
        for (int i = 0; i < idButtons.length; i++) btn_difficulty_stars.add((Button) findViewById(idButtons[i]));
        btn_difficulty_stars.get(0).setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.drawable.ic_radio_button_checked_white_24dp), null, null, null);
        for (int i = 0; i < 5; i++) btn_difficulty_stars.get(i).setOnClickListener(this);
        addBlock.setOnClickListener(this);
        btn_denied.setOnClickListener(this);
        btn_confirmed.setOnClickListener(this);

        ArrayAdapter<CharSequence> levelDropdownAdapter = ArrayAdapter.createFromResource(getContext(), R.array.sizePool, R.layout.dropdown_training_size_pool);
        ArrayAdapter<CharSequence> swimDropdownAdapter = ArrayAdapter.createFromResource(getContext(), R.array.swims, R.layout.dropdown_training_size_pool);
        ArrayAdapter<CharSequence> zoneDropdownAdapter = ArrayAdapter.createFromResource(getContext(), R.array.zones, R.layout.dropdown_training_size_pool);
        levelDropdownAdapter.setDropDownViewResource(R.layout.dropdown_training_size_pool);
        levelDropdownAdapter.setDropDownViewResource(R.layout.dropdown_training_size_pool);
        levelDropdownAdapter.setDropDownViewResource(R.layout.dropdown_training_size_pool);
        sizePoolDropdown.setAdapter(levelDropdownAdapter);
        swimDropdown.setAdapter(swimDropdownAdapter);
        zoneDropdown.setAdapter(zoneDropdownAdapter);


        newDifficulty = 1;
        newSizePool   = 25;
        newDate       = dateFormat.format(today);
        getDateEditText().setText(newDate);
    }

    @Override
    public void onClick(View v) {
             if (v.getTag().equals("difficulty_1"))  newDifficulty = 1;
        else if (v.getTag().equals("difficulty_2"))  newDifficulty = 2;
        else if (v.getTag().equals("difficulty_3"))  newDifficulty = 3;
        else if (v.getTag().equals("difficulty_4"))  newDifficulty = 4;
        else if (v.getTag().equals("difficulty_5"))  newDifficulty = 5;
        else if (v.getTag().equals("addBlock"))      addBlockElement();
        else if (v.getTag().equals("btn_denied"))    dismiss();
        else if (v.getTag().equals("btn_confirmed")) addTraining();
        updateDifficulty();
    }

    private void updateDifficulty() {
        for (int i = 0; i < btn_difficulty_stars.size(); i++) btn_difficulty_stars.get(i).setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.drawable.ic_radio_button_unchecked_white_24dp), null, null, null);
        for (int i = 0; i < newDifficulty; i++)               btn_difficulty_stars.get(i).setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.drawable.ic_radio_button_checked_white_24dp), null, null, null);
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

    private boolean checkInputFormatDate() {
        String text = getDateEditText().getText().toString();
        int textSize = text.length();
        if (textSize == 10) {
            return (0 < Integer.parseInt(text.substring(0, 2)) && Integer.parseInt(text.substring(0, 2)) <= 31)
                    && (0 < Integer.parseInt(text.substring(3, 5)) && Integer.parseInt(text.substring(3, 5)) <= 12)
                    && (1970 < Integer.parseInt(text.substring(6, 10)) && Integer.parseInt(text.substring(6, 10)) <= Calendar.getInstance().get(Calendar.YEAR));
        } else return false;
    }

    private void updateInputCityFormatEditText() {
        cityEditText.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
    }

    private void configureAndShowSizePoolDropdown() {
        sizePoolDropdown.setPopupBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.sizePool, R.layout.dropdown_training_size_pool);
        adapter.setDropDownViewResource(R.layout.dropdown_training_size_pool);
        sizePoolDropdown.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        /*sizePoolDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sizePool = parent.getItemAtPosition(position).toString();
                sizePool = sizePool.replaceAll("[a-zA-Z ]", "");
                newSizePool = Integer.parseInt(sizePool);
            }
        });*/
    }

    private void configureAndShowSwimDropdown() {
        swimDropdown.setPopupBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.swims, R.layout.dropdown_training_size_pool);
        adapter.setDropDownViewResource(R.layout.dropdown_training_size_pool);
        swimDropdown.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        /*swimDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String swim = parent.getItemAtPosition(position).toString();
                swim = Race.convertSwimFromFrenchToEnglish(swim);
                newSwim = swim;
            }
        });*/
    }

    private void configureAndShowZoneDropdown() {
        zoneDropdown.setPopupBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.zones, R.layout.dropdown_training_size_pool);
        adapter.setDropDownViewResource(R.layout.dropdown_training_size_pool);
        zoneDropdown.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        /*zoneDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String zone = parent.getItemAtPosition(position).toString();
                zone = zone.replace("Z", "");
                newZone = Integer.parseInt(zone);
            }
        });*/
    }

    public void addTraining() {
        System.out.println("swims      : " + newSizePool);
        System.out.println("sets       : " + newSet);
        System.out.println("zones      : " + newZone);
        System.out.println("distance   : " + newDistance);
        System.out.println("date       : " + newDate);
        System.out.println("city       : " + newCity);
        System.out.println("difficulty : " + newDifficulty);
        System.out.println("sizePool   : " + newSizePool);
    }

    private void addBlockElement() {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        newBlock = getLayoutInflater().inflate(R.layout.popup_add_training_time, (ViewGroup) getWindow().getDecorView(), false);

        TextView nbSets   = (TextView) newBlock.findViewById(R.id.rv_popup_add_training_sets_edit_text);
        TextView distance = (TextView) newBlock.findViewById((R.id.rv_popup_add_training_distance_edit_text));
        TextView swim     = (TextView) newBlock.findViewById(R.id.rv_popup_add_training_swim_spinner);
        TextView zone     = (TextView) newBlock.findViewById(R.id.rv_popup_add_training_zone_spinner);
        nbSets.setText(setsEditText.getText());
        distance.setText(distanceEditText.getText());
        swim.setText(swimDropdown.getSelectedItem().toString());
        zone.setText(zoneDropdown.getSelectedItem().toString());

        newSizePool = Integer.parseInt(sizePoolDropdown.getSelectedItem().toString().replaceAll("[a-zA-Z ]", ""));
        newSet      = Integer.parseInt(setsEditText.getText().toString().replaceAll("[a-zA-Z ]", ""));
        newDistance = Integer.parseInt(distanceEditText.getText().toString().replaceAll("[a-zA-Z ]", ""));
        newZone     = Integer.parseInt(zoneDropdown.getSelectedItem().toString().replaceAll("[a-zA-Z ]", ""));
        newSwim     = swimDropdown.getSelectedItem().toString();
        newCity     = getCityEditText().getText().toString();

        addTraining();
        gridLayout.addView(newBlock, nbBlock);
        nbBlock++;
    }

    public void build() {
        show();
        Window window = getWindow();
        if (window != null) window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, 2225);
    }

    public EditText getDateEditText() { return dateEditText; }
    public EditText getCityEditText() { return cityEditText; }
    public void setDateEditText(EditText dateEditText) { this.dateEditText = dateEditText; }
    public void setCityEditText(EditText cityEditText) { this.cityEditText = cityEditText; }

}