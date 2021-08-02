package com.example.project02;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project02.db.AppDatabase;

@Entity(tableName = AppDatabase.TABLE_NAME_)
public class User {
    @PrimaryKey(autoGenerate = true)
    private int userId;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "userItems")
    private String userItems;

    public String getUserItems() {
        return userItems;
    }

    public void setUserItems(String userItems) {
        this.userItems = userItems;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Username = " + username + '\n' +
                "Password = " + password + '\n' +
                "====================\n\n";
    }
}
