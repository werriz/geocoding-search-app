package com.jurijz.geocodingsearch.domain.geocoding;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by jurijz on 10/22/2018.
 */
@XmlRootElement(name = "GeocodeResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class GeocodeResponse {

    @XmlElement
    private String status;

    @XmlElement(name = "error_message")
    private String errorMessage;

    @XmlElement(name = "result")
    private GeocodeResult result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public GeocodeResult getResult() {
        return result;
    }

    public void setResult(GeocodeResult result) {
        this.result = result;
    }
}
