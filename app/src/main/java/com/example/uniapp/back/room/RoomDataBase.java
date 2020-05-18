package com.example.uniapp.back.room;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.uniapp.front.model.data.PointFFN;
import com.example.uniapp.back.dao.PointFFNDAO;
import com.example.uniapp.front.model.data.Race;
import com.example.uniapp.back.dao.RaceDAO;
import com.example.uniapp.front.model.data.Training;
import com.example.uniapp.back.dao.TrainingDAO;
import com.example.uniapp.front.model.data.User;
import com.example.uniapp.back.dao.UserDAO;

@TypeConverters({Converters.class})
@Database(entities = {User.class, PointFFN.class, Race.class, Training.class}, version = 1, exportSchema = false)
public abstract class RoomDataBase extends RoomDatabase {

    private static volatile RoomDataBase INSTANCE;
    public static String URL_DATA = "https://raw.githubusercontent.com/JeremyTo95/myData/";

    public abstract UserDAO     userDAO();
    public abstract PointFFNDAO pointFFNDAO();
    public abstract RaceDAO     raceDAO();
    public abstract TrainingDAO trainingDAO();

    public static RoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoomDataBase.class) {
                if (INSTANCE == null) {
                    Log.i("Room", "new room instance is loading");
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), RoomDataBase.class, "uni_app_db").allowMainThreadQueries().build();
                }
            }
        }

        return INSTANCE;
    }
}
