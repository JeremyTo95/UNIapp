package com.example.uniapp.controllers.fragments;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
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
import com.example.uniapp.controllers.adapters.recyclerview.RvTrainingAdapter;
import com.example.uniapp.models.markets.MarketTrainings;
import com.example.uniapp.models.database.dao.training.Training;
import com.example.uniapp.models.swiptodeletecallback.SwipeToDeleteCallback;
import com.example.uniapp.views.AboutScreen;
import com.example.uniapp.views.popup.training.AddTrainingPopup;
import com.example.uniapp.views.comparators.TrainingDateComparator;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class TrainingsFragment extends Fragment implements View.OnClickListener {
    private View           layoutInflater;
    private AppBarLayout   appBarLayout;
    private List<Training> currentTrainings;

    private int    sizePool;
    private String swim;
    private int    difficulty;

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

    private RecyclerView      trainingRecyclerView;
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
        updateDifficulty();
        setupSizePoolDropdown();
        updateCurrentTrainings();
        setupTrainingList();

        return layoutInflater;
    }

    @Override
    public void onResume() {
        super.onResume();
        configureHeader();
        setupSizePoolDropdown();
        updateCurrentTrainings();
        setupTrainingList();
    }

    @Override
    public void onClick(View v) {
             if (v.getTag().equals("btn_25"))       sizePool = 25;
        else if (v.getTag().equals("btn_50"))       sizePool = 50;
        else if (v.getTag().equals("all"))          swim = v.getTag().toString();
        else if (v.getTag().equals("butterfly"))    swim = v.getTag().toString();
        else if (v.getTag().equals("backstroke"))   swim = v.getTag().toString();
        else if (v.getTag().equals("breaststroke")) swim = v.getTag().toString();
        else if (v.getTag().equals("freestyle"))    swim = v.getTag().toString();
        else if (v.getTag().equals("IM"))           swim = v.getTag().toString();
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
        else if (v.getTag().equals("addTraining"))  setupAddTrainingPopup();

        updateColors();
        updateDifficulty();
        setupTrainingList();
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

    private void setupAddTrainingPopup() {
        final AddTrainingPopup addTrainingPopup = new AddTrainingPopup(getActivity());
        addTrainingPopup.build();
        addTrainingPopup.getBtn_confirmed().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addTrainingPopup.isEnabled()) {
                    Log.e("ADD", "Add training");
                    Training training = new Training(UUID.randomUUID().toString(), addTrainingPopup.getNewDifficulty(), addTrainingPopup.getNewSizePool(), addTrainingPopup.getNewDate(), addTrainingPopup.getNewCity(), addTrainingPopup.getTrainingBlockList());
                    MainActivity.appDataBase.trainingDAO().insertTraining(training);
                    addTrainingPopup.dismiss();
                    updateCurrentTrainings();
                    setupTrainingList();
                    Toast.makeText(getContext(), "Nouvel entrainement enregistré", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateColors() {
        training_title.setTextColor(AboutScreen.getColorByThemeAttr(getContext(), R.attr.secondaryColor, R.color.colorSecondaryDark));
        btn_all.setTextColor(         (swim.equals("all"))          ? AboutScreen.getColorByThemeAttr(getContext(), R.attr.secondaryColor, R.color.colorSecondaryDark) : AboutScreen.getColorByThemeAttr(getContext(), R.attr.textColor, R.color.textColorDark));
        btn_butterfly.setTextColor(   (swim.equals("butterfly"))    ? AboutScreen.getColorByThemeAttr(getContext(), R.attr.secondaryColor, R.color.colorSecondaryDark) : AboutScreen.getColorByThemeAttr(getContext(), R.attr.textColor, R.color.textColorDark));
        btn_backstroke.setTextColor(  (swim.equals("backstroke"))   ? AboutScreen.getColorByThemeAttr(getContext(), R.attr.secondaryColor, R.color.colorSecondaryDark) : AboutScreen.getColorByThemeAttr(getContext(), R.attr.textColor, R.color.textColorDark));
        btn_breaststroke.setTextColor((swim.equals("breaststroke")) ? AboutScreen.getColorByThemeAttr(getContext(), R.attr.secondaryColor, R.color.colorSecondaryDark) : AboutScreen.getColorByThemeAttr(getContext(), R.attr.textColor, R.color.textColorDark));
        btn_freestyle.setTextColor(   (swim.equals("freestyle"))    ? AboutScreen.getColorByThemeAttr(getContext(), R.attr.secondaryColor, R.color.colorSecondaryDark) : AboutScreen.getColorByThemeAttr(getContext(), R.attr.textColor, R.color.textColorDark));
        btn_IM.setTextColor(          (swim.equals("IM"))           ? AboutScreen.getColorByThemeAttr(getContext(), R.attr.secondaryColor, R.color.colorSecondaryDark) : AboutScreen.getColorByThemeAttr(getContext(), R.attr.textColor, R.color.textColorDark));
    }

    private void updateDifficulty() {
        if (AboutScreen.isNightMode(getActivity())) {
            for (int i = 0; i < btn_difficulty_stars.size(); i++) btn_difficulty_stars.get(i).setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_radio_button_unchecked_white_24dp), null, null, null);
            for (int i = 0; i < difficulty; i++)                  btn_difficulty_stars.get(i).setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_radio_button_checked_white_24dp), null, null, null);
        } else {
            for (int i = 0; i < btn_difficulty_stars.size(); i++) btn_difficulty_stars.get(i).setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_radio_button_unchecked_black_24dp), null, null, null);
            for (int i = 0; i < difficulty; i++)                  btn_difficulty_stars.get(i).setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_radio_button_checked_black_24dp), null, null, null);
        }
    }

    private void updateCurrentTrainings() {
        currentTrainings = MarketTrainings.getTrainingsBySizePoolSwimDifficulty(MainActivity.appDataBase.trainingDAO().getAllTrainings(), sizePool, swim, difficulty);
        Collections.sort(currentTrainings, new TrainingDateComparator());
    }

    private void setupSizePoolDropdown() {
        dropdownPool = layoutInflater.findViewById(R.id.fragment_training_spinner_sizePool);
        dropdownPool.setPopupBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(layoutInflater.getContext(), R.array.sizePool, R.layout.dropdown_item_auto);
        adapter.setDropDownViewResource(R.layout.dropdown_all_items);
        dropdownPool.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        dropdownPool.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String newSizePool = parent.getItemAtPosition(position).toString().replaceAll("[a-zA-Z]", "").replace(" ", "");
                sizePool           = Integer.parseInt(newSizePool);
                setupTrainingList();
            }
        });
    }

    private void setupTrainingList() {
        updateCurrentTrainings();
        trainingRecyclerViewAdapter = new RvTrainingAdapter(getActivity(), currentTrainings);
        trainingRecyclerView.setLayoutManager(new LinearLayoutManager(layoutInflater.getContext()));
        trainingRecyclerView.setAdapter(trainingRecyclerViewAdapter);
        trainingRecyclerView.setNestedScrollingEnabled(false);
        trainingRecyclerViewAdapter.notifyDataSetChanged();
        ItemTouchHelper onSwipe = new ItemTouchHelper(new SwipeToDeleteCallback() {
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_delete_white_48dp));
                setBackground(ContextCompat.getDrawable(getContext(), R.drawable.sh_gradient_blue));
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                super.onSwiped(viewHolder, direction);
                int position = viewHolder.getAdapterPosition();
                trainingRecyclerViewAdapter.removeItem(position);
            }
        });
        onSwipe.attachToRecyclerView(trainingRecyclerView);
    }
}
