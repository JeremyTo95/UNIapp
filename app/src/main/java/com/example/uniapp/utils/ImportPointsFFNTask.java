package com.example.uniapp.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.room.Room;

import com.example.uniapp.controllers.activities.MainActivity;
import com.example.uniapp.models.database.AppDataBase;
import com.example.uniapp.models.database.dao.ElementRepertories;
import com.example.uniapp.models.database.dao.pointFFN.PointFFN;
import com.example.uniapp.models.database.dao.pointFFN.PointFFNAPI;
import com.example.uniapp.models.database.dao.pointFFN.PointFFNRepository;
import com.example.uniapp.models.database.dao.race.RaceRepository;
import com.example.uniapp.models.database.dao.training.Training;
import com.example.uniapp.models.database.dao.training.TrainingRepository;
import com.example.uniapp.models.database.dao.user.User;
import com.example.uniapp.models.database.dao.user.UserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImportPointsFFNTask extends AsyncTask<Void, Void, Void> {
    private WeakReference<Activity> weakReference;
    private Application application;
    private ElementRepertories elementRepertories;
    private String tag;

    public ImportPointsFFNTask(Activity activity, Application application, String tag) {
        weakReference = new WeakReference<>(activity);
        this.application = application;
        this.elementRepertories = elementRepertories;
        this.tag = tag;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Log.e("IN BACKGROUND", "start");
        if (tag.equals("User")) elementRepertories = new UserRepository(application);
        else if (tag.equals("Race")) elementRepertories = new RaceRepository(application);
        else if (tag.equals("Training")) elementRepertories = new TrainingRepository(application);
        else elementRepertories = new PointFFNRepository(application);

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.e("IN PRE EXECUTE", "start");
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        System.out.println("nbElements : " + elementRepertories.getNbElement());
        Activity activity = weakReference.get();
        if(activity == null) {
            return;
        }
        Log.e("IN POST EXECUTE", "start");
    }
}