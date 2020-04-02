package com.example.uniapp.controllers.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.uniapp.R;
import com.example.uniapp.controllers.fragments.CompetitionsFragment;
import com.example.uniapp.controllers.fragments.MainFragment;
import com.example.uniapp.controllers.fragments.SettingsFragment;
import com.example.uniapp.controllers.fragments.StatisticsFragment;
import com.example.uniapp.controllers.fragments.TrainingsFragment;
import com.example.uniapp.models.MarketRaces;
import com.example.uniapp.models.MarketTrainings;
import com.example.uniapp.models.Race;
import com.example.uniapp.models.Training;
import com.example.uniapp.views.AboutScreen;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Race> allRaces;
    private List<Training> allTrainings;
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

        //TODO: METTRE EN PLACE LES POINTS FFN AVEC LE FICHIER JSON STOCKE SUR SERVEUR
        //TODO: METTRE EN PLACE LA RECUPERATION DE TOUS LES TEMPS DU NAGEUR SUR FFN_EXTRANAT
        //TODO: SAUVEGARDER LES COURSES DEJA EN PLACE SUR L'APPLICATION SYSTEME D'IMPORTATION DE DONNEE SUR SERVEUR ET
        //      SAUVEGARDE DES DONNEES SUR TELEPHONE, PLUS GESTION POUR NE PAS ECRASER LES DONNEE DEJA SAUVEGADER
        //TODO: METTRE EN PLACE LA MODIFICATION DES TEMPS DEJA ENREGISTRER
        //TODO: MISE A JOUR DES TEMPS REALISER A L'ENRAINEMENT
        //TODO: AJOUT DE NOUVEAUX ENTRAINEMENTS
        //TODO: ADD RACE --> UPDATE RACE

        allRaces     = MarketRaces.initAllTimes();
        allTrainings = MarketTrainings.initAllTrainings();

        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.navbar);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        configureAndShowFragment(new MainFragment());
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
}
