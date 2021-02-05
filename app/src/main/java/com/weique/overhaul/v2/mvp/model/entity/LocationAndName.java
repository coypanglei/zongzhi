package com.weique.overhaul.v2.mvp.model.entity;


/**
 * @author GK
 * @description:
 * @date :2020/8/10 16:03
 */
public class LocationAndName {
    private TourVistLonAndLatBean latLng;
    private String name;

    public LocationAndName(TourVistLonAndLatBean latLng, String name) {
        this.latLng = latLng;
        this.name = name;
    }

    public TourVistLonAndLatBean getLatLng() {
        return latLng;
    }

    public String getName() {
        return name;
    }
}
