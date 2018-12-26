package com.bloodgift.bloodgift.Vue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bloodgift.bloodgift.Controller.LoginController;
import com.bloodgift.bloodgift.Model.User;
import com.bloodgift.bloodgift.R;

public class LoginActivity extends AppCompatActivity {

    private EditText userName;

    private EditText password;

    LoginController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = findViewById(R.id.usrName);
        password = findViewById(R.id.passwrd);

        TextView loginButton = findViewById(R.id.login);
        TextView signUpButton = findViewById(R.id.sup);

        controller = new LoginController(this);


        //defines the listener of the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!controller.login(userName.getText().toString(), password.getText().toString())){
                    connectionFailed();
                }
            }
        });

        //defines the listener of the sign up button
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.signUp();
            }
        });
    }

    public void connectionFailed(){
        Toast.makeText(this, "User name ou Password incorrecte", Toast.LENGTH_LONG).show();
    }
}
