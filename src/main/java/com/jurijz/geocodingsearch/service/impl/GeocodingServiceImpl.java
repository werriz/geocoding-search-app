package com.jurijz.geocodingsearch.service.impl;

import com.jurijz.geocodingsearch.domain.City;
import com.jurijz.geocodingsearch.domain.geocoding.GeocodeResponse;
import com.jurijz.geocodingsearch.service.GeocodingService;
import com.jurijz.geocodingsearch.utils.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by jurijz on 10/18/2018.
 */
public class GeocodingServiceImpl implements GeocodingService {

    private static final Logger LOG = LoggerFactory.getLogger(GeocodingServiceImpl.class);

    private static final File GEOCODE_RESPONSE_XSD = new File(GeocodingServiceImpl.class.getClassLoader()
            .getResource("GeocodeResponse.xsd")
            .getFile());

    private static final String PROTOCOL = "https";
    private static final String PATH = "//maps.googleapis.com/maps/api/geocode/xml";
    private static final String FRAGMENT_ADDRESS = "address=";
    private static final String FRAGMENT_COUNTRY = "&components=country:";
    private static final String FRAGMENT_KEY = "&key=";
    private static final String PROPERTY_KEY = "google.apiKey";

    private Schema schema;

    //JAXBContext is thread-safe
    private static final JAXBContext JAXB_CONTEXT;

    static {
        try {
            JAXB_CONTEXT = JAXBContext.newInstance(GeocodeResponse.class);
        } catch (JAXBException e) {
            LOG.error("Cannot create jaxb context.");
            throw new RuntimeException("GeocodeResponse.class is not valid class for mapping results.", e);
        }
    }



    public GeocodeResponse getCityInfo(City city, int counter) {
        URI uri = null;
        try {
            String query = FRAGMENT_ADDRESS + city.getCityName() +
                FRAGMENT_COUNTRY + city.getCountry() +
                FRAGMENT_KEY + PropertyUtil.getProperty(PROPERTY_KEY);
            uri = new URI(PROTOCOL,
                    null,
                    PATH, query, null);
        } catch (URISyntaxException e) {
            LOG.error("Cannot form uri for request ", e);
        }
        Exception exception;
        try {
            if (uri != null) {
                //Unmarshaller creation is very light, yet it is not thread-safe
                Unmarshaller unmarshaller = JAXB_CONTEXT.createUnmarshaller();
                SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                if (schema == null) {
                    schema = schemaFactory.newSchema(GEOCODE_RESPONSE_XSD);
                }
                InputStream xml = uri.toURL().openStream();
                unmarshaller.setSchema(schema);
                GeocodeResponse response = (GeocodeResponse) unmarshaller.unmarshal(xml);
                xml.close();
                if (response.getStatus().equals("OK")) {
                    return response;
                } else {
                    LOG.warn("Response status was: " + response.getStatus());
                }
            }
            exception = new Exception("Failed to receive: " + city.getCityName() + ", " + city.getCountry());
        } catch (JAXBException | SAXException | IOException e) {
            exception = e;
            LOG.warn("Failed to parse: " + city.getCityName() + ", " + city.getCountry());
        }

        //Google's recommendation to retry after increasing time intervals.
        counter++;
        try {
            Thread.sleep(100 * counter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (counter < 5) {
            LOG.warn("Retrying to retrieve: " + city.getCityName() + ", " + city.getCountry());
            return getCityInfo(city, counter);
        } else {
            LOG.error("Unable to retrieve:" + city.getCityName() + ", " + city.getCountry(), exception);
            return null;
        }
    }
}
