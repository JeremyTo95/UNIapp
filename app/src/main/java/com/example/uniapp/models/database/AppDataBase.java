package com.example.uniapp.models.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.uniapp.controllers.activities.MainActivity;
import com.example.uniapp.models.database.dao.pointFFN.PointFFN;
import com.example.uniapp.models.database.dao.pointFFN.PointFFNAPI;
import com.example.uniapp.models.database.dao.pointFFN.PointFFNDAO;
import com.example.uniapp.models.database.dao.pointFFN.PointFFNRepository;
import com.example.uniapp.models.database.dao.race.Race;
import com.example.uniapp.models.database.dao.race.RaceDAO;
import com.example.uniapp.models.database.dao.training.Training;
import com.example.uniapp.models.database.dao.training.TrainingDAO;
import com.example.uniapp.models.database.dao.trainingblock.TrainingBlock;
import com.example.uniapp.models.database.dao.user.User;
import com.example.uniapp.models.database.dao.user.UserDAO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@TypeConverters({Converters.class})
@Database(entities = {User.class, PointFFN.class, Race.class, Training.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    private static volatile AppDataBase INSTANCE;
    public static String URL_DATA = "https://raw.githubusercontent.com/JeremyTo95/myData/";

    public abstract UserDAO     userDAO();
    public abstract PointFFNDAO pointFFNDAO();
    public abstract RaceDAO     raceDAO();
    public abstract TrainingDAO trainingDAO();

    public static AppDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "uni_app_db").allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            PointFFNDAO pointFFNDAO = INSTANCE.pointFFNDAO();
            pointFFNDAO.deleteAllPointFFN();

            Gson gson = new GsonBuilder().setLenient().create();
            Retrofit retrofit = new Retrofit.Builder().baseUrl(AppDataBase.URL_DATA).addConverterFactory(GsonConverterFactory.create(gson)).build();
            PointFFNAPI pointFFNAPI = retrofit.create(PointFFNAPI.class);
            Call<List<PointFFN>> call = pointFFNAPI.getResponsePointsFFN();

            call.enqueue(new retrofit2.Callback<List<PointFFN>>() {
                @Override
                public void onResponse(Call<List<PointFFN>> call, Response<List<PointFFN>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<PointFFN> pointFFNList = response.body();
                        System.out.println("before : " + pointFFNList.size());
                        for (int i = 0; i < pointFFNList.size(); i++) {
                            pointFFNDAO.insertPointFFN(pointFFNList.get(i));
                        }
                        System.out.println("after : " + pointFFNDAO.getNb());
                    }
                }

                @Override
                public void onFailure(Call<List<PointFFN>> call, Throwable t) { }
            });
        }
    };
}
