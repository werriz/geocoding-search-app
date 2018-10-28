package com.jurijz.geocodingsearch.domain;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by jurijz on 10/18/2018.
 */
@Entity
@Table(name="CITY", schema = "GEOCODING")
public class City {

    @Id
    private String id;

    private String cityName;

    private String country;

    private float longitude;

    private float latitude;

    private float swLat;

    private float swLng;

    private float neLat;

    private float neLng;

    public City() {}

    public City(String cityName, String country) {
        this.cityName = cityName;
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public float getSwLat() {
        return swLat;
    }

    public void setSwLat(float swLat) {
        this.swLat = swLat;
    }

    public float getSwLng() {
        return swLng;
    }

    public void setSwLng(float swLng) {
        this.swLng = swLng;
    }

    public float getNeLat() {
        return neLat;
    }

    public void setNeLat(float neLat) {
        this.neLat = neLat;
    }

    public float getNeLng() {
        return neLng;
    }

    public void setNeLng(float neLng) {
        this.neLng = neLng;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
}
