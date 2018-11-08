package com.jurijz.geocodingsearch.service;

import com.jurijz.geocodingsearch.domain.City;

import java.util.List;

/**
 * Created by jurijz on 11/8/2018.
 */
public interface NearestCitySearchService {

    City isInCity(List<City> cities, float longitude, float latitude);

    List<City> tenNearest(List<City> cities, float longitude, float latitude);
}
