package com.jurijz.geocodingsearch.domain.geocoding;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by jurijz on 10/23/2018.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Viewport {

    @XmlElement
    private GeocodingLocation southwest;

    @XmlElement
    private GeocodingLocation northeast;

    public Viewport() {
    }

    public GeocodingLocation getSouthwest() {
        return southwest;
    }

    public void setSouthwest(GeocodingLocation southwest) {
        this.southwest = southwest;
    }

    public GeocodingLocation getNortheast() {
        return northeast;
    }

    public void setNortheast(GeocodingLocation northeast) {
        this.northeast = northeast;
    }
}
