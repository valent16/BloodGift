package com.bloodgift.bloodgift.Vue;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
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

    private MapController controller;

    private RelativeLayout containerView;

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

        containerView = (RelativeLayout) findViewById(R.id.container);
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
     * Update all the markers on the Google map.
     * @param collectionPOI
     */
    public void updateMarkers(CollectionPOI collectionPOI) {
        gmap.clear();

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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MapController.MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        //Request location updates:
                        //locationManager.requestLocationUpdates(provider, 400, 1, this);
                    }
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

        }
    }

    public void explainPermission()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Cette permission est nécessaire pour localiser votre position pour centrer la caméra de Maps").setTitle("Permission localisation");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        controller.requestLocationPermission();
                    }
                }
        );
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
