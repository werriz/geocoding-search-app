package com.jurijz.geocodingsearch.service;

import com.jurijz.geocodingsearch.domain.City;

import java.net.URI;
import java.util.List;

/**
 * Created by jurijz on 10/18/2018.
 */
public interface CityImportService {

    List<City> getCities(URI uri);
}
