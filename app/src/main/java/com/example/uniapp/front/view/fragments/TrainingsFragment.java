package com.example.uniapp.front.view.fragments;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uniapp.R;
import com.example.uniapp.front.controller.global.AboutScreen;
import com.example.uniapp.front.controller.fragmentcontroller.TrainingController;
import com.example.uniapp.front.controller.simplecallback.SwipeToDeleteCallback;
import com.example.uniapp.front.view.recyclerview.RvTrainingAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TrainingsFragment extends Fragment implements View.OnClickListener {
    private TrainingController controller;
    private View               layoutInflater;

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutInflater = inflater.inflate(R.layout.fragment_training, container, false);

        controller = new TrainingController(this);
        controller.onStart();

        return layoutInflater;
    }

    @Override
    public void onClick(View v) {
             if (v.getTag().equals("btn_25"))       controller.updateSizePool(25);
        else if (v.getTag().equals("btn_50"))       controller.updateSizePool(50);
        else if (v.getTag().equals("all"))          controller.updateSwim(v.getTag().toString());
        else if (v.getTag().equals("butterfly"))    controller.updateSwim(v.getTag().toString());
        else if (v.getTag().equals("backstroke"))   controller.updateSwim(v.getTag().toString());
        else if (v.getTag().equals("breaststroke")) controller.updateSwim(v.getTag().toString());
        else if (v.getTag().equals("freestyle"))    controller.updateSwim(v.getTag().toString());
        else if (v.getTag().equals("IM"))           controller.updateSwim(v.getTag().toString());
        else if (v.getTag().equals("difficulty_1") && controller.getDifficulty() == 1) controller.updateDifficulty(0);
        else if (v.getTag().equals("difficulty_2") && controller.getDifficulty() == 2) controller.updateDifficulty(0);
        else if (v.getTag().equals("difficulty_3") && controller.getDifficulty() == 3) controller.updateDifficulty(0);
        else if (v.getTag().equals("difficulty_4") && controller.getDifficulty() == 4) controller.updateDifficulty(0);
        else if (v.getTag().equals("difficulty_5") && controller.getDifficulty() == 5) controller.updateDifficulty(0);
        else if (v.getTag().equals("difficulty_1")) controller.updateDifficulty(1);
        else if (v.getTag().equals("difficulty_2")) controller.updateDifficulty(2);
        else if (v.getTag().equals("difficulty_3")) controller.updateDifficulty(3);
        else if (v.getTag().equals("difficulty_4")) controller.updateDifficulty(4);
        else if (v.getTag().equals("difficulty_5")) controller.updateDifficulty(5);
        else if (v.getTag().equals("addTraining"))  controller.setupAddTrainingPopup();
    }

    public void setupUIElements() {
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

        controller.unlockUI();
    }


    public void updateColors() {
        training_title.setTextColor(AboutScreen.getColorByThemeAttr(getContext(), R.attr.secondaryColor, R.color.colorSecondaryDark));
        btn_all.setTextColor(         (controller.getSwim().equals("all"))          ? AboutScreen.getColorByThemeAttr(getContext(), R.attr.secondaryColor, R.color.colorSecondaryDark) : AboutScreen.getColorByThemeAttr(getContext(), R.attr.textColor, R.color.textColorDark));
        btn_butterfly.setTextColor(   (controller.getSwim().equals("butterfly"))    ? AboutScreen.getColorByThemeAttr(getContext(), R.attr.secondaryColor, R.color.colorSecondaryDark) : AboutScreen.getColorByThemeAttr(getContext(), R.attr.textColor, R.color.textColorDark));
        btn_backstroke.setTextColor(  (controller.getSwim().equals("backstroke"))   ? AboutScreen.getColorByThemeAttr(getContext(), R.attr.secondaryColor, R.color.colorSecondaryDark) : AboutScreen.getColorByThemeAttr(getContext(), R.attr.textColor, R.color.textColorDark));
        btn_breaststroke.setTextColor((controller.getSwim().equals("breaststroke")) ? AboutScreen.getColorByThemeAttr(getContext(), R.attr.secondaryColor, R.color.colorSecondaryDark) : AboutScreen.getColorByThemeAttr(getContext(), R.attr.textColor, R.color.textColorDark));
        btn_freestyle.setTextColor(   (controller.getSwim().equals("freestyle"))    ? AboutScreen.getColorByThemeAttr(getContext(), R.attr.secondaryColor, R.color.colorSecondaryDark) : AboutScreen.getColorByThemeAttr(getContext(), R.attr.textColor, R.color.textColorDark));
        btn_IM.setTextColor(          (controller.getSwim().equals("IM"))           ? AboutScreen.getColorByThemeAttr(getContext(), R.attr.secondaryColor, R.color.colorSecondaryDark) : AboutScreen.getColorByThemeAttr(getContext(), R.attr.textColor, R.color.textColorDark));
    }

    public void updateDifficulty() {
        if (AboutScreen.isNightMode(getActivity())) {
            for (int i = 0; i < btn_difficulty_stars.size(); i++) btn_difficulty_stars.get(i).setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_radio_button_unchecked_white_24dp), null, null, null);
            for (int i = 0; i < controller.getDifficulty(); i++)  btn_difficulty_stars.get(i).setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_radio_button_checked_white_24dp), null, null, null);
        } else {
            for (int i = 0; i < btn_difficulty_stars.size(); i++) btn_difficulty_stars.get(i).setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_radio_button_unchecked_black_24dp), null, null, null);
            for (int i = 0; i < controller.getDifficulty(); i++)  btn_difficulty_stars.get(i).setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_radio_button_checked_black_24dp), null, null, null);
        }
    }

    public void setupSizePoolDropdown() {
        dropdownPool = layoutInflater.findViewById(R.id.fragment_training_spinner_sizePool);
        dropdownPool.setPopupBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(layoutInflater.getContext(), R.array.sizePool, R.layout.dropdown_item_auto);
        adapter.setDropDownViewResource(R.layout.dropdown_all_items);
        dropdownPool.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void setupTrainingList() {
        trainingRecyclerViewAdapter = new RvTrainingAdapter(getActivity(), controller.getCurrentTrainings());
        trainingRecyclerView.setLayoutManager(new LinearLayoutManager(layoutInflater.getContext()));
        trainingRecyclerView.setAdapter(trainingRecyclerViewAdapter);
        trainingRecyclerView.setHasFixedSize(true);
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
                controller.updateCurrentTrainings();
            }
        });
        onSwipe.attachToRecyclerView(trainingRecyclerView);
    }

    public Spinner getDropdownPool() { return dropdownPool; }
}
