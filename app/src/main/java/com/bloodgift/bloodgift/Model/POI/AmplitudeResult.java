package com.bloodgift.bloodgift.Model.POI;

public class AmplitudeResult {
    private double maxLatitude;

    private double minLatitude;

    private double maxLongitude;

    private double minLongitude;

    public double getMaxLatitude() {
        return maxLatitude;
    }

    public double getMinLatitude() {
        return minLatitude;
    }

    public double getMaxLongitude() {
        return maxLongitude;
    }

    public double getMinLongitude() {
        return minLongitude;
    }

    private double offsetLat;
    private double offsetLong;

    public AmplitudeResult(double latitude, double longitude, int rayon){
        computeAmplitudeResult(latitude, longitude, rayon);
    }

    //rayon en mètre
    public void computeAmplitudeResult(double latitude, double longitude, int radius){
        offsetLat = radius / 111110.0;

        double OneLongitudeDegree = 111110 * Math.cos(latitude * Math.PI/180);
        offsetLong = radius / OneLongitudeDegree;

        computeNewLimits(latitude, longitude);
    }

    private void computeNewLimits(double latitude, double longitude){
        maxLatitude = latitude + offsetLat;
        minLatitude = latitude - offsetLat;
        maxLongitude = longitude + offsetLong;
        minLongitude = longitude - offsetLong;
    }

}
