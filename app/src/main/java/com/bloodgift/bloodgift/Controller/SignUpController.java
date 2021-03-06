package com.bloodgift.bloodgift.Controller;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.bloodgift.bloodgift.Model.DAO.ProfileDAO;
import com.bloodgift.bloodgift.Model.DAO.SettingsDAO;
import com.bloodgift.bloodgift.Model.DAO.UserDAO;
import com.bloodgift.bloodgift.Model.ToolBox;
import com.bloodgift.bloodgift.Model.User;
import com.bloodgift.bloodgift.Vue.LoginActivity;
import com.bloodgift.bloodgift.Vue.SignUpActivity;

public class SignUpController {
    private SignUpActivity activity;

    private User user;

    public SignUpController(SignUpActivity activity){
        this.activity = activity;
    }

    public void signUp(String userName, String password, String confirmPassword){
        Log.i("infoBlood", password);
        Log.i("infoBlood", confirmPassword);

        if (password.equals(confirmPassword)){
            user = new User (ToolBox.getHash(userName), ToolBox.getHash(password));

            UserDAO userDAO = new UserDAO(activity);
            if (userDAO.isThereUser()){
                activity.createValidationFragment();
                return;
            }else{
                createUser();
                login("Compte créé");
                return;
            }


        }else {
            activity.displaySignUpError("Erreur au niveau du mot de passe");
        }
    }

    public void login(String message){
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
        Intent intent = new Intent().setClass(activity,LoginActivity.class);
        Log.i("infoBlood", "back to login called");
        activity.startActivity(intent);
        activity.finish();
    }

    public void createUser(){
        UserDAO userDAO = new UserDAO(activity);
        userDAO.removeAllUser();
        userDAO.addUser(user);

        ProfileDAO profileDAO = new ProfileDAO(activity);
        profileDAO.removeProfile();

        SettingsDAO settingsDAO = new SettingsDAO(activity);
        settingsDAO.removeSettings();
    }
}
