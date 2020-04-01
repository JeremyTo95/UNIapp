package com.example.uniapp.controllers.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.uniapp.R;
import com.example.uniapp.views.SwimCards;

import java.util.List;

public class SwimItemAdapter extends PagerAdapter {
    private List<SwimCards> swimCardsList;
    private Context context;
    private int sizePool;

    private LinearLayout rightSide;
    private TextView titleSwim;
    private TextView time1, time2, time3;
    private TextView time4, time5, time6;

    public SwimItemAdapter(List<SwimCards> swimTimes, Context context, int sizePool) {
        swimCardsList = swimTimes;
        this.context  = context;
        this.sizePool = sizePool;
    }

    @Override
    public int getCount() {
        return swimCardsList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.swim_items, container, false);

        configureAndShowUIElements(view, position);
        configureAndSetSwimItemElement(position);

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    private void configureAndShowUIElements(View view, int position) {
        rightSide = view.findViewById(R.id.swim_items_p2);
        LinearLayout.LayoutParams rightSideParams = (LinearLayout.LayoutParams) rightSide.getLayoutParams();
        titleSwim = view.findViewById(R.id.swim_items_name);
        time1     = view.findViewById(R.id.swim_items_d1);
        time2     = view.findViewById(R.id.swim_items_d2);
        time3     = view.findViewById(R.id.swim_items_d3);
        time4     = view.findViewById(R.id.swim_items_d4);
        time5     = view.findViewById(R.id.swim_items_d5);
        time6     = view.findViewById(R.id.swim_items_d6);

        titleSwim.setText(swimCardsList.get(position).getTitleSwim());
        titleSwim.setTextColor(view.getResources().getColor(swimCardsList.get(position).getColorText()));
    }

    private void configureAndSetSwimItemElement(int position) {
        switch (swimCardsList.get(position).getTitleSwim()) {
            case "P a p i l l o n":
            case "D o s":
            case "B r a s s e":
                rightSide.setVisibility(LinearLayout.GONE);
                time1.setText("50m   : " + swimCardsList.get(position).getTime50());
                time2.setText("100m  : " + swimCardsList.get(position).getTime100());
                time3.setText("200m  : " + swimCardsList.get(position).getTime200());
                break;

            case "N a g e  L i b r e":
                time1.setText("50m   : " + swimCardsList.get(position).getTime50());
                time2.setText("100m  : " + swimCardsList.get(position).getTime100());
                time3.setText("200m  : " + swimCardsList.get(position).getTime200());
                time4.setText("400m  : " + swimCardsList.get(position).getTime400());
                time5.setText("800m  : " + swimCardsList.get(position).getTime800());
                time6.setText("1500m : " + swimCardsList.get(position).getTime1500());
                break;

            case "4  N a g e s":
                rightSide.setVisibility(LinearLayout.GONE);
                if (sizePool == 25) {
                    time1.setText("100m  : " + swimCardsList.get(position).getTime100());
                    time2.setText("200m  : " + swimCardsList.get(position).getTime200());
                    time3.setText("400m  : " + swimCardsList.get(position).getTime400());
                } else {
                    time1.setText("200m  : " + swimCardsList.get(position).getTime200());
                    time2.setText("400m  : " + swimCardsList.get(position).getTime400());
                    time3.setText("");
                }
                break;
        }
    }
}
