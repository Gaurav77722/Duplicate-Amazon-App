package com.example.project02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class LandingPage extends AppCompatActivity {

    private static final String PREF_NAME_ = "My_Pref";

    Button landing_signOut;
    Button landing_admin;
    TextView landing_TV2;
    Button landing_search;
    Button landing_OHistory;
    Button landing_COrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        landing_signOut = findViewById(R.id.landing_logout_button);
        landing_admin = findViewById(R.id.landing_admin_button);
        landing_TV2 = findViewById(R.id.landing_textView2);
        landing_search = findViewById(R.id.landing_search_button);
        landing_OHistory = findViewById(R.id.landing_orderHist_button);
        landing_COrder = findViewById(R.id.landing_cancelOrder_button);


        // Search Button
        landing_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LandingPage.this,SearchActivity.class));
            }
        });

        // Order History Button
        landing_OHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LandingPage.this,OrderHistory.class));
            }
        });

        // Cancel Order Button
        landing_COrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LandingPage.this,CancelOrderActivity.class));
            }
        });

        //Admin Button
        landing_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LandingPage.this,AdminActivity.class));
            }
        });

        if(LoginActivity.flag){
            landing_admin.setVisibility(View.VISIBLE);
        }
        else{
            landing_admin.setVisibility(View.INVISIBLE);
        }


        SharedPreferences preferences = getSharedPreferences(PREF_NAME_,MODE_PRIVATE);
        String pref_login_username =  preferences.getString("username","null");


        landing_TV2.setText("Welcome " + pref_login_username);

        landing_signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoginActivity.flag = false;
                Intent intent = new Intent(LandingPage.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}