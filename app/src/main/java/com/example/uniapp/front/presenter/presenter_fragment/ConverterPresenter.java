package com.example.uniapp.front.presenter.presenter_fragment;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.uniapp.R;
import com.example.uniapp.front.presenter.global.AboutScreen;
import com.example.uniapp.front.presenter.global.Controller;
import com.example.uniapp.front.model.market.MarketTimes;
import com.example.uniapp.front.view.actvities.MainActivity;
import com.example.uniapp.front.view.fragments.ConverterFragment;

public class ConverterPresenter extends Controller {
    private ConverterFragment view;

    private String inputTimeStr;
    private String outputTimeStr;
    private String zoneSelectedStr;

    public ConverterPresenter(ConverterFragment view) {
        super(view);
        this.view = view;
    }

    @Override
    public void onStart() {
        super.onStart();
        view.setupUIElements();
        view.updateZone();

        zoneSelectedStr = "Z1";
        AboutScreen.lockUI((MainActivity) getActivity(), false, null);
    }

    public void convertTime() {
        if (view.getInputTimeET().getText().toString().length() == 0) {
            view.getInputTimeET().setHintTextColor(view.getResources().getColor(R.color.redDeep));
        } else {
            Toast.makeText(getActivity(), "Conversion terminé", Toast.LENGTH_SHORT).show();
            inputTimeStr = MarketTimes.convertTimeToFormat(view.getInputTimeET().getText().toString());
            outputTimeStr = MarketTimes.convertCompetitionTimeToZoneTime(MarketTimes.fetchTimeToFloat(inputTimeStr), Integer.parseInt(zoneSelectedStr.substring(1)));
            System.out.println("input : " + inputTimeStr + " | output : " + outputTimeStr);
            view.setOutputText(getOutputResult());
            view.getOutputTimeTV().setVisibility(View.VISIBLE);
        }
    }

    public ArrayAdapter<CharSequence> getSelectedAdapter() {
        ArrayAdapter<CharSequence> zoneSelectedAdapter;
        if (AboutScreen.isNightMode(getActivity())) zoneSelectedAdapter = ArrayAdapter.createFromResource(getContext(), R.array.zones, R.layout.dropdown_big_item_dark);
        else zoneSelectedAdapter = ArrayAdapter.createFromResource(getContext(), R.array.zones, R.layout.dropdown_big_item_light);
        return zoneSelectedAdapter;
    }

    private String getOutputResult() { return "Temps " + zoneSelectedStr + " : " + outputTimeStr; }
    public void setInputTimeStr(String inputTimeStr) { this.inputTimeStr = inputTimeStr; }
    public void setZoneSelectedStr(String zoneSelectedStr) { this.zoneSelectedStr = zoneSelectedStr; }
}
