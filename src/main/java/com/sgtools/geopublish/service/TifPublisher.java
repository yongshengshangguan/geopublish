package com.sgtools.geopublish.service;

import com.sgtools.geopublish.client.GeoServerRestClient;
import java.io.File;

public class TifPublisher {
    private final GeoServerRestClient client;

    public TifPublisher(GeoServerRestClient client) {
        this.client = client;
    }

    public void publish(File tifFile, boolean overwrite) throws Exception {
        String layerName = tifFile.getName().replace(".tif", "");
        client.uploadGeoTiff(layerName, tifFile, overwrite);
    }
}
