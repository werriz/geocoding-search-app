package com.jurijz.geocodingsearch.domain.geocoding;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Created by jurijz on 10/23/2018.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AddressComponent {

    @XmlElement(name = "long_name")
    private String longName;

    @XmlElement(name = "short_name")
    private String shortName;

    @XmlElement(name = "type")
    private List<String> types;

    public AddressComponent() {
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }
}
