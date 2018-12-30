package com.jurijz.geocodingsearch.service.impl;

import com.jurijz.geocodingsearch.domain.City;
import com.jurijz.geocodingsearch.service.CityImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by jurijz on 10/18/2018.
 */
public class CityImportCsvServiceImpl implements CityImportService {

    private static final Logger LOG = LoggerFactory.getLogger(CityImportCsvServiceImpl.class);

    private static final String CSV_SPLIT_BY = ";";

    public List<City> getCities(URI uri) {
        LOG.info("Starting importing cities from " + uri.toString());
        try (Stream<String> stream = Files.lines(Paths.get(uri))) {
            return stream.skip(1).map(line -> {
                String[] str = line.split(CSV_SPLIT_BY);
                return new City(str[0], str[1]);
            }).collect(Collectors.toList());
        } catch (IOException e) {
            LOG.error("Something is wrong parsing cities.");
            e.printStackTrace();
        }

        return null;
    }
}
