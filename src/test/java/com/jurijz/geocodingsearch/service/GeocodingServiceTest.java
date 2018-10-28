package com.jurijz.geocodingsearch.service;

import com.jurijz.geocodingsearch.domain.City;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.jurijz.geocodingsearch.utils.PropertyUtil.initProperties;

/**
 * Created by jurijz on 10/22/2018.
 */
public class GeocodingServiceTest {

    private GeocodingService service;

    @BeforeEach
    public void setUp() {
        initProperties();
        service = new GeocodingServiceImpl();
    }

    @Test
    public void given_one_city_and_country_parse_xml_response() {
        City city = new City("Moscow", "Russia");

        Object result = service.getCityInfo(city);
        Assertions.assertNotNull(result, "Xml response should not be null");
    }
}
