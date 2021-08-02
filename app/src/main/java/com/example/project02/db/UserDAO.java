package com.example.project02.db;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project02.Item;
import com.example.project02.User;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    void insert(User... userLogs);

    @Update
    void update(User... userLogs);

    @Delete
    void delete(User userLog);

    @Insert
    void insert(Item...items);

    @Update
    void update(Item...items);

    @Delete
    void delete(Item item);

    @Query("SELECT * FROM " + AppDatabase.TABLE_NAME_)
    List<User> getUsers();

    @Query("SELECT * FROM " + AppDatabase.TABLE_NAME_ + " WHERE username = :username and password = :password")
    User login(String username, String password);

    //DELETE
    @Query("SELECT * FROM " + AppDatabase.TABLE_NAME_ + " WHERE username = :username")
    User getUser(String username);

    @Query("SELECT userItems FROM " + AppDatabase.TABLE_NAME_ + " WHERE username = :username")
    String getUserItems(String username);

    @Query("UPDATE " + AppDatabase.TABLE_NAME_ + " SET userItems = :userItems WHERE username = :username")
    void setUserItems(String userItems, String username);


    @Query("SELECT * FROM " + AppDatabase.TABLE2_NAME_ )
    List<Item> getItems();

    @Query("SELECT * FROM " + AppDatabase.TABLE2_NAME_ + " WHERE product_name = :product_name")
    Item getSingleItem(String product_name);

    @Query("SELECT product_quantity FROM "+ AppDatabase.TABLE2_NAME_+  " WHERE product_name = :product_name")
    int getItemQuantity(String product_name);

    @Query(" UPDATE " + AppDatabase.TABLE2_NAME_ + " SET product_quantity = :product_quantity WHERE product_name = :product_name")
    void updateItemQuantity(int product_quantity, String product_name);




}
