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
import com.example.uniapp.models.database.dao.pointFFN.PointFFN;
import com.example.uniapp.models.database.dao.race.Race;
import com.example.uniapp.models.database.dao.training.Training;
import com.example.uniapp.models.database.AppDataBase;
import com.example.uniapp.models.database.dao.user.User;
import com.example.uniapp.utils.ImportPointsFFNTask;
import com.example.uniapp.utils.MyHandlerThread;
import com.example.uniapp.views.AboutScreen;
import com.example.uniapp.views.popup.SignInPopup;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.List;


//TODO: METTRE EN PLACE LA RECUPERATION DE TOUS LES TEMPS DU NAGEUR SUR FFN_EXTRANAT
//TODO: SAUVEGARDER LES COURSES DEJA EN PLACE SUR L'APPLICATION SYSTEME D'IMPORTATION DE DONNEE SUR SERVEUR ET
//      SAUVEGARDE DES DONNEES SUR TELEPHONE, PLUS GESTION POUR NE PAS ECRASER LES DONNEE DEJA SAUVEGADER
//TODO: METTRE EN PLACE LA MODIFICATION DES TEMPS DEJA ENREGISTRER
//TODO: ADD RACE --> UPDATE RACE

public class MainActivity extends AppCompatActivity {
    public static AppDataBase    appDataBase;
    public static List<PointFFN> pointFFNList;
    public static List<Race>     allRaces;
    public static List<Training> allTrainings;
    public static User           user;

    private ProgressBar progressBar;

    private BottomNavigationView mBottomNavigationView;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navbar_custom_competition_btn:
                    configureAndShowFragment(new CompetitionsFragment(allRaces));
                    return true;
                case R.id.navbar_custom_training_btn:
                    configureAndShowFragment(new TrainingsFragment(allTrainings, allRaces));
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

        progressBar = findViewById(R.id.glb_progress_bar);
        progressBar.setVisibility(View.GONE);

        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.navbar);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        appDataBase  = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "uni_app_db").allowMainThreadQueries().build();

        if (getIntent().getSerializableExtra("EXTRA_NEW_USER") != null) {
            Log.e("HERE", "HERE");
            user = (User) getIntent().getSerializableExtra("EXTRA_NEW_USER");
            appDataBase.userDAO().deleteAll();
            appDataBase.userDAO().insert(user);
            // PointFFN.makePointFFNApiCall();
            startAsyncTask(getCurrentFocus());
        }

        if (appDataBase.userDAO().getNbUser() == 0) {
            goSignInUser();
        } else {
            user = appDataBase.userDAO().getAll().get(0);
            configureAndShowFragment(new MainFragment());
        }
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

    public void startAsyncTask(View v) {
        Log.e("Function", "IN");
        ImportPointsFFNTask importPointsFFNTask = new ImportPointsFFNTask(progressBar);
        importPointsFFNTask.execute();
    }
}
