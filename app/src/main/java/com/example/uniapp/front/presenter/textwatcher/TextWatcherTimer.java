package com.example.uniapp.front.presenter.textwatcher;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class TextWatcherTimer implements TextWatcher {
    private EditText editText;

    public TextWatcherTimer(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
    @Override
    public void afterTextChanged(Editable s) { }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String strValue;
        editText.removeTextChangedListener(this);
        strValue = editText.getText().toString().replaceAll(":", "");
        if (strValue.length() > 2) strValue = new StringBuilder(strValue).insert(strValue.length() - 2, ":").toString();
        editText.setText(strValue);
        editText.setSelection(strValue.length());
        editText.addTextChangedListener(this);
    }
}
