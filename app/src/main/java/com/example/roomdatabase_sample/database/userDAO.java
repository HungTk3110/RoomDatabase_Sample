package com.example.roomdatabase_sample.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.roomdatabase_sample.user;

import java.util.List;

@Dao
public interface userDAO {

    @Insert
    void insertUser (user user);

    @Query("SELECT * FROM user")
    List<user>  getListUser();

    @Query("SELECT * FROM user WHERE name= :name")
    List<user> checkUser(String name);

    @Update
    void updateUser(user user);

    @Delete
    void deleteUser(user user);

    @Query("DELETE FROM user")
    void deleteAllUser();
}
