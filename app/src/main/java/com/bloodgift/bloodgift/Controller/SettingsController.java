package com.bloodgift.bloodgift.Controller;

import android.content.Context;
import android.widget.Toast;

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


    public void createSettings(Integer notifSang, Integer notifPlaquette, Integer notifPlasma) {
        settings = new Settings(new Date(),notifSang, notifPlaquette, notifPlasma);
        SettingsDAO settingsDAO = new SettingsDAO(context);
        settingsDAO.addSettings(settings);
    }

    public Integer getNotifSang(){
        if(settings == null){
            return 0;
        } else {
            return settings.getNotifSang();
        }
    }

    public Integer getNotifPlaquette(){
        if(settings == null){
            return 0;
        } else {
            return settings.getNotifPlaquette();
        }
    }

    public Integer getNotifPlasma(){
        if(settings == null){
            return 0;
        } else {
            return settings.getNotifPlasma();
        }
    }


}
