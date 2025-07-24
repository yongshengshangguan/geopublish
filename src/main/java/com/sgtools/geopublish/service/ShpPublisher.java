package com.sgtools.geopublish.service;

import com.sgtools.geopublish.client.GeoServerRestClient;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ShpPublisher {
    private final GeoServerRestClient client;

    public ShpPublisher(GeoServerRestClient client) {
        this.client = client;
    }

    public void publish(String workspace, File shpFile, boolean overwrite) throws Exception {
        String layerName = shpFile.getName().replaceFirst("\\.shp$", "");
        File zipFile = zipShapefile(shpFile);
        boolean success = client.uploadShp(workspace, layerName, zipFile, overwrite);
        if (!success) {
            throw new RuntimeException("上传失败: " + shpFile.getName());
        }
        zipFile.delete();
    }

    private File zipShapefile(File shpFile) throws Exception {
        File dir = shpFile.getParentFile();
        String baseName = shpFile.getName().replaceFirst("\\.shp$", "");
        File zip = new File(dir, baseName + ".zip");
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zip))) {
            for (String ext : new String[]{".shp", ".shx", ".dbf", ".prj"}) {
                File f = new File(dir, baseName + ext);
                if (f.exists()) {
                    zos.putNextEntry(new ZipEntry(f.getName()));
                    Files.copy(f.toPath(), zos);
                    zos.closeEntry();
                }
            }
        }
        return zip;
    }
}
