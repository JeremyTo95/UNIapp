package com.example.uniapp.front.view.popup;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.uniapp.back.room.RoomDataBase;
import com.example.uniapp.front.controller.global.AboutScreen;
import com.example.uniapp.front.model.market.MarketSwim;
import com.example.uniapp.R;
import com.example.uniapp.front.controller.textwatcher.TextWatcherDate;
import com.example.uniapp.front.controller.textwatcher.TextWatcherDistance;
import com.example.uniapp.front.model.data.TrainingBlock;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    private RoomDataBase        roomDataBase;
    private Activity            activity;
    private List<TrainingBlock> trainingBlockList;
    private int                 nbBlock;

    public AddTrainingPopup(Activity activity) {
        super(activity, R.style.Theme_AppCompat_Dialog);
        setContentView(R.layout.popup_add_training);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.activity     = activity;
        roomDataBase = RoomDataBase.getDatabase(activity.getApplicationContext());

        setupUIElements();
        updateInputDateFormatEditText();
        updateInputCityFormatEditText();
        updateInputSetFormatEditText();
        updateInputDistanceFormatEditText();
        updateDifficulty();
        configureAndShowSizePoolDropdown();
        configureAndShowSwimDropdown();
        configureAndShowZoneDropdown();
    }

    public void setupUIElements() {
        gridLayout              = (GridLayout) findViewById(R.id.popup_add_training_block_space);
        dateEditText            = (EditText)   findViewById(R.id.popup_add_training_date_edit_text);
        cityEditText            = (EditText)   findViewById(R.id.popup_add_training_city_edit_text);
        sizePoolDropdown        = (Spinner)    findViewById(R.id.popup_add_training_size_pool_dropdown);
        setsEditText            = (EditText)   findViewById(R.id.rv_popup_add_training_sets_edit_text);
        distanceEditText        = (EditText)   findViewById(R.id.rv_popup_add_training_distance_edit_text);
        swimDropdown            = (Spinner)    findViewById(R.id.rv_popup_add_training_swim_spinner);
        zoneDropdown            = (Spinner)    findViewById(R.id.rv_popup_add_training_zone_spinner);
        addBlock                = (Button)     findViewById(R.id.popup_add_training_add_block_btn);
        btn_denied              = (Button)     findViewById(R.id.popup_add_training_denied_btn);
        btn_confirmed           = (Button)     findViewById(R.id.popup_add_training_confirmed_btn);
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
        updateDifficulty();
        for (int i = 0; i < 5; i++) btn_difficulty_stars.get(i).setOnClickListener(this);
        addBlock.setOnClickListener(this);
        addBlock.setEnabled(false);
        btn_denied.setOnClickListener(this);
        btn_confirmed.setOnClickListener(this);

        ArrayAdapter<CharSequence> levelDropdownAdapter = ArrayAdapter.createFromResource(getContext(), R.array.sizePool, R.layout.dropdown_item_auto);
        ArrayAdapter<CharSequence> swimDropdownAdapter  = ArrayAdapter.createFromResource(getContext(), R.array.swims, R.layout.dropdown_item_auto);
        ArrayAdapter<CharSequence> zoneDropdownAdapter  = ArrayAdapter.createFromResource(getContext(), R.array.zones, R.layout.dropdown_item_auto);
        levelDropdownAdapter.setDropDownViewResource(R.layout.dropdown_all_items);
        swimDropdownAdapter.setDropDownViewResource(R.layout.dropdown_all_items);
        zoneDropdownAdapter.setDropDownViewResource(R.layout.dropdown_all_items);
        sizePoolDropdown.setAdapter(levelDropdownAdapter);
        swimDropdown.setAdapter(swimDropdownAdapter);
        zoneDropdown.setAdapter(zoneDropdownAdapter);

        trainingBlockList = new ArrayList<TrainingBlock>();
        if (roomDataBase.userDAO().getUser() != null)
            cityEditText.setText(roomDataBase.userDAO().getUser().getCityTraining());
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
        else if (v.getTag().equals("btn_denied"))    dismiss();
        else if (v.getTag().equals("addBlock")) {
            addBlockElement();
            hideKeybaord(v);
        }
        updateDifficulty();
    }

    private void updateDifficulty() {
        if (AboutScreen.isNightMode(activity)) {
            for (int i = 0; i < newDifficulty; i++) btn_difficulty_stars.get(i).setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.drawable.ic_radio_button_checked_white_24dp), null, null, null);
            for (int i = newDifficulty; i < btn_difficulty_stars.size(); i++) btn_difficulty_stars.get(i).setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.drawable.ic_radio_button_unchecked_white_24dp), null, null, null);
        } else {
            for (int i = 0; i < newDifficulty; i++) btn_difficulty_stars.get(i).setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.drawable.ic_radio_button_checked_black_24dp), null, null, null);
            for (int i = newDifficulty; i < btn_difficulty_stars.size(); i++) btn_difficulty_stars.get(i).setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.drawable.ic_radio_button_unchecked_black_24dp), null, null, null);
        }
    }

    private void  updateInputDateFormatEditText() {
        dateEditText.addTextChangedListener(new TextWatcherDate(dateEditText));
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

    public boolean isEnabled() {
        if (checkInputFormatDate() && cityEditText.getText().toString().length() != 0 && trainingBlockList.size() > 0)
            return true;
        else {
            if (!checkInputFormatDate()) dateEditText.setTextColor(getContext().getResources().getColor(R.color.redDeep));
            if (cityEditText.getText().toString().length() == 0) {
                cityEditText.setTextColor(getContext().getResources().getColor(R.color.redDeep));
                cityEditText.setHintTextColor(getContext().getResources().getColor(R.color.redDeep));
            }
            if (trainingBlockList.size() == 0) {
                addBlock.setBackground(getContext().getResources().getDrawable(R.drawable.sh_gradient_red));
                setsEditText.setHintTextColor(getContext().getResources().getColor(R.color.redDeep));
                distanceEditText.setHintTextColor(getContext().getResources().getColor(R.color.redDeep));;
            }
            return false;
        }
    }

    private void updateInputCityFormatEditText() {
        cityEditText.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
    }

    private void configureAndShowSizePoolDropdown() {
        sizePoolDropdown.setPopupBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.sizePool, R.layout.dropdown_item_auto);
        adapter.setDropDownViewResource(R.layout.dropdown_all_items);
        sizePoolDropdown.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void updateInputSetFormatEditText() {
        setsEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setsEditText.setText("");
                if (distanceEditText.getText().toString().length() > 0) addBlock.setEnabled(true);
            }
        });
    }

    private void updateInputDistanceFormatEditText() {
        distanceEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distanceEditText.setText("");
            }
        });
        distanceEditText.addTextChangedListener(new TextWatcherDistance(distanceEditText));
        distanceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {addBlock.setEnabled(true); }
        });
    }

    private void configureAndShowSwimDropdown() {
        swimDropdown.setPopupBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.swims, R.layout.dropdown_item_auto);
        adapter.setDropDownViewResource(R.layout.dropdown_all_items);
        swimDropdown.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void configureAndShowZoneDropdown() {
        zoneDropdown.setPopupBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.zones, R.layout.dropdown_item_auto);
        adapter.setDropDownViewResource(R.layout.dropdown_all_items);
        zoneDropdown.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void addBlockElement() {
        newBlock = getLayoutInflater().inflate(R.layout.popup_add_training_time, (ViewGroup) getWindow().getDecorView(), false);

        TextView nbSets        = (TextView) newBlock.findViewById(R.id.rv_popup_add_training_sets_edit_text);
        TextView distance      = (TextView) newBlock.findViewById((R.id.rv_popup_add_training_distance_edit_text));
        TextView swim          = (TextView) newBlock.findViewById(R.id.rv_popup_add_training_swim_spinner);
        TextView zone          = (TextView) newBlock.findViewById(R.id.rv_popup_add_training_zone_spinner);
        final Button deleteBtn = (Button)   newBlock.findViewById(R.id.rv_popup_training_delete_btn);
        distance.setText(distanceEditText.getText());
        swim.setText(swimDropdown.getSelectedItem().toString());
        zone.setText(zoneDropdown.getSelectedItem().toString());

        System.out.println(AboutScreen.isNightMode(activity));
        if (AboutScreen.isNightMode(activity))
            deleteBtn.setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.drawable.ic_clear_white_24dp), null, null, null);
        else
            deleteBtn.setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.drawable.ic_clear_black_24dp), null, null, null);
        nbSets.setText(setsEditText.getText());

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int indexBlock = gridLayout.indexOfChild((View) deleteBtn.getParent());
                trainingBlockList.remove(indexBlock);
                gridLayout.removeViewAt(indexBlock);
                nbBlock--;
            }
        });

        newSizePool = Integer.parseInt(sizePoolDropdown.getSelectedItem().toString().replaceAll("[a-zA-Z ]", ""));
        newSet      = Integer.parseInt(setsEditText.getText().toString().replaceAll("[a-zA-Z ]", ""));
        newDistance = Integer.parseInt(distanceEditText.getText().toString().replaceAll("[a-zA-Z ]", ""));
        newZone     = Integer.parseInt(zoneDropdown.getSelectedItem().toString().replaceAll("[a-zA-Z ]", ""));
        newSwim     = swimDropdown.getSelectedItem().toString();
        newCity     = getCityEditText().getText().toString();

        trainingBlockList.add(new TrainingBlock(newSet, MarketSwim.convertSwimFromFrenchToEnglish(newSwim), newDistance, initEmptyTime(), newZone));
        gridLayout.addView(newBlock, nbBlock);
        nbBlock++;
    }

    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }

    public void build() {
        show();
        Window window = getWindow();
        if (window != null) window.setLayout(AboutScreen.getWidth(activity), AboutScreen.getHeight(activity) - 100);
    }

    public List<Float> initEmptyTime() {
        List<Float> allTimes = new ArrayList<Float>();
        for (int i = 0; i < newSet; i++) {
            allTimes.add(0.0f);
        }
        return allTimes;
    }

    public EditText getDateEditText() { return dateEditText; }
    public EditText getCityEditText() { return cityEditText; }
    public Button getBtn_confirmed() { return btn_confirmed; }
    public View getNewBlock() { return newBlock; }
    public int getNewSet() { return newSet; }
    public int getNewDistance() { return newDistance; }
    public int getNewDifficulty() { return newDifficulty; }
    public int getNewSizePool() { return newSizePool; }
    public int getNewZone() { return newZone; }
    public String getNewSwim() { return newSwim; }
    public String getNewDate() { return newDate; }
    public String getNewCity() { return newCity; }
    public List<TrainingBlock> getTrainingBlockList() { return trainingBlockList; }
    public void setDateEditText(EditText dateEditText) { this.dateEditText = dateEditText; }
    public void setCityEditText(EditText cityEditText) { this.cityEditText = cityEditText; }
    public void setBtn_confirmed(Button btn_confirmed) { this.btn_confirmed = btn_confirmed; }

}
