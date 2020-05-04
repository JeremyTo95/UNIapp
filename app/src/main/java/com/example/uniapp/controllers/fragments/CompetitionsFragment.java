package com.example.uniapp.controllers.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uniapp.R;
import com.example.uniapp.controllers.activities.MainActivity;
import com.example.uniapp.controllers.adapters.recyclerview.RvRaceAdapter;
import com.example.uniapp.controllers.adapters.pageviewer.PvSwimItemAdapter;
import com.example.uniapp.models.MarketTimes;
import com.example.uniapp.views.AboutScreen;
import com.example.uniapp.views.comparators.RaceDateComparator;
import com.example.uniapp.views.popup.competition.AddRacePopup;
import com.example.uniapp.models.database.dao.race.Race;
import com.example.uniapp.views.SwimCards;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static android.widget.AdapterView.*;

public class CompetitionsFragment extends Fragment implements View.OnClickListener {
    private int           sizePool;
    private String        swim;
    private int           distance;
    private List<Race>    currentRaces;

    private View            layoutInflater;
    private TextView        competition_title;
    private TextView        progression_title;
    private Button          btn_25m;
    private Button          btn_50m;
    private ViewPager       mViewPager;
    private int             viewPagerIndex;
    private List<SwimCards> mSwimCardsList;
    private Spinner         selectSwimDistance;
    private LineChart       lineChart;
    private LineDataSet     lineDataSet;
    // private Button          refreshRaceBtn;
    private Button          addRaceTimeBtn;
    private RecyclerView    recyclerView;
    private RvRaceAdapter   rvRaceAdapter;

    public CompetitionsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutInflater = inflater.inflate(R.layout.fragment_competition, container, false);

        sizePool       = 25;
        distance       = 50;
        viewPagerIndex = 0;
        swim           = "butterfly";

        configureUIElements();
        configureAndShowPageViewer();
        configureAndShowDistanceSelectionDropdown();
        configureAndShowLineChart(lineChart, true);
        updateRecyclerViewRaceList();
        updateColors();

        return layoutInflater;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateCurrentRaces();
        updateRecyclerViewRaceList();
        configureAndShowLineChart(lineChart, true);
    }

    private void configureUIElements() {
        competition_title = (TextView)     layoutInflater.findViewById(R.id.fragment_competition_competition_title);
        btn_25m           = (Button)       layoutInflater.findViewById(R.id.fragment_competition_pool_25);
        btn_50m           = (Button)       layoutInflater.findViewById(R.id.fragment_competition_pool_50);
        progression_title = (TextView)     layoutInflater.findViewById(R.id.fragment_competition_progression_title);
        lineChart         = (LineChart)    layoutInflater.findViewById(R.id.fragment_competition_linechart);
        // refreshRaceBtn    = (Button)       layoutInflater.findViewById(R.id.fragment_competition_refresh_race_time);
        addRaceTimeBtn    = (Button)       layoutInflater.findViewById(R.id.fragment_competition_add_race_time);
        recyclerView      = (RecyclerView) layoutInflater.findViewById(R.id.fragment_competition_recycler_view);
        btn_25m.setOnClickListener(this);
        btn_50m.setOnClickListener(this);
        addRaceTimeBtn.setOnClickListener(this);
        // refreshRaceBtn.setOnClickListener(this);
    }

    private void updateToPool25() {
        sizePool = 25;
        updateRecyclerViewRaceList();
        updateItemsDropdown();
        updateTimesForSwimCards();
        updateColors();
    }

    private void updateToPool50() {
        sizePool = 50;
        updateRecyclerViewRaceList();
        updateItemsDropdown();
        updateTimesForSwimCards();
        updateColors();
    }

    private void refreshRaces() {
        Race.startAsyncTaskLoadingRace(this.getActivity(), MainActivity.appDataBase.userDAO().getUser());
        Toast.makeText(getContext(), "Actualisation des temps...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v.getTag().equals("pool25")) updateToPool25();
        else if (v.getTag().equals("pool50")) updateToPool50();
        else if (v.getTag().equals("addBtn")) configureAndShowAddRacePopup();
        else if (v.getTag().equals("refreshBtn")) refreshRaces();
    }

    private void updateTimesForSwimCards() {
        mSwimCardsList.clear();
        mSwimCardsList.add(new SwimCards(MainActivity.appDataBase.raceDAO().getAllRaces(),"butterfly", sizePool));
        mSwimCardsList.add(new SwimCards(MainActivity.appDataBase.raceDAO().getAllRaces(),"backstroke", sizePool));
        mSwimCardsList.add(new SwimCards(MainActivity.appDataBase.raceDAO().getAllRaces(),"breaststroke", sizePool));
        mSwimCardsList.add(new SwimCards(MainActivity.appDataBase.raceDAO().getAllRaces(),"freestyle", sizePool));
        mSwimCardsList.add(new SwimCards(MainActivity.appDataBase.raceDAO().getAllRaces(),"IM", sizePool));

        if (mViewPager == null) mViewPager = layoutInflater.findViewById(R.id.fragment_competition_viewpager);
        mViewPager.setAdapter(new PvSwimItemAdapter(mSwimCardsList, layoutInflater.getContext(), sizePool));
        mViewPager.getAdapter().notifyDataSetChanged();
        mViewPager.setCurrentItem(viewPagerIndex);
    }

    private void configureAndShowPageViewer() {
        mSwimCardsList = new ArrayList<>();
        updateTimesForSwimCards();
        mViewPager.setPageMargin(30);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
            @Override
            public void onPageScrollStateChanged(int state) { }
            @Override
            public void onPageSelected(int position) {
                viewPagerIndex = position;
                swim = mSwimCardsList.get(position).getTitleSwim();
                swim = swim.replace(" ", "");
                if (swim.equals("Papillon"))   swim = "butterfly";
                if (swim.equals("Dos"))        swim = "backstroke";
                if (swim.equals("Brasse"))     swim = "breaststroke";
                if (swim.equals("NageLibre"))  swim = "freestyle";
                if (swim.equals("4Nages"))     swim = "IM";
                updateRecyclerViewRaceList();
                updateColors();
                updateItemsDropdown();
                configureAndShowLineChart(lineChart, true);
            }
        });
    }

    private void configureAndShowDistanceSelectionDropdown() {
        selectSwimDistance = layoutInflater.findViewById(R.id.fragment_competition_spinner);
        selectSwimDistance.setPopupBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(layoutInflater.getContext(), R.array.distance_spe, R.layout.dropdown_item_auto);
        adapter.setDropDownViewResource(R.layout.dropdown_all_items);
        selectSwimDistance.setAdapter(adapter);
        selectSwimDistance.setSelection(1);
        adapter.notifyDataSetChanged();
        selectSwimDistance.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String newDistance =  parent.getItemAtPosition(position).toString(); // récupération de la nouvelle distance de course
                newDistance = newDistance.replace(" ", "");
                newDistance = newDistance.replace("m", "");
                distance = Integer.parseInt(newDistance);
                updateRecyclerViewRaceList();
                configureAndShowLineChart(lineChart, false);
            }
        });
    }

    private void configureAndShowLineChart(LineChart lineChart, boolean isAnimation) {
        lineChart.clear();
        lineChart.setScaleEnabled(false);
        ArrayList<Entry> yValues = setupTimesLineChart();
        lineDataSet = new LineDataSet(yValues, "");

        lineDataSet.setLineWidth(1.75f);
        lineDataSet.setCircleRadius(5f);
        lineDataSet.setCircleHoleRadius(2.5f);
        lineDataSet.setColor(Race.getCurrentColor(getContext(), swim));
        lineDataSet.setCircleColor(Race.getCurrentColor(getContext(), swim));
        lineDataSet.setHighLightColor(Race.getCurrentColor(getContext(), swim));
        lineDataSet.setDrawValues(false);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillColor(Race.getCurrentColor(getContext(), swim));
        lineDataSet.setFillAlpha(100);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.clear();
        dataSets.add(lineDataSet);
        LineData data = new LineData(dataSets);

        data.setHighlightEnabled(false);
        if (isAnimation) lineChart.animateXY(300, 500, Easing.EaseInOutQuad);
        lineChart.notifyDataSetChanged();
        lineChart.setData(data);
        lineChart.getDescription().setEnabled(false); //ok
        lineChart.setDrawGridBackground(false); //ok
        lineChart.getAxisLeft().setEnabled(false);
        lineChart.getAxisLeft().setSpaceTop(40);
        lineChart.getAxisLeft().setSpaceBottom(40);
        lineChart.getAxisLeft().setAxisMinimum(0.0f);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getXAxis().setEnabled(false);
        lineChart.setTouchEnabled(false);
        lineChart.getLegend().setEnabled(false);
    }

    private ArrayList<Entry> setupTimesLineChart() {
        currentRaces = (List<Race>) MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(sizePool, distance, swim);
        Collections.sort(currentRaces, new RaceDateComparator());
        ArrayList<Entry> result = new ArrayList<>();
        float time;
        for (int i = 0; i < currentRaces.size(); i++) {
            time = currentRaces.get(currentRaces.size() - i - 1).getTime() / 1 * 60 + currentRaces.get(currentRaces.size() - i - 1).getTime()%1;
            System.out.println(time);
            result.add(new Entry(i, time));
        }

        return result;
    }

    private void configureAndShowAddRacePopup() {
        final AddRacePopup addRacePopup = new AddRacePopup(getActivity());
        addRacePopup.setTitle("A j o u t e r   N o u v e l l e   C o u r s e");
        addRacePopup.setSizePool(sizePool);
        addRacePopup.setSwim(swim);
        addRacePopup.setDistanceRace(distance);
        addRacePopup.getSubtitleDescription().setText("Bassin " + sizePool + "m : " + distance + "m " + Race.convertSwimFromEnglishToFrench(swim));
        addRacePopup.build();

        addRacePopup.getConfirmedButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRacePopup.setDate(addRacePopup.getDateEditText().getText().toString());
                addRacePopup.setCity(addRacePopup.getCityEditText().getText().toString());
                addRacePopup.setCountry(addRacePopup.getCountryEditText().getText().toString());
                addRacePopup.setTime(MarketTimes.fetchTimeToFloat(addRacePopup.getTimeEditText().getText().toString()));
                addRacePopup.checkInputFormatTime();
                if (addRacePopup.isEnableConfirmed()) {
                    Race newRace = new Race(UUID.randomUUID().toString(),
                            addRacePopup.getDate(), addRacePopup.getCity(), addRacePopup.getCountry(),
                            MainActivity.appDataBase.userDAO().getUser().getClub(), addRacePopup.getDistanceRace(), addRacePopup.getSizePool(),
                            addRacePopup.getSwim(), addRacePopup.getTime(),
                            addRacePopup.getLevel()
                    );
                    MainActivity.appDataBase.raceDAO().insertRace(newRace);
                    addRacePopup.dismiss();
                    updateCurrentRaces();
                    updateRecyclerViewRaceList();
                    updateTimesForSwimCards();
                    setupTimesLineChart();
                    configureAndShowLineChart(lineChart, true);
                    Toast.makeText(getContext(), "Nouvelle course ajoutée", Toast.LENGTH_SHORT).show();
                }
            }
        });
        configureAndShowLineChart(lineChart, true);
    }

    private void updateItemsDropdown() {
        int i;
        ArrayAdapter<CharSequence> adapter;
        String currentDistanceSelected;

        if  (swim.equals("butterfly") || swim.equals("backstroke") || swim.equals("breaststroke")) {
            adapter = ArrayAdapter.createFromResource(layoutInflater.getContext(), R.array.distance_spe, R.layout.dropdown_item_auto);
        } else if (swim.equals("freestyle")) {
            adapter = ArrayAdapter.createFromResource(layoutInflater.getContext(), R.array.distance_freestyle, R.layout.dropdown_item_auto);
        } else if (swim.equals("IM") && sizePool == 25) {
            adapter = ArrayAdapter.createFromResource(layoutInflater.getContext(), R.array.distance_4N_25, R.layout.dropdown_item_auto);
        } else {
            adapter = ArrayAdapter.createFromResource(layoutInflater.getContext(), R.array.distance_4N_50, R.layout.dropdown_item_auto);
        }

        adapter.setDropDownViewResource(R.layout.dropdown_all_items);
        selectSwimDistance.setAdapter(adapter);
        adapter.notifyDataSetChanged();

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
        competition_title.setTextColor(Race.getCurrentColor(getContext(), swim));
        progression_title.setTextColor(Race.getCurrentColor(getContext(), swim));
        btn_25m.setTextColor((sizePool == 25) ? Race.getCurrentColor(getContext(), swim) : AboutScreen.getColorByThemeAttr(getContext(), R.attr.textColor, R.color.textColorDark));
        btn_50m.setTextColor((sizePool == 50) ? Race.getCurrentColor(getContext(), swim) : AboutScreen.getColorByThemeAttr(getContext(), R.attr.textColor, R.color.textColorDark));
    }

    private void updateCurrentRaces() {
        currentRaces.clear();
        currentRaces = new ArrayList<>();
        currentRaces = (List<Race>) MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(sizePool, distance, swim);
        System.out.println(Arrays.toString(currentRaces.toArray()));
        Collections.sort(currentRaces, new RaceDateComparator());
    }

    private void updateRecyclerViewRaceList() {
        updateCurrentRaces();
        rvRaceAdapter = new RvRaceAdapter(getActivity(), getContext(), currentRaces);
        recyclerView.setLayoutManager(new LinearLayoutManager(layoutInflater.getContext()));
        recyclerView.setAdapter(rvRaceAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        rvRaceAdapter.notifyDataSetChanged();
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        MainActivity.appDataBase.raceDAO().delete(currentRaces.get(viewHolder.getAdapterPosition()));
                        currentRaces.remove(viewHolder.getAdapterPosition());
                        rvRaceAdapter.removeItem(viewHolder.getAdapterPosition());
                        configureAndShowLineChart(lineChart, true);
                        updateTimesForSwimCards();
                    }
                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}