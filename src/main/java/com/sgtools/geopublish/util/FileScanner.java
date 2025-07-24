package com.sgtools.geopublish.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileScanner {
    public static List<File> findFiles(String basePath, String ext) {
        List<File> result = new ArrayList<>();
        scan(new File(basePath), ext.toLowerCase(), result);
        return result;
    }

    private static void scan(File dir, String ext, List<File> result) {
        if (!dir.exists() || !dir.isDirectory()) return;
        for (File f : dir.listFiles()) {
            if (f.isDirectory()) {
                scan(f, ext, result);
            } else if (f.getName().toLowerCase().endsWith(ext)) {
                result.add(f);
            }
        }
    }
}
