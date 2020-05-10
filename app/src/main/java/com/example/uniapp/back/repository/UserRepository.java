package com.example.uniapp.back.repository;

import android.app.Application;

import com.example.uniapp.back.dao.UserDAO;
import com.example.uniapp.back.room.RoomDataBase;
import com.example.uniapp.front.model.data.User;

import java.util.List;

public class UserRepository extends ElementRepertory {
    private UserDAO userDAO;
    private List<User> userList;

    public UserRepository(RoomDataBase roomDataBase) {
        userDAO  = roomDataBase.userDAO();
        userList = userDAO.getAll();
    }

    public List<User> getAllUsers() {
        return userList;
    }

    public User getUser() {
        return userDAO.getUser();
    }

    @Override
    public int getNb() { return userDAO.getNb(); }

    public void insert (final User user) {
        userDAO.insert(user);
    }

    public void deleteAll () {
        userDAO.deleteAll();
    }

    public void delete (final User user) {
        userDAO.delete(user);
    }

    public int checkUserByKey(String firstname, String lastname, String mykey) { return userDAO.checkUserByKey(firstname, lastname, mykey); }
}
