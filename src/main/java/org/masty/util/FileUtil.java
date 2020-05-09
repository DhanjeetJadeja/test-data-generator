package org.masty.util;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileUtil {
    private static final Logger logger = Logger.getLogger(FileUtil.class.getName());

    private FileUtil() {
    }

    public static List<String> read(String path) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                URL url = FileUtil.class.getClassLoader().getResource(path);
                file = new File(url.getPath());
            }
            return Files.readAllLines(Paths.get(file.getAbsolutePath()));
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return new ArrayList<>();
    }

    public static Properties readProperties(String path) {
        Properties prop = new Properties();
        InputStream inputStream = FileUtil.class.getClassLoader().getResourceAsStream(path);
        if (inputStream != null) {
            try {
                logger.log(Level.INFO, String.format("Reading properties from %s", path));
                prop.load(inputStream);
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        } else {
            logger.log(Level.WARNING, String.format("Property file %s not found in classpath", path));
        }
        return prop;
    }

    public static void write(String path, List<String> records) {
        try {
            Files.write(Paths.get(path), records);
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    public static Map<String, String> arguments(String[] args) {
        String argDelitter = "=";
        Map<String, String> argMap = new HashMap<>();
        String key;
        String value;
        for (String s : args) {
            String[] param = s.split(argDelitter);
            key = param[0];
            try {
                value = param[1];
            } catch (IndexOutOfBoundsException e) {
                value = "";
            }
            if (!key.isEmpty()) {
                argMap.put(key.trim(), value.trim());
            }
        }
        return argMap;
    }
}