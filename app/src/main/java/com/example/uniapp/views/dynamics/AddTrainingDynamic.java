package com.example.uniapp.views.dynamics;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.InputType;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.uniapp.R;

public class AddTrainingDynamic {
    Context context;
    int position;

    public AddTrainingDynamic(Context context, int position) { this.context = context; }

    public LinearLayout createLayoutSets(Context context, int position) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(createTextView(context, position, "Sets"));
        linearLayout.addView(createEditText(context, position+1, "0"));

        return linearLayout;
    }

    public LinearLayout createLayoutDistance(Context context, int position) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(createTextView(context, position, "Distance"));
        linearLayout.addView(createEditText(context, position+1, "25m"));

        return linearLayout;
    }

    public LinearLayout createLayoutSwim(Context context, int position) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(createTextView(context, position, "Nage"));
        linearLayout.addView(createSpinner(context, position+1));

        return linearLayout;
    }

    public LinearLayout createLayoutZone(Context context, int position) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(createTextView(context, position, "Zone"));
        linearLayout.addView(createSpinner(context, position+1));

        return linearLayout;
    }

    public LinearLayout createFullLayout(Context context, int position) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150);
        params.setMargins(10, 10, 10, 10);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setBackground(context.getResources().getDrawable(R.drawable.sh_gradient_background));
        linearLayout.addView(createLayoutSets(context, position));
        linearLayout.addView(createLayoutDistance(context, position));
        linearLayout.addView(createLayoutSwim(context, position));
        linearLayout.addView(createLayoutZone(context, position));

        return linearLayout;
    }

    public TextView createTextView(Context context, int id, String text) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f);
        TextView textView = new TextView(context);
        textView.setLayoutParams(params);
        textView.setText(text);
        textView.setTextColor(context.getResources().getColor(R.color.colorSecondaryLight));
        textView.setTextSize(18);
        textView.setGravity(Gravity.CENTER);
        textView.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
        textView.setId(id);

        return textView;
    }

    public EditText createEditText(Context context, int id, String hint) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f);
        EditText editText = new EditText(context);
        editText.setLayoutParams(params);
        editText.setTextColor(context.getResources().getColor(R.color.colorText));
        editText.setTextSize(15);
        editText.setGravity(Gravity.CENTER);
        editText.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
        editText.setHint(hint);
        editText.setInputType(InputType.TYPE_NUMBER_VARIATION_NORMAL);
        editText.setId(id);

        return editText;
    }

    public Spinner createSpinner(Context context, int id) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f);
        Spinner spinner = new Spinner(context);
        spinner.setLayoutParams(params);
        spinner.setId(id);

        return spinner;
    }
}
