package com.example.uniapp.controllers.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.uniapp.R;
import com.example.uniapp.controllers.fragments.CompetitionsFragment;
import com.example.uniapp.controllers.fragments.MainFragment;
import com.example.uniapp.controllers.fragments.SettingsFragment;
import com.example.uniapp.controllers.fragments.StatisticsFragment;
import com.example.uniapp.controllers.fragments.TrainingsFragment;
import com.example.uniapp.models.database.AppDataBase;
import com.example.uniapp.models.database.dao.ElementRepertories;
import com.example.uniapp.models.database.dao.pointFFN.PointFFN;
import com.example.uniapp.models.database.dao.pointFFN.PointFFNRepository;
import com.example.uniapp.models.database.dao.race.Race;
import com.example.uniapp.models.database.dao.race.RaceRepository;
import com.example.uniapp.models.database.dao.training.Training;
import com.example.uniapp.models.database.dao.training.TrainingRepository;
import com.example.uniapp.models.database.dao.user.User;
import com.example.uniapp.models.database.dao.user.UserRepository;
//import com.example.uniapp.utils.ImportPointsFFNTask;
import com.example.uniapp.utils.ImportPointsFFNTask;
import com.example.uniapp.views.AboutScreen;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.List;


//TODO: METTRE EN PLACE LA RECUPERATION DE TOUS LES TEMPS DU NAGEUR SUR FFN_EXTRANAT
//TODO: SAUVEGARDER LES COURSES DEJA EN PLACE SUR L'APPLICATION SYSTEME D'IMPORTATION DE DONNEE SUR SERVEUR ET
//      SAUVEGARDE DES DONNEES SUR TELEPHONE, PLUS GESTION POUR NE PAS ECRASER LES DONNEE DEJA SAUVEGADER
//TODO: METTRE EN PLACE LA MODIFICATION DES TEMPS DEJA ENREGISTRER
//TODO: ADD RACE --> UPDATE RACE

public class MainActivity extends AppCompatActivity {
    public static PointFFNRepository pointFFNRepository;
    public static RaceRepository raceRepository;
    public static TrainingRepository trainingRepository;
    public static UserRepository userRepository;

    private BottomNavigationView mBottomNavigationView;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navbar_custom_competition_btn:
                    configureAndShowFragment(new CompetitionsFragment());
                    return true;
                case R.id.navbar_custom_training_btn:
                    configureAndShowFragment(new TrainingsFragment(trainingRepository.getAllTrainings(), raceRepository.getRaces()));
                    return true;
                case R.id.navbar_custom_home_btn:
                    configureAndShowFragment(new MainFragment());
                    return true;
                case R.id.navbar_custom_statistic_btn:
                    configureAndShowFragment(new StatisticsFragment());
                    return true;
                case R.id.navbar_custom_settings_btn:
                    configureAndShowFragment(new SettingsFragment());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //pointFFNRepository = new PointFFNRepository(getApplication());
        //raceRepository     = new RaceRepository(getApplication());
        //trainingRepository = new TrainingRepository(getApplication());
        //userRepository     = new UserRepository(getApplication());

        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.navbar);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        //appDataBase  = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "uni_app_db").allowMainThreadQueries().build();

        if (getIntent().getSerializableExtra("EXTRA_NEW_USER") != null) {
            Log.e("HERE", "HERE");
            userRepository.insert((User) getIntent().getSerializableExtra("EXTRA_NEW_USER"));
            //pointFFNList = (List<PointFFN>) pointFFNRepository.getAllPoints();
            //appDataBase.userDAO().deleteAll();
            //appDataBase.userDAO().insert(user);
            // PointFFN.makePointFFNApiCall();
        }
        //startAsyncTask(getCurrentFocus(), userRepository, "User");
        startAsyncTask(getCurrentFocus(), raceRepository, "Race");
        startAsyncTask(getCurrentFocus(), trainingRepository, "Training");
        startAsyncTask(getCurrentFocus(), pointFFNRepository, "PointFFN");



        /*if (appDataBase.userDAO().getNbUser() == 0) {
            goSignInUser();
        } else { */
            //user = appDataBase.userDAO().getAll().get(0);
            configureAndShowFragment(new MainFragment());
        //}
    }

    public void configureAndShowFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.activty_main_fragment_layout, fragment).commit();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) AboutScreen.hideSystemUI(this);
    }

    private void goSignInUser() {
        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
        startActivity(intent);
        finish();
    }

    public void startAsyncTask(View v, ElementRepertories elementRepertories, String tag) {
        Log.e("Function", "IN");
        ImportPointsFFNTask importPointsFFNTask = new ImportPointsFFNTask(this, getApplication(), elementRepertories, tag);
        importPointsFFNTask.execute();
    }
}
