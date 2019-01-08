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

    private static String NOTIF_SANG = "notifSang";
    private static String NOTIF_PLAQUETTE = "notifPlaquette";
    private static String NOTIF_PLASMA = "notifPlasma";

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
                + NOTIF_SANG + " INTEGER, "
                + NOTIF_PLAQUETTE + " INTEGER, "
                + NOTIF_PLASMA + " INTEGER );";

        return table_settings;
    }

    public void addSettings(Settings settings){
        ContentValues values = new ContentValues();

        values.put(SETTINGS_DATE, settings.getDateSettings().toString());
        values.put(NOTIF_SANG, settings.getNotifSang());
        values.put(NOTIF_PLAQUETTE, settings.getNotifPlaquette());
        values.put(NOTIF_PLASMA, settings.getNotifPlasma());

        removeSettings();
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
            Integer notifSang = cursor.getInt(1);
            Integer notifPlaquette = cursor.getInt(2);
            Integer notifPlasma = cursor.getInt(3);
            settings = new Settings(date, notifSang, notifPlaquette, notifPlasma);
        }
        cursor.close();
        return settings;
    }

    /**
     * Removes the settings from the database.
     * Called when a new user is created to erase data on the previous user
     */
    public void removeSettings(){
        Database db = Database.getInstance(context);
        db.getWritableDatabase().delete(SETTINGS_TABLE, null, null);
    }
}
