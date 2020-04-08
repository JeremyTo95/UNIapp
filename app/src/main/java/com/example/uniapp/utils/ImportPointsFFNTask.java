package com.example.uniapp.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.room.Room;

import com.example.uniapp.controllers.activities.MainActivity;
import com.example.uniapp.models.database.AppDataBase;
import com.example.uniapp.models.database.dao.pointFFN.PointFFN;
import com.example.uniapp.models.database.dao.pointFFN.PointFFNAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImportPointsFFNTask extends AsyncTask<Void, Void, AppDataBase> {
    private ProgressBar progressBar;
    private Context context;
    private AppDataBase appDataBase;

    public ImportPointsFFNTask(Context context, ProgressBar progressBar) {
        this.context = context;
        this.progressBar = progressBar;
    }

    @Override
    protected AppDataBase doInBackground(Void... voids) {
        Log.e("IN BACKGROUND", "start");
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppDataBase.URL_DATA)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        final PointFFNAPI pointFFNAPI = retrofit.create(PointFFNAPI.class);

        Call<List<PointFFN>> call = pointFFNAPI.getResponsePointsFFN();
        call.enqueue(new Callback<List<PointFFN>>() {
            @Override
            public void onFailure(Call<List<PointFFN>> call, Throwable t) {
            }

            @Override
            public void onResponse(Call<List<PointFFN>> call, Response<List<PointFFN>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<PointFFN> pointFFNList = response.body();
                    System.out.println("size : " + pointFFNList.size());
                    if (appDataBase.pointFFNDAO().getAllPoints().size() == 0) {
                        for (int i = 0; i < pointFFNList.size(); i++) {
                            appDataBase.pointFFNDAO().insertPointFFN(pointFFNList.get(i));
                        }
                    }
                }
            }
        });

        return appDataBase;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.e("IN PRE EXECUTE", "start");
        progressBar.setVisibility(View.VISIBLE);
        appDataBase  = Room.databaseBuilder(context, AppDataBase.class, "uni_app_db").allowMainThreadQueries().build();
    }

    @Override
    protected void onPostExecute(AppDataBase appDataBase) {
        super.onPostExecute(appDataBase);
        Log.e("IN POST EXECUTE", "start");
        System.out.println("nbPoint : " + appDataBase.pointFFNDAO().getNbPoint());
        MainActivity.appDataBase = appDataBase;
        progressBar.setVisibility(View.GONE);
    }

    private void loadPointsFFN() {
        if (MainActivity.appDataBase.pointFFNDAO().getNbPoint() != 0) {
            MainActivity.pointFFNList = MainActivity.appDataBase.pointFFNDAO().getAllPoints();
        } else {
            PointFFN.makePointFFNApiCall();
        }
    }
}

/*

Bonjour Monsieur, j'ai réussi a intégrer les points dans mon application, merci. J'ai stocké le JSON sur un github. Maintenant j'essaye de stocker les données sur une base de donnée pour avoir les informations même en hors connexion (les points, plus d'autre données aussi). J'ai réussi à mettre en place une base de donnée Room. Elle fonctionne sans problème et j'ai pu sauvegarder toutes mes données directement dans le téléphone. 
Mais dans mon idée, lors du premier lancement de l'application, on arrive sur une page d'inscription (en local) pour obtenir les informations de bases de l'utilisateur. Ensuite on appuie sur un bouton validé qui enregistre les données et qui charge les points de l'API vers la base de donnée interne. Ca fonctionne bien mais l'application est bloqué car le chargement prend beaucoup de temps. J'ai essayé de mettre en place un AsyncTask pour ajouter des threads et ça ne marche pas. En fait, le thread fonctionne mais la fonction pour l'appel et l'enregistrement des données 

 */
