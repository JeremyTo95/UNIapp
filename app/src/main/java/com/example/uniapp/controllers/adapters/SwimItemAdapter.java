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
import com.example.uniapp.models.SwimCards;

import java.util.List;

public class SwimItemAdapter extends PagerAdapter {
    private List<SwimCards> mSwimCardsList;
    private Context mContext;

    public SwimItemAdapter(List<SwimCards> swimTimes, Context context) {
        mSwimCardsList = swimTimes;
        mContext       = context;
    }

    @Override
    public int getCount() {
        return mSwimCardsList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.swim_items, container, false);

        LinearLayout leftSide, rightSide;
        TextView titleSwim;
        TextView time1, time2, time3;
        TextView time4, time5, time6;

        leftSide  = view.findViewById(R.id.swim_items_p1);
        rightSide = view.findViewById(R.id.swim_items_p2);
        LinearLayout.LayoutParams rightSideParams = (LinearLayout.LayoutParams) rightSide.getLayoutParams();
        titleSwim = view.findViewById(R.id.swim_items_name);
        time1     = view.findViewById(R.id.swim_items_d1);
        time2     = view.findViewById(R.id.swim_items_d2);
        time3     = view.findViewById(R.id.swim_items_d3);
        time4     = view.findViewById(R.id.swim_items_d4);
        time5     = view.findViewById(R.id.swim_items_d5);
        time6     = view.findViewById(R.id.swim_items_d6);

        // linearLayout.setBackgroundDrawable(view.getResources().getDrawable(mModelList.get(position).getGradientBackground()));
        titleSwim.setText(mSwimCardsList.get(position).getTitleSwim());
        titleSwim.setTextColor(view.getResources().getColor(mSwimCardsList.get(position).getColorText()));
        switch (mSwimCardsList.get(position).getTitleSwim()) {
            case "P a p i l l o n":
            case "D o s":
            case "B r a s s e":
                rightSide.setVisibility(LinearLayout.GONE);
                time1.setText("50m   : " + mSwimCardsList.get(position).getTime50());
                time2.setText("100m  : " + mSwimCardsList.get(position).getTime100());
                time3.setText("200m  : " + mSwimCardsList.get(position).getTime200());
                break;

            case "N a g e  L i b r e":
                time1.setText("50m   : " + mSwimCardsList.get(position).getTime50());
                time2.setText("100m  : " + mSwimCardsList.get(position).getTime100());
                time3.setText("200m  : " + mSwimCardsList.get(position).getTime200());
                time4.setText("400m  : " + mSwimCardsList.get(position).getTime400());
                time5.setText("800m  : " + mSwimCardsList.get(position).getTime800());
                time6.setText("1500m : " + mSwimCardsList.get(position).getTime1500());
                break;

            case "4  N a g e s":
                rightSide.setVisibility(LinearLayout.GONE);
                time1.setText("100m  : " + mSwimCardsList.get(position).getTime100());
                time2.setText("200m  : " + mSwimCardsList.get(position).getTime200());
                time3.setText("400m  : " + mSwimCardsList.get(position).getTime400());
                break;
        }
        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
