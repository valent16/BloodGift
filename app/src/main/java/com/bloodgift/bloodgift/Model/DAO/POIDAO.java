package com.bloodgift.bloodgift.Model.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bloodgift.bloodgift.Model.POI.CollectionPOI;
import com.bloodgift.bloodgift.Model.POI.InfoPOI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class POIDAO {
    private static String LATITUDE = "latitude";

    private static String LONGITUDE = "longitude";

    private static String LOCATION_NAME = "location_name";

    private static String TOWN_NAME ="town_name";

    private static String START_DATE = "start_date";

    private static String STOP_DATE = "stop_date";

    private static String POI_TABLE = "poi";

    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'CET'");

    private Context context;

    public POIDAO(Context context){
        this.context = context;
    }

    public static String getPOITableName(){
        return POI_TABLE;
    }
    /**
     * Builds the query used to create the table to store a POI
     * @return the query as String
     */
    public static String getPOITable(){
        final String table_poi_create =
            "CREATE TABLE " + POI_TABLE + "(" +
                    LATITUDE + " DOUBLE NOT NULL,"  +
                    LONGITUDE + " DOUBLE NOT NULL, " +
                    LOCATION_NAME + " TEXT, " +
                    TOWN_NAME +" TEXT, " +
                    START_DATE+" VARCHAR(26), " +
                    STOP_DATE+" VARCHAR(26)" + ");";
        return table_poi_create;
    }

    /**
     * Count all the Point of Interest stored in the database
     * @return the number of POI stored in the database
     */
    public int countPOI(){
        Database db = Database.getInstance(context);
        Cursor cursor = db.getWritableDatabase().rawQuery("SELECT COUNT(*) FROM "+POI_TABLE, null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    /**
     * Removes all the Point of Interest from the database
     */
    public void removeAllPOI(){
        Database db = Database.getInstance(context);
        db.getWritableDatabase().delete(POI_TABLE, null, null);
        Log.i("infoBlood", "Database effacée");
    }

    /**
     * Add a new point of interest in the database
     * @param poi the point of interest to add in the database
     */
    public void addPOI(InfoPOI poi){
        ContentValues values = new ContentValues();

        values.put(LATITUDE, poi.getLatitude());
        values.put(LONGITUDE, poi.getLongitude());
        values.put(LOCATION_NAME, poi.getLocationName());
        values.put(TOWN_NAME, poi.getTownName());
        values.put(START_DATE, SIMPLE_DATE_FORMAT.format(poi.getStartDate()));
        values.put(STOP_DATE, SIMPLE_DATE_FORMAT.format(poi.getStopDate()));

        Database db = Database.getInstance(context);

        db.getWritableDatabase().insert(POI_TABLE, null,values);
    }

    /**
     * Returns all the Point of Interest stored in the database
     * @return CollectionPOI object containing every poi
     */
    public CollectionPOI getAllPOI(){
        CollectionPOI pois = new CollectionPOI();
        Database db = Database.getInstance(context);

        Cursor cursor = db.getWritableDatabase().rawQuery("SELECT * FROM "+POI_TABLE, null);

        if (cursor.moveToFirst()) {
            do {
                Double latitude = cursor.getDouble(cursor.getColumnIndex(LATITUDE));
                Double longitude = cursor.getDouble(cursor.getColumnIndex(LONGITUDE));
                String locationName = cursor.getString(cursor.getColumnIndex(LOCATION_NAME));
                String townName = cursor.getString(cursor.getColumnIndex(TOWN_NAME));

                try{
                    Date startDate = SIMPLE_DATE_FORMAT.parse(cursor.getString(cursor.getColumnIndex(START_DATE)));
                    Date stopDate = SIMPLE_DATE_FORMAT.parse(cursor.getString(cursor.getColumnIndex(STOP_DATE)));

                    InfoPOI poi = new InfoPOI(latitude, longitude, locationName, townName, startDate, stopDate);
                    pois.addPoi(poi);
                }catch(ParseException e){
                    Log.e("infoBlood", "Error while parsing dates from database");
                }
            }while(cursor.moveToNext());
        }
        return pois;
    }
}
