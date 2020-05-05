package org.masty.util;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class FileUtil {

    private static void writeRaw(List<String> records) throws IOException {
        File file = File.createTempFile("foo", ".txt");
        try {
            FileWriter writer = new FileWriter(file);
            System.out.print("Writing raw... ");
            write(records, writer);
        } finally {
            file.delete();
        }
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
            e.printStackTrace();
        }
        return null;
    }

    public static Properties readProperties(String path) {
        Properties prop = new Properties();
        InputStream inputStream = FileUtil.class.getClassLoader().getResourceAsStream(path);
        if (inputStream != null) {
            try {
                System.out.println("Reading properties from " + path);
                prop.load(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("property file " + path + " not found in classpath");
        }
        return prop;
    }

    private static void write(List<String> records, Writer writer) throws IOException {
        long start = System.currentTimeMillis();
        for (String record : records) {
            writer.write(record);
        }
        writer.flush();
        writer.close();
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000f + " seconds");
    }

    public static void write(String path, List<String> records) {
        try {
            FileWriter writer = new FileWriter(new File(path));
            for (String record : records) {
                writer.write(record);
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> arguments(String[] args) {
        String argDelitter = "=";
        Map<String, String> argMap = new HashMap<>();
        String key, value;
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