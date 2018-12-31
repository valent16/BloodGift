package com.bloodgift.bloodgift.Model;

import java.util.Calendar;
import java.util.Date;

public class Settings {

    //propriétés
    private Date dateSettings;
    private Integer notifSang;
    private Integer notifPlaquette;
    private Integer notifPlasma;

    public Settings(Date dateSettings, Integer notifSang, Integer notifPlaquette, Integer notifPlasma) {
        this.dateSettings = dateSettings;
        this.notifSang = notifSang;
        this.notifPlaquette = notifPlaquette;
        this.notifPlasma = notifPlasma;
    }

    public Date getDateSettings() {
        return dateSettings;
    }

    public Integer getNotifSang() {
        return notifSang;
    }

    public Integer getNotifPlaquette() {
        return notifPlaquette;
    }

    public Integer getNotifPlasma() {
        return notifPlasma;
    }
}
