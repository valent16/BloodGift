package com.bloodgift.bloodgift.Controller;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bloodgift.bloodgift.Model.POI.CollectionPOI;
import com.bloodgift.bloodgift.Model.POI.JsonParserPOI;
import com.bloodgift.bloodgift.R;
import com.bloodgift.bloodgift.Vue.MapsActivity;

public class MapController implements LocationListener {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    /**
     * Path of the Json file
     */
    private static final String JSON_PATH = "http://api.openeventdatabase.org/event/?what=health.blood.collect&when=nextweek&limit=1000";

    /**
     * Paris default longitude
     */
    private static final double PARIS_LONGITUDE = 2.33333;

    /**
     * Paris default latitude
     */
    private static final double PARIS_LATIDUDE = 48.866667;

    /**
     * Latitude of the user location
     */
    private double latitude = 0;

    /**
     * Longitude of the user location
     */
    private double longitude = 0;

    /**
     * Activity containing the map and the context of the application
     */
    private MapsActivity activity;

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            if (latitude == 0 && longitude == 0){
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                activity.updateCameraLocation(latitude, longitude);
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    public MapController(MapsActivity activity) {
        this.activity = activity;

        //Gets the refresh button from the activity layout
        Button buttonRefresh = activity.findViewById(R.id.refreshMap);
        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshJsonMap();
            }
        });

        //Set the listener on the user current localization.
        LocationManager lm = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
        }else{
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION) || ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_COARSE_LOCATION))
            {
                activity.explainPermission();
            }else{
                activity.explainPermission();
            }
        }
    }

    /**
     * Requests the permissions for locations
     */
    public void requestLocationPermission(){
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
    }


    /**
     *  Method used to refresh the database with the download of the new JSON file
     */
    private void refreshJsonMap(){
        Log.i("infoBlood", "refresh JSON Map called");
        new GetJSONFile().execute();
    }

    public double getDefaultLatitude(){
        return PARIS_LATIDUDE;
    }

    public double getDefaultLongitude(){
        return PARIS_LONGITUDE;
    }

    /**
     * Gets the current user longitude. If the geolocalisation is not activated, returns the longitude of Paris
     * @return longitude of the user
     */
//    public double getCurrentLongitude(){
//        LocationManager lm = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
//        double longitude;
//
//        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
////          longitude = location.getLongitude();
//            longitude = 0;
//            if (longitude == 0){
//                return PARIS_LONGITUDE;
//            }else{
//                return longitude;
//            }
//        }else{
//            return PARIS_LONGITUDE;
//        }
//    }

    /**
     * Gets the current user latitude. If the geolocalisation is not activated, returns the latitude of Paris
     * @return latitude of the user
     */
//    public double getCurrentLatitude(){
//        LocationManager lm = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
//        double latitude;
//
//        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            lm.requestLocationUpdates(lm.GPS_PROVIDER, 0, 0, this);
//            //GPSTracker tracker = new GPSTracker();
////            latitude = location.getLatitude();
//            latitude = 0;
//            if (latitude == 0){
//                return PARIS_LATIDUDE;
//            }else{
//                return latitude;
//            }
//        }else{
//            return PARIS_LATIDUDE;
//        }
//    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    /**
     * Class used to download the Json File.
     */
    public class GetJSONFile extends AsyncTask<Void, Void, Void> {
        private ProgressDialog pDialog;

        private CollectionPOI collectionPOI;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage("Chargement des centres de dons...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            JsonParserPOI jsonParser = new JsonParserPOI(JSON_PATH, activity);
            collectionPOI = jsonParser.parse();

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing()){
                pDialog.dismiss();
            }

            if (collectionPOI == null){
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity.getApplicationContext(),
                                "Veuillez activer votre connexion internet!",
                                Toast.LENGTH_LONG)
                                .show();
                        //Log.i("infoBlood", "Couldn't get Json from server, Check logCat for possible errors");
                    }
                });
            }else{
                activity.updateMarkers(collectionPOI);
            }
        }
    }
}