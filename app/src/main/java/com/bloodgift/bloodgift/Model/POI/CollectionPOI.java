package com.bloodgift.bloodgift.Model.POI;

import java.util.ArrayList;

public class CollectionPOI {
    ArrayList<InfoPOI> pois = new ArrayList<InfoPOI>();

    public void addPoi(InfoPOI poi){
        this.pois.add(poi);
    }

    /**
     * Returns a collection of POI in a given radius
     * @param radius radius in meters
     */
    public ArrayList<InfoPOI> getPOIInRadius(double latitude, double longitude, int radius){
        AmplitudeResult amplitudeCalc = new AmplitudeResult(latitude, longitude, radius);

        ArrayList<InfoPOI> poiInRadius = new ArrayList<InfoPOI>();

        for (InfoPOI poi: pois){
            if (poi.getLatitude()<amplitudeCalc.getMaxLatitude() && poi.getLatitude()>amplitudeCalc.getMinLatitude()){
                if (poi.getLongitude()<amplitudeCalc.getMaxLongitude() && poi.getLongitude()>amplitudeCalc.getMinLongitude()){
                    poiInRadius.add(poi);
                }
            }
        }
        return poiInRadius;
    }
}
