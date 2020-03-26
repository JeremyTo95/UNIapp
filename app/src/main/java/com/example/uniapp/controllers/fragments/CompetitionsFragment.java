package com.example.uniapp.controllers.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.SystemClock;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uniapp.R;
import com.example.uniapp.controllers.adapters.RecyclerViewAdapter;
import com.example.uniapp.controllers.adapters.SwimItemAdapter;
import com.example.uniapp.models.CustomPopup;
import com.example.uniapp.models.MarketRaceTime;
import com.example.uniapp.models.RaceTime;
import com.example.uniapp.models.SwimCards;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import static android.widget.AdapterView.*;

/*
TODO: Popup pour ajouter un nouveau temps
TODO: Rendre le Recycler View cliquable pour aller sur une nouvelles pages et avoir tous les détails sur la course en question
TODO: Obtenir les sources des temps sur un serveur avec un REST API
TODO: Mettre en place des animations pour le graphique quand on change le viewPager
 */

public class CompetitionsFragment extends Fragment {
    private View layoutInflater;

    private int            sizePool;
    private String         swim;
    private int            distance;
    private List<RaceTime> allRaces;
    private List<RaceTime> currentRaces;

    private TextView            competition_title;
    private TextView            progression_title;
    private Button              btn_25m;
    private Button              btn_50m;
    private ViewPager           mViewPager;
    private int                 viewPagerIndex;
    private SwimItemAdapter     mAdapter;
    private List<SwimCards>     mSwimCardsList;
    private Spinner             selectSwimDistance;
    private LineChart           mLineChart;
    private LineDataSet         lineDataSet;
    private Button              addRaceTimeBtn;
    private RecyclerView        mRecyclerView;
    private RecyclerViewAdapter mRecyclerViewAdapter;

    private char[] baseTimeFormat;
    private String baseDateFormat = "DD/MM/YYYY";

    public CompetitionsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutInflater = inflater.inflate(R.layout.fragment_competition, container, false);

        allRaces = MarketRaceTime.initAllTime();
        sizePool = 25;
        distance = 50;
        swim     = "butterfly";

        currentRaces = currentRaces = MarketRaceTime.getRacesByPoolSizeDistanceRaceSwimRace(allRaces, sizePool, distance, swim);

        competition_title = (TextView)     layoutInflater.findViewById(R.id.fragment_competition_competition_title);
        btn_25m           = (Button)       layoutInflater.findViewById(R.id.fragment_competition_pool_25);
        btn_50m           = (Button)       layoutInflater.findViewById(R.id.fragment_competition_pool_50);
        progression_title = (TextView)     layoutInflater.findViewById(R.id.fragment_competition_progression_title);
        mLineChart        = (LineChart)    layoutInflater.findViewById(R.id.fragment_competition_linechart);
        addRaceTimeBtn    = (Button)       layoutInflater.findViewById(R.id.fragment_competition_add_race_time);
        mRecyclerView     = (RecyclerView) layoutInflater.findViewById(R.id.fragment_competition_recycler_view);

        mSwimCardsList = new ArrayList<>();
        updateBestTime();

        btn_25m.setTextColor(getResources().getColor(RaceTime.getCurrentColor(swim)));
        btn_25m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("25pool");
                sizePool = 25;
                updateColors();
                updateRaceList();
                updateItemsDropdown();
                updateBestTime();
            }
        });

        btn_50m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("50pool");
                sizePool = 50;
                updateColors();
                updateRaceList();
                updateItemsDropdown();
                updateBestTime();
            }
        });

        viewPagerIndex = 0;
        mAdapter = new SwimItemAdapter(mSwimCardsList, layoutInflater.getContext());
        mViewPager = layoutInflater.findViewById(R.id.fragment_competition_viewpager);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setPadding(50, 0, 50, 0);
        mViewPager.setPageMargin(30);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                /*System.out.println("__onPageScrolled__");
                System.out.println("position             : " + position);
                System.out.println("positionOffset       : " + positionOffset);
                System.out.println("positionOffsetPixels : " + positionOffsetPixels);*/
            }

            @Override
            public void onPageSelected(int position) {
                viewPagerIndex = position;
                System.out.println("__onPageSelected__");
                System.out.println("position : " + position);
                swim = mSwimCardsList.get(position).getTitleSwim();
                swim = swim.replace(" ", "");
                if (swim.equals("Papillon"))   swim = "butterfly";
                if (swim.equals("Dos"))        swim = "backstroke";
                if (swim.equals("Brasse"))     swim = "breaststroke";
                if (swim.equals("NageLibre")) swim = "freestyle";
                if (swim.equals("4Nages"))     swim = "IM";
                updateRaceList();
                updateColors();
                updateItemsDropdown();
                setupLineChart(mLineChart, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                /*System.out.println("__onPageScrollStateChanged__");
                System.out.println("state : " + state);*/
            }
        });

        selectSwimDistance = layoutInflater.findViewById(R.id.fragment_competition_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(layoutInflater.getContext(), R.array.distance_spe, R.layout.dropdown_item);
        adapter.setDropDownViewResource(R.layout.dropdown_item);
        selectSwimDistance.setAdapter(adapter);
        selectSwimDistance.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String newDistance =  parent.getItemAtPosition(position).toString(); // récupération de la nouvelle distance de course
                newDistance = newDistance.replace(" ", "");
                newDistance = newDistance.replace("m", "");
                distance = Integer.parseInt(newDistance);
                System.out.println(distance);
                updateRaceList();
                setupLineChart(mLineChart, false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        setupLineChart(mLineChart, true);

        addRaceTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("addNewTime");
                final CustomPopup customPopup = new CustomPopup(getActivity());
                customPopup.setTitle("A j o u t e r   N o u v e l l e   C o u r s e");
                customPopup.setSizePool(sizePool);
                customPopup.setSwim(swim);
                customPopup.setDistanceRace(distance);
                customPopup.getSubtitleDescription().setText("Bassin " + sizePool + "m : " + distance + "m " + RaceTime.convertSwim(swim));
                /*customPopup.updateDateEditText();
                customPopup.updateCityEditText();
                customPopup.updateTimeEditText();
                customPopup.updateLevelEditText();*/
                customPopup.getConfirmedButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        customPopup.setDate(customPopup.getDateEditText().getText().toString());
                        customPopup.setCity(customPopup.getCityEditText().getText().toString());
                        customPopup.setTime(customPopup.getTimeEditText().getText().toString());
                        customPopup.setLevel(customPopup.getLevelEditText().getText().toString());
                        customPopup.checkInputFormatTime();
                        customPopup.isEnableConfirmed();
                        System.out.println("time : " + customPopup.getTime());
                        System.out.println("date : " + customPopup.getDate());
                        customPopup.dismiss();
                        Toast.makeText(layoutInflater.getContext(), "Confirmed", Toast.LENGTH_SHORT);
                    }
                });
                customPopup.getDeniedButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        customPopup.dismiss();
                        Toast.makeText(layoutInflater.getContext(), "Denied", Toast.LENGTH_SHORT);
                    }
                });
                customPopup.build();
            }
        });

        mRecyclerViewAdapter = new RecyclerViewAdapter(currentRaces);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(layoutInflater.getContext()));
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        return layoutInflater;
    }

    private void setupLineChart(LineChart lineChart, boolean isAnimation) {
        lineChart.clear();
        lineChart.setScaleEnabled(false);
        ArrayList<Entry> yValues = setupTimesLineChart();
        lineDataSet = new LineDataSet(yValues, "");

        lineDataSet.setLineWidth(1.75f);
        lineDataSet.setCircleRadius(5f);
        lineDataSet.setCircleHoleRadius(2.5f);
        lineDataSet.setColor(getResources().getColor(RaceTime.getCurrentColor(swim)));
        lineDataSet.setCircleColor(getResources().getColor(RaceTime.getCurrentColor(swim)));
        lineDataSet.setHighLightColor(getResources().getColor(RaceTime.getCurrentColor(swim)));
        lineDataSet.setDrawValues(false);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillColor(getResources().getColor(RaceTime.getCurrentColor(swim)));
        lineDataSet.setFillAlpha(100);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.clear();
        dataSets.add(lineDataSet);
        LineData data = new LineData(dataSets);

        data.setHighlightEnabled(false);
        if (isAnimation)
            lineChart.animateXY(300, 500, Easing.EaseInOutQuad);
        lineChart.notifyDataSetChanged();
        lineChart.setData(data);
        lineChart.getDescription().setEnabled(false); //ok
        lineChart.setDrawGridBackground(false); //ok
        lineChart.getAxisLeft().setEnabled(false);
        lineChart.getAxisLeft().setSpaceTop(40);
        lineChart.getAxisLeft().setSpaceBottom(40);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getXAxis().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
    }

    private ArrayList<Entry> setupTimesLineChart() {
        ArrayList<Entry> result = new ArrayList<Entry>();
        for (int i = 0; i < currentRaces.size(); i++) {
            result.add(new Entry(i, MarketRaceTime.fetchTimeToFloat(currentRaces.get(currentRaces.size() - i - 1).getTime())));
        }
        return result;
    }

    private void updateBestTime() {
        mSwimCardsList.clear();
        mSwimCardsList.add(new SwimCards(allRaces,"butterfly", sizePool));
        mSwimCardsList.add(new SwimCards(allRaces,"backstroke", sizePool));
        mSwimCardsList.add(new SwimCards(allRaces,"breaststroke", sizePool));
        mSwimCardsList.add(new SwimCards(allRaces,"freestyle", sizePool));
        mSwimCardsList.add(new SwimCards(allRaces,"IM", sizePool));

        mAdapter = new SwimItemAdapter(mSwimCardsList, layoutInflater.getContext());
        if (mViewPager == null)
            mViewPager = layoutInflater.findViewById(R.id.fragment_competition_viewpager);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(viewPagerIndex);
    }

    private void updateItemsDropdown() {
        int i;
        String currentDistanceSelected;
        ArrayAdapter<CharSequence> adapter;

        // NOUVELLE LISTE DE DROPDOWN ITEM EN FONCTION DE LA NAGE
        if (swim.equals("butterfly") || swim.equals("backstroke") || swim.equals("breaststroke")) adapter = ArrayAdapter.createFromResource(layoutInflater.getContext(), R.array.distance_spe, R.layout.dropdown_item);
        else if (swim.equals("freestyle")) adapter = ArrayAdapter.createFromResource(layoutInflater.getContext(), R.array.distance_freestyle, R.layout.dropdown_item);
        else if (swim.equals("IM") && sizePool == 25) adapter = ArrayAdapter.createFromResource(layoutInflater.getContext(), R.array.distance_4N_25, R.layout.dropdown_item);
        else adapter = ArrayAdapter.createFromResource(layoutInflater.getContext(), R.array.distance_4N_50, R.layout.dropdown_item);

        // MISE A JOUR DU DROPDOWN
        adapter.setDropDownViewResource(R.layout.dropdown_item);
        selectSwimDistance.setAdapter(adapter);

        // FOCUS SUR LA BONNE DISTANCE EN FONCTION DES AUTRES SWIMITEMS
        for (i = 0; i < adapter.getCount(); i++) {
            currentDistanceSelected = adapter.getItem(i).toString();
            currentDistanceSelected = currentDistanceSelected.replace(" ", "");
            currentDistanceSelected = currentDistanceSelected.replace("m", "");
            if (currentDistanceSelected.equals(String.valueOf(distance))) {
                selectSwimDistance.setSelection(i);
                i = adapter.getCount() + 2;
            }
        }
        if (i == adapter.getCount() + 2) selectSwimDistance.setSelection(0);
    }

    private void updateColors() {
        competition_title.setTextColor(getResources().getColor(RaceTime.getCurrentColor(swim)));
        progression_title.setTextColor(getResources().getColor(RaceTime.getCurrentColor(swim)));
        btn_25m.setTextColor(getResources().getColor((sizePool == 25) ? RaceTime.getCurrentColor(swim) : R.color.colorText));
        btn_50m.setTextColor(getResources().getColor((sizePool == 50) ? RaceTime.getCurrentColor(swim) : R.color.colorText));
    }

    private void updateRaceList() {
        currentRaces.clear();
        currentRaces = new ArrayList<>();
        currentRaces = MarketRaceTime.getRacesByPoolSizeDistanceRaceSwimRace(allRaces, sizePool, distance, swim);

        mRecyclerViewAdapter.notifyDataSetChanged();
        mRecyclerViewAdapter = new RecyclerViewAdapter(currentRaces);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(layoutInflater.getContext()));
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }
}
