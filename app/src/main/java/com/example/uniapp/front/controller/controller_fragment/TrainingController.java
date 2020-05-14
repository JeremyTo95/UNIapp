package com.example.uniapp.front.controller.controller_fragment;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.uniapp.front.controller.comparator.TrainingDateComparator;
import com.example.uniapp.front.controller.global.Controller;
import com.example.uniapp.front.model.data.Training;
import com.example.uniapp.front.model.market.MarketTrainings;
import com.example.uniapp.front.view.fragments.TrainingsFragment;
import com.example.uniapp.front.view.popup.AddTrainingPopup;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class TrainingController extends Controller {
    private TrainingsFragment view;
    private AddTrainingPopup  addTrainingPopup;

    private List<Training> currentTrainings;
    private int            sizePool;
    private String         swim;
    private int            difficulty;

    public TrainingController(TrainingsFragment view) {
        super(view);
        this.view    = view;
    }

    @Override
    public void onStart() {
        super.onStart();
        sizePool   = 25;
        swim       = "all";
        difficulty = 0;
        updateCurrentTrainings();
        view.setupUIElements();
        view.setupSizePoolDropdown();
        updatePoolSizeSpinner();
        view.updateColors();
        view.updateDifficulty();
        view.setupTrainingList();
    }

    public void updateCurrentTrainings() {
        currentTrainings = MarketTrainings.getTrainingsBySizePoolSwimDifficulty(getRoomDataBase().trainingDAO().getAllTrainings(), sizePool, swim, difficulty);
        Collections.sort(currentTrainings, new TrainingDateComparator());
    }

    private void updatePoolSizeSpinner() {
        view.getDropdownPool().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String newSizePool = parent.getItemAtPosition(position).toString().replaceAll("[a-zA-Z]", "").replace(" ", "");
                sizePool           = Integer.parseInt(newSizePool);
                updateCurrentTrainings();
                TrainingController.this.view.setupTrainingList();
            }
        });
    }

    public void setupAddTrainingPopup() {
        addTrainingPopup = new AddTrainingPopup(view.getActivity());
        addTrainingPopup.build();
        addTrainingPopup.getBtn_confirmed().setOnClickListener(v -> {
            if (addTrainingPopup.isEnabled()) {
                Log.e("ADD", "Add training");
                Training training = new Training(UUID.randomUUID().toString(), addTrainingPopup.getNewDifficulty(), addTrainingPopup.getNewSizePool(), addTrainingPopup.getNewDate(), addTrainingPopup.getNewCity(), addTrainingPopup.getTrainingBlockList());
                insertNewTraining(training);
            }
        });
    }

    private void insertNewTraining(Training training) {
        getRoomDataBase().trainingDAO().insertTraining(training);
        addTrainingPopup.dismiss();
        updateCurrentTrainings();
        view.setupTrainingList();
        Toast.makeText(getContext(), "Nouvel entrainement enregistré", Toast.LENGTH_SHORT).show();
    }

    public void updateDifficulty(int difficulty) {
        this.difficulty = difficulty;
        view.updateDifficulty();
        view.setupTrainingList();
    }

    public void updateSwim(String swim) {
        this.swim = swim;
        view.updateColors();
        view.setupTrainingList();
    }

    public void updateSizePool(int sizePool) {
        this.sizePool = sizePool;
        view.setupTrainingList();
    }

    public List<Training> getCurrentTrainings() { updateCurrentTrainings(); return currentTrainings; }
    public int getSizePool()                    { return sizePool; }
    public String getSwim()                     { return swim; }
    public int getDifficulty()                  { return difficulty; }
    public void setSizePool(int sizePool)       { this.sizePool = sizePool; }
    public void setSwim(String swim)            { this.swim = swim; }

}
