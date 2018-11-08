package com.jurijz.geocodingsearch.service.impl;

import com.jurijz.geocodingsearch.domain.City;
import com.jurijz.geocodingsearch.service.CityImportService;
import com.jurijz.geocodingsearch.service.CityRepository;
import com.jurijz.geocodingsearch.service.CityService;
import com.jurijz.geocodingsearch.service.GeocodingService;
import com.jurijz.geocodingsearch.utils.PropertyUtil;
import com.jurijz.geocodingsearch.utils.TransformResponseToCity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

/**
 * Created by jurijz on 11/6/2018.
 */
public class CityServiceImpl implements CityService {

    private static final Logger LOG = LoggerFactory.getLogger(CityServiceImpl.class);

    private final CityImportService cityImportService;
    private final CityRepository cityRepository;
    private final GeocodingService geocodingService;

    public CityServiceImpl(CityImportService cityImportService,
                           CityRepository cityRepository, GeocodingService geocodingService) {
        this.cityImportService = cityImportService;
        this.cityRepository = cityRepository;
        this.geocodingService = geocodingService;
    }

    //TODO: Google limitation - 50 queries per second
    public void processCities() {
        URL url = Thread.currentThread().getContextClassLoader()
                .getResource(PropertyUtil.getProperty("import.csv.path"));
        List<City> cities;
        try {
            cities = cityImportService.getCities(url != null ? url.toURI() : null);
            LOG.info("Import finished, imported {} cities", cities.size());
        } catch (URISyntaxException e) {
            LOG.error("Error importing cities from file ", e);
            return;
        }

        final ExecutorService pool = Executors.newFixedThreadPool(10);
        final ExecutorCompletionService<City> completionService =
                new ExecutorCompletionService<>(pool);

        for (City city : cities) {
            completionService.submit(() -> TransformResponseToCity.transform(city,
                    geocodingService.getCityInfo(city, 1)));
        }

        for (int i = 0; i < cities.size(); i++) {
            final Future<City> future;
            try {
                future = completionService.take();
                final City city = future.get();
                if (city.getId() != null) {
                    cityRepository.save(city);
                }
            } catch (InterruptedException | ExecutionException e) {
                LOG.error("Error retrieving results from Google ", e);
            }
        }
    }
}
