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
import com.example.uniapp.front.presenter.global.AboutScreen;
import com.example.uniapp.front.presenter.presenter_fragment.TrainingPresenter;
import com.example.uniapp.front.presenter.simplecallback.SwipeToDeleteCallback;
import com.example.uniapp.front.view.recyclerview.RvTrainingAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TrainingsFragment extends Fragment implements View.OnClickListener {
    private TrainingPresenter presenter;
    private View              layoutInflater;

    private TextView             training_title;
    private Spinner              dropdownPool;
    private List<Button>         btn_difficulty_stars;
    private Button               btn_all;
    private Button               btn_butterfly;
    private Button               btn_backstroke;
    private Button               btn_breaststroke;
    private Button               btn_freestyle;
    private Button               btn_IM;

    private RecyclerView      trainingRecyclerView;
    private RvTrainingAdapter trainingRecyclerViewAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutInflater = inflater.inflate(R.layout.fragment_training_bis, container, false);

        presenter = new TrainingPresenter(this);
        presenter.onStart();

        return layoutInflater;
    }

    @Override
    public void onClick(View v) {
             if (v.getTag().equals("btn_25"))       presenter.updateSizePool(25);
        else if (v.getTag().equals("btn_50"))       presenter.updateSizePool(50);
        else if (v.getTag().equals("all"))          presenter.updateSwim(v.getTag().toString());
        else if (v.getTag().equals("butterfly"))    presenter.updateSwim(v.getTag().toString());
        else if (v.getTag().equals("backstroke"))   presenter.updateSwim(v.getTag().toString());
        else if (v.getTag().equals("breaststroke")) presenter.updateSwim(v.getTag().toString());
        else if (v.getTag().equals("freestyle"))    presenter.updateSwim(v.getTag().toString());
        else if (v.getTag().equals("IM"))           presenter.updateSwim(v.getTag().toString());
        else if (v.getTag().equals("difficulty_1") && presenter.getDifficulty() == 1) presenter.updateDifficulty(0);
        else if (v.getTag().equals("difficulty_2") && presenter.getDifficulty() == 2) presenter.updateDifficulty(0);
        else if (v.getTag().equals("difficulty_3") && presenter.getDifficulty() == 3) presenter.updateDifficulty(0);
        else if (v.getTag().equals("difficulty_4") && presenter.getDifficulty() == 4) presenter.updateDifficulty(0);
        else if (v.getTag().equals("difficulty_5") && presenter.getDifficulty() == 5) presenter.updateDifficulty(0);
        else if (v.getTag().equals("difficulty_1")) presenter.updateDifficulty(1);
        else if (v.getTag().equals("difficulty_2")) presenter.updateDifficulty(2);
        else if (v.getTag().equals("difficulty_3")) presenter.updateDifficulty(3);
        else if (v.getTag().equals("difficulty_4")) presenter.updateDifficulty(4);
        else if (v.getTag().equals("difficulty_5")) presenter.updateDifficulty(5);
        else if (v.getTag().equals("addTraining"))  presenter.setupAddTrainingPopup();
    }

    public void setupUIElements() {
        int[] idButtons = {
                R.id.fragment_training_difficulty_star_1,
                R.id.fragment_training_difficulty_star_2,
                R.id.fragment_training_difficulty_star_3,
                R.id.fragment_training_difficulty_star_4,
                R.id.fragment_training_difficulty_star_5
        };
        btn_difficulty_stars = new ArrayList<>();
        for (int i = 0; i < 5; i++) btn_difficulty_stars.add(layoutInflater.findViewById(idButtons[i]));
        for (int i = 0; i < 5; i++) btn_difficulty_stars.get(i).setOnClickListener(this);

        training_title                            = layoutInflater.findViewById(R.id.fragment_training_title);
        btn_all                                   = layoutInflater.findViewById(R.id.fragment_training_all);
        btn_butterfly                             = layoutInflater.findViewById(R.id.fragment_training_butterfly);
        btn_backstroke                            = layoutInflater.findViewById(R.id.fragment_training_backstroke);
        btn_breaststroke                          = layoutInflater.findViewById(R.id.fragment_training_breaststroke);
        btn_freestyle                             = layoutInflater.findViewById(R.id.fragment_training_freestyle);
        btn_IM                                    = layoutInflater.findViewById(R.id.fragment_training_IM);
        FloatingActionButton floatingActionButton = layoutInflater.findViewById(R.id.fragment_training_floating_action_button);
        trainingRecyclerView                      = layoutInflater.findViewById(R.id.fragment_training_recycler_view);

        btn_all.setOnClickListener(this);
        btn_butterfly.setOnClickListener(this);
        btn_backstroke.setOnClickListener(this);
        btn_breaststroke.setOnClickListener(this);
        btn_freestyle.setOnClickListener(this);
        btn_IM.setOnClickListener(this);
        floatingActionButton.setOnClickListener(this);

        presenter.unlockUI();
    }


    public void updateColors() {
        if (getContext() != null) training_title.setTextColor(AboutScreen.getColorByThemeAttr(getContext(), R.attr.secondaryColor, R.color.colorSecondaryDark));
        btn_all.setTextColor(         (presenter.getSwim().equals("all"))          ? AboutScreen.getColorByThemeAttr(getContext(), R.attr.secondaryColor, R.color.colorSecondaryDark) : AboutScreen.getColorByThemeAttr(getContext(), R.attr.textColor, R.color.textColorDark));
        btn_butterfly.setTextColor(   (presenter.getSwim().equals("butterfly"))    ? AboutScreen.getColorByThemeAttr(getContext(), R.attr.secondaryColor, R.color.colorSecondaryDark) : AboutScreen.getColorByThemeAttr(getContext(), R.attr.textColor, R.color.textColorDark));
        btn_backstroke.setTextColor(  (presenter.getSwim().equals("backstroke"))   ? AboutScreen.getColorByThemeAttr(getContext(), R.attr.secondaryColor, R.color.colorSecondaryDark) : AboutScreen.getColorByThemeAttr(getContext(), R.attr.textColor, R.color.textColorDark));
        btn_breaststroke.setTextColor((presenter.getSwim().equals("breaststroke")) ? AboutScreen.getColorByThemeAttr(getContext(), R.attr.secondaryColor, R.color.colorSecondaryDark) : AboutScreen.getColorByThemeAttr(getContext(), R.attr.textColor, R.color.textColorDark));
        btn_freestyle.setTextColor(   (presenter.getSwim().equals("freestyle"))    ? AboutScreen.getColorByThemeAttr(getContext(), R.attr.secondaryColor, R.color.colorSecondaryDark) : AboutScreen.getColorByThemeAttr(getContext(), R.attr.textColor, R.color.textColorDark));
        btn_IM.setTextColor(          (presenter.getSwim().equals("IM"))           ? AboutScreen.getColorByThemeAttr(getContext(), R.attr.secondaryColor, R.color.colorSecondaryDark) : AboutScreen.getColorByThemeAttr(getContext(), R.attr.textColor, R.color.textColorDark));
    }

    public void updateDifficulty() {
        if (getActivity() != null) {
            if (AboutScreen.isNightMode(getActivity())) {
                for (int i = 0; i < btn_difficulty_stars.size(); i++) btn_difficulty_stars.get(i).setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_radio_button_unchecked_white_24dp), null, null, null);
                for (int i = 0; i < presenter.getDifficulty(); i++)  btn_difficulty_stars.get(i).setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_radio_button_checked_white_24dp), null, null, null);
            } else {
                for (int i = 0; i < btn_difficulty_stars.size(); i++) btn_difficulty_stars.get(i).setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_radio_button_unchecked_black_24dp), null, null, null);
                for (int i = 0; i < presenter.getDifficulty(); i++)  btn_difficulty_stars.get(i).setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_radio_button_checked_black_24dp), null, null, null);
            }
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
        if (getActivity() != null) {

            trainingRecyclerViewAdapter = new RvTrainingAdapter(getActivity(), presenter.getCurrentTrainings());
            trainingRecyclerView.setLayoutManager(new LinearLayoutManager(layoutInflater.getContext()));
            trainingRecyclerView.setAdapter(trainingRecyclerViewAdapter);
            trainingRecyclerView.setHasFixedSize(true);
            trainingRecyclerView.setNestedScrollingEnabled(false);
            trainingRecyclerViewAdapter.notifyDataSetChanged();
            ItemTouchHelper onSwipe = new ItemTouchHelper(new SwipeToDeleteCallback() {
                @Override
                public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                    if (getContext() != null) {
                        setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_delete_white_48dp));
                        setBackground(ContextCompat.getDrawable(getContext(), R.drawable.sh_gradient_blue));
                    }
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    super.onSwiped(viewHolder, direction);
                    int position = viewHolder.getAdapterPosition();
                    trainingRecyclerViewAdapter.removeItem(position);
                    presenter.updateCurrentTrainings();
                }
            });
            onSwipe.attachToRecyclerView(trainingRecyclerView);
        }
    }

    public Spinner getDropdownPool() { return dropdownPool; }
}
