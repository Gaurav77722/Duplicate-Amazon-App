package com.example.project02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.FtsOptions;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.project02.db.AppDatabase;
import com.example.project02.db.UserDAO;

import java.util.ArrayList;
import java.util.List;

public class OrderHistory extends AppCompatActivity {

    Button OHistory_back_button;
    TextView OHistory_TextView2;
    private static final String PREF_NAME_ = "My_Pref";

    UserDAO appDatabase;

    List<Item> items = new ArrayList<>();
    String[] itemsOwned;
    List<Item>itemsOwnedByUser = new ArrayList<>();

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        // Retrieve username from preferences
        preferences = getSharedPreferences(PREF_NAME_,MODE_PRIVATE);
        String username = preferences.getString("username","null");


        appDatabase = Room.databaseBuilder(OrderHistory.this, AppDatabase.class,AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserDAO();

        // Split the items that we get from the database by using .split","
        String itemString = appDatabase.getUserItems(username);

        if(itemString!=null && !itemString.isEmpty()) {
            itemsOwned = itemString.split(",");

            for (String s : itemsOwned) {
                Item item = appDatabase.getSingleItem(s);
                item.setProduct_quantity(1);
                itemsOwnedByUser.add(item);
            }
        }


        OHistory_back_button = findViewById(R.id.Ohistory_back_button);
        OHistory_TextView2 = findViewById(R.id.Ohistory_textView2);

        // Back Button
        OHistory_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderHistory.this,LandingPage.class));
            }
        });

        // TextView 2
        OHistory_TextView2.setMovementMethod(new ScrollingMovementMethod());

        if(itemString!=null && !itemString.isEmpty()) {
            OHistory_TextView2.setText(itemsOwnedByUser.toString());
        }
        else{
            OHistory_TextView2.setText("Your Order History is Empty");
        }






    }
}