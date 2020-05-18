package com.example.uniapp.front.controller.controller_activity;

import android.content.Intent;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.uniapp.R;
import com.example.uniapp.back.sharedpreferences.SharedPrefManager;
import com.example.uniapp.front.controller.global.Controller;
import com.example.uniapp.front.model.data.PointFFN;
import com.example.uniapp.front.model.data.Race;
import com.example.uniapp.front.model.data.User;
import com.example.uniapp.front.view.actvities.MainActivity;
import com.example.uniapp.front.view.actvities.SignInActivity;
import com.example.uniapp.front.view.fragments.HomeFragment;

public class MainController extends Controller {
    private MainActivity view;

    public MainController(MainActivity view) {
        super(view);
        this.view = view;
    }

    public void onStart() {
        view.setupUIElements();
        if (SharedPrefManager.getUser(getContext()) != null) setupNewUser();

        //if (getRoomDataBase().pointFFNDAO().getNb() != 54000 && getRoomDataBase().userDAO().getNb() == 1) PointFFN.startAsyncTaskLoadingPointsFFN(view);
        if (getRoomDataBase().userDAO().getNb() == 1) configureAndShowFragment(new HomeFragment());
        else goSignInUser();
        if (getRoomDataBase().pointFFNDAO().getNb() == 54000) view.unlockUI();

    }

    private void setupNewUser() {
        getRoomDataBase().userDAO().deleteAll();
        prepopulateUsersInDataBase();
        User newUser = SharedPrefManager.getUser(getContext());
        SharedPrefManager.clearUser(getContext());

        if (newUser != null) {
            if (getRoomDataBase().userDAO().checkUserByKey(newUser.getFirstname(), newUser.getLastname(), newUser.getMykey()) == 0) {
                goSignInUser();
            } else {
                getRoomDataBase().userDAO().deleteAll();
                getRoomDataBase().userDAO().insert(newUser);
                Toast.makeText(getContext(), "Nouvel utilisateur enregistré", Toast.LENGTH_SHORT).show();
                if (getRoomDataBase().raceDAO().getNb() == 0) Race.startAsyncTaskLoadingRace(view);
                if (getRoomDataBase().pointFFNDAO().getNb() != 54000) PointFFN.startAsyncTaskLoadingPointsFFN(view);
                else view.unlockUI();
            }
        }
    }

    private void configureAndShowFragment(Fragment fragment) {
        FragmentManager fragmentManager = view.getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.activty_main_fragment_layout, fragment).commit();
    }

    private void prepopulateUsersInDataBase() {
        User tourari_jeremy      = new User("Homme", "Jeremy",     "Tourari",    "11/11/1999", 180, 70, "AS HERBLAY NATATION", "freestyle", "Herblay", "abcd-efgh-ijkl");
        User peuffier_arthur     = new User("Homme", "Arthur",     "Peuffier",   "03/02/1995", 185, 73, "AS HERBLAY NATATION", "freestyle", "Herblay", "abcd-efgh-ijkl");
        User noirbent_christophe = new User("Homme", "Christophe", "Noirbent",   "12/11/1993", 186, 82, "AS HERBLAY NATATION", "butterfly", "Herblay", "abcd-efgh-ijkl");
        User bencherqui_younes   = new User("Homme", "Younes",     "Bencherqui", "11/11/1999", 180, 70, "AS HERBLAY NATATION", "freestyle", "Herblay", "abcd-efgh-ijkl");
        User valenza_dylan       = new User("Homme", "Dylan",      "Valenza",    "11/11/1999", 180, 70, "AS HERBLAY NATATION", "freestyle", "Herblay", "abcd-efgh-ijkl");
        User andre_baptiste      = new User("Homme", "Baptiste",   "Andre",      "11/11/1999", 180, 70, "AS HERBLAY NATATION", "freestyle", "Herblay", "abcd-efgh-ijkl");

        getRoomDataBase().userDAO().insert(tourari_jeremy);
        getRoomDataBase().userDAO().insert(peuffier_arthur);
        getRoomDataBase().userDAO().insert(noirbent_christophe);
        getRoomDataBase().userDAO().insert(bencherqui_younes);
        getRoomDataBase().userDAO().insert(valenza_dylan);
        getRoomDataBase().userDAO().insert(andre_baptiste);
    }

    private void goSignInUser() {
        Intent intent = new Intent(view.getApplicationContext(), SignInActivity.class);
        view.startActivity(intent);
        view.finish();
    }

}
