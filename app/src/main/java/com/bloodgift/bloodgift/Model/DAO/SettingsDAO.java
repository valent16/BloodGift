package com.bloodgift.bloodgift.Model.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.bloodgift.bloodgift.Model.Profile;
import com.bloodgift.bloodgift.Model.Settings;
import com.bloodgift.bloodgift.Model.User;
import com.bloodgift.bloodgift.Outils.ConvertDate;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

public class SettingsDAO {

    private static String NOTIF = "notif";
    private static String SETTINGS_DATE = "settingsDate";
    private static String SETTINGS_TABLE = "settings";

    private Context context;

    public SettingsDAO(Context context){
        this.context = context;
    }

    public static String getSettingsTableName(){
        return SETTINGS_TABLE;
    }

    public static String getSettingsTable(){
        final String table_settings = "CREATE TABLE " + SETTINGS_TABLE + "("
                + SETTINGS_DATE +" TEXT PRIMARY KEY, "
                + NOTIF + " INTEGER );";
        return table_settings;
    }

    public void addSettings(Settings settings){
        ContentValues values = new ContentValues();

        values.put(SETTINGS_DATE, settings.getDateSettings().toString());
        values.put(NOTIF, settings.getNotif());

        Database db = Database.getInstance(context);
        db.getWritableDatabase().insert(SETTINGS_TABLE, null, values);
    }

    public Settings getSettings() {
        Database db = Database.getInstance(context);
        Settings settings = null;

        Cursor cursor = db.getWritableDatabase().rawQuery("SELECT * FROM " + SETTINGS_TABLE, null);

        cursor.moveToLast();
        if (!cursor.isAfterLast()) {
            Date date = new Date();
            Integer notif = cursor.getInt(1);
            settings = new Settings(date, notif);
        }
        cursor.close();
        return settings;
    }
}
