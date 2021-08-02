package com.example.project02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.project02.db.AppDatabase;
import com.example.project02.db.UserDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CancelOrderActivity extends AppCompatActivity {

    Button COrder_back_button;
    Spinner COrder_spinner;
    Button COrder_cancel_button;

    UserDAO appDatabase;

    SharedPreferences preferences;

    List<String> itemArrayList;

    // Output String
    String finalString = "";

    private static final String PREF_NAME_ = "My_Pref";
    String[] itemsOwned;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_order);

        COrder_back_button = findViewById(R.id.COrder_back_button);
        COrder_spinner = findViewById(R.id.COrder_spinner);
        COrder_cancel_button = findViewById(R.id.COrder_cancel_order_button);

        appDatabase = Room.databaseBuilder(CancelOrderActivity.this, AppDatabase.class,AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserDAO();

        // Retrieve username from preferences
        preferences = getSharedPreferences(PREF_NAME_,MODE_PRIVATE);
        username = preferences.getString("username","null");

        // Split the items that we get from the database by using .split","
        String itemString = appDatabase.getUserItems(username);
        if(itemString==null){
            startActivity(new Intent(CancelOrderActivity.this,LandingPage.class));
            Toast toast = Toast.makeText(CancelOrderActivity.this,"ORDER SOMETHING FIRST!",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
        else {
            itemsOwned = itemString.split(",");

            // Cancel Order Spinner
            COrder_spinner.setAdapter(new ArrayAdapter<>(CancelOrderActivity.this, android.R.layout.simple_spinner_dropdown_item, itemsOwned));


            // New ArrayList
            itemArrayList = new ArrayList<>();

            // Iterate and Add Elements in arrayList
            itemArrayList.addAll(Arrays.asList(itemsOwned));
        }


        // Cancel Order Button
        COrder_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String spinner = COrder_spinner.getSelectedItem().toString();

                for (String s : itemArrayList) {
                    if (s.equals(spinner)) {
                        itemArrayList.remove(s);
                        break;
                    } else {
                        // Nothing HAPPENS HERE
                    }
                }

                for (String j : itemArrayList) {
                    finalString = finalString + j + ",";
                }

                appDatabase.setUserItems(finalString, username);

                int quantity = appDatabase.getItemQuantity(spinner);
                quantity++;
                appDatabase.updateItemQuantity(quantity, spinner);

                Toast t = Toast.makeText(CancelOrderActivity.this, "Your Order has been cancelled", Toast.LENGTH_SHORT);
                t.setGravity(Gravity.CENTER, 0, 0);
                t.show();


            }
        });

        // Back Button
        COrder_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CancelOrderActivity.this,LandingPage.class));
            }
        });
    }
}