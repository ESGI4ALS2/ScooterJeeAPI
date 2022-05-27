package com.scooterjee;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageForwardRequest;
import com.byteowls.jopencage.model.JOpenCageLatLng;
import com.byteowls.jopencage.model.JOpenCageResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Disabled
public class GeocodingTest {

    private Properties appProps;

    @BeforeEach
    void setUp() throws IOException {
        String rootPath = getClass().getClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "application.properties";

        appProps = new Properties();
        appProps.load(new FileInputStream(appConfigPath));
    }

    @Test
    void shouldReturnLatLng() {
        JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder(appProps.getProperty("open_cages.token"));
        JOpenCageForwardRequest request = new JOpenCageForwardRequest("242 Rue du Faubourg Saint-Antoine, 75012 Paris");
        request.setRestrictToCountryCode("fr"); // restrict results to a specific country

        JOpenCageResponse response = jOpenCageGeocoder.forward(request);
        JOpenCageLatLng firstResultLatLng = response.getFirstPosition(); // get the coordinate pair of the first result
        System.out.println(firstResultLatLng.getLat().toString() + "," + firstResultLatLng.getLng().toString());
        Assertions.assertEquals(firstResultLatLng.getLat(), 48.84916408327136, 0.0002);
        Assertions.assertEquals(firstResultLatLng.getLng(), 2.389726114492201, 0.0002);
    }
}
