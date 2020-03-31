package com.example.uniapp.controllers.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.uniapp.R;
import com.example.uniapp.controllers.adapters.RecyclerViewTrainingAdapter;
import com.example.uniapp.models.MarketRaces;
import com.example.uniapp.models.MarketTrainings;
import com.example.uniapp.models.Training;
import com.example.uniapp.views.TrainingDateComparator;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrainingsFragment extends Fragment implements View.OnClickListener {
    private View layoutInflater;
    private AppBarLayout appBarLayout;
    private RelativeLayout relativeLayout;
    private List<Training> allTrainings;
    private int screenHeight;
    private List<Training> currentTrainings;

    private int sizePool;
    private String swim;
    private int difficulty;

    private TextView training_title;
    private Spinner dropdownPool;
    private List<Button> btn_difficulty_stars;
    private Button btn_all;
    private Button btn_butterfly;
    private Button btn_backstroke;
    private Button btn_breaststroke;
    private Button btn_freestyle;
    private Button btn_IM;

    private RecyclerView trainingRecyclerView;
    private RecyclerViewTrainingAdapter trainingRecyclerViewAdapter;

    public TrainingsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutInflater = inflater.inflate(R.layout.fragment_training, container, false);
        appBarLayout   = layoutInflater.findViewById(R.id.fragment_training_appbar);
        screenHeight   = getResources().getDisplayMetrics().heightPixels;

        training_title       = (TextView) layoutInflater.findViewById(R.id.fragment_training_title);
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
        btn_all              = (Button) layoutInflater.findViewById(R.id.fragment_training_all);
        btn_butterfly        = (Button) layoutInflater.findViewById(R.id.fragment_training_butterfly);
        btn_backstroke       = (Button) layoutInflater.findViewById(R.id.fragment_training_backstroke);
        btn_breaststroke     = (Button) layoutInflater.findViewById(R.id.fragment_training_breaststroke);
        btn_freestyle        = (Button) layoutInflater.findViewById(R.id.fragment_training_freestyle);
        btn_IM               = (Button) layoutInflater.findViewById(R.id.fragment_training_IM);
        trainingRecyclerView = (RecyclerView) layoutInflater.findViewById(R.id.fragment_training_recycler_view);

        btn_all.setOnClickListener(this);
        btn_butterfly.setOnClickListener(this);
        btn_backstroke.setOnClickListener(this);
        btn_breaststroke.setOnClickListener(this);
        btn_freestyle.setOnClickListener(this);
        btn_IM.setOnClickListener(this);

        sizePool   = 25;
        swim       = "all";
        difficulty = 0;

        allTrainings     = MarketTrainings.initTrainings();
        currentTrainings = MarketTrainings.getTrainingsBySizePoolSwimDifficulty(allTrainings, sizePool, swim, difficulty);

        updateColors();
        configureAndShowSizePoolDropdown();
        updateRecyclerViewTrainingList();

        return layoutInflater;
    }

    @Override
    public void onClick(View v) {
        System.out.println(v.getTag());
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
        updateColors();
        updateDifficulty();
        updateRecyclerViewTrainingList();
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
        currentTrainings.clear();
        currentTrainings = new ArrayList<Training>();
        currentTrainings = MarketTrainings.getTrainingsBySizePoolSwimDifficulty(allTrainings, sizePool, swim, difficulty);
        Collections.sort(currentTrainings, new TrainingDateComparator());
    }

    private void updateRecyclerViewTrainingList() {
        updateCurrentTrainings();

        trainingRecyclerViewAdapter = new RecyclerViewTrainingAdapter(currentTrainings);
        trainingRecyclerView.setLayoutManager(new LinearLayoutManager(layoutInflater.getContext()));
        trainingRecyclerView.setAdapter(trainingRecyclerViewAdapter);
        trainingRecyclerViewAdapter.notifyDataSetChanged();
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        MarketTrainings.removeTrainings(allTrainings, currentTrainings.get(viewHolder.getAdapterPosition()));
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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(layoutInflater.getContext(), R.array.sizePool, R.layout.fragment_training_size_pool_dropdown);
        adapter.setDropDownViewResource(R.layout.fragment_training_size_pool_dropdown);
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
