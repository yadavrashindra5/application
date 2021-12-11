package com.example.internproject;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insertAll(User users);

    @Query("SELECT * FROM User")
    List<User> getallusers();
    @Query("DELETE FROM User WHERE id=:id")
    void deleteById(int id);

}