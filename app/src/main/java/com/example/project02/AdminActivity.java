package com.example.project02;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project02.db.AppDatabase;
import com.example.project02.db.UserDAO;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {



    Button Admin_back_button;
    Button Admin_add_items_button;
    Button Admin_view_exiting_users_button;
    Button Admin_view_existing_items_button;
    Button Admin_remove_item_button;

    UserDAO appDatabase;

    List<User> userList = new ArrayList<>();
    List<Item> itemList = new ArrayList<>();
    List<String> itemName = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Admin_back_button = findViewById(R.id.Admin_back_button);
        Admin_add_items_button = findViewById(R.id.Admin_add_item_button);
        Admin_view_exiting_users_button = findViewById(R.id.Admin_View_exiting_users_button);
        Admin_view_existing_items_button = findViewById(R.id.Admin_View_exiting_items_button);
        Admin_remove_item_button = findViewById(R.id.Admin_remove_item_button);

        appDatabase = Room.databaseBuilder(AdminActivity.this, AppDatabase.class,AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserDAO();

        //TEMP List to store all the items
        final List<Item>[] tmp = new List[]{appDatabase.getItems()};


        // Add Items Button
        Admin_add_items_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AdminActivity.this);
                alertBuilder.setTitle("Adding a new Item");

                LinearLayout layout = new LinearLayout(AdminActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText pName = new EditText(AdminActivity.this);
                pName.setHint("Enter a Product name");

                final EditText pPrice = new EditText(AdminActivity.this);
                pPrice.setHint("Enter a Product price");

                final EditText pLocation = new EditText(AdminActivity.this);
                pLocation.setHint("Enter a Product location");

                final EditText pQuantity = new EditText(AdminActivity.this);
                pQuantity.setHint("Enter a Product quantity");

                final EditText pDetails = new EditText(AdminActivity.this);
                pDetails.setHint("Enter Product details");

                layout.addView(pName);
                layout.addView(pPrice);
                layout.addView(pLocation);
                layout.addView(pQuantity);
                layout.addView(pDetails);

                alertBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Item item = new Item();
                        item.setProduct_name(pName.getText().toString());
                        item.setProduct_details(pDetails.getText().toString());
                        item.setProduct_location(pLocation.getText().toString());
                        item.setProduct_price(Integer.parseInt(pPrice.getText().toString()));
                        item.setProduct_quantity(Integer.parseInt(pQuantity.getText().toString()));

                        boolean tmpFlag = false;

                        // If product already exists increase quantity else insert the product
                        for(Item it: tmp[0]){
                            if(it.getProduct_name().equals(pName.getText().toString())){
                                it.setProduct_quantity(Integer.parseInt(pQuantity.getText().toString()));
                                it.setProduct_details(pDetails.getText().toString());
                                it.setProduct_location(pLocation.getText().toString());
                                it.setProduct_price(Integer.parseInt(pPrice.getText().toString()));

                                Item itDel = appDatabase.getSingleItem(pName.getText().toString());
                                appDatabase.delete(itDel);

                                appDatabase.insert(it);
                                tmpFlag = true;
                            }
                        }

                        if(!tmpFlag){
                            appDatabase.insert(item);
                        }



                        // Update list for Remove item
                        tmp[0] = appDatabase.getItems();

                        Toast toast = Toast.makeText(AdminActivity.this,"Item was added successfully", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();

                    }
                });

                alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                alertBuilder.setView(layout);
                alertBuilder.setCancelable(true);
                alertBuilder.show();

            }
        });

        // View Existing Users Button
        Admin_view_exiting_users_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userList = appDatabase.getUsers();

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AdminActivity.this);
                alertBuilder.setTitle("All Existing Users");

                LinearLayout layout = new LinearLayout(AdminActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                TextView users = new TextView(AdminActivity.this);
                users.setText(userList.toString());
                users.setMovementMethod(new ScrollingMovementMethod());

                layout.addView(users);

                alertBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                alertBuilder.setView(layout);
                alertBuilder.setCancelable(true);
                alertBuilder.show();
            }
        });

        // View Existing Items Button
        Admin_view_existing_items_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemList = appDatabase.getItems();

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AdminActivity.this);
                alertBuilder.setTitle("All Existing Items");

                LinearLayout layout = new LinearLayout(AdminActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                TextView items = new TextView(AdminActivity.this);
                items.setText(itemList.toString());
                items.setMovementMethod(new ScrollingMovementMethod());

                layout.addView(items);

                alertBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                alertBuilder.setView(layout);
                alertBuilder.setCancelable(true);
                alertBuilder.show();
            }
        });

        // Remove Item Button
        Admin_remove_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Iterate through items list and add item names in the item name list
                for(Item i : tmp[0]){
                    itemName.add(i.getProduct_name());
                }


                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AdminActivity.this);
                alertBuilder.setTitle("Remove An Item");

                LinearLayout layout = new LinearLayout(AdminActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                final AutoCompleteTextView atv = new AutoCompleteTextView(AdminActivity.this);
                atv.setAdapter(new ArrayAdapter<>(AdminActivity.this, android.R.layout.simple_list_item_1, itemName));

                layout.addView(atv);

                alertBuilder.setPositiveButton("Remove Item", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String itName = atv.getText().toString();
                        Item item = new Item();

                        // Retrieve the item from the item list whose name matches the value in Editable Text
                        for(Item j : tmp[0]){
                            if(j.getProduct_name().equals(itName)){
                                item = j;
                            }
                        }

                        //BEGONE ITEM
                        appDatabase.delete(item);
                    }
                });


                alertBuilder.setView(layout);
                alertBuilder.setCancelable(true);
                alertBuilder.show();

            }
        });


        // Back Button
        Admin_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this,LandingPage.class));
            }
        });
    }
}