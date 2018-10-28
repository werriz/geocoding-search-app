package com.jurijz.geocodingsearch;

import com.jurijz.geocodingsearch.client.GeocodingController;
import com.jurijz.geocodingsearch.service.*;
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
        GeocodingRepository repository = new GeocodingRepositoryImpl();
        try (Scanner scanner = new Scanner(System.in)) {
            GeocodingController controller = new GeocodingController(cityImportService, geocodingService,
                    repository, scanner);

            controller.entry();
        } finally {
            HibernateUtil.shutdown();
            System.exit(0);
        }
    }
}
