package com.example.uniapp.front.view.fragments;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
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
import com.example.uniapp.front.controller.controller_fragment.CompetitionController;
import com.example.uniapp.front.controller.simplecallback.SwipeToDeleteCallback;
import com.example.uniapp.front.model.graphicsitem.BuildLineChart;
import com.example.uniapp.front.model.graphicsitem.SwimCard;
import com.example.uniapp.front.model.market.MarketRaces;
import com.example.uniapp.front.model.market.MarketSwim;
import com.example.uniapp.front.view.recyclerview.RvRaceAdapter;
import com.github.mikephil.charting.charts.LineChart;

import java.util.List;

import static android.widget.AdapterView.LayoutParams;

public class CompetitionsFragment extends Fragment implements View.OnClickListener {
    private CompetitionController controller;

    private View                 layoutInflater;
    private TextView             competition_title;
    private TextView             progression_title;
    private Button               btn_25m;
    private Button               btn_50m;
    private GestureDetector      gestureDetector;
    private HorizontalScrollView horizontalScrollView;
    private List<SwimCard>       swimCardList;
    private LinearLayout         linearLayout;
    private Spinner              selectSwimDistance;
    private LineChart            lineChart;
    private Button               addRaceTimeBtn;
    private RecyclerView         recyclerView;
    private RvRaceAdapter        rvRaceAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutInflater = inflater.inflate(R.layout.fragment_competition, container, false);

        controller = new CompetitionController(this);
        controller.onStart();

        return layoutInflater;
    }

    public void setupUIElements() {
        competition_title    = layoutInflater.findViewById(R.id.fragment_competition_competition_title);
        btn_25m              = layoutInflater.findViewById(R.id.fragment_competition_pool_25);
        btn_50m              = layoutInflater.findViewById(R.id.fragment_competition_pool_50);
        progression_title    = layoutInflater.findViewById(R.id.fragment_competition_progression_title);
        horizontalScrollView = layoutInflater.findViewById(R.id.horizontalScrollView);
        linearLayout         = layoutInflater.findViewById(R.id.linearLayout);
        selectSwimDistance   = layoutInflater.findViewById(R.id.fragment_competition_spinner);
        lineChart            = layoutInflater.findViewById(R.id.fragment_competition_linechart);
        addRaceTimeBtn       = layoutInflater.findViewById(R.id.fragment_competition_add_race_time);
        recyclerView         = layoutInflater.findViewById(R.id.fragment_competition_recycler_view);

        btn_25m.setOnClickListener(this);
        btn_50m.setOnClickListener(this);
        addRaceTimeBtn.setOnClickListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.distance_spe, R.layout.dropdown_item_auto);
        adapter.setDropDownViewResource(R.layout.dropdown_all_items);
        selectSwimDistance.setAdapter(adapter);
        selectSwimDistance.setSelection(1);

        controller.updateSelectedDistance();
    }

    public void updateUIColors() {
        competition_title.setTextColor(MarketSwim.getCurrentColor(getContext(), controller.getSwim()));
        progression_title.setTextColor(MarketSwim.getCurrentColor(getContext(), controller.getSwim()));
        btn_25m.setTextColor((controller.getSizePool() == 25) ? MarketSwim.getCurrentColor(getContext(), controller.getSwim()) : AboutScreen.getColorByThemeAttr(getContext(), R.attr.textColor, R.color.textColorDark));
        btn_50m.setTextColor((controller.getSizePool() == 50) ? MarketSwim.getCurrentColor(getContext(), controller.getSwim()) : AboutScreen.getColorByThemeAttr(getContext(), R.attr.textColor, R.color.textColorDark));
    }

    @Override
    public void onClick(View v) {
        if      (v.getTag().equals("pool25")) controller.updateToPool25();
        else if (v.getTag().equals("pool50")) controller.updateToPool50();
        else if (v.getTag().equals("addBtn")) controller.setupAddRacePopup();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setupSelectedSwim() {
        double widthCard       =  AboutScreen.getWidth(getActivity()) * 0.9;
        double widthCardMargin = (AboutScreen.getWidth(getActivity()) * 0.1)/2;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int)(widthCard), LayoutParams.MATCH_PARENT);
        layoutParams.setMargins((int) widthCardMargin, 0, (int) widthCardMargin, 0);

        swimCardList = controller.loadSwimCards();
        linearLayout.removeAllViews();
        for (int i = 0; i < swimCardList.size(); i++) linearLayout.addView(swimCardList.get(i), i, layoutParams);
        horizontalScrollView.removeAllViews();
        horizontalScrollView.addView(linearLayout);

        gestureDetector = controller.loadGestureSwimCard();
        controller.configureHorizontalScrollViewTouchListener();
    }

    public void setupLineChart(boolean isAnimation) {
        BuildLineChart.configureMyLineChart(getActivity(), MarketRaces.getFloatTimes(controller.getCurrentRaces()), lineChart, MarketSwim.getCurrentColor(getContext(), controller.getSwim()), isAnimation);
    }

    public void setupRaceList() {
        rvRaceAdapter = new RvRaceAdapter(getActivity(), getContext(), controller.getCurrentRaces()) {
            @Override
            public void undoDelete() {
                super.undoDelete();
                controller.updateCurrentRaces();
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
                setBackground(ContextCompat.getDrawable(getContext(), MarketSwim.getCurrentDrawable(controller.getSwim())));
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                super.onSwiped(viewHolder, direction);
                int position = viewHolder.getAdapterPosition();
                rvRaceAdapter.removeItem(position);
                controller.updateCurrentRaces();
                setupSelectedSwim();
                setupLineChart(false);
            }
        });
        onSwipe.attachToRecyclerView(recyclerView);
    }

    public Spinner getSelectSwimDistance() { return selectSwimDistance; }
    public CompetitionController getController() { return controller; }
    public List<SwimCard> getSwimCardList() { return swimCardList; }
    public HorizontalScrollView getHorizontalScrollView() { return horizontalScrollView; }
    public GestureDetector getGestureDetector() { return gestureDetector; }
}