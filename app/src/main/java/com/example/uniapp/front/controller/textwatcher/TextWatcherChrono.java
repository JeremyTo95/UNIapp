package com.example.uniapp.front.controller.textwatcher;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class TextWatcherChrono implements TextWatcher {
    private EditText editText;

    public TextWatcherChrono(EditText editText) {
        this.editText = editText;
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
    @Override
    public void afterTextChanged(Editable s) { }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        editText.removeTextChangedListener(this);
        int timeInt = Integer.parseInt(editText.getText().toString().replaceAll("[:.]", ""));
        String text = String.valueOf(timeInt);
        int textSize = text.length();

        if (textSize >= 5) {
            text = new StringBuilder(text).insert(textSize - 2, ".").insert(textSize - 4, ":").toString();
            editText.setText(text);
            editText.setSelection(text.length());
        } else if (textSize > 2) {
            text = new StringBuilder(text).insert(textSize - 2, ".").toString();
            editText.setText(text);
            editText.setSelection(text.length());
        } else {
            for (int i = 0; i < 3 - textSize; i++) text = new StringBuilder(text).insert(i, "0").toString();
            text = new StringBuilder(text).insert(text.length() - 2, ".").toString();
            editText.setText(text);
            editText.setSelection(text.length());
        }
        editText.addTextChangedListener(this);
    }
}
