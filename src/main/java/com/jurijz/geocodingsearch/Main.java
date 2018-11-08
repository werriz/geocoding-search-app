package com.jurijz.geocodingsearch;

import com.jurijz.geocodingsearch.client.GeocodingController;
import com.jurijz.geocodingsearch.utils.HibernateUtil;
import com.jurijz.geocodingsearch.utils.PropertyUtil;

import java.util.Scanner;

/**
 * Created by jurijz on 10/21/2018.
 */
public class Main {

    public static void main(String[] args) {

        PropertyUtil.initProperties();
        HibernateUtil.getSessionFactory();
        //Initialize beans
        CityImportService cityImportService = new CityImportCsvServiceImpl();
        GeocodingService geocodingService = new GeocodingServiceImpl();
        CityRepository repository = new CityRepositoryImpl();
        NearestCitySearchService searchService = new NearestCitySearchServiceImpl();
        CityService cityService = new CityServiceImpl(cityImportService, repository, geocodingService);

        try (Scanner scanner = new Scanner(System.in)) {
            GeocodingController controller = new GeocodingController(cityService,
                    repository, searchService, scanner);

            controller.entry();
        } finally {
            HibernateUtil.shutdown();
            System.exit(0);
        }
    }
}
