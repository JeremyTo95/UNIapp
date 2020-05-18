package com.example.uniapp.front.view.fragments;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.os.Bundle;
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
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uniapp.R;
import com.example.uniapp.front.presenter.global.AboutScreen;
import com.example.uniapp.front.presenter.presenter_fragment.CompetitionPresenter;
import com.example.uniapp.front.presenter.simplecallback.SwipeToDeleteCallback;
import com.example.uniapp.front.model.graphicsitem.BuildLineChart;
import com.example.uniapp.front.model.graphicsitem.SwimCard;
import com.example.uniapp.front.model.market.MarketRaces;
import com.example.uniapp.front.model.market.MarketSwim;
import com.example.uniapp.front.view.recyclerview.RvRaceAdapter;
import com.github.mikephil.charting.charts.LineChart;

import java.util.List;

import static android.widget.AdapterView.LayoutParams;

public class CompetitionsFragment extends Fragment implements View.OnClickListener {
    private CompetitionPresenter presenter;

    private View                 layoutInflater;
    private TextView             competition_title;
    private TextView             progression_title;
    private Button               btn_25m;
    private Button               btn_50m;
    private GestureDetectorCompat gestureDetector;
    private HorizontalScrollView horizontalScrollView;
    private List<SwimCard>       swimCardList;
    private LinearLayout         linearLayout;
    private Spinner              selectSwimDistance;
    private LineChart            lineChart;
    private RecyclerView         recyclerView;
    private RvRaceAdapter        rvRaceAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutInflater = inflater.inflate(R.layout.fragment_competition, container, false);

        presenter = new CompetitionPresenter(this);
        presenter.onStart();

        return layoutInflater;
    }

    public void setupUIElements() {
        competition_title     = layoutInflater.findViewById(R.id.fragment_competition_competition_title);
        btn_25m               = layoutInflater.findViewById(R.id.fragment_competition_pool_25);
        btn_50m               = layoutInflater.findViewById(R.id.fragment_competition_pool_50);
        progression_title     = layoutInflater.findViewById(R.id.fragment_competition_progression_title);
        horizontalScrollView  = layoutInflater.findViewById(R.id.horizontalScrollView);
        linearLayout          = layoutInflater.findViewById(R.id.linearLayout);
        selectSwimDistance    = layoutInflater.findViewById(R.id.fragment_competition_spinner);
        lineChart             = layoutInflater.findViewById(R.id.fragment_competition_linechart);
        recyclerView          = layoutInflater.findViewById(R.id.fragment_competition_recycler_view);
        Button addRaceTimeBtn = layoutInflater.findViewById(R.id.fragment_competition_add_race_time);

        btn_25m.setOnClickListener(this);
        btn_50m.setOnClickListener(this);
        addRaceTimeBtn.setOnClickListener(this);

        ArrayAdapter<CharSequence> adapter = presenter.getDistanceAdapter();
        selectSwimDistance.setAdapter(adapter);
        selectSwimDistance.setSelection(1);

        presenter.updateSelectedDistance();
    }

    public void updateUIColors() {
        competition_title.setTextColor(presenter.getCurrentColor());
        progression_title.setTextColor(presenter.getCurrentColor());
        btn_25m.setTextColor((presenter.getSizePool() == 25) ? presenter.getCurrentColor() : presenter.getTextColor());
        btn_50m.setTextColor((presenter.getSizePool() == 50) ? presenter.getCurrentColor() : presenter.getTextColor());
    }

    @Override
    public void onClick(View v) {
        if      (v.getTag().equals("pool25")) presenter.updateToPool25();
        else if (v.getTag().equals("pool50")) presenter.updateToPool50();
        else if (v.getTag().equals("addBtn")) presenter.setupAddRacePopup();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setupSelectedSwim() {
        if (getActivity() != null) {
            double widthCard       =  AboutScreen.getWidth(getActivity()) * 0.9;
            double widthCardMargin = (AboutScreen.getWidth(getActivity()) * 0.1)/2;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int)(widthCard), LayoutParams.MATCH_PARENT);
            layoutParams.setMargins((int) widthCardMargin, 0, (int) widthCardMargin, 0);

            swimCardList = presenter.loadSwimCards();
            linearLayout.removeAllViews();
            for (int i = 0; i < swimCardList.size(); i++) linearLayout.addView(swimCardList.get(i), i, layoutParams);
            horizontalScrollView.removeAllViews();
            horizontalScrollView.addView(linearLayout);

            gestureDetector = presenter.loadGestureSwimCard();
            presenter.configureHorizontalScrollViewTouchListener();
        }
    }

    public void setupLineChart(boolean isAnimation) {
        BuildLineChart.configureMyLineChart(getActivity(), MarketRaces.getFloatTimes(presenter.getCurrentRaces()), lineChart, MarketSwim.getCurrentColor(getContext(), presenter.getSwim()), isAnimation);
    }

    public void setupRaceList() {
        rvRaceAdapter = new RvRaceAdapter(getActivity(), getContext(), presenter.getCurrentRaces()) {
            @Override
            public void undoDelete() {
                super.undoDelete();
                presenter.updateCurrentRaces();
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
                if (getContext() != null) {
                    setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_delete_white_48dp));
                    setBackground(ContextCompat.getDrawable(getContext(), MarketSwim.getCurrentDrawable(presenter.getSwim())));
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                super.onSwiped(viewHolder, direction);
                int position = viewHolder.getAdapterPosition();
                rvRaceAdapter.removeItem(position);
                presenter.updateCurrentRaces();
                setupSelectedSwim();
                setupLineChart(false);
            }
        });
        onSwipe.attachToRecyclerView(recyclerView);
    }

    public Spinner getSelectSwimDistance() { return selectSwimDistance; }
    public CompetitionPresenter getPresenter() { return presenter; }
    public List<SwimCard> getSwimCardList() { return swimCardList; }
    public HorizontalScrollView getHorizontalScrollView() { return horizontalScrollView; }
    public GestureDetectorCompat getGestureDetector() { return gestureDetector; }
}