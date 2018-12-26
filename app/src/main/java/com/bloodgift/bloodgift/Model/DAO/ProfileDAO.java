package com.bloodgift.bloodgift.Model.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.bloodgift.bloodgift.Model.Profile;
import com.bloodgift.bloodgift.Outils.ConvertDate;

import java.util.Calendar;
import java.util.Date;

public class ProfileDAO {

    private Context context;

    private static String PROFILE_TABLE = "profile_table";

    private static String PROFILE_DATE = "profileDate";

    private static String PROFILE_DATESANG = "profileDateSang";

    private static String PROFILE_AGE = "profileAge";

    private static String PROFILE_WEIGHT = "profileWeight";

    private static String PROFILE_SEX = "profileSex";

    public ProfileDAO(Context context){
        this.context = context;
    }

    public static String getProfileTableName(){
        return PROFILE_TABLE;
    }

    public static String getProfileTable(){
        String profile_table = "CREATE TABLE "+PROFILE_TABLE+" ("
                + PROFILE_DATE+" TEXT PRIMARY KEY, "
                + PROFILE_DATESANG+" TEXT NOT NULL, "
                + PROFILE_AGE+" INTEGER NOT NULL, "
                + PROFILE_WEIGHT+" INTEGER NOT NULL, "
                + PROFILE_SEX+" INTEGER NOT NULL);";

        return profile_table;
    }

    public void addProfile(Profile profile){
        ContentValues values = new ContentValues();
        String dateSang = profile.getDateSang().get(Calendar.DAY_OF_MONTH)+"-"+(profile.getDateSang().get(Calendar.MONTH)+1)+"-"+profile.getDateSang().get(Calendar.YEAR);

        values.put(PROFILE_DATE, profile.getDateProfile().toString());
        values.put(PROFILE_DATESANG, dateSang);
        values.put(PROFILE_AGE, profile.getAge());
        values.put(PROFILE_WEIGHT, profile.getWeight());
        values.put(PROFILE_SEX, profile.getSex());

        Database db = Database.getInstance(context);
        db.getWritableDatabase().insert(PROFILE_TABLE, null, values);
//        String req = "insert into profil (dateProfil, age, poids, sexe) values ";
//        req += "(\""+profil.getDateProfil()+"\","+profil.getAge()+","+profil.getPoids()+","+profil.getSexe()+")";
//        bd.execSQL(req);
    }

    public Profile getProfile(){
        Database db = Database.getInstance(context);
        Profile profile = null;

        Cursor cursor = db.getWritableDatabase().rawQuery("SELECT * FROM "+PROFILE_TABLE, null);

//        String req = "select * from profil";
//        Cursor curseur = bd.rawQuery(req,null);
        cursor.moveToLast();
        if (!cursor.isAfterLast()){
            Date date = new Date();
            Calendar dateSang = ConvertDate.ConvertStringToCalendar(cursor.getString(1));
            Integer age = cursor.getInt(2);
            Integer poids = cursor.getInt(3);
            Integer sexe = cursor.getInt(4);
            profile = new Profile(date, dateSang, age, poids, sexe);
        }
        cursor.close();
        return profile;
    }

}
