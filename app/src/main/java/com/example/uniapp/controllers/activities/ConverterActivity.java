package com.example.uniapp.controllers.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uniapp.R;
import com.example.uniapp.models.MarketTimes;
import com.example.uniapp.views.AboutScreen;

public class ConverterActivity extends AppCompatActivity implements View.OnClickListener {
    private String inputTimeStr;
    private String outputTimeStr;
    private String zoneSelectedStr;

    private LinearLayout linearLayout;
    private Spinner  zoneSelected;
    private EditText inputTimeET;
    private Button   converterBtn;
    private TextView outputTimeTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AboutScreen.setupThemeApp(this);
        setContentView(R.layout.activity_converter);
        // this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        setupUIElements();
        updateZone();
    }

    private void setupUIElements() {
        linearLayout     = (LinearLayout) findViewById(R.id.activity_converter_main_layout);
        zoneSelected     = (Spinner)      findViewById(R.id.activity_converter_zone_selected);
        inputTimeET      = (EditText)     findViewById(R.id.activity_converter_input_time);
        converterBtn     = (Button)       findViewById(R.id.activity_converter_convert_btn);
        outputTimeTV     = (TextView)     findViewById(R.id.activity_converter_output_time);
        converterBtn.setOnClickListener(this);
        inputTimeET.setOnClickListener(this);
        updateInputTime();

        ArrayAdapter<CharSequence> zoneSelectedAdapter;
        if (AboutScreen.isNightMode(this)) zoneSelectedAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.zones, R.layout.dropdown_big_item_dark);
        else zoneSelectedAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.zones, R.layout.dropdown_big_item_light);
        zoneSelectedAdapter.setDropDownViewResource(R.layout.dropdown_all_big_items);
        zoneSelected.setAdapter(zoneSelectedAdapter);

        zoneSelectedStr = "Z1";
    }

    @Override
    public void onClick(View v) {
        if (v.getTag().equals("inputTime")) inputTimeET.clearComposingText();
        if (v.getTag().equals("converterButton")) {
            convertTime();
            hideKeybaord(v);
        }
        inputTimeET.onEditorAction(EditorInfo.IME_ACTION_DONE);
        //InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.hideSoftInputFromWindow(linearLayout.getWindowToken(), 0);
        // this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SO);
    }

    private void updateInputTime() {
        inputTimeET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputTimeET.removeTextChangedListener(this);
                int timeInt = Integer.parseInt(inputTimeET.getText().toString().replaceAll("[:.]", "")); // permet de retirer les premiers zéros du String
                String text = String.valueOf(timeInt);
                int textSize = text.length();

                if (textSize >= 5) {
                    text = new StringBuilder(text).insert(textSize - 2, ".").insert(textSize - 4, ":").toString();
                    inputTimeET.setText(text);
                    inputTimeET.setSelection(text.length());
                } else if (textSize > 2) {
                    text = new StringBuilder(text).insert(textSize - 2, ".").toString();
                    inputTimeET.setText(text);
                    inputTimeET.setSelection(text.length());
                } else {
                    for (int i = 0; i < 3 - textSize; i++) {
                        text = new StringBuilder(text).insert(i, "0").toString();
                    }
                    text = new StringBuilder(text).insert(text.length() - 2, ".").toString();
                    inputTimeET.setText(text);
                    inputTimeET.setSelection(text.length());
                }
                inputTimeET.addTextChangedListener(this);
            }
        });
    }

    private void updateZone() {
        zoneSelected.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { zoneSelectedStr = parent.getItemAtPosition(position).toString(); }
        });
    }

    private void convertTime() {
        if (inputTimeET.getText().toString().length() == 0) {
            inputTimeET.setHintTextColor(getResources().getColor(R.color.redDeep));
        } else {
            Toast.makeText(getApplicationContext(), "Conversion terminé", Toast.LENGTH_SHORT).show();
            inputTimeStr = MarketTimes.convertTimeToFormat(inputTimeET.getText().toString());
            outputTimeStr = MarketTimes.convertCompetitionTimeToZoneTime(MarketTimes.fetchTimeToFloat(inputTimeStr), Integer.parseInt(zoneSelectedStr.substring(1)));
            System.out.println("input : " + inputTimeStr + " | output : " + outputTimeStr);
            outputTimeTV.setText("Temps " + zoneSelectedStr + " : " + outputTimeStr);
            outputTimeTV.setVisibility(View.VISIBLE);
        }
    }

    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }

    /*@Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) AboutScreen.hideNavigationBar(this);
    }*/
}
