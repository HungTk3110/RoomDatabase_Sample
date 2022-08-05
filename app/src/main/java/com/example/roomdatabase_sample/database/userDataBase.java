package com.example.roomdatabase_sample.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.roomdatabase_sample.user;

@Database(entities = {user.class} ,version = 1)
public abstract class userDataBase extends RoomDatabase  {

    private static final String DATABASE_NAME = "user.db";
    private static userDataBase instance;

    public static synchronized userDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), userDataBase.class, DATABASE_NAME)
                    .allowMainThreadQueries().build();
        }
        return instance;
    }

    public abstract userDAO userDAO();
}
