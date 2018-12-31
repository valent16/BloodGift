package com.bloodgift.bloodgift.Model;

import java.util.Calendar;
import java.util.Date;

public class Settings {

    //propriétés
    private Date dateSettings;
    private Integer notif;

    public Settings(Date dateSettings, Integer notif) {
        this.dateSettings = dateSettings;
        this.notif = notif;
    }

    public Date getDateSettings() {
        return dateSettings;
    }

    public Integer getNotif() {
        return notif;
    }
}
