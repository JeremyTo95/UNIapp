package com.example.uniapp.models.database.dao.pointFFN;

import android.app.Activity;
import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.lifecycle.LiveData;

import com.example.uniapp.models.database.AppDataBase;
import com.example.uniapp.models.database.dao.ElementRepertories;
import com.example.uniapp.utils.ImportPointsFFNTask;

import java.util.List;

public class PointFFNRepository extends ElementRepertories {
    private PointFFNDAO pointFFNDAO;
    private List<PointFFN> pointFFNList;

    public PointFFNRepository(Application application) {
        AppDataBase appDataBase = AppDataBase.getDatabase(application);
        pointFFNDAO = appDataBase.pointFFNDAO();
        pointFFNList = pointFFNDAO.getAllPoints();
    }

    public List<PointFFN> getAllPoints() {
        return pointFFNList;
    }

    public int getNbElement() { return pointFFNDAO.getNb(); }

    public PointFFN getPointsFFNByGenderDistanceSwimTime(String gender, int distance, String swim, Float time) {
        return pointFFNDAO.getPointsFFNByGenderDistanceSwimTime(gender, distance, swim, time);
    }

    public void deleteAll() {
        pointFFNDAO.deleteAllPointFFN();
    }

    public void insert (final PointFFN pointFFN) {
        pointFFNDAO.insertPointFFN(pointFFN);
    }

    public static void startAsyncTaskLoadingPointsFFN(Activity activity, LinearLayout linearLayout) {
        Log.e("Function", "IN");
        ImportPointsFFNTask importPointsFFNTask = new ImportPointsFFNTask(activity, linearLayout);
        importPointsFFNTask.execute();
    }
}
