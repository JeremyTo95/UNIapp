package com.example.uniapp.back.asynctask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.example.uniapp.back.api.PointFFNAPI;
import com.example.uniapp.back.executor.AppExecutors;
import com.example.uniapp.back.gson.GsonManager;
import com.example.uniapp.back.room.RoomDataBase;
import com.example.uniapp.front.controller.global.AboutScreen;
import com.example.uniapp.front.model.data.PointFFN;
import com.example.uniapp.front.view.actvities.MainActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImportPointsFFNTask extends AsyncTask<Void, Void, Void> {
    @SuppressLint("StaticFieldLeak")
    private Activity activity;
    private RoomDataBase roomDataBase;

    public ImportPointsFFNTask(Activity activity) {
        this.activity      = activity;
        this.roomDataBase  = RoomDataBase.getDatabase(activity.getApplicationContext());
    }

    @Override
    protected Void doInBackground(Void... voids) {
        roomDataBase.pointFFNDAO().deleteAll();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RoomDataBase.URL_DATA).addConverterFactory(GsonConverterFactory.create(GsonManager.getGsonInstance())).build();
        PointFFNAPI pointFFNAPI = retrofit.create(PointFFNAPI.class);
        Call<List<PointFFN>> call = pointFFNAPI.getResponsePointsFFN();

        call.enqueue(new Callback<List<PointFFN>>() {
            @Override
            public void onResponse(Call<List<PointFFN>> call, Response<List<PointFFN>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<PointFFN> pointFFNList = response.body();
                    AppExecutors.getInstance(activity.getApplicationContext()).getDiskIo().execute(() -> {
                        System.out.println("nbPointsFFN to load : " + pointFFNList.size());
                        for (int i = 0; i < pointFFNList.size(); i++) {
                            PointFFN currentPointFFN = pointFFNList.get(i);
                            roomDataBase.pointFFNDAO().insert(currentPointFFN);
                            System.out.println("input value " + i);
                            int indexElementLoaded = i+1;
                            AppExecutors.getInstance(activity.getApplicationContext()).getUIThread().execute(() -> AboutScreen.lockUI((MainActivity) activity, true, indexElementLoaded + " / " + pointFFNList.size() + " points ffn chargés"));
                        }
                        AppExecutors.getInstance(activity.getApplicationContext()).getUIThread().execute(() -> AboutScreen.unlockUI((MainActivity) activity));
                    });
                }
            }

            @Override
            public void onFailure(Call<List<PointFFN>> call, Throwable t) {
                Log.e("ERREUR", "didn't connect to pointFFN API");
            }
        });

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}