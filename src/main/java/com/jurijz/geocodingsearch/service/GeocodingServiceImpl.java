package com.jurijz.geocodingsearch.service;

import com.jurijz.geocodingsearch.domain.City;
import com.jurijz.geocodingsearch.domain.geocoding.GeocodeResponse;
import com.jurijz.geocodingsearch.utils.PropertyUtil;
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
import java.net.URL;

/**
 * Created by jurijz on 10/18/2018.
 */
public class GeocodingServiceImpl implements GeocodingService {

    private static final String GEOCODING_URL = "https://maps.googleapis.com/maps/api/geocode/xml?";

    public GeocodeResponse getCityInfo(City city) {
        JAXBContext jc;
        try {
            jc = JAXBContext.newInstance(GeocodeResponse.class);

            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(
                    new File(this.getClass().getClassLoader().getResource("GeocodeResponse.xsd")
                    .getFile()));
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            unmarshaller.setSchema(schema);
            String url = "address=" + city.getCityName() +
                    "&components=country:" + city.getCountry() +
                    "&key=" + PropertyUtil.getProperty("google.apiKey");
            URL xmlURL = new URL(GEOCODING_URL + url);
            InputStream xml = xmlURL.openStream();
            GeocodeResponse response = (GeocodeResponse) unmarshaller.unmarshal(xml);
            xml.close();

            return response;
        } catch (JAXBException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
