package com.sgtools.geopublish;

import com.sgtools.geopublish.client.GeoServerRestClient;
import com.sgtools.geopublish.service.ShpPublisher;
import com.sgtools.geopublish.service.TifPublisher;
import com.sgtools.geopublish.util.FileScanner;
import java.io.File;
import java.util.List;

public class GeoPublisherApplication {
    public static void main(String[] args) {
        System.out.println("🔧 GeoServer Publisher 启动中...");

        String uploadDir = System.getProperty("upload.dir", "./data");
        boolean overwrite = Boolean.parseBoolean(System.getProperty("overwrite", "true"));

        GeoServerRestClient client = new GeoServerRestClient(
                "http://localhost:8080/geoserver",
                "admin",
                "geoserver",
                "my_workspace"
        );

        List<File> shpFiles = FileScanner.findFiles(uploadDir, ".shp");
        List<File> tifFiles = FileScanner.findFiles(uploadDir, ".tif");

        ShpPublisher shpPublisher = new ShpPublisher(client);
        TifPublisher tifPublisher = new TifPublisher(client);

        for (File shp : shpFiles) {
            try {
                shpPublisher.publish(shp, overwrite);
            } catch (Exception e) {
                System.err.println(" SHP 发布失败: " + shp.getName());
                e.printStackTrace();
            }
        }

        for (File tif : tifFiles) {
            try {
                tifPublisher.publish(tif, overwrite);
            } catch (Exception e) {
                System.err.println(" TIF 发布失败: " + tif.getName());
                e.printStackTrace();
            }
        }

        System.out.println(" 所有文件处理完毕");
    }
}
