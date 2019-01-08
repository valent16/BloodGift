package com.bloodgift.bloodgift.Model;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {

    /**
     * user name
     */
    private String userName;

    /**
     * user password, must be hashed
     */
    private String password;

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Creates a new user. Must be called at the creation of a new user
     * @param userName userName as String
     * @param password password as String
     */
    public User(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User user = (User) obj;

        if (user.userName.equals(userName)){
            if(user.password.equals(password)){
                Log.i("infoBlood", "les utilisateurs sont égaux");
                return true;
            }
        }
        return false;
    }
}
