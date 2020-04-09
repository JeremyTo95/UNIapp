package com.example.uniapp.controllers.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.uniapp.R;
import com.example.uniapp.controllers.fragments.CompetitionsFragment;
import com.example.uniapp.controllers.fragments.MainFragment;
import com.example.uniapp.controllers.fragments.SettingsFragment;
import com.example.uniapp.controllers.fragments.StatisticsFragment;
import com.example.uniapp.controllers.fragments.TrainingsFragment;
import com.example.uniapp.models.database.AppDataBase;
import com.example.uniapp.models.database.dao.pointFFN.PointFFN;
import com.example.uniapp.models.database.dao.pointFFN.PointFFNRepository;
import com.example.uniapp.models.database.dao.race.Race;
import com.example.uniapp.models.database.dao.race.RaceRepository;
import com.example.uniapp.models.database.dao.training.TrainingRepository;
import com.example.uniapp.models.database.dao.user.User;
import com.example.uniapp.models.database.dao.user.UserRepository;
//import com.example.uniapp.utils.ImportPointsFFNTask;
import com.example.uniapp.utils.ImportPointsFFNTask;
import com.example.uniapp.utils.ImportRacesTask;
import com.example.uniapp.views.AboutScreen;
import com.google.android.material.bottomnavigation.BottomNavigationView;


//TODO: METTRE EN PLACE LA RECUPERATION DE TOUS LES TEMPS DU NAGEUR SUR FFN_EXTRANAT
//TODO: SAUVEGARDER LES COURSES DEJA EN PLACE SUR L'APPLICATION SYSTEME D'IMPORTATION DE DONNEE SUR SERVEUR ET
//      SAUVEGARDE DES DONNEES SUR TELEPHONE, PLUS GESTION POUR NE PAS ECRASER LES DONNEE DEJA SAUVEGADER
//TODO: METTRE EN PLACE LA MODIFICATION DES TEMPS DEJA ENREGISTRER
//TODO: ADD RACE --> UPDATE RACE

public class MainActivity extends AppCompatActivity {
    public static AppDataBase appDataBase;
    /*public static PointFFNRepository pointFFNRepository;
    public static RaceRepository raceRepository;
    public static TrainingRepository trainingRepository;
    public static UserRepository userRepository;*/

    private LinearLayout linearLayout;

    private BottomNavigationView mBottomNavigationView;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navbar_custom_competition_btn:
                    configureAndShowFragment(new CompetitionsFragment());
                    return true;
                case R.id.navbar_custom_training_btn:
                    configureAndShowFragment(new TrainingsFragment());
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
        appDataBase = AppDataBase.getDatabase(getApplicationContext());
        linearLayout = findViewById(R.id.glb_progress_bar);
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.navbar);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        /*userRepository     = new UserRepository(getApplication());
        raceRepository     = new RaceRepository(getApplication());
        trainingRepository = new TrainingRepository(getApplication());
        pointFFNRepository = new PointFFNRepository(getApplication());*/

        // PointFFNRepository.startAsyncTaskLoadingPointsFFN(this, linearLayout);

        if (getIntent().getSerializableExtra("EXTRA_NEW_USER") != null) {
            Log.e("HERE", "HERE");
            appDataBase.userDAO().deleteAll();
            appDataBase.userDAO().insert((User) getIntent().getSerializableExtra("EXTRA_NEW_USER"));
            if (appDataBase.pointFFNDAO().getNb() != 54000) PointFFN.startAsyncTaskLoadingPointsFFN(this, linearLayout);
            else linearLayout.setVisibility(View.GONE);
            if (appDataBase.raceDAO().getNb() == 0) Race.startAsyncTaskLoadingRace(this);

            System.out.println("nbUsers     : " + appDataBase.userDAO().getNb());
            System.out.println("nbRaces     : " + appDataBase.raceDAO().getNb());
            System.out.println("nbTrainings : " + appDataBase.trainingDAO().getNb());
            System.out.println("nbPointsFFN : " + appDataBase.pointFFNDAO().getNb());
        }

        System.out.println("nbUsers     : " + appDataBase.userDAO().getNb());
        System.out.println("nbRaces     : " + appDataBase.raceDAO().getNb());
        System.out.println("nbTrainings : " + appDataBase.trainingDAO().getNb());
        System.out.println("nbPointsFFN : " + appDataBase.pointFFNDAO().getNb());

        if (appDataBase.userDAO().getNb() != 0) {
            linearLayout.setVisibility(View.GONE);
            configureAndShowFragment(new MainFragment());
        } else {
            goSignInUser();
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
}
