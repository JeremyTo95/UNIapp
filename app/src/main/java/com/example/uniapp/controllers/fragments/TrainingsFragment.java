package com.example.uniapp.controllers.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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
import com.example.uniapp.controllers.adapters.RvTrainingAdapter;
import com.example.uniapp.models.MarketTrainings;
import com.example.uniapp.models.database.dao.race.Race;
import com.example.uniapp.models.database.dao.training.Training;
import com.example.uniapp.views.popup.AddTrainingPopup;
import com.example.uniapp.views.comparators.TrainingDateComparator;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class TrainingsFragment extends Fragment implements View.OnClickListener {
    private View layoutInflater;
    private AppBarLayout appBarLayout;
    private List<Training> currentTrainings;

    private int sizePool;
    private String swim;
    private int difficulty;

    private TextView             training_title;
    private Spinner              dropdownPool;
    private List<Button>         btn_difficulty_stars;
    private Button               btn_all;
    private Button               btn_butterfly;
    private Button               btn_backstroke;
    private Button               btn_breaststroke;
    private Button               btn_freestyle;
    private Button               btn_IM;
    private FloatingActionButton floatingActionButton;

    private RecyclerView trainingRecyclerView;
    private RvTrainingAdapter trainingRecyclerViewAdapter;

    public TrainingsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutInflater = inflater.inflate(R.layout.fragment_training, container, false);
        appBarLayout   = layoutInflater.findViewById(R.id.fragment_training_appbar);

        sizePool   = 25;
        swim       = "all";
        difficulty = 0;

        configureHeader();
        updateColors();
        configureAndShowSizePoolDropdown();
        updateRecyclerViewTrainingList();

        return layoutInflater;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateCurrentTrainings();
        updateRecyclerViewTrainingList();
    }

    @Override
    public void onClick(View v) {
        if (v.getTag().equals("btn_25")) sizePool = 25;
        else if (v.getTag().equals("btn_50")) sizePool = 50;
        else if (v.getTag().equals("all")) swim = v.getTag().toString();
        else if (v.getTag().equals("butterfly")) swim = v.getTag().toString();
        else if (v.getTag().equals("backstroke")) swim = v.getTag().toString();
        else if (v.getTag().equals("breaststroke")) swim = v.getTag().toString();
        else if (v.getTag().equals("freestyle")) swim = v.getTag().toString();
        else if (v.getTag().equals("IM")) swim = v.getTag().toString();
        else if (v.getTag().equals("difficulty_1") && difficulty == 1) difficulty = 0;
        else if (v.getTag().equals("difficulty_2") && difficulty == 2) difficulty = 0;
        else if (v.getTag().equals("difficulty_3") && difficulty == 3) difficulty = 0;
        else if (v.getTag().equals("difficulty_4") && difficulty == 4) difficulty = 0;
        else if (v.getTag().equals("difficulty_5") && difficulty == 5) difficulty = 0;
        else if (v.getTag().equals("difficulty_1")) difficulty = 1;
        else if (v.getTag().equals("difficulty_2")) difficulty = 2;
        else if (v.getTag().equals("difficulty_3")) difficulty = 3;
        else if (v.getTag().equals("difficulty_4")) difficulty = 4;
        else if (v.getTag().equals("difficulty_5")) difficulty = 5;
        else if (v.getTag().equals("addTraining")) configureAndShowAddTrainingPopup();

        updateColors();
        updateDifficulty();
        updateRecyclerViewTrainingList();
    }

    private void configureHeader() {
        int[] idButtons = {
                R.id.fragment_training_difficulty_star_1,
                R.id.fragment_training_difficulty_star_2,
                R.id.fragment_training_difficulty_star_3,
                R.id.fragment_training_difficulty_star_4,
                R.id.fragment_training_difficulty_star_5
        };
        btn_difficulty_stars = new ArrayList<Button>();
        for (int i = 0; i < 5; i++) btn_difficulty_stars.add((Button) layoutInflater.findViewById(idButtons[i]));
        for (int i = 0; i < 5; i++) btn_difficulty_stars.get(i).setOnClickListener(this);

        training_title       = (TextView)             layoutInflater.findViewById(R.id.fragment_training_title);
        btn_all              = (Button)               layoutInflater.findViewById(R.id.fragment_training_all);
        btn_butterfly        = (Button)               layoutInflater.findViewById(R.id.fragment_training_butterfly);
        btn_backstroke       = (Button)               layoutInflater.findViewById(R.id.fragment_training_backstroke);
        btn_breaststroke     = (Button)               layoutInflater.findViewById(R.id.fragment_training_breaststroke);
        btn_freestyle        = (Button)               layoutInflater.findViewById(R.id.fragment_training_freestyle);
        btn_IM               = (Button)               layoutInflater.findViewById(R.id.fragment_training_IM);
        floatingActionButton = (FloatingActionButton) layoutInflater.findViewById(R.id.fragment_training_floating_action_button);
        trainingRecyclerView = (RecyclerView)         layoutInflater.findViewById(R.id.fragment_training_recycler_view);

        btn_all.setOnClickListener(this);
        btn_butterfly.setOnClickListener(this);
        btn_backstroke.setOnClickListener(this);
        btn_breaststroke.setOnClickListener(this);
        btn_freestyle.setOnClickListener(this);
        btn_IM.setOnClickListener(this);
        floatingActionButton.setOnClickListener(this);
    }

    private void configureAndShowAddTrainingPopup() {
        final AddTrainingPopup addTrainingPopup = new AddTrainingPopup(getActivity());
        addTrainingPopup.build();
        addTrainingPopup.getBtn_confirmed().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("swims      : " + addTrainingPopup.getNewSizePool());
                System.out.println("sets       : " + addTrainingPopup.getNewSet());
                System.out.println("zones      : " + addTrainingPopup.getNewZone());
                System.out.println("distance   : " + addTrainingPopup.getNewDistance());
                System.out.println("date       : " + addTrainingPopup.getNewDate());
                System.out.println("city       : " + addTrainingPopup.getNewCity());
                System.out.println("difficulty : " + addTrainingPopup.getNewDifficulty());
                System.out.println("sizePool   : " + addTrainingPopup.getNewSizePool());
                if (addTrainingPopup.isEnabled()) {
                    Log.e("ADD", "Add training");
                    Training training = new Training(UUID.randomUUID().toString(), addTrainingPopup.getNewDifficulty(), addTrainingPopup.getNewSizePool(), addTrainingPopup.getNewDate(), addTrainingPopup.getNewCity(), addTrainingPopup.getTrainingBlockList());
                    MainActivity.appDataBase.trainingDAO().insertTraining(training);
                    addTrainingPopup.dismiss();
                    updateCurrentTrainings();
                    updateRecyclerViewTrainingList();
                    Toast.makeText(getContext(), "Nouvel entrainement enregistré", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateColors() {
        training_title.setTextColor(getResources().getColor(R.color.colorSecondaryLight));
        btn_all.setTextColor(getResources().getColor((swim.equals("all")) ? R.color.colorSecondaryLight : R.color.colorText));
        btn_butterfly.setTextColor(getResources().getColor((swim.equals("butterfly")) ? R.color.colorSecondaryLight : R.color.colorText));
        btn_backstroke.setTextColor(getResources().getColor((swim.equals("backstroke")) ? R.color.colorSecondaryLight : R.color.colorText));
        btn_breaststroke.setTextColor(getResources().getColor((swim.equals("breaststroke")) ? R.color.colorSecondaryLight : R.color.colorText));
        btn_freestyle.setTextColor(getResources().getColor((swim.equals("freestyle")) ? R.color.colorSecondaryLight : R.color.colorText));
        btn_IM.setTextColor(getResources().getColor((swim.equals("IM")) ? R.color.colorSecondaryLight : R.color.colorText));
    }

    private void updateDifficulty() {
        for (int i = 0; i < btn_difficulty_stars.size(); i++) btn_difficulty_stars.get(i).setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_radio_button_unchecked_white_24dp), null, null, null);
        for (int i = 0; i < difficulty; i++)                  btn_difficulty_stars.get(i).setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_radio_button_checked_white_24dp), null, null, null);
    }

    private void updateCurrentTrainings() {
        currentTrainings = MarketTrainings.getTrainingsBySizePoolSwimDifficulty(MainActivity.appDataBase.trainingDAO().getAllTrainings(), sizePool, swim, difficulty);
        Collections.sort(currentTrainings, new TrainingDateComparator());
    }

    private void updateRecyclerViewTrainingList() {
        updateCurrentTrainings();
        System.out.println("sizeTraining : " + currentTrainings.size());
        trainingRecyclerViewAdapter = new RvTrainingAdapter(getContext(), currentTrainings);
        trainingRecyclerView.setLayoutManager(new LinearLayoutManager(layoutInflater.getContext()));
        trainingRecyclerView.setAdapter(trainingRecyclerViewAdapter);
        trainingRecyclerView.setNestedScrollingEnabled(false);
        trainingRecyclerView.setHasFixedSize(true);
        trainingRecyclerView.setItemViewCacheSize(20);
        trainingRecyclerViewAdapter.notifyDataSetChanged();
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        MainActivity.appDataBase.trainingDAO().deleteTraining(currentTrainings.get(viewHolder.getAdapterPosition()));
                        currentTrainings.remove(viewHolder.getAdapterPosition());
                        trainingRecyclerViewAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                    }

                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(trainingRecyclerView);
    }

    private void configureAndShowSizePoolDropdown() {
        dropdownPool = layoutInflater.findViewById(R.id.fragment_training_spinner_sizePool);
        dropdownPool.setPopupBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(layoutInflater.getContext(), R.array.sizePool, R.layout.dropdown_item);
        adapter.setDropDownViewResource(R.layout.dropdown_item);
        dropdownPool.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        dropdownPool.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String newSizePool = parent.getItemAtPosition(position).toString();
                newSizePool = newSizePool.replaceAll("[a-zA-Z ]", "");
                sizePool = Integer.parseInt(newSizePool);
                updateRecyclerViewTrainingList();
            }
        });
    }
}
