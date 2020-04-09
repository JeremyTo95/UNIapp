package com.example.uniapp.models.database.dao.user;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.uniapp.models.database.AppDataBase;
import com.example.uniapp.models.database.dao.ElementRepertories;
import com.example.uniapp.models.database.dao.pointFFN.PointFFN;
import com.example.uniapp.models.database.dao.pointFFN.PointFFNDAO;

import java.util.List;

public class UserRepository extends ElementRepertories {
    private UserDAO userDAO;
    private List<User> userList;

    public UserRepository(Application application) {
        AppDataBase appDataBase = AppDataBase.getDatabase(application);
        userDAO = appDataBase.userDAO();
        userList = userDAO.getAll();
    }

    public List<User> getAllUsers() {
        return userList;
    }

    public User getUser() {
        return userDAO.getUser();
    }

    @Override
    public int getNbElement() { return userDAO.getNb(); }

    public void insert (final User user) {
        userDAO.insert(user);
    }

    public void deleteAll () {
        userDAO.deleteAll();
    }

    public void delete (final User user) {
        userDAO.delete(user);
    }
}
