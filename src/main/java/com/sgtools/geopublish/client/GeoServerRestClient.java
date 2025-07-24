package com.sgtools.geopublish.client;

import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.File;
import java.util.Base64;

public class GeoServerRestClient {
    private final String baseUrl;
    private final String authHeader;
    private final String workspace;

    public GeoServerRestClient(String baseUrl, String username, String password, String workspace) {
        this.baseUrl = baseUrl;
        this.workspace = workspace;
        String auth = username + ":" + password;
        this.authHeader = "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
    }

    public boolean uploadShp(String layerName, File zipFile, boolean overwrite) throws Exception {
        String url = baseUrl + "/rest/workspaces/" + workspace + "/datastores/" + layerName + "/file.shp";
        if (overwrite) url += "?overwrite=true";
        HttpPut put = new HttpPut(url);
        put.setHeader("Authorization", authHeader);
        put.setHeader("Content-type", "application/zip");
        put.setEntity(new org.apache.http.entity.FileEntity(zipFile));

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(put)) {
            return response.getStatusLine().getStatusCode() < 300;
        }
    }

    public boolean uploadGeoTiff(String layerName, File tifFile, boolean overwrite) throws Exception {
        String url = baseUrl + "/rest/workspaces/" + workspace + "/coveragestores/" + layerName + "/file.geotiff";
        if (overwrite) url += "?overwrite=true";
        HttpPut put = new HttpPut(url);
        put.setHeader("Authorization", authHeader);
        put.setHeader("Content-type", "image/tiff");
        put.setEntity(new org.apache.http.entity.FileEntity(tifFile));

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(put)) {
            return response.getStatusLine().getStatusCode() < 300;
        }
    }
}
