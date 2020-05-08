package com.example.uniapp.models;

import android.app.Activity;

import com.example.uniapp.R;
import com.example.uniapp.views.AboutScreen;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class SetupLineChart {
    private List<List<Float>> inputValues;
    private LineChart lineChart;
    private Activity activity;

    public SetupLineChart(Activity activity, LineChart lineChart, List<List<Float>> inputValues) {
        this.activity = activity;
        this.lineChart = lineChart;
        this.inputValues = inputValues;
    }

    public static void configureMyLinesChart(Activity activity, List<List<Float>> inputValues, LineChart lineChart, List<String> legendLines, boolean isAnimation) {
        SetupLineChart setupLineChart = new SetupLineChart(activity, lineChart, inputValues);
        LineData lineData = setupLineChart.setupTrainingLinesData(inputValues, legendLines);
        lineChart.setScaleEnabled(false);
        setupLineChart.configureLineChart(lineData, isAnimation, legendLines);
    }

    public static void configureMyLineChart(Activity activity, List<Float> inputValues, LineChart lineChart, int idColor, boolean isAnimation) {
        List<List<Float>> inputValuesParent = new ArrayList<List<Float>>();
        inputValuesParent.add(inputValues);
        SetupLineChart setupLineChart = new SetupLineChart(activity, lineChart, inputValuesParent);
        LineData lineData = setupLineChart.setupTrainingLineData(inputValuesParent, null, idColor);
        lineChart.setScaleEnabled(false);
        setupLineChart.configureLineChart(lineData, isAnimation, null);
    }

    public LineData setupTrainingLineData(List<List<Float>> inputValues, List<String> legendLines, int idColor) {
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        for (int i = 0; i < inputValues.size(); i++) {
            ArrayList<Entry> yValues = setupTimesLineChart(inputValues, i);
            LineDataSet lineDataSet = new LineDataSet(yValues, (legendLines == null) ? "" : legendLines.get(i));
            setupLineDataSet(lineDataSet, idColor);
            dataSets.add(lineDataSet);
        }
        LineData data = new LineData(dataSets);
        data.setHighlightEnabled(false);
        return data;
    }

    public static ArrayList<Entry> setupTimesLineChart(List<List<Float>> inputValues, int setIndex) {
        ArrayList<Entry> result = new ArrayList<>();
        int cpt = 0;
        if (inputValues.get(setIndex) != null) {
            for (int i = 0; i < inputValues.get(setIndex).size(); i++) {
                result.add(new Entry(cpt, inputValues.get(setIndex).get(i)));
                cpt++;
            }
        }
        return result;
    }

    public LineData setupTrainingLinesData(List<List<Float>> inputValues, List<String> legendLines) {
        int[] colors = {R.color.colorSecondaryDark, R.color.redLight, R.color.greenLight, R.color.blueDeep, R.color.orangeLight, R.color.orangeLight};
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        for (int i = 0; i < inputValues.size(); i++) {
            ArrayList<Entry> yValues = setupTimesLineChart(inputValues, i);
            LineDataSet lineDataSet = new LineDataSet(yValues, (legendLines == null) ? "" : legendLines.get(i));
            setupLineDataSet(lineDataSet, activity.getResources().getColor(colors[(i%colors.length)]));
            dataSets.add(lineDataSet);
        }
        LineData data = new LineData(dataSets);
        data.setHighlightEnabled(false);
        return data;
    }

    public void configureLineChart(LineData lineData, boolean isAnimation, List<String> legendLines) {
        if (isAnimation) lineChart.animateXY(400, 800, Easing.EaseInOutQuad);
        else lineChart.animateXY(0, 0, Easing.EaseInOutQuad);
        lineChart.notifyDataSetChanged();
        lineChart.setData(lineData);
        lineChart.getDescription().setEnabled(false); //ok
        lineChart.setDrawGridBackground(false); //ok
        lineChart.getAxisLeft().setEnabled(false);
        lineChart.getAxisLeft().setSpaceTop(40);
        lineChart.getAxisLeft().setSpaceBottom(40);
        lineChart.getAxisLeft().setAxisMinimum(0.0f);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getXAxis().setEnabled(false);
        lineChart.setTouchEnabled(false);
        if (legendLines == null) lineChart.getLegend().setEnabled(false);
        else {
            lineChart.getLegend().setTextColor(activity.getResources().getColor((AboutScreen.isNightMode(activity) == true) ? R.color.textColorDark : R.color.textColorLight));
        }
    }

    public static void setupLineDataSet(LineDataSet lineDataSet, int idColor) {
        lineDataSet.setLineWidth(1.5f);
        lineDataSet.setCircleRadius(3f);
        lineDataSet.setCircleHoleRadius(1.5f);
        lineDataSet.setDrawValues(false);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillAlpha(100);
        lineDataSet.setColor(idColor);
        lineDataSet.setCircleColor(idColor);
        lineDataSet.setHighLightColor(idColor);
        lineDataSet.setFillColor(idColor);

    }
}
