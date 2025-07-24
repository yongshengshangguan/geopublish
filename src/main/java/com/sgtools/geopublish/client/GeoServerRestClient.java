package com.sgtools.geopublish.client;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.util.Base64;

public class GeoServerRestClient {
    private final String baseUrl;
    private final String authHeader;

    public GeoServerRestClient(String baseUrl, String username, String password) {
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        String auth = username + ":" + password;
        this.authHeader = "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
    }

    public boolean uploadShp(String workspace, String layerName, File zipFile, boolean overwrite) throws Exception {
        String url = String.format("%s/rest/workspaces/%s/datastores/%s/file.shp", baseUrl, workspace, layerName);
        if (overwrite) url += "?overwrite=true";
        HttpPut put = new HttpPut(url);
        put.setHeader("Authorization", authHeader);
        put.setHeader("Content-type", "application/zip");
        put.setEntity(new FileEntity(zipFile));

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(put)) {
            int code = response.getStatusLine().getStatusCode();
            return code >= 200 && code < 300;
        }
    }

    public boolean uploadGeoTiff(String workspace, String layerName, File tifFile, boolean overwrite) throws Exception {
        String url = String.format("%s/rest/workspaces/%s/coveragestores/%s/file.geotiff", baseUrl, workspace, layerName);
        if (overwrite) url += "?overwrite=true";
        HttpPut put = new HttpPut(url);
        put.setHeader("Authorization", authHeader);
        put.setHeader("Content-type", "image/tiff");
        put.setEntity(new FileEntity(tifFile));

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(put)) {
            int code = response.getStatusLine().getStatusCode();
            return code >= 200 && code < 300;
        }
    }
}
