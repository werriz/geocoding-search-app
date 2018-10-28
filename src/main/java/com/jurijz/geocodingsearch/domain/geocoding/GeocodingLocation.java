package com.jurijz.geocodingsearch.domain.geocoding;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by jurijz on 10/23/2018.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class GeocodingLocation {

    @XmlElement
    private float lat;

    @XmlElement
    private float lng;

    public GeocodingLocation() {
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }
}
