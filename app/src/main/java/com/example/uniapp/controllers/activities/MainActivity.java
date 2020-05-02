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
import android.widget.Toast;

import com.example.uniapp.R;
import com.example.uniapp.controllers.fragments.CompetitionsFragment;
import com.example.uniapp.controllers.fragments.MainFragment;
import com.example.uniapp.controllers.fragments.SettingsFragment;
import com.example.uniapp.controllers.fragments.GadgetsFragment;
import com.example.uniapp.controllers.fragments.TrainingsFragment;
import com.example.uniapp.models.database.AppDataBase;
import com.example.uniapp.models.database.dao.pointFFN.PointFFN;
import com.example.uniapp.models.database.dao.race.Race;
import com.example.uniapp.models.database.dao.user.User;
//import com.example.uniapp.utils.ImportPointsFFNTask;
import com.example.uniapp.views.AboutScreen;
import com.google.android.material.bottomnavigation.BottomNavigationView;


// TODO: SAUVEGARDER LES COURSES DEJA EN PLACE SUR L'APPLICATION,
//       SAUVEGARDE DES DONNEES SUR TELEPHONE, PLUS GESTION POUR NE PAS ECRASER LES DONNEE DEJA SAUVEGADER

public class MainActivity extends AppCompatActivity {
    private LinearLayout linearLayoutLoading;
    public static AppDataBase appDataBase;

    private BottomNavigationView bottomNavigationView;
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
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
                    configureAndShowFragment(new GadgetsFragment());
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
        appDataBase          = AppDataBase.getDatabase(getApplicationContext());
        linearLayoutLoading  = findViewById(R.id.glb_loading);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navbar);
        bottomNavigationView.setSelectedItemId(R.id.navbar_custom_home_btn);

        lockUI();
        /* for (int i = 0; i < appDataBase.userDAO().getNb(); i++) {
            System.out.println(appDataBase.userDAO().getAll().get(i).getFirstname());
            System.out.println(appDataBase.userDAO().getAll().get(i).getKey());
        } */

        if (getIntent().getSerializableExtra("EXTRA_NEW_USER") != null) {
            Log.e("HERE", "HERE");
            appDataBase.userDAO().deleteAll();
            prepopulateUsersInDataBase();
            User newUser = (User) getIntent().getSerializableExtra("EXTRA_NEW_USER");
            if (appDataBase.userDAO().checkUserByKey(newUser.getFirstname(), newUser.getLastname(), newUser.getMykey()) == 0) {
                Log.e("RETURN", "Return to SignIn menu");
                goSignInUser();
            } else {
                Log.e("OK", "User authentified");
                appDataBase.userDAO().deleteAll();
                appDataBase.userDAO().insert(newUser);
                Toast.makeText(getApplicationContext(), "Nouvel utilisateur enregistré", Toast.LENGTH_SHORT).show();
                if (appDataBase.raceDAO().getNb() == 0) Race.startAsyncTaskLoadingRace(this, newUser);
                if (appDataBase.pointFFNDAO().getNb() != 54000) PointFFN.startAsyncTaskLoadingPointsFFN(this, linearLayoutLoading, bottomNavigationView, onNavigationItemSelectedListener);
                else unlockUI();

                System.out.println("nbUsers     : " + appDataBase.userDAO().getNb());
                System.out.println("nbRaces     : " + appDataBase.raceDAO().getNb());
                System.out.println("nbTrainings : " + appDataBase.trainingDAO().getNb());
                System.out.println("nbPointsFFN : " + appDataBase.pointFFNDAO().getNb());
            }
        }

        if (appDataBase.pointFFNDAO().getNb() == 54000) unlockUI();
        if (appDataBase.userDAO().getNb() == 1) configureAndShowFragment(new MainFragment());
        else goSignInUser();
    }

    private void lockUI() {
        bottomNavigationView.setEnabled(false);
        bottomNavigationView.setFocusable(false);
        bottomNavigationView.setFocusableInTouchMode(false);
        bottomNavigationView.setClickable(false);
        bottomNavigationView.setOnNavigationItemSelectedListener(null);
        linearLayoutLoading.setVisibility(View.VISIBLE);
    }

    private void unlockUI() {
        bottomNavigationView.setEnabled(true);
        bottomNavigationView.setFocusable(true);
        bottomNavigationView.setFocusableInTouchMode(true);
        bottomNavigationView.setClickable(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        linearLayoutLoading.setVisibility(View.GONE);
    }

    public void configureAndShowFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.activty_main_fragment_layout, fragment).commit();
    }

    private void goSignInUser() {
        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
        startActivity(intent);
        finish();
    }

    private void prepopulateUsersInDataBase() {
        //User tourari_jeremy      = new User("Homme", "Jeremy", "Tourari", "11/11/1999", 180, 70, "AS HERBLAY NATATION", "freestyle", "Herblay", "ahdk-1kg4-mqp0");
        User tourari_jeremy      = new User("Homme", "Jeremy", "Tourari", "11/11/1999", 180, 70, "AS HERBLAY NATATION", "freestyle", "Herblay", "abcd-efgh-ijkl");
        User peuffier_arthur     = new User("Homme", "Arthur", "Peuffier", "03/02/1995", 185, 73, "AS HERBLAY NATATION", "freestyle", "Herblay", "abcd-efgh-ijkl");
        User noirbent_christophe = new User("Homme", "Christophe", "Noirbent", "12/11/1993", 186, 82, "AS HERBLAY NATATION", "butterfly", "Herblay", "abcd-efgh-ijkl");
        User bencherqui_younes   = new User("Homme", "Younes", "Bencherqui", "11/11/1999", 180, 70, "AS HERBLAY NATATION", "freestyle", "Herblay", "abcd-efgh-ijkl");
        User valenza_dylan       = new User("Homme", "Dylan", "Valenza", "11/11/1999", 180, 70, "AS HERBLAY NATATION", "freestyle", "Herblay", "abcd-efgh-ijkl");
        User andre_baptiste      = new User("Homme", "Baptiste", "Andre", "11/11/1999", 180, 70, "AS HERBLAY NATATION", "freestyle", "Herblay", "abcd-efgh-ijkl");

        appDataBase.userDAO().insert(tourari_jeremy);
        appDataBase.userDAO().insert(peuffier_arthur);
        appDataBase.userDAO().insert(noirbent_christophe);
        appDataBase.userDAO().insert(bencherqui_younes);
        appDataBase.userDAO().insert(valenza_dylan);
        appDataBase.userDAO().insert(andre_baptiste);
    }

    /*@Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) AboutScreen.hideNavigationBar(this);
    }*/
}
