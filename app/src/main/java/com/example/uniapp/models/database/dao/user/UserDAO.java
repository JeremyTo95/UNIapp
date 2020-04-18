package com.example.uniapp.models.database.dao.user;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user LIMIT 1")
    User getUser();

    @Query("SELECT count(*) FROM user")
    int getNb();

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last")
    User findByName(String first, String last);

    @Query("SELECT count(*) FROM user WHERE first_name = :first AND last_name = :last AND mykey = :key")
    int checkUserByKey(String first, String last, String key);

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM user")
    void deleteAll();
}
