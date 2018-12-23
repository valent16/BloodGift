package com.bloodgift.bloodgift.Model.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bloodgift.bloodgift.Model.Profil;

import java.util.Date;

public class AccesLocal {

    //propriétés
    private String nomBase = "bdProfil.sqlite";
    private Integer versionBase = 1;
    private ProfilDAO accesBD;
    private SQLiteDatabase bd;

    public AccesLocal(Context context){
        accesBD = new ProfilDAO(context,nomBase,null,versionBase);
    }

    /**
     * ajout d'un profil dans la base de données
     * @param profil
     */
    public void ajout(Profil profil){
        bd = accesBD.getWritableDatabase();
        String req = "insert into profil (dateProfil, age, poids, sexe) values ";
        req += "(\""+profil.getDateProfil()+"\","+profil.getAge()+","+profil.getPoids()+","+profil.getSexe()+")";
        bd.execSQL(req);
    }

    public Profil recupProfil(){
        bd = accesBD.getReadableDatabase();
        Profil profil = null;
        String req = "select * from profil";
        Cursor curseur = bd.rawQuery(req,null);
        curseur.moveToLast();
        if (!curseur.isAfterLast()){
            Date date = new Date();
            Integer age = curseur.getInt(1);
            Integer poids = curseur.getInt(2);
            Integer sexe = curseur.getInt(3);
            profil = new Profil(date, age, poids, sexe);
        }
        curseur.close();
        return profil;
    }

}
