package com.jurijz.geocodingsearch.service;

import com.jurijz.geocodingsearch.domain.City;

import java.util.List;

/**
 * Created by jurijz on 10/18/2018.
 */
public interface GeocodingRepository {

    void save(City city);

    List<City> loadAll();

}
