package com.example.uniapp.front.controller.controller_fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.uniapp.R;
import com.example.uniapp.back.executor.AppExecutors;
import com.example.uniapp.back.room.RoomDataBase;
import com.example.uniapp.front.controller.global.Controller;
import com.example.uniapp.front.controller.comparator.RaceDateComparator;
import com.example.uniapp.front.controller.listener.SwimCardListener;
import com.example.uniapp.front.model.data.Race;
import com.example.uniapp.front.model.graphicsitem.SwimCard;
import com.example.uniapp.front.model.market.MarketSwim;
import com.example.uniapp.front.model.market.MarketTimes;
import com.example.uniapp.front.view.fragments.CompetitionsFragment;
import com.example.uniapp.front.view.popup.AddRacePopup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class CompetitionController extends Controller {
    private CompetitionsFragment view;
    private Context              context;
    private RoomDataBase         roomDataBase;
    private AddRacePopup         addRacePopup;

    private int        sizePool;
    private String     swim;
    private int        distance;
    private List<Race> currentRaces;

    public CompetitionController(CompetitionsFragment view) {
        super(view);
        this.view         = view;
        this.context      = view.getContext();
        this.roomDataBase = RoomDataBase.getDatabase(context);
    }

    @Override
    public void onStart() {
        sizePool = 25;
        distance = 50;
        swim     = "butterfly";
        updateCurrentRaces();
        view.setupUIElements();
        updateSelectedDistance();
        view.setupSelectedSwim();
        view.setupLineChart(true);
        view.setupRaceList();
        view.updateUIColors();
    }

    public void updateToPool25() {
        sizePool = 25;
        updateCurrentRaces();
        updateSelectedDistanceArray();
        view.setupSelectedSwim();
        view.updateUIColors();
        view.setupLineChart(false);
        view.setupRaceList();
    }

    public void updateToPool50() {
        sizePool = 50;
        updateCurrentRaces();
        updateSelectedDistanceArray();
        view.setupSelectedSwim();
        view.updateUIColors();
        view.setupLineChart(false);
        view.setupRaceList();
    }

    private void updateUI() {
        view.updateUIColors();
        view.setupLineChart(true);
        view.setupRaceList();
    }

    public void updateCurrentRaces() {
        currentRaces = roomDataBase.raceDAO().getRacesByPoolSizeDistanceRaceSwimRace(sizePool, distance, swim);
        Collections.sort(currentRaces, new RaceDateComparator());
    }

    private void updateSelectedDistanceArray() {
        int i;
        String currentDistanceSelected;
        ArrayAdapter<CharSequence> adapter;

        if  (swim.equals("butterfly") || swim.equals("backstroke") || swim.equals("breaststroke")) adapter = ArrayAdapter.createFromResource(context, R.array.distance_spe, R.layout.dropdown_item_auto);
        else if (swim.equals("freestyle")) adapter = ArrayAdapter.createFromResource(context, R.array.distance_freestyle, R.layout.dropdown_item_auto);
        else if (swim.equals("IM") && sizePool == 25) adapter = ArrayAdapter.createFromResource(context, R.array.distance_4N_25, R.layout.dropdown_item_auto);
        else adapter = ArrayAdapter.createFromResource(context, R.array.distance_4N_50, R.layout.dropdown_item_auto);

        adapter.setDropDownViewResource(R.layout.dropdown_all_items);
        view.getSelectSwimDistance().setAdapter(adapter);
        adapter.notifyDataSetChanged();

        for (i = 0; i < adapter.getCount(); i++) {
            currentDistanceSelected = adapter.getItem(i).toString().replace(" ", "").replace("m", "");
            if (currentDistanceSelected.equals(String.valueOf(distance))) {
                view.getSelectSwimDistance().setSelection(i);
                i = adapter.getCount() + 2;
            }
        }
        if (i == adapter.getCount()) {
            view.getSelectSwimDistance().setSelection(0);
            distance = Integer.parseInt(view.getSelectSwimDistance().getItemAtPosition(0).toString().replaceAll("[ m]", ""));
        }
    }

    public void updateSelectedDistance() {
        view.getSelectSwimDistance().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int newDistance = Integer.parseInt(parent.getItemAtPosition(position).toString().replaceAll("[a-zA-Z ]", ""));
                if (newDistance != distance) {
                    distance = newDistance;
                    updateCurrentRaces();
                    CompetitionController.this.view.setupRaceList();
                    CompetitionController.this.view.setupLineChart(false);
                }
            }
        });
    }

    public List<SwimCard> loadSwimCards() {
        List<SwimCard> swimCardList  =  new ArrayList<SwimCard>();
        swimCardList.add(new SwimCard(context, "butterfly", sizePool));
        swimCardList.add(new SwimCard(context, "backstroke", sizePool));
        swimCardList.add(new SwimCard(context, "breaststroke", sizePool));
        swimCardList.add(new SwimCard(context, "freestyle", sizePool));
        swimCardList.add(new SwimCard(context, "IM", sizePool));
        return swimCardList;
    }

    public GestureDetector loadGestureSwimCard() {
        return new GestureDetector(new SwimCardListener(view.getSwimCardList(), view.getHorizontalScrollView(), swim) {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                super.onFling(e1, e2, velocityX, velocityY);
                setSwim(view.getSwimCardList().get(getIndexSwimCard()).getSwim());
                updateSelectedDistanceArray();
                updateSelectedDistance();
                updateCurrentRaces();
                updateUI();

                return true;
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    public void configureHorizontalScrollViewTouchListener() {
        view.getHorizontalScrollView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (view.getGestureDetector().onTouchEvent(event)) return true;
                else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    int indexSwimCard = MarketSwim.getSwimIndex(swim);
                    int scrollTo      = indexSwimCard * view.getHorizontalScrollView().getMeasuredWidth();
                    view.getHorizontalScrollView().smoothScrollTo(scrollTo, 0);
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    public void setupAddRacePopup() {
        addRacePopup = new AddRacePopup(view.getActivity());
        addRacePopup.setSizePool(sizePool);
        addRacePopup.setSwim(swim);
        addRacePopup.setDistanceRace(distance);
        addRacePopup.getSubtitleDescription().setText("Bassin " + sizePool + "m : " + distance + "m " + MarketSwim.convertSwimFromEnglishToFrench(swim));
        addRacePopup.getCountryEditText().setText("FRANCE");
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
                            roomDataBase.userDAO().getUser().getClub(), addRacePopup.getDistanceRace(), addRacePopup.getSizePool(),
                            addRacePopup.getSwim(), addRacePopup.getTime(),
                            addRacePopup.getLevel()
                    );
                    insertNewRace(newRace);
                }
            }
        });
    }

    private void insertNewRace(Race newRace) {
        roomDataBase.raceDAO().insert(newRace);
        addRacePopup.dismiss();
        updateCurrentRaces();
        view.setupSelectedSwim();
        view.setupLineChart(true);
        view.setupRaceList();
        Toast.makeText(context, "Nouvelle course ajoutée", Toast.LENGTH_SHORT).show();
    }

    public void setSizePool(int sizePool) { this.sizePool = sizePool; }
    public void setSwim(String swim) { this.swim = swim; }
    public void setDistance(int distance) { this.distance = distance; }

    public int getSizePool() { return sizePool; }
    public String getSwim() { return swim; }
    public int getDistance() { return distance; }
    public List<Race> getCurrentRaces() { return currentRaces; }
}
