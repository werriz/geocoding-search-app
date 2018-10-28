package com.jurijz.geocodingsearch.client;

import com.jurijz.geocodingsearch.domain.City;
import com.jurijz.geocodingsearch.domain.geocoding.GeocodeResponse;
import com.jurijz.geocodingsearch.service.CityImportService;
import com.jurijz.geocodingsearch.service.GeocodingRepository;
import com.jurijz.geocodingsearch.service.GeocodingService;
import com.jurijz.geocodingsearch.service.NearestCitySearchService;
import com.jurijz.geocodingsearch.utils.PropertyUtil;
import com.jurijz.geocodingsearch.utils.TransformResponseToCity;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

/**
 * Created by jurijz on 10/23/2018.
 */
public class GeocodingController {

    private CityImportService cityImportService;
    private GeocodingService geocodingService;
    private GeocodingRepository repository;
    private NearestCitySearchService searchService;

    private Scanner scanner;

    public GeocodingController(CityImportService cityImportService, GeocodingService geocodingService,
                               GeocodingRepository repository, Scanner scanner) {
        this.cityImportService = cityImportService;
        this.geocodingService = geocodingService;
        this.repository = repository;
        this.scanner = scanner;
        this.searchService = new NearestCitySearchService();
    }

    public void entry() {
        boolean isActive = true;

        while (isActive) {
            System.out.println("--== WELCOME TO GEOCODING APP ==--");
            System.out.println();
            System.out.println("Please enter following menu number to proceed");
            System.out.println("1. Import cities to DB.");
            System.out.println("2. Check if provided point is in the imported city.");
            System.out.println("3. Find 10 nearest imported cities.");
            System.out.println("4. Exit");
            scanner = new Scanner(System.in);
            int menu = scanner.nextInt();
            switch(menu) {
                case 1:
                    processCities();
                    break;
                case 2:
                    checkIfCity();
                    break;
                case 3:
                    findNearest();
                    break;
                case 4:
                    isActive = false;
                    break;
                default:
                    System.out.println("Invalid menu option, please try again.");
            }

        }
    }

    private void processCities() {
        System.out.println("Starting importing cities from " +
                PropertyUtil.getProperty("import.csv.path"));
        URL url = Thread.currentThread().getContextClassLoader()
                .getResource(PropertyUtil.getProperty("import.csv.path"));
        List<City> cities = null;
        try {
            cities = cityImportService.getCities(url != null ? url.toURI() : null);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if (cities != null) {
            for (City city : cities) {
                new Thread(() -> {
                    GeocodeResponse response = geocodingService.getCityInfo(city);
                    repository.save(TransformResponseToCity.transform(city, response));
                }).start();
            }
        }

    }

    private void checkIfCity() {
        boolean isActive = true;
        System.out.println("--== Check if point is in one of the imported cities ==--");
        while (isActive) {
            System.out.println("======================================");
            List<City> cities = repository.loadAll();
            System.out.print("Please enter longitude: ");
            float longitude = scanner.nextFloat();
            System.out.println("Great!");
            System.out.print("Now enter latitude: ");
            float latitude = scanner.nextFloat();
            System.out.println("Great!");
            System.out.println("--------------------------------------");
            System.out.println("Latitude: " + latitude + " and longitude " + longitude);
            City found = searchService.isInCity(cities, longitude, latitude);

            if (found != null && longitude >= found.getSwLng() && longitude <= found.getNeLng()
                    && latitude >= found.getSwLat() && latitude <= found.getNeLat()) {
                System.out.print("Provided point is in: ");
                System.out.println(found.getCityName() + " , " + found.getCountry());
                System.out.println("(" + found.getLongitude() + " , " + found.getLatitude() + ")");
            }

            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            System.out.println("Want to retry or go back (options 1 and 2):");
            int menu = scanner.nextInt();
            isActive = menu == 1;
        }
    }

    private void findNearest() {
        boolean isActive = true;
        System.out.println("--== Find 10 nearest imported cities for point ==--");
        while (isActive) {
            System.out.println("======================================");
            List<City> cities = repository.loadAll();
            System.out.print("Please enter longitude: ");
            float longitude = scanner.nextFloat();
            System.out.println("Great!");
            System.out.print("Now enter latitude: ");
            float latitude = scanner.nextFloat();
            System.out.println("Great!");
            System.out.println("--------------------------------------");
            System.out.println("Latitude: " + latitude + " and longitude " + longitude);
            List<City> found = searchService.tenNearest(cities, longitude, latitude);

            found.forEach(city ->
                System.out.println(city.getCityName() + ", " + city.getCountry() + " : " +
                        city.getLongitude() + " - " + city.getLatitude()));

            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            System.out.println("Want to retry or go back (options 1 and 2):");
            int menu = scanner.nextInt();
            isActive = menu == 1;
        }
    }


}
