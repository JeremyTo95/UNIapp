package com.example.uniapp.front.presenter.listener;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

import com.example.uniapp.front.model.graphicsitem.SwimCard;
import com.example.uniapp.front.model.market.MarketSwim;

import java.util.List;

public class SwimCardListener extends GestureDetector.SimpleOnGestureListener {
    private HorizontalScrollView horizontalScrollView;
    private List<SwimCard> swimItem;
    private int                  indexSwimCard;

    private static final int SWIPE_MIN_DISTANCE        = 5;
    private static final int SWIPE_THRESHOLD_VELOCITY  = 100;

    protected SwimCardListener(List<SwimCard> swimItem, HorizontalScrollView horizontalScrollView, String swim) {
        this.swimItem             = swimItem;
        this.horizontalScrollView = horizontalScrollView;
        indexSwimCard = MarketSwim.getSwimIndex(swim);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        try {
            //right to left
            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                indexSwimCard = (indexSwimCard < (swimItem.size() - 1)) ? indexSwimCard + 1 : swimItem.size() - 1;
                horizontalScrollView.smoothScrollTo(indexSwimCard * horizontalScrollView.getMeasuredWidth(), 0);
                System.out.println(indexSwimCard);
                return true;
            }
            //left to right
            else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                indexSwimCard = (indexSwimCard > 0) ? indexSwimCard - 1 : 0;
                horizontalScrollView.smoothScrollTo(indexSwimCard * horizontalScrollView.getMeasuredWidth(), 0);
                System.out.println(indexSwimCard);
                return true;
            }
        } catch (Exception e) {
            // nothing
        }
        return false;
    }

    protected int getIndexSwimCard() { return indexSwimCard; }
}
