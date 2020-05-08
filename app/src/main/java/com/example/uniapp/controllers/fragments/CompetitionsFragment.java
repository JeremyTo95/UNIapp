package com.example.uniapp.controllers.fragments;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uniapp.R;
import com.example.uniapp.controllers.activities.MainActivity;
import com.example.uniapp.controllers.adapters.recyclerview.RvRaceAdapter;
import com.example.uniapp.models.markets.MarketRaces;
import com.example.uniapp.models.markets.MarketSwim;
import com.example.uniapp.models.markets.MarketTimes;
import com.example.uniapp.models.SetupLineChart;
import com.example.uniapp.models.gesturedetector.SwimCard;
import com.example.uniapp.models.swiptodeletecallback.SwipeToDeleteCallback;
import com.example.uniapp.views.AboutScreen;
import com.example.uniapp.views.comparators.RaceDateComparator;
import com.example.uniapp.views.popup.competition.AddRacePopup;
import com.example.uniapp.models.database.dao.race.Race;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static android.widget.AdapterView.*;

public class CompetitionsFragment extends Fragment implements View.OnClickListener {
    private int        sizePool;
    private String     swim;
    private int        distance;
    private List<Race> currentRaces;

    private View     layoutInflater;
    private TextView competition_title;
    private TextView progression_title;
    private Button   btn_25m;
    private Button   btn_50m;

    private GestureDetector       gestureDetector;
    private List<SwimCard>        swimCardList;
    private HorizontalScrollView  horizontalScrollView;
    private LinearLayout          linearLayout;

    private Spinner                    selectSwimDistance;
    private ArrayAdapter<CharSequence> adapter;

    private LineChart lineChart;

    private Button        addRaceTimeBtn;
    private RecyclerView  recyclerView;
    private RvRaceAdapter rvRaceAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutInflater = inflater.inflate(R.layout.fragment_competition, container, false);

        sizePool       = 25;
        distance       = 50;
        swim           = "butterfly";

        setupUIElements();
        updateCurrentRaces();
        setupSelectedSwim();
        updateSelectedDistance();
        setupLineChart(true);
        setupRaceList();
        updateColors();

        return layoutInflater;
    }

    private void setupUIElements() {
        competition_title    = (TextView)             layoutInflater.findViewById(R.id.fragment_competition_competition_title);
        btn_25m              = (Button)               layoutInflater.findViewById(R.id.fragment_competition_pool_25);
        btn_50m              = (Button)               layoutInflater.findViewById(R.id.fragment_competition_pool_50);
        progression_title    = (TextView)             layoutInflater.findViewById(R.id.fragment_competition_progression_title);
        horizontalScrollView = (HorizontalScrollView) layoutInflater.findViewById(R.id.horizontalScrollView);
        linearLayout         = (LinearLayout)         layoutInflater.findViewById(R.id.linearLayout);
        selectSwimDistance   = (Spinner)              layoutInflater.findViewById(R.id.fragment_competition_spinner);
        lineChart            = (LineChart)            layoutInflater.findViewById(R.id.fragment_competition_linechart);
        addRaceTimeBtn       = (Button)               layoutInflater.findViewById(R.id.fragment_competition_add_race_time);
        recyclerView         = (RecyclerView)         layoutInflater.findViewById(R.id.fragment_competition_recycler_view);

        btn_25m.setOnClickListener(this);
        btn_50m.setOnClickListener(this);
        addRaceTimeBtn.setOnClickListener(this);

        adapter = ArrayAdapter.createFromResource(getContext(), R.array.distance_spe, R.layout.dropdown_item_auto);
        adapter.setDropDownViewResource(R.layout.dropdown_all_items);
        selectSwimDistance.setAdapter(adapter);
        selectSwimDistance.setSelection(1);
        updateSelectedDistance();
    }

    @Override
    public void onClick(View v) {
        if      (v.getTag().equals("pool25")) updateToPool25();
        else if (v.getTag().equals("pool50")) updateToPool50();
        else if (v.getTag().equals("addBtn")) setupAddRacePopup();
    }

    private void updateToPool25() {
        sizePool = 25;
        updateCurrentRaces();
        updateSelectedDistanceArray();
        setupSelectedSwim();
        updateColors();
        setupLineChart(false);
        setupRaceList();
    }

    private void updateToPool50() {
        sizePool = 50;
        updateCurrentRaces();
        updateSelectedDistanceArray();
        setupSelectedSwim();
        updateColors();
        setupLineChart(false);
        setupRaceList();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupSelectedSwim() {
        double widthCard       =  AboutScreen.getWidth(getActivity()) * 0.9;
        double widthCardMargin = (AboutScreen.getWidth(getActivity()) * 0.1)/2;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int)(widthCard), LayoutParams.MATCH_PARENT);
        layoutParams.setMargins((int) widthCardMargin, 0, (int) widthCardMargin, 0);

        swimCardList = new ArrayList<SwimCard>();
        swimCardList.add(new SwimCard(getContext(), "butterfly", sizePool));
        swimCardList.add(new SwimCard(getContext(), "backstroke", sizePool));
        swimCardList.add(new SwimCard(getContext(), "breaststroke", sizePool));
        swimCardList.add(new SwimCard(getContext(), "freestyle", sizePool));
        swimCardList.add(new SwimCard(getContext(), "IM", sizePool));

        linearLayout.removeAllViews();
        for (int i = 0; i < swimCardList.size(); i++) linearLayout.addView(swimCardList.get(i), i, layoutParams);
        horizontalScrollView.removeAllViews();
        horizontalScrollView.addView(linearLayout);

        gestureDetector = new GestureDetector(new SwimCard.OnGestureDetector(swimCardList, horizontalScrollView, swim) {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                super.onFling(e1, e2, velocityX, velocityY);
                swim = swimCardList.get(getIndexSwimCard()).getSwim();
                updateColors();
                updateSelectedDistanceArray();
                updateSelectedDistance();
                updateCurrentRaces();
                setupLineChart(true);
                setupRaceList();

                return true;
            }
        });

        horizontalScrollView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (gestureDetector.onTouchEvent(event)) return true;
                else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    int indexSwimCard = MarketSwim.getSwimIndex(swim);
                    int scrollTo      = indexSwimCard * horizontalScrollView.getMeasuredWidth();
                    horizontalScrollView.smoothScrollTo(scrollTo, 0);
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    private void updateSelectedDistance() {
        selectSwimDistance.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int newdistance = Integer.parseInt(parent.getItemAtPosition(position).toString().replaceAll("[a-zA-Z ]", ""));
                System.out.println("adapter : " + adapter.getCount() +
                        " | parent : " + parent.getCount());
                if (newdistance != distance) {
                    distance = newdistance;
                    updateCurrentRaces();
                    setupRaceList();
                    setupLineChart(false);
                }
            }
        });
    }

    private void updateSelectedDistanceArray() {
        int i;
        String currentDistanceSelected;
        ArrayAdapter<CharSequence> adapter;

        if  (swim.equals("butterfly") || swim.equals("backstroke") || swim.equals("breaststroke")) adapter = ArrayAdapter.createFromResource(layoutInflater.getContext(), R.array.distance_spe, R.layout.dropdown_item_auto);
        else if (swim.equals("freestyle")) adapter = ArrayAdapter.createFromResource(layoutInflater.getContext(), R.array.distance_freestyle, R.layout.dropdown_item_auto);
        else if (swim.equals("IM") && sizePool == 25) adapter = ArrayAdapter.createFromResource(layoutInflater.getContext(), R.array.distance_4N_25, R.layout.dropdown_item_auto);
        else adapter = ArrayAdapter.createFromResource(layoutInflater.getContext(), R.array.distance_4N_50, R.layout.dropdown_item_auto);

        adapter.setDropDownViewResource(R.layout.dropdown_all_items);
        selectSwimDistance.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        for (i = 0; i < adapter.getCount(); i++) {
            currentDistanceSelected = adapter.getItem(i).toString().replace(" ", "").replace("m", "");
            if (currentDistanceSelected.equals(String.valueOf(distance))) {
                selectSwimDistance.setSelection(i);
                i = adapter.getCount() + 2;
            }
        }
        if (i == adapter.getCount()) {
            selectSwimDistance.setSelection(0);
            distance = Integer.parseInt(selectSwimDistance.getItemAtPosition(0).toString().replaceAll("[ m]", ""));
        }
    }

    private void updateColors() {
        competition_title.setTextColor(MarketSwim.getCurrentColor(getContext(), swim));
        progression_title.setTextColor(MarketSwim.getCurrentColor(getContext(), swim));
        btn_25m.setTextColor((sizePool == 25) ? MarketSwim.getCurrentColor(getContext(), swim) : AboutScreen.getColorByThemeAttr(getContext(), R.attr.textColor, R.color.textColorDark));
        btn_50m.setTextColor((sizePool == 50) ? MarketSwim.getCurrentColor(getContext(), swim) : AboutScreen.getColorByThemeAttr(getContext(), R.attr.textColor, R.color.textColorDark));
    }

    private void updateCurrentRaces() {
        currentRaces = MainActivity.appDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(sizePool, distance, swim);
        Collections.sort(currentRaces, new RaceDateComparator());
    }

    private void setupLineChart(boolean isAnimation) {
        SetupLineChart.configureMyLineChart(getActivity(), MarketRaces.getFloatTimes(currentRaces), lineChart, MarketSwim.getCurrentColor(getContext(), swim), isAnimation);
    }


    @SuppressLint("SetTextI18n")
    private void setupAddRacePopup() {
        final AddRacePopup addRacePopup = new AddRacePopup(getActivity());
        addRacePopup.setSizePool(sizePool);
        addRacePopup.setSwim(swim);
        addRacePopup.setDistanceRace(distance);
        addRacePopup.getSubtitleDescription().setText("Bassin " + sizePool + "m : " + distance + "m " + MarketSwim.convertSwimFromEnglishToFrench(swim));
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
                    setupSelectedSwim();
                    setupLineChart(true);
                    setupRaceList();
                    Toast.makeText(getContext(), "Nouvelle course ajoutée", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupRaceList() {
        rvRaceAdapter = new RvRaceAdapter(getActivity(), getContext(), currentRaces) {
            @Override
            public void undoDelete() {
                super.undoDelete();
                updateCurrentRaces();
                setupSelectedSwim();
                setupLineChart(false);
            }
        };
        recyclerView.setAdapter(rvRaceAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(layoutInflater.getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        rvRaceAdapter.notifyDataSetChanged();
        ItemTouchHelper onSwipe = new ItemTouchHelper(new SwipeToDeleteCallback() {
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_delete_white_48dp));
                setBackground(ContextCompat.getDrawable(getContext(), MarketSwim.getCurrentDrawable(swim)));
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                super.onSwiped(viewHolder, direction);
                int position = viewHolder.getAdapterPosition();
                rvRaceAdapter.removeItem(position);
                updateCurrentRaces();
                setupSelectedSwim();
                setupLineChart(false);
            }
        });
        onSwipe.attachToRecyclerView(recyclerView);
    }
}