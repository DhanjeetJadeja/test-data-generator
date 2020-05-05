import org.masty.util.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class TestDataGenerator {
    static List<String> ips;

    public static void main(String[] args) {
        TestDataGenerator tdg = new TestDataGenerator();
        long start = System.currentTimeMillis(), stop;
        String fileName = tdg.generateFlatFile(args);
        stop = System.currentTimeMillis();
        System.out.println("~~ Success ~~ !!");
        System.out.println("Test Data Generator generated " + ips.size() + " IPs in " + fileName + " taking " + (stop - start) + " ms");
    }

    public String generateFlatFile(String... args) {
        ips = new ArrayList<>();
        Map<String, String> arguments = FileUtil.arguments(args);
        short[] from = new short[4];
        short[] to = new short[4];
        short i, j, k, l;
        Properties prop = new Properties();
        String confFile = resolveStringArgument(arguments, prop, "config", "tdg.properties");
        if (null == prop) prop = FileUtil.readProperties(confFile);

        String[] octets = {"first", "second", "third", "fourth"};
        short defaultFrom = 1, defaultTo = 254;
        String defaultDelimiter = ":";
        int count = defaultFrom;

        String c = "", line = "", ipDecimal = "", ipHex = "", ipBinary = "";
        boolean finerIps = arguments.get("finerIps") != null ? Boolean.parseBoolean(arguments.get("finerIps")) :
                prop.getProperty("finerIps") != null && Boolean.parseBoolean(prop.getProperty("finerIps"));

        int ROW_COUNT = resolveIntegerArgument(arguments, prop, "count", 1_000_000);
        String delimiter = resolveStringArgument(arguments, prop, "delimiter", defaultDelimiter);

        if (finerIps) {
            short counter = 0;
            for (String position : octets) {
                from[counter] = resolveShortArgument(arguments, prop, "from." + position, defaultFrom);
                to[counter++] = resolveShortArgument(arguments, prop, "to." + position, defaultFrom);
            }
        } else {
            short fromAll = resolveShortArgument(arguments, prop, "from", defaultFrom);
            short toAll = resolveShortArgument(arguments, prop, "to", defaultTo);
            for (i = 0; i < 4; i++) {
                from[i] = fromAll;
                to[i] = toAll;
            }
        }

        boolean index = resolveBooleanArgument(arguments, prop, "index", false);
        boolean decimalIp = resolveBooleanArgument(arguments, prop, "decimalIp", false);
        boolean hexIp = resolveBooleanArgument(arguments, prop, "hexIp", true);
        boolean binaryIp = resolveBooleanArgument(arguments, prop, "binaryIp", false);

        for (i = from[0]; i <= to[0]; i++) {
            for (j = from[1]; j <= to[1]; j++) {
                for (k = from[2]; k <= to[2]; k++) {
                    for (l = from[3]; l <= to[3]; l++) {
                        if (count <= ROW_COUNT) {
                            line = "";
                            if (index) {
                                c = String.format("%08d", count++);
                                line = c + delimiter;
                            } else {
                                count++;
                            }
                            if (decimalIp) {
                                ipDecimal = getDecimalIp(i, j, k, l);
                                line = line + ipDecimal + delimiter;
                            }
                            if (hexIp) {
                                ipHex = getHexIp(i, j, k, l);
                                line = line + ipHex + delimiter;
                            }
                            if (binaryIp) {
                                ipBinary = getBinaryIp(i, j, k, l);
                                line = line + ipBinary + delimiter;
                            }
                            line = line.substring(0, line.length() - delimiter.length()) + "\n";
                            ips.add(line);
                        } else {
                            break;
                        }
                    }
                }
            }
        }

        String fileName = "tdg-op-" + System.currentTimeMillis() + ".csv";
        FileUtil.write(fileName, ips);
        return fileName;
    }

    public int resolveIntegerArgument(Map<String, String> arguments, Properties prop, String key, int defaultValue) {
        return arguments.get(key) == null ? prop.getProperty(key) == null ? defaultValue : Integer.parseInt(prop.getProperty(key)) : Integer.parseInt(arguments.get(key));
    }

    public String resolveStringArgument(Map<String, String> arguments, Properties prop, String key,
                                        String defaultValue) {
        return arguments.get(key) == null ? prop.getProperty(key) == null ? defaultValue : prop.getProperty(key) : arguments.get(key);
    }

    public short resolveShortArgument(Map<String, String> arguments, Properties prop, String key, short defaultValue) {
        return arguments.get(key) == null ? prop.getProperty(key) == null ? defaultValue : Short.parseShort(prop.getProperty(key)) : Short.parseShort(arguments.get(key));
    }

    public boolean resolveBooleanArgument(Map<String, String> arguments, Properties prop, String key, boolean defaultValue) {
        return arguments.get(key) == null ? prop.getProperty(key) == null ? defaultValue : Boolean.parseBoolean(prop.getProperty(key)) : Boolean.parseBoolean(arguments.get(key));
    }

    public String getBinaryIp(short i, short j, short k, short l) {
        String first, second, third, fourth;
        first = Integer.toBinaryString(i);
        second = Integer.toBinaryString(j);
        third = Integer.toBinaryString(k);
        fourth = Integer.toBinaryString(l);

        return String.format("%16s", first)
                + "." + String.format("%16s", second)
                + "." + String.format("%16s", third)
                + "." + String.format("%16s", fourth);
    }

    public String getHexIp(short i, short j, short k, short l) {
        String first, second, third, fourth;
        first = Integer.toHexString(i);
        second = Integer.toHexString(j);
        third = Integer.toHexString(k);
        fourth = Integer.toHexString(l);

        first = first.length() == 1 ? "0" + first : first;
        second = second.length() == 1 ? "0" + second : second;
        third = third.length() == 1 ? "0" + third : third;
        fourth = fourth.length() == 1 ? "0" + fourth : fourth;

        return first + second + third + fourth;
    }

    public String getDecimalIp(short i, short j, short k, short l) {
        return i
                + "." + j
                + "." + k
                + "." + l;
    }
}
