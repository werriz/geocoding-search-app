package com.jurijz.geocodingsearch.client;

import com.jurijz.geocodingsearch.domain.City;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by jurijz on 10/23/2018.
 */
public class GeocodingController {

    private final CityRepository repository;
    private final NearestCitySearchService searchService;
    private final CityService cityService;

    private Scanner scanner;

    public GeocodingController(CityService cityService, CityRepository repository,
                               NearestCitySearchService searchService, Scanner scanner) {
        this.repository = repository;
        this.scanner = scanner;
        this.cityService = cityService;
        this.searchService = searchService;
    }


    public void entry() {
        boolean isActive = true;

        while (isActive) {
            try {
                System.out.println("--== WELCOME TO GEOCODING APP ==--");
                System.out.println();
                System.out.println("Please enter following menu number to proceed");
                System.out.println("1. Import cities to DB.");
                System.out.println("2. Check if provided point is in the imported city.");
                System.out.println("3. Find 10 nearest imported cities.");
                System.out.println("4. Exit");
                int menu = scanner.nextInt();
                switch (menu) {
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
            } catch (InputMismatchException e) {
                System.out.println("Entered value is of incorrect type! Please reenter your choice " +
                        "according to menu numeric values");
                scanner.next();
            }
        }
    }

    private void processCities() {
        System.out.println("Starting processing cities ");
        cityService.processCities();
        System.out.println("Cities have been processed.");
    }

    private void checkIfCity() {
        boolean isActive = true;
        System.out.println("--== Check if point is in one of the imported cities ==--");
        while (isActive) {
            try {
                System.out.println("======================================");
                List<City> cities = repository.loadAll();
                System.out.print("Please enter longitude: ");
                float longitude = scanner.nextFloat();
                System.out.println("Great!");
                System.out.print("Now enter latitude: ");
                float latitude = scanner.nextFloat();
                System.out.println("Great!");
                System.out.println("--------------------------------------");
                System.out.println("Latitude: " + latitude + " and longitude: " + longitude);
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
            } catch (InputMismatchException e) {
                System.out.println("Entered value is of incorrect type! Please retry again.");
                scanner.next();
            }

        }
    }

    private void findNearest() {
        boolean isActive = true;
        System.out.println("--== Find 10 nearest imported cities for point ==--");
        while (isActive) {
            try {
                System.out.println("======================================");
                List<City> cities = repository.loadAll();
                System.out.print("Please enter longitude: ");
                float longitude = scanner.nextFloat();
                System.out.println("Great!");
                System.out.print("Now enter latitude: ");
                float latitude = scanner.nextFloat();
                System.out.println("Great!");
                System.out.println("--------------------------------------");
                System.out.println("Latitude: " + latitude + " and longitude: " + longitude);
                List<City> found = searchService.tenNearest(cities, longitude, latitude);

                found.forEach(city ->
                        System.out.println(city.getCityName() + ", " + city.getCountry() + " : " +
                                city.getLongitude() + " - " + city.getLatitude()));

                System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
                System.out.println("Want to retry or go back (options 1 and 2):");
                int menu = scanner.nextInt();
                isActive = menu == 1;
            } catch (InputMismatchException e) {
                System.out.println("Entered value is of incorrect type! Please retry again.");
                scanner.next();
            }
        }
    }


}
