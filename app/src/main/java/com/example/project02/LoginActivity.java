package com.example.project02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project02.db.AppDatabase;
import com.example.project02.db.UserDAO;

public class LoginActivity extends AppCompatActivity {

    private static final String PREF_NAME_ = "My_Pref";

    EditText etLogin_login;
    EditText etPass_login;
    Button login_Login;

    static String login_username;
    static String login_password;
    static boolean flag = false;

    UserDAO appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLogin_login = findViewById(R.id.login_username_editText);
        etPass_login = findViewById(R.id.login_password_editText);
        login_Login  = findViewById(R.id.login_login_button);

        appDatabase = Room.databaseBuilder(LoginActivity.this, AppDatabase.class,AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserDAO();




        login_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                login_username = etLogin_login.getText().toString();
                login_password = etPass_login.getText().toString();



                 if(login_username.isEmpty() || login_password.isEmpty()){

                    Toast toast = Toast.makeText(LoginActivity.this,"Fields cannot be empty",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();

                }
                else{
                    //User user = appDatabase.login(login_username,login_password);
                    User user = appDatabase.getUser(login_username);
                    if(user==null){
                        Toast toast = Toast.makeText(LoginActivity.this,"Username is incorrect",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }
                    else if(!user.getPassword().equals(login_password)){
                        Toast toast = Toast.makeText(LoginActivity.this,"Password is incorrect",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }
                    else{
                        String username = user.getUsername();
                        if(username.equals("admin2")) {
                            flag = true;
                        }
                        else {
                            flag = false;
                        }
                        SharedPreferences preferences = getSharedPreferences(PREF_NAME_,MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("username",username);
                        editor.commit();
                        startActivity(new Intent(LoginActivity.this,LandingPage.class));
                    }
                }
            }
        });
    }
}