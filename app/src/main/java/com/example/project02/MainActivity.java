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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String PREF_NAME_ = "My_Pref";

    Button main_Login;
    Button main_create_account;
    EditText etMain_login;
    EditText etMain_pass;

    static  String main_username;
    static  String main_password;


    List<User> users = new ArrayList<>();

    UserDAO appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        User user1 = new User();
        User user2 = new User();



        etMain_login = findViewById(R.id.main_username_editText);
        etMain_pass = findViewById(R.id.main_password_editText);
        main_Login = findViewById(R.id.main_login_button);
        main_create_account = findViewById(R.id.main_create_account_button);

        appDatabase = Room.databaseBuilder(this, AppDatabase.class,AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserDAO();
        users = appDatabase.getUsers();

        if(users.isEmpty()) {
            addUser(user1, "admin2", "admin2");
            addUser(user2, "testuser1", "testuser1");
        }

        main_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SharedPreferences preferences = getSharedPreferences(PREF_NAME_,MODE_PRIVATE);

                main_username = etMain_login.getText().toString();
                main_password = etMain_pass.getText().toString();

                //SharedPreferences.Editor editor = preferences.edit();

//                editor.putString(main_username + main_password, main_username + main_password);
//                editor.putString(main_username,main_username);
//                editor.commit();

                User valUser = appDatabase.getUser(main_username);

                if(valUser!=null && valUser.getUsername().equals(main_username)){
                    Toast toast = Toast.makeText(MainActivity.this,"Username Taken Already",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }
                else if(main_username.isEmpty() || main_password.isEmpty()){
                    Toast toast = Toast.makeText(MainActivity.this,"Fields cannot be empty", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }
                else {
                    User user = new User();
                    user.setUsername(main_username);
                    user.setPassword(main_password);




                    appDatabase.insert(user);

                    Toast toast = Toast.makeText(MainActivity.this,"User Registered Successfully", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }

            }
        });

        main_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    // Function to add User
    void addUser(User user,String username,String password){
        user.setUsername(username);
        user.setPassword(password);
        appDatabase.insert(user);
    }



}