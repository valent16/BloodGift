package com.bloodgift.bloodgift.Vue;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bloodgift.bloodgift.Controller.MapController;
import com.bloodgift.bloodgift.Model.DAO.POIDAO;
import com.bloodgift.bloodgift.Model.POI.CollectionPOI;
import com.bloodgift.bloodgift.Model.POI.InfoPOI;
import com.bloodgift.bloodgift.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MapsActivity extends ActivityWithDrawer implements OnMapReadyCallback {


    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    //Android view where the map is loaded
    private MapView mapView;

    //interaction with the map
    private GoogleMap gmap;

    private Marker troyes;

    private TextView text;

    private MapController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        initializeView();

        controller = new MapController(this);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
    }

    protected void initializeView() {
        super.initializeToolBar();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMinZoomPreference(10);

        //Sets the camera view:
//        double longitude = controller.getCurrentLongitude();
//        double latitude = controller.getCurrentLatitude();
        double latitude = controller.getDefaultLatitude();
        double longitude = controller.getDefaultLongitude();

        LatLng ny = new LatLng(latitude, longitude);
        gmap.moveCamera(CameraUpdateFactory.newLatLng(ny));

        if (gmap != null) {
            gmap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    View view = getLayoutInflater().inflate(R.layout.information_window_map, null);

                    TextView tvTitle = view.findViewById(R.id.mapWindow_title);
                    TextView tvStartStop = view.findViewById(R.id.mapWindow_start_stop);

                    tvTitle.setText(marker.getTitle());
                    tvStartStop.setText(marker.getSnippet());
                    return view;
                }
            });
        }

        POIDAO poiDAO = new POIDAO(this);
        updateMarkers(poiDAO.getAllPOI());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    /**
     * Updatse all the markers on the Google map.
     * @param collectionPOI
     */
    public void updateMarkers(CollectionPOI collectionPOI) {
        gmap.clear();

        //double longitude = controller.getCurrentLongitude();
        //double latitude = controller.getCurrentLatitude();

//        Log.i("infoBlood", "longitude: "+longitude);
//        Log.i("infoBlood", "latitude: "+latitude);

        //Define the location of the user. Take Paris as basis Location

        //ArrayList<InfoPOI> listPOI = collectionPOI.getPOIInRadius(latitude, longitude, 20000);

        ArrayList<InfoPOI> listPOI = collectionPOI.getAllPOI();

        for( InfoPOI poi: listPOI){
            LatLng location = new LatLng(poi.getLatitude(), poi.getLongitude());
            Marker marker;

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            marker = gmap.addMarker(new MarkerOptions()
                    .position(location)
                    .title(poi.getLocationName())
                    .snippet("Début: "+dateFormat.format(poi.getStartDate())+"\n"
                            +        "Fin:      "+dateFormat.format(poi.getStopDate()))
            );
            marker.setTag(0);
        }
    }

    /**
     * Updates the camera location view
     * @param latitude
     * @param longitude
     */
    public void updateCameraLocation(double latitude, double longitude){
        Log.i("infoBlood", "update de la position de caméra");
        LatLng ny = new LatLng(latitude, longitude);
        gmap.moveCamera(CameraUpdateFactory.newLatLng(ny));
    }

    /**
     * Reads the JSon file
     */
//    private void readJSon(){
//
//        try {
//            InputStream is = getAssets().open("DonSangCenter.json");
//
//            int size = is.available();
//            byte[] buffer = new byte[size];
//
//            is.read(buffer);
//            is.close();
//
//            //casts the buffer table to a string
//            String json = new String(buffer, "UTF-8");
//
//            //JSONArray mArray = new JSONArray(json);
//            JSONObject obj = new JSONObject(json);
//
//           // Toast.makeText(this, "LongueurArray:"+mArray.length(), Toast.LENGTH_LONG).show()
//            Log.i("infoBlood", "nombre element JSONObject: "+obj.length());
//
//            JSONArray mArray = obj.getJSONArray("features");
//            Log.i("infoBlood", "nombre element JSONArray: "+mArray.length());
//
//            CollectionPOI pois = new CollectionPOI();
//
//            for (int i=0; i<mArray.length(); i++){
//                JSONObject poi = mArray.getJSONObject(i);
//                JSONObject poiProp = poi.getJSONObject("properties");
//                //Log.i("infoBlood", "lattitude: "+poiProp.optDouble("lat"));
//
//                double latitude = poiProp.optDouble("lat");
//                double longitude = poiProp.optDouble("lon");
//                String locationName = poiProp.optString("name");
//                String townName = poiProp.optString("where:name");
//
//                String startDateStr = poiProp.optString("start");
//                String stopDateStr = poiProp.optString("stop");
//
//                Date startDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'CET'").parse(startDateStr);
//                Date stopDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'CET'").parse(stopDateStr);
//
//                pois.addPoi(new InfoPOI(latitude, longitude, locationName, townName, startDate, stopDate));
//                //Log.i("infoBlood", "date: "+dateFormat.format(d1));
//
//                //Log.i("infoBlood", "lattitude: "+poiProp.optString("what"));
//                //poi.optString("properties");
//            }
//        }
//        catch(Exception e ){
//            e.printStackTrace();
//        }
//    }
}
