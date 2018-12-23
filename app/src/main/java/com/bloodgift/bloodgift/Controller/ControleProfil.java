package com.bloodgift.bloodgift.Controller;

import android.content.Context;

import com.bloodgift.bloodgift.Model.DAO.AccesLocal;
import com.bloodgift.bloodgift.Model.Profil;

import java.util.Date;

public final class ControleProfil {


    private static ControleProfil instance = null;
    private static Profil profil;
    private static AccesLocal accesLocal;


    private ControleProfil() {
        super();
    }

    /**
     * Création de l'instance du controleur en Singleton
     * @return instance
     */
    public static final ControleProfil getInstance(Context context) {
        if (ControleProfil.instance == null) {
            ControleProfil.instance = new ControleProfil();
            accesLocal = new AccesLocal(context);
            profil = accesLocal.recupProfil();
        }
        return ControleProfil.instance;
    }

    /**
     *  Création du test d'éligibilité
     * @param age en année
     * @param poids en kg
     * @param sexe 1 pour homme et 0 pour femme
     */
    public void créerProfil(Integer age, Integer poids, Integer sexe, Context contexte) {
        profil = new Profil(new Date(),age, poids, sexe);
        accesLocal.ajout(profil);
    }

    /**
     * récupère le message du test
     * @return message
     */
    public String getMessage() {
        return profil.getMessage();
    }

    public Integer getPoids(){
        if(profil == null){
            return null;
        } else {
            return profil.getPoids();
        }
    }

    public Integer getAge(){
        if(profil == null){
            return null;
        } else {
            return profil.getAge();
        }
    }

    public Integer getSexe(){
        if(profil == null){
            return null;
        } else {
            return profil.getSexe();
        }
    }


}

