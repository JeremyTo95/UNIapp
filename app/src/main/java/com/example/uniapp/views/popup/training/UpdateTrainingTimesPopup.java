package com.example.uniapp.views.popup.training;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import androidx.annotation.NonNull;

import com.example.uniapp.R;
import com.example.uniapp.models.database.dao.training.Training;

public class UpdateTrainingTimesPopup extends Dialog {
    Training training;

    public UpdateTrainingTimesPopup(@NonNull Context context, Training training) {
        super(context);
        this.training = training;
        setContentView(R.layout.popup_update_training_times);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void build() {
        show();
        Window window = getWindow();
        if (window != null) window.setLayout(900, 1500);
    }
}
