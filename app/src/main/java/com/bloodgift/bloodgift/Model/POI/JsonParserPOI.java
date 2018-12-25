package com.bloodgift.bloodgift.Model.POI;

import android.content.Context;
import android.util.Log;

import com.bloodgift.bloodgift.Model.DAO.POIDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;

public class JsonParserPOI {

    private String jsonPATH;

    private Context context;

    public JsonParserPOI(String jsonPATH, Context context){
        this.jsonPATH = jsonPATH;
        this.context = context;
    }

    public CollectionPOI parse(){
        HttpHandler sh = new HttpHandler();

        // Making a request to url and getting response
        Log.i("infoBlood", jsonPATH);
        String jsonStr = sh.makeServiceCall(jsonPATH);

        CollectionPOI pois = new CollectionPOI();

        POIDAO poiDAO = new POIDAO(context);

        if (jsonStr != null) {
            //empty the database
            poiDAO.removeAllPOI();

            try {
                JSONObject obj = new JSONObject(jsonStr);
                Log.i("infoBlood", "Pase du JSON File extrait de l'URL");

                JSONArray mArray = obj.getJSONArray("features");
                Log.i("infoBlood", "nombre element JSONArray: "+mArray.length());

                for (int i=0; i<mArray.length(); i++){
                    JSONObject jsonPoi = mArray.getJSONObject(i);
                    JSONObject poiProp = jsonPoi.getJSONObject("properties");

                    double latitude = poiProp.optDouble("lat");
                    double longitude = poiProp.optDouble("lon");
                    String locationName = poiProp.optString("name");
                    String townName = poiProp.optString("where:name");
                    String startDateStr = poiProp.optString("start");
                    String stopDateStr = poiProp.optString("stop");

                    try{
                        Date startDate = POIDAO.SIMPLE_DATE_FORMAT.parse(startDateStr);
                        Date stopDate = POIDAO.SIMPLE_DATE_FORMAT.parse(stopDateStr);
                        InfoPOI poi = new InfoPOI(latitude, longitude, locationName, townName, startDate, stopDate);

                        //Add the poi in the collection that will be returned
                        pois.addPoi(poi);

                        //Add the poi in the database
                        poiDAO.addPOI(poi);
                    }catch (ParseException e){
                        e.printStackTrace();
                    }
                }
            } catch (final JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("infoBlood", "Json string is empty");
            return null;
        }
        return pois;
    }

}
