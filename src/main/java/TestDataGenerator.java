import org.masty.util.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class TestDataGenerator {
    public static void main(String[] args) {
        Map<String, String> arguments = FileUtil.arguments(args);
        Properties prop;

        String confFile = arguments.get("config") != null ? arguments.get("config") : "tdg.properties";
        prop = FileUtil.readProperties(confFile);
        List<String> ips = new ArrayList<>();
        int count = 1;

        String c = "", line = "", ipDecimal = "", ipHex = "", ipBinary = "";
        String first, second, third, fourth;
        int from = arguments.get("from") != null ? Integer.parseInt(arguments.get("from")) :
                prop.getProperty("from") != null ? Integer.parseInt(prop.getProperty("from")) : 1;
        int to = arguments.get("to") != null ? Integer.parseInt(arguments.get("to")) :
                prop.getProperty("to") != null ? Integer.parseInt(prop.getProperty("to")) : 254;

        long start, stop;

        int ROW_COUNT = arguments.get("count") != null ? Integer.parseInt(arguments.get("count")) :
                prop.getProperty("count") != null ? Integer.parseInt(prop.getProperty("count")) : 1_000_000;

        boolean index = arguments.get("index") != null ? Boolean.parseBoolean(arguments.get("index")) :
                prop.getProperty("index") != null ? Boolean.parseBoolean(prop.getProperty("index")) : false;

        boolean decimalIp = arguments.get("decimalIp") != null ? Boolean.parseBoolean(arguments.get("decimalIp")) :
                prop.getProperty("decimalIp") != null ? Boolean.parseBoolean(prop.getProperty("decimalIp")) : false;

        boolean hexIp = arguments.get("hexIp") != null ? Boolean.parseBoolean(arguments.get("hexIp")) :
                prop.getProperty("hexIp") != null ? Boolean.parseBoolean(prop.getProperty("hexIp")) : false;

        boolean binaryIp = arguments.get("binaryIp") != null ? Boolean.parseBoolean(arguments.get("binaryIp")) :
                prop.getProperty("binaryIp") != null ? Boolean.parseBoolean(prop.getProperty("binaryIp")) : false;

        start = System.currentTimeMillis();
        for (int i = from; i <= to; i++) {
            for (int j = from; j <= to; j++) {
                for (int k = from; k <= to; k++) {
                    for (int l = from; l <= to; l++) {
                        if (count <= ROW_COUNT) {
                            line = "";
                            if (index) {
                                c = String.format("%08d", count++);
                                line = c + ",";
                            } else {
                                count++;
                            }
                            if (decimalIp) {
                                ipDecimal = String.format("%02d", i)
                                        + "." + String.format("%02d", j)
                                        + "." + String.format("%02d", k)
                                        + "." + String.format("%02d", l);
                                line = line + ipDecimal + ",";
                            }
                            if (hexIp) {
                                first = Integer.toHexString(i);
                                second = Integer.toHexString(j);
                                third = Integer.toHexString(k);
                                fourth = Integer.toHexString(l);

                                first = first.length() == 1 ? "0" + first : first;
                                second = second.length() == 1 ? "0" + second : second;
                                third = third.length() == 1 ? "0" + third : third;
                                fourth = fourth.length() == 1 ? "0" + fourth : fourth;

                                ipHex = first + second + third + fourth;
                                line = line + ipHex + ",";
                            }
                            if (binaryIp) {
                                first = Integer.toBinaryString(i);
                                second = Integer.toBinaryString(j);
                                third = Integer.toBinaryString(k);
                                fourth = Integer.toBinaryString(l);

                                ipBinary = String.format("%16s", first)
                                        + "." + String.format("%16s", second)
                                        + "." + String.format("%16s", third)
                                        + "." + String.format("%16s", fourth);
                                line = line + ipBinary + ",";
                            }
                            line = line + "\n";
                            ips.add(line);
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        stop = System.currentTimeMillis();
        String fileName = "ips-UnIverse-" + stop + ".csv";
        FileUtil.write(fileName, ips);
        System.out.println("~~ Success ~~ !! wrote " + ips.size() + " IPs in " + fileName + " taking " + (stop - start) + " ms");
    }
}
