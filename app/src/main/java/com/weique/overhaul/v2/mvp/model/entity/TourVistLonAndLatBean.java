package com.weique.overhaul.v2.mvp.model.entity;

import com.weique.overhaul.v2.R;

import static com.blankj.utilcode.util.StringUtils.getString;

public class TourVistLonAndLatBean {
    private double lng;
    private double lat;

    public TourVistLonAndLatBean() {
    }


    public String getLngAndLatName() {
        return String.format(getString(R.string.lon_lat), getLat(), getLng());
    }

    public String getPointJson() {
        return String.format(getString(R.string.address_lon_lat), getLat(), getLng());
    }

    public TourVistLonAndLatBean(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }


    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }


}
