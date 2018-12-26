package com.bloodgift.bloodgift.Controller;

import android.bluetooth.BluetoothA2dp;
import android.content.Intent;
import android.widget.Toast;

import com.bloodgift.bloodgift.Model.DAO.UserDAO;
import com.bloodgift.bloodgift.Model.ToolBox;
import com.bloodgift.bloodgift.Model.User;
import com.bloodgift.bloodgift.Vue.HomePageActivity;
import com.bloodgift.bloodgift.Vue.LoginActivity;
import com.bloodgift.bloodgift.Vue.SignUpActivity;
import com.bloodgift.bloodgift.Vue.TestEligibleActivity;

public class LoginController {
    LoginActivity activity;

    public LoginController(LoginActivity activity){
        this.activity = activity;
    }

    public boolean login(String userName, String password){
        User newUser = new User(ToolBox.getHash(userName), ToolBox.getHash(password));

        UserDAO userDAO = new UserDAO(activity);
        if (userDAO.doesUserExist(newUser)){
            Intent intent = new Intent().setClass(activity, HomePageActivity.class);
            activity.startActivity(intent);
            activity.finish();
            return true;
        }
        return false;
    }

    public void signUp(){
        Toast.makeText(activity, "Inscription", Toast.LENGTH_LONG).show();
        Intent intent = new Intent().setClass(activity,SignUpActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}
