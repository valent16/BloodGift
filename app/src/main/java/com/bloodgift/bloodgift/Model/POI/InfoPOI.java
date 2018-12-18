package com.bloodgift.bloodgift.Model.POI;

import java.util.Date;

public class InfoPOI {
    private double latitude;

    private double longitude;

    private String locationName;

    private String townName;

    private Date startDate;

    private Date stopDate;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getTownName() {
        return townName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getStopDate() {
        return stopDate;
    }

    public InfoPOI(double latitude, double longitude, String locationName, String townName, Date startDate, Date stopDate) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.locationName = locationName;
        this.townName = townName;
        this.startDate = startDate;
        this.stopDate = stopDate;
    }
}
