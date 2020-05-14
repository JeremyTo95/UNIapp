package com.example.uniapp.front.controller.controller_activity;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.uniapp.R;
import com.example.uniapp.back.room.RoomDataBase;
import com.example.uniapp.back.sharedpreferences.SharedPrefManager;
import com.example.uniapp.front.controller.global.Controller;
import com.example.uniapp.front.model.data.PointFFN;
import com.example.uniapp.front.model.data.Race;
import com.example.uniapp.front.model.data.User;
import com.example.uniapp.front.view.actvities.MainActivity;
import com.example.uniapp.front.view.actvities.SignInActivity;
import com.example.uniapp.front.view.fragments.HomeFragment;

public class MainController extends Controller {
    private RoomDataBase roomDataBase;
    private MainActivity view;
    private Context      context;

    public MainController(MainActivity view) {
        super(view);
        this.view         = view;
        this.context      = view.getApplicationContext();
        this.roomDataBase = RoomDataBase.getDatabase(context);
    }

    public void onStart() {
        view.setupUIElements();
        System.out.println("user : " + SharedPrefManager.getUser(context));
        if (SharedPrefManager.getUser(context) != null) setupNewUser();

        if (roomDataBase.userDAO().getNb() == 1) configureAndShowFragment(new HomeFragment());
        else goSignInUser();
        if (roomDataBase.pointFFNDAO().getNb() == 54000) view.unlockUI();

    }

    private void setupNewUser() {
        roomDataBase.userDAO().deleteAll();
        prepopulateUsersInDataBase();
        User newUser = SharedPrefManager.getUser(context);
        SharedPrefManager.clearUser(context);

        if (newUser != null) {
            if (roomDataBase.userDAO().checkUserByKey(newUser.getFirstname(), newUser.getLastname(), newUser.getMykey()) == 0) {
                goSignInUser();
            } else {
                roomDataBase.userDAO().deleteAll();
                roomDataBase.userDAO().insert(newUser);
                Toast.makeText(context, "Nouvel utilisateur enregistré", Toast.LENGTH_SHORT).show();
                if (roomDataBase.raceDAO().getNb() == 0) Race.startAsyncTaskLoadingRace(view);
                if (roomDataBase.pointFFNDAO().getNb() != 54000) PointFFN.startAsyncTaskLoadingPointsFFN(view);
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

        roomDataBase.userDAO().insert(tourari_jeremy);
        roomDataBase.userDAO().insert(peuffier_arthur);
        roomDataBase.userDAO().insert(noirbent_christophe);
        roomDataBase.userDAO().insert(bencherqui_younes);
        roomDataBase.userDAO().insert(valenza_dylan);
        roomDataBase.userDAO().insert(andre_baptiste);
    }

    private void goSignInUser() {
        Intent intent = new Intent(view.getApplicationContext(), SignInActivity.class);
        view.startActivity(intent);
        view.finish();
    }

}
