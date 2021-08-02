package com.example.project02;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project02.db.AppDatabase;
import com.example.project02.db.UserDAO;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    Button search_back_button;
    AutoCompleteTextView search_AutoCompleteTextView;
    TextView search_TextView2;
    Button search_search_button;
    Button search_buy;

    UserDAO appDatabase;
    List<Item> items = new ArrayList<>();
    List<String>itemName = new ArrayList<>();

    String retrieveItemName;
    private static final String PREF_NAME_ = "My_Pref";
    String username;

    SharedPreferences preferences;

    Item itemDisplay;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        preferences = getSharedPreferences(PREF_NAME_,MODE_PRIVATE);
        username = preferences.getString("username","null");

        appDatabase = Room.databaseBuilder(SearchActivity.this, AppDatabase.class,AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserDAO();

        search_back_button = findViewById(R.id.search_back_button);
        search_AutoCompleteTextView = findViewById(R.id.search_auto_complete_textview);
        search_TextView2 = findViewById(R.id.search_textView2);
        search_search_button = findViewById(R.id.search_search_button);
        search_buy = findViewById(R.id.search_buy_button);


        // Back Button
        search_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchActivity.this,LandingPage.class));

            }
        });

        // Wiring up Autocomplete TextView
        // Retrieve product names from item array
        items = appDatabase.getItems();
        for(Item i : items){
            itemName.add(i.getProduct_name());
        }

        // Set the Autocomplete TextView to the array of product names
        search_AutoCompleteTextView.setAdapter(new ArrayAdapter<>(SearchActivity.this,android.R.layout.simple_list_item_1,itemName));


        // Search Button
        search_search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Wiring up TextView2

                retrieveItemName = search_AutoCompleteTextView.getText().toString();
                //search_TextView2.setText(retrieveItemName);
                itemDisplay = appDatabase.getSingleItem(retrieveItemName);
                if(itemDisplay!=null) {
                    search_TextView2.setText(itemDisplay.toString());
                }
                else{
                    Toast toast = Toast.makeText(SearchActivity.this,"Item not in inventory", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }

            }
        });

        // Buy button
        search_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(SearchActivity.this);
                alertBuilder.setTitle("Confirm Purchase");
                alertBuilder.setMessage("Please Confirm that this is the product you want");

                alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder sb = new StringBuilder();
                        String tmp = appDatabase.getUserItems(username);
                        if(tmp == null) {
                            sb.append(retrieveItemName);
                        }
                        else{
                            sb.append(tmp);

                            sb.append(retrieveItemName);
                            sb.append(",");

                        }


                        int quantity = appDatabase.getItemQuantity(retrieveItemName);
                        if(quantity==0){
                            Toast toast = Toast.makeText(SearchActivity.this,"STOCK EMPTY! FEED THE APP FOR STOCK", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER,0,0);
                            toast.show();
                        }
                        else{
                            quantity--;
                            appDatabase.setUserItems(sb.toString(),username);
                            appDatabase.updateItemQuantity(quantity,retrieveItemName);
                            itemDisplay.setProduct_quantity(quantity);
                            search_TextView2.setText(itemDisplay.toString());
                        }
                    }
                });

                alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                alertBuilder.setCancelable(true);
                alertBuilder.show();

            }
        });



    }
}