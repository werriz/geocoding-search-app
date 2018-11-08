package com.jurijz.geocodingsearch.utils;

import com.jurijz.geocodingsearch.domain.City;
import com.jurijz.geocodingsearch.domain.geocoding.GeocodeResponse;
import com.jurijz.geocodingsearch.domain.geocoding.Geometry;
import com.jurijz.geocodingsearch.domain.geocoding.Viewport;

/**
 * Created by jurijz on 10/24/2018.
 */
public class TransformResponseToCity {

    public static City transform(City city, final GeocodeResponse response) {
        if (response != null && response.getResult() != null) {
            city.setId(response.getResult().getPlaceId());
            Geometry geometry = response.getResult().getGeometry();
            city.setLatitude(geometry.getLocation().getLat());
            city.setLongitude(geometry.getLocation().getLng());
            Viewport bounds = geometry.getViewport();
            city.setNeLat(bounds.getNortheast().getLat());
            city.setNeLng(bounds.getNortheast().getLng());
            city.setSwLat(bounds.getSouthwest().getLat());
            city.setSwLng(bounds.getSouthwest().getLng());
        } else {
            System.out.println("ERROR! Empty response object!");
        }
        return city;
    }
}
