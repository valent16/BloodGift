package com.bloodgift.bloodgift.Model.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.bloodgift.bloodgift.Model.Settings;

public class Database extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "bloodgift.sb";

    public static final int DATABASE_VERSION = 1;

    private static Database sInstance;

    private Database(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i("infoBlood", "POI table creation");

        //Erase the previous POI table, create a new one
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + POIDAO.getPOITableName());
        sqLiteDatabase.execSQL(POIDAO.getPOITable());

        //Erase the previous Profile table, create a new one
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ ProfileDAO.getProfileTableName());
        sqLiteDatabase.execSQL(ProfileDAO.getProfileTable());

        //Erase the previous User table, create a new one
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+UserDAO.getUserTableName());
        sqLiteDatabase.execSQL(UserDAO.getUserTable());

        //Erase the previous User table, create a new one
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+SettingsDAO.getSettingsTableName());
        sqLiteDatabase.execSQL(SettingsDAO.getSettingsTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public static synchronized Database getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new Database(context.getApplicationContext());
        }
        return sInstance;
    }
}
