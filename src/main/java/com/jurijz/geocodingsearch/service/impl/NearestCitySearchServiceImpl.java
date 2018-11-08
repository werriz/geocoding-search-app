package com.jurijz.geocodingsearch.service.impl;

import com.jurijz.geocodingsearch.domain.City;
import com.jurijz.geocodingsearch.domain.CityKDTree;
import com.jurijz.geocodingsearch.domain.CityNode;
import com.jurijz.geocodingsearch.service.NearestCitySearchService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jurijz on 10/26/2018.
 */
public class NearestCitySearchServiceImpl implements NearestCitySearchService {

    public City isInCity(List<City> cities, float longitude, float latitude) {

        CityKDTree kdt = new CityKDTree(cities.size());

        cities.forEach(city -> kdt.add(new float[]{city.getLongitude(), city.getLatitude()},
                city.getId()));

        float point[] = {longitude, latitude};

        CityNode nearestNode = kdt.findNearest(point);

        System.out.println("The nearest city is: ");

        return cities.stream().filter(city -> city.getId().equals(nearestNode.getCityId()))
                .findFirst().orElse(null);
    }

    public List<City> tenNearest(List<City> cities, float longitude, float latitude) {

        List<City> nearestCities = new ArrayList<>();

        float point[] = {longitude, latitude};

        for (int i = 0 ; i < 10; i++) {
            CityKDTree kdt = new CityKDTree(cities.size() - i);
            cities.forEach(city -> kdt.add(new float[]{city.getLongitude(), city.getLatitude()},
                    city.getId()));


            CityNode nearestNode = kdt.findNearest(point);

            City found = cities.stream().filter(city -> city.getId().equals(nearestNode.getCityId()))
                    .findFirst().orElse(null);
            nearestCities.add(found);
            cities.removeIf(city -> city.getId().equals(nearestNode.getCityId()));
        }

        return nearestCities;
    }
}
