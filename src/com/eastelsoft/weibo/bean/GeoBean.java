package com.eastelsoft.weibo.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class GeoBean {

	private String type;
    private double[] coordinates = {0.0, 0.0};

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

    public double getLat() {
        return coordinates[0];
    }

    public double getLon() {
        return coordinates[1];
    }

    public void setLatitude(double lat) {
        coordinates[0] = lat;
    }

    public void setLongitude(double lon) {
        coordinates[1] = lon;
    }

    public int describeContents() {
        return 0;
    }

}
