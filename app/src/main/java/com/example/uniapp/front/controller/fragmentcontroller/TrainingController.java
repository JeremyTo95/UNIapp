package com.example.uniapp.front.controller.fragmentcontroller;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.uniapp.back.room.RoomDataBase;
import com.example.uniapp.front.controller.global.Controller;
import com.example.uniapp.front.controller.comparator.TrainingDateComparator;
import com.example.uniapp.front.model.data.Training;
import com.example.uniapp.front.model.market.MarketTrainings;
import com.example.uniapp.front.view.fragments.TrainingsFragment;
import com.example.uniapp.front.view.popup.AddTrainingPopup;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class TrainingController extends Controller {
    private TrainingsFragment view;
    private Context context;
    private RoomDataBase roomDataBase;

    private List<Training> currentTrainings;
    private int    sizePool;
    private String swim;
    private int    difficulty;

    public TrainingController(TrainingsFragment view) {
        super(view);
        this.view = view;
        this.context           = view.getContext();
        this.roomDataBase      = RoomDataBase.getDatabase(context);
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
        currentTrainings = MarketTrainings.getTrainingsBySizePoolSwimDifficulty(roomDataBase.trainingDAO().getAllTrainings(), sizePool, swim, difficulty);
        Collections.sort(currentTrainings, new TrainingDateComparator());
    }

    public void updatePoolSizeSpinner() {
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
        final AddTrainingPopup addTrainingPopup = new AddTrainingPopup(view.getActivity());
        addTrainingPopup.build();
        addTrainingPopup.getBtn_confirmed().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addTrainingPopup.isEnabled()) {
                    Log.e("ADD", "Add training");
                    Training training = new Training(UUID.randomUUID().toString(), addTrainingPopup.getNewDifficulty(), addTrainingPopup.getNewSizePool(), addTrainingPopup.getNewDate(), addTrainingPopup.getNewCity(), addTrainingPopup.getTrainingBlockList());
                    roomDataBase.trainingDAO().insertTraining(training);
                    addTrainingPopup.dismiss();
                    updateCurrentTrainings();
                    view.setupTrainingList();
                    Toast.makeText(context, "Nouvel entrainement enregistré", Toast.LENGTH_SHORT).show();
                }
            }
        });
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

    public List<Training> getCurrentTrainings() { return currentTrainings; }
    public int getSizePool() { return sizePool; }
    public void setSizePool(int sizePool) { this.sizePool = sizePool; }
    public String getSwim() { return swim; }
    public void setSwim(String swim) { this.swim = swim; }
    public int getDifficulty() { return difficulty; }

}
