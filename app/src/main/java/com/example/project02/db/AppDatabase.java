package com.example.project02.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.project02.Item;
import com.example.project02.User;

@Database(entities = {User.class, Item.class},version = 3)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DB_NAME = "User_DATABASE";
    public static final String TABLE_NAME_ = "User_TABLE";
    public static final String TABLE2_NAME_ = "Product_TABLE";

    public abstract UserDAO getUserDAO();
}
