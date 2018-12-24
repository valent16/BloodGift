package com.bloodgift.bloodgift.Controller;

import android.content.Context;

import com.bloodgift.bloodgift.Model.DAO.ProfileDAO;
import com.bloodgift.bloodgift.Model.Profile;

import java.util.Date;

public final class ProfileController {

    private Context context;

    private ProfileController instance = null;
    private Profile profile;
    //private static AccesLocal accesLocal;


    public ProfileController(Context context) {
        this.context = context;
        ProfileDAO profileDAO = new ProfileDAO(context);
        profile = profileDAO.getProfile();
    }

    /**
     * Création de l'instance du controleur en Singleton
     * @return instance
     */
//    public static final ProfileController getInstance(Context context) {
//        if (ProfileController.instance == null) {
//            ProfileController.instance = new ProfileController();
//            //accesLocal = new AccesLocal(context);
//
//
//            profil = profileDAO.getProfile();
//        }
//        return ProfileController.instance;
//    }

    /**
     *  Création du test d'éligibilité
     * @param age en année
     * @param poids en kg
     * @param sexe 1 pour homme et 0 pour femme
     */
    public void createProfile(Integer age, Integer poids, Integer sexe) {
        profile = new Profile(new Date(),age, poids, sexe);
        ProfileDAO profileDAO = new ProfileDAO(context);
        profileDAO.addProfile(profile);
//        accesLocal.ajout(profil);
    }

    /**
     * récupère le message du test
     * @return message
     */
    public String getMessage() {
        return profile.getMessage();
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

