package com.bloodgift.bloodgift.Model.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.bloodgift.bloodgift.Model.Profile;
import com.bloodgift.bloodgift.Model.ToolBox;
import com.bloodgift.bloodgift.Model.User;

import java.util.Date;

public class UserDAO {

    private static String USER_NAME = "user_name";

    private static String PASSWORD = "password";

    private static String USER_TABLE = "user";

    private Context context;

    public UserDAO(Context context){
        this.context = context;
    }

    public static String getUserTableName(){
        return USER_TABLE;
    }

    public static String getUserTable(){
        final String table_user = "CREATE TABLE " + USER_TABLE + "(" +
                USER_NAME + " VARCHAR(128),"  +
                PASSWORD + " VARCHAR(128));";
        return table_user;
    }

    /**
     * Defines if the user that is going to be logged exists in the database
     * @param userToCompare user that is trying to be logged
     * @return boolean, true if the user exists, false otherwise
     */
    public boolean doesUserExist(User userToCompare){
        Database db = Database.getInstance(context);
        Cursor cursor = db.getWritableDatabase().rawQuery("SELECT * FROM "+USER_TABLE, null);

        if (cursor == null || cursor.getCount() == 0){
            return false;
        }else{
            cursor.moveToFirst();
            String userName = cursor.getString(0);
            String password = cursor.getString(1);
            User baseUser = new User(userName, password);

            Log.i("infoBlood", "username: "+userName);
            Log.i("infoBlood", "userToCompare: "+userToCompare.getUserName());

            if (userToCompare.equals(baseUser)){
                cursor.close();
                return true;
            }
        }
        cursor.close();
        return false;
    }

    public void addUser(User user){
        //check si l'utilisateur n'existe pas déjà.
        ContentValues values = new ContentValues();

        values.put(USER_NAME, user.getUserName());
        values.put(PASSWORD, user.getPassword());

        Database db = Database.getInstance(context);
        db.getWritableDatabase().insert(USER_TABLE, null, values);
    }

    public void removeAllUser(){
        Database db = Database.getInstance(context);
        db.getWritableDatabase().delete(USER_TABLE, null, null);
        Log.i("infoBlood", "Database user effacée");
    }

    //Checks if there is a user already registered
    public boolean isThereUser(){
        Database db = Database.getInstance(context);
        Cursor cursor = db.getWritableDatabase().rawQuery("SELECT * FROM "+USER_TABLE, null);

        if (cursor == null || cursor.getCount() == 0){
            return false;
        }
        return true;
    }
}
