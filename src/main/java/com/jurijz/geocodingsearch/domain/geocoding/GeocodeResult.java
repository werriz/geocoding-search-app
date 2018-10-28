package com.jurijz.geocodingsearch.domain.geocoding;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Created by jurijz on 10/22/2018.
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class GeocodeResult {

    @XmlElement(name = "type")
    private List<String> types;

    @XmlElement(name = "formatted_address")
    private String formattedAddress;

    @XmlElement(name = "address_component")
    private List<AddressComponent> addressComponents;

    @XmlElement
    private Geometry geometry;

    @XmlElement(name = "place_id")
    private String placeId;

    public GeocodeResult() {
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public List<AddressComponent> getAddressComponents() {
        return addressComponents;
    }

    public void setAddressComponents(List<AddressComponent> addressComponents) {
        this.addressComponents = addressComponents;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
}
