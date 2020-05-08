package com.example.uniapp.models.textwatcher;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class TextWatcherDate implements TextWatcher {
    private EditText editText;

    public TextWatcherDate(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
    @Override
    public void afterTextChanged(Editable s) { }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String text = editText.getText().toString();
        int textSize = text.length();

        if ((textSize == 3 && text.charAt(textSize - 1) != '/') || (textSize == 6 && text.charAt(textSize - 1) != '/')) {
            editText.setText(new StringBuilder(text).insert(textSize - 1, "/").toString());
            editText.setSelection(editText.getText().length());
        }
    }
}
