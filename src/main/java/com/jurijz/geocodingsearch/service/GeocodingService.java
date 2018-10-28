package com.jurijz.geocodingsearch.service;

import com.jurijz.geocodingsearch.domain.City;
import com.jurijz.geocodingsearch.domain.geocoding.GeocodeResponse;

/**
 * Created by jurijz on 10/18/2018.
 */
public interface GeocodingService {

    GeocodeResponse getCityInfo(City city);

}
