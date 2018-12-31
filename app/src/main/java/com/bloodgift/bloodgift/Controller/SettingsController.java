package com.bloodgift.bloodgift.Controller;

import android.content.Context;

import com.bloodgift.bloodgift.Model.DAO.ProfileDAO;
import com.bloodgift.bloodgift.Model.DAO.SettingsDAO;
import com.bloodgift.bloodgift.Model.Profile;
import com.bloodgift.bloodgift.Model.Settings;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SettingsController {

    private Context context;

    private Settings settings;

    public SettingsController(Context context) {
        this.context = context;
        SettingsDAO settingsDAO = new SettingsDAO(context);
        settings = settingsDAO.getSettings();
    }


    public void createSettings(Integer notif) {
        settings = new Settings(new Date(),notif);
        SettingsDAO settingsDAO = new SettingsDAO(context);
        settingsDAO.addSettings(settings);
    }

    public Integer getNotif(){
        if(settings == null){
            return null;
        } else {
            return settings.getNotif();
        }
    }


}
