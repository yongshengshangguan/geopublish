package com.sgtools.geopublish;

import com.sgtools.geopublish.client.GeoServerRestClient;
import com.sgtools.geopublish.service.ShpPublisher;
import com.sgtools.geopublish.service.TifPublisher;

import java.io.File;

public class GeoPublisher {
    private final GeoServerRestClient client;
    private final ShpPublisher shpPublisher;
    private final TifPublisher tifPublisher;

    public GeoPublisher(String geoserverUrl, String username, String password) {
        this.client = new GeoServerRestClient(geoserverUrl, username, password);
        this.shpPublisher = new ShpPublisher(client);
        this.tifPublisher = new TifPublisher(client);
    }

    /** 
     * 批量发布 Shapefile 文件夹下所有 shp 文件
     * @param workspace GeoServer 工作区
     * @param folderPath 本地文件夹路径，递归查找 *.shp
     * @param overwrite 是否覆盖已存在图层
     */
    public void publishShapefiles(String workspace, String folderPath, boolean overwrite) throws Exception {
        for (File shp : com.sgtools.geopublish.util.FileScanner.findFiles(folderPath, ".shp")) {
            shpPublisher.publish(workspace, shp, overwrite);
        }
    }

    /**
     * 批量发布 GeoTIFF 文件夹下所有 tif 文件
     * @param workspace GeoServer 工作区
     * @param folderPath 本地文件夹路径，递归查找 *.tif
     * @param overwrite 是否覆盖已存在图层
     */
    public void publishTiffFiles(String workspace, String folderPath, boolean overwrite) throws Exception {
        for (File tif : com.sgtools.geopublish.util.FileScanner.findFiles(folderPath, ".tif")) {
            tifPublisher.publish(workspace, tif, overwrite);
        }
    }
}
