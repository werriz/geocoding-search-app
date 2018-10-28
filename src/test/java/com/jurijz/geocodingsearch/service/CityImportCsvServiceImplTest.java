package com.jurijz.geocodingsearch.service;

import com.jurijz.geocodingsearch.domain.City;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

/**
 * Created by jurijz on 10/18/2018.
 */
public class CityImportCsvServiceImplTest {

    private static final String CSV_FILE_PATH = "sources/csv/500_europe_cities.csv";

    private CityImportCsvServiceImpl service;

    @BeforeEach
    public void setUp() {
        service = new CityImportCsvServiceImpl();
    }

    @Test
    public void given_csv_file_import_to_500_java_objects_list() throws URISyntaxException {
        URL url = Thread.currentThread().getContextClassLoader().getResource(CSV_FILE_PATH);
        List<City> result = service.getCities(url.toURI());
        Assertions.assertNotNull(result, "Imported city list should not be null");
        Assertions.assertEquals("Sarande", result.get(0).getCityName(), "First imported city name should be 'Sarande'");
    }
}
