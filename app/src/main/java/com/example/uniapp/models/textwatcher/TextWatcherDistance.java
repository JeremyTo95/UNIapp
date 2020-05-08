package com.example.uniapp.models.textwatcher;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class TextWatcherDistance implements TextWatcher {
    private EditText editText;

    public TextWatcherDistance(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
    @Override
    public void afterTextChanged(Editable s) { }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        editText.removeTextChangedListener(this);
        if (editText.getText().length() >= 1 && !editText.getText().toString().equals("m")) {
            String text = editText.getText().toString().replaceAll("[a-zA-Z ]", "") + "m";
            editText.setText(text);
            editText.setSelection(text.length() - 1);
        } else if (editText.getText().toString().equals("m")) {
            editText.setText("");
        }
        editText.addTextChangedListener(this);
    }
}
