package com.example.uniapp.front.presenter.textwatcher;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class TextWatcherWeight implements TextWatcher {
    private EditText editText;

    public TextWatcherWeight(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { }
    @Override
    public void afterTextChanged(Editable s) {
        editText.removeTextChangedListener(this);
        if (editText.getText().length() >= 1 && !editText.getText().toString().equals("kg")) {
            String text = editText.getText().toString().replaceAll("[a-zA-Z ]", "") + "kg";
            editText.setText(text);
            editText.setSelection(text.length() - 2);
        } else if (editText.getText().toString().equals("kg")) {
            editText.setText("");
        }
        editText.addTextChangedListener(this);
    }
}
