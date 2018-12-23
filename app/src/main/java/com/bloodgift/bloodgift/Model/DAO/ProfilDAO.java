package com.bloodgift.bloodgift.Model.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class ProfilDAO extends SQLiteOpenHelper {

    //Propriétés
    private String creation = "create table profil ("
            + "dateProfil TEXT PRIMARY KEY, "
            + "age INTEGER NOT NULL, "
            + "poids INTEGER NOT NULL, "
            + "sexe INTEGER NOT NULL);";



    /**
     * Constructeur
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public ProfilDAO(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * Si changement de base de données
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(creation);
    }

    /**
     * Si changement de version
     * @param sqLiteDatabase
     * @param i  Ancienne version
     * @param i1  Nouvelle version
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
