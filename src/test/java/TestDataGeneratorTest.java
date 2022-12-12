import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.masty.util.FileUtil;
import org.masty.util.TestDataGenerator;

import java.io.File;
import java.util.*;

public class TestDataGeneratorTest {
    TestDataGenerator tdg;

    @Before
    public void setup() {
        tdg = new TestDataGenerator();
    }

    @Test
    public void testConversions() {
        short i = 192, j = 168, k = 1, l = 254;
        Assert.assertEquals("192.168.1.254", tdg.getDecimalIp(i, j, k, l));
        Assert.assertEquals("        11000000.        10101000.               1.        11111110", tdg.getBinaryIp(i, j, k, l));
        Assert.assertEquals("0xc0a801fe", tdg.getHexIp(i, j, k, l));
    }

    @Test
    public void argumentMapParsing() {
        String[] args = {"key = value", "onlyKey=", "onlyKeyWithoutEquals", "=onlyValue"};
        Map<String, String> map = FileUtil.arguments(args);
        Assert.assertEquals("value", map.get("key"));
        Assert.assertEquals("", map.get("onlyKey"));
        Assert.assertEquals("", map.get("onlyKeyWithoutEquals"));
        Assert.assertEquals(false, map.keySet().contains(""));
    }

    @Test
    public void testResolvingParameter() {
        Map<String, String> arguments = new HashMap<>();
        Properties prop = new Properties();
        arguments.put("stringKey", "arguments");
        arguments.put("shortKey", "1");
        arguments.put("integerKey", "1000000");
        arguments.put("booleanTrueKey", "true");
        arguments.put("booleanFalseKey", "true");

        prop.setProperty("shortKey", "property");
        prop.setProperty("shortKey", "192");
        prop.setProperty("integerKey", "1000000");
        prop.setProperty("booleanTrueKey", "true");
        prop.setProperty("booleanFalseKey", "true");
        prop.setProperty("stringKey1", "property1");
        prop.setProperty("shortKey1", "168");
        prop.setProperty("integerKey1", "2000000");
        prop.setProperty("booleanTrueKey1", "true");
        prop.setProperty("booleanFalseKey1", "false");

        Assert.assertEquals("arguments", tdg.resolveStringArgument(arguments, prop, "stringKey", "defaultValue"));
        Assert.assertEquals("property1", tdg.resolveStringArgument(arguments, prop, "stringKey1", "arguments"));
        Assert.assertEquals("default", tdg.resolveStringArgument(arguments, prop, "none", "default"));

        short defaultShort = 255;
        Assert.assertEquals(1, tdg.resolveShortArgument(arguments, prop, "shortKey", defaultShort));
        Assert.assertEquals(168, tdg.resolveShortArgument(arguments, prop, "shortKey1", defaultShort));
        Assert.assertEquals(255, tdg.resolveShortArgument(arguments, prop, "none", defaultShort));

        Assert.assertEquals(true, tdg.resolveBooleanArgument(arguments, prop, "booleanTrueKey", false));
        Assert.assertEquals(false, tdg.resolveBooleanArgument(arguments, prop, "booleanFalseKey1", true));
        Assert.assertEquals(true, tdg.resolveBooleanArgument(arguments, prop, "none", true));

        Assert.assertEquals(1_000_000, tdg.resolveIntegerArgument(arguments, prop, "integerKey", 3_000_000));
        Assert.assertEquals(2_000_000, tdg.resolveIntegerArgument(arguments, prop, "integerKey1", 3_000_000));
        Assert.assertEquals(3_000_000, tdg.resolveIntegerArgument(arguments, prop, "none", 3_000_000));
    }

    @Test
    public void finerIps() {
        String[] args = {"count=2", "finerIps=true",
                "from.first=1", "from.second=2", "from.third=3", "from.fourth=4",
                "to.first=1", "to.second=2", "to.third=3", "to.fourth=5",
                "index=true", "hexIp=true", "decimalIp=true", "binaryIp=true"};
        String test1File = tdg.generateFlatFile(args);
        File file = new File(test1File);
        List<String> lines = FileUtil.read(test1File);
        Assert.assertEquals(2, lines.size());
        Assert.assertEquals("00000001:1.2.3.4:0x01020304:               1.              10.              11.         " +
                "    100", lines.get(0));
        Assert.assertEquals("00000002:1.2.3.5:0x01020305:               1.              10.              11.         " +
                "    101", lines.get(1));
        file.delete();
    }

    @Test
    public void fileSizeForAllRecords() {
        String[] args1 = {"count=1", "finerIps=false",
                "from=1", "to=2", "delimiter=:",
                "index=true", "hexIp=true", "decimalIp=true", "binaryIp=true"};
        String[] args2 = {"count=2", "finerIps=false",
                "from=1", "to=2",
                "index=true", "hexIp=true", "decimalIp=true", "binaryIp=true"};
        String test1File = tdg.generateFlatFile(args1);
        String test2File = tdg.generateFlatFile(args2);
        File file1 = new File(test1File);
        File file2 = new File(test2File);
        List<String> lines1 = FileUtil.read(test1File);
        List<String> lines2 = FileUtil.read(test2File);
        Assert.assertEquals(1, lines1.size());
        Assert.assertEquals(2, lines2.size());
        Assert.assertEquals("00000001:1.1.1.1:0x01010101:               1.               1.               1.         " +
                " " +
                "     1", lines1.get(0));
        Assert.assertEquals("00000001:1.1.1.1:0x01010101:               1.               1.               1.         " +
                " " +
                "     1", lines2.get(0));
        Assert.assertEquals("00000002:1.1.1.2:0x01010102:               1.               1.               1.         " +
                " " +
                "    10", lines2.get(1));
        Assert.assertEquals(96, file1.length());
        Assert.assertEquals(192, file2.length());
        file1.delete();
        file2.delete();
    }

    @Test
    public void testMillionOfHexIps() {
        long start, stop, time;
        int[] lineCount = {1000, 2000, 5000, 10000, 100000, 500000, 1_000_000};
        String[][] allargs = {{"count="},
                {"count=", "decimalIp=true"},
                {"count=", "decimalIp=true", "binaryIp=true"}};
        for (String[] args : allargs) {
            for (int count : lineCount) {
                args[0] = "count=" + count;
                start = System.nanoTime();
                String test1File = tdg.generateFlatFile(args);
                stop = System.nanoTime();
                time = stop - start;
                File file = new File(test1File);
                List<String> lines = FileUtil.read(test1File);
                Assert.assertEquals(count, lines.size());
                System.out.println(count + " IPs in " + file.getName() + " took " + time + " ns\tFile Size " + file.length() +
                        " bytes\targuments" + Arrays.toString(args));
                file.delete();
            }
        }
    }

    @Test
    public void generateJsonFile() {
        //TODO: # Future Diversified Improvements
    }

    @Test
    public void generateXMLFile() {
        //TODO: # Future Diversified Improvements
    }

    @Test
    public void generateSQLQuery() {
        //TODO: # Future Diversified Improvements
    }
}
