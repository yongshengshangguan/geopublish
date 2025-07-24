package com.sgtools.geopublish.service;

import com.sgtools.geopublish.client.GeoServerRestClient;

import java.io.File;

public class TifPublisher {
    private final GeoServerRestClient client;

    public TifPublisher(GeoServerRestClient client) {
        this.client = client;
    }

    public void publish(String workspace, File tifFile, boolean overwrite) throws Exception {
        String layerName = tifFile.getName().replaceFirst("\\.tif$", "");
        boolean success = client.uploadGeoTiff(workspace, layerName, tifFile, overwrite);
        if (!success) {
            throw new RuntimeException("上传失败: " + tifFile.getName());
        }
    }
}
