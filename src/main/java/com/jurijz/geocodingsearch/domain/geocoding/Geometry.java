package com.jurijz.geocodingsearch.domain.geocoding;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by jurijz on 10/23/2018.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Geometry {

    @XmlElement(name = "location")
    private GeocodingLocation location;

    @XmlElement(name = "location_type")
    private String locationType;

    @XmlElement
    private Viewport viewport;

    @XmlElement
    private Viewport bounds;

    public Geometry() {
    }

    public GeocodingLocation getLocation() {
        return location;
    }

    public void setLocation(GeocodingLocation location) {
        this.location = location;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public Viewport getBounds() {
        return bounds;
    }

    public void setBounds(Viewport bounds) {
        this.bounds = bounds;
    }
}
