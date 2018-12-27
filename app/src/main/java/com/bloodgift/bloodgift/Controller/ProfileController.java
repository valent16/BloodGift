package com.bloodgift.bloodgift.Controller;

import android.content.Context;

import com.bloodgift.bloodgift.Model.DAO.ProfileDAO;
import com.bloodgift.bloodgift.Model.Profile;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public final class ProfileController {

    private Context context;

    private ProfileController instance = null;
    private Profile profile;

    public ProfileController(Context context) {
        this.context = context;
        ProfileDAO profileDAO = new ProfileDAO(context);
        profile = profileDAO.getProfile();
    }

    /**
     *  Création du test d'éligibilité
     * @param age en année
     * @param poids en kg
     * @param sexe 1 pour homme et 0 pour femme
     */
    public void createProfile(Calendar dateSang, Calendar datePlaque, Calendar datePlasma, Integer age, Integer poids, Integer sexe) {
        profile = new Profile(new Date(),dateSang, datePlaque, datePlasma, age, poids, sexe);
        ProfileDAO profileDAO = new ProfileDAO(context);
        profileDAO.addProfile(profile);
    }

    /**
     * récupère le message du test
     * @return message
     */
    public String getMessage() {
        return profile.getMessage();
    }

    public Calendar getDateSang(){
        if (profile == null){
            Calendar c = new GregorianCalendar(2016,0,1);
            return c;
        } else {
            return profile.getDateSang();
        }
    }

    public Calendar getDatePlaque(){
        if (profile == null){
            Calendar c = new GregorianCalendar(2016,0,1);
            return c;
        } else {
            return profile.getDatePlaque();
        }
    }

    public Calendar getDatePlasma(){
        if (profile == null){
            Calendar c = new GregorianCalendar(2016,0,1);
            return c;
        } else {
            return profile.getDatePlasma();
        }
    }

    public Integer getPoids(){
        if(profile == null){
            return null;
        } else {
            return profile.getWeight();
        }
    }

    public Integer getAge(){
        if(profile == null){
            return null;
        } else {
            return profile.getAge();
        }
    }

    public Integer getSexe(){
        if(profile == null){
            return null;
        } else {
            return profile.getSex();
        }
    }


}

