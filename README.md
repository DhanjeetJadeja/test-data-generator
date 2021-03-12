# test-data-generator
Test data determines the Quality of Tests, Environments, Products, Services and Platforms. Over the years we have sufficed with Excel, Shell (bash, sed, awk) scripts, SQL Queries etc. for Test Data. An attempt to simplify the test-data-generation with this..

## We need a csv file containing unique IPv4,Hex or Decimal IP addresses.

This works like below

```
$ java -jar test-data-generator-1.0.jar from=192 to=255 count=10 index=true
Reading properties from tdg.properties
~~ Success ~~ !!
Test Data Generator generated 10 IPs in tdg-op-1588717553131.csv taking 49 ms
```
## Lets look at the file.

```
$ cat tdg-op-1588717553131.csv 
00000001,c0a80101
00000002,c0a80102
00000003,c0a80103
00000004,c0a80104
00000005,c0a80105
00000006,c0a80106
00000007,c0a80107
00000008,c0a80108
00000009,c0a80109
00000010,c0a8010a
```

## Lets see the configurable flags in the properties
**_finerIps=false from and to_** 
```
#
#finerIps=false - This will generate count ips from 10.10.10.10 to 200.200.200.200
from=10
#10.10.10.10
#
to=200
#200.200.200.200
#
```
**_finerIps=true from.octet and to.octet_**
```
#finerIps=true - This will generate count ips from 192.168.1.1 to 192.168.254.254
finerIps=true
from.first=192
from.second=168
from.third=1
from.fourth=1
to.first=192
to.second=168
to.third=254
to.fourth=254
#
```
**_count_**
```
#count=1000000 - Attempt 1000000 IPs but if the from[.octet] through to to[.octet]
```
If the ip range is exhausted, it will write generated ips into the file
eg. 192.168.1.1 to 192.168.1.255 will only generate 255 IPs despite count being 500.

## 1m, 2m, 5m, 10m, 30m, 50m, 100m
Have been tested, with generated jar with -Xmx upto 6G.

## Other examples to try
delimiter=:

## File Size and Writing Time Metrics
```
1000 IPs in tdg-op-1588719333640.csv took 91 ms	File Size 9000 bytes	arguments[count=1000]
2000 IPs in tdg-op-1588719333887.csv took 223 ms	File Size 18000 bytes	arguments[count=2000]
5000 IPs in tdg-op-1588719334109.csv took 220 ms	File Size 45000 bytes	arguments[count=5000]
10000 IPs in tdg-op-1588719334398.csv took 208 ms	File Size 90000 bytes	arguments[count=10000]
100000 IPs in tdg-op-1588719334595.csv took 220 ms	File Size 900000 bytes	arguments[count=100000]
1000000 IPs in tdg-op-1588719335202.csv took 747 ms	File Size 9000000 bytes	arguments[count=1000000]
1000 IPs in tdg-op-1588719335758.csv took 202 ms	File Size 18568 bytes	arguments[count=1000, decimalIp=true]
2000 IPs in tdg-op-1588719335902.csv took 142 ms	File Size 37136 bytes	arguments[count=2000, decimalIp=true]
5000 IPs in tdg-op-1588719336002.csv took 101 ms	File Size 95554 bytes	arguments[count=5000, decimalIp=true]
10000 IPs in tdg-op-1588719336107.csv took 103 ms	File Size 193399 bytes	arguments[count=10000, decimalIp=true]
100000 IPs in tdg-op-1588719336256.csv took 158 ms	File Size 2002584 bytes	arguments[count=100000, decimalIp=true]
1000000 IPs in tdg-op-1588719337166.csv took 1345 ms	File Size 20555244 bytes	arguments[count=1000000, decimalIp=true]
1000 IPs in tdg-op-1588719337925.csv took 166 ms	File Size 86568 bytes	arguments[count=1000, decimalIp=true, binaryIp=true]
2000 IPs in tdg-op-1588719338077.csv took 150 ms	File Size 173136 bytes	arguments[count=2000, decimalIp=true, binaryIp=true]
5000 IPs in tdg-op-1588719338297.csv took 221 ms	File Size 435554 bytes	arguments[count=5000, decimalIp=true, binaryIp=true]
10000 IPs in tdg-op-1588719338546.csv took 247 ms	File Size 873399 bytes	arguments[count=10000, decimalIp=true, binaryIp=true]
100000 IPs in tdg-op-1588719339407.csv took 922 ms	File Size 8802584 bytes	arguments[count=100000, decimalIp=true, binaryIp=true]
1000000 IPs in tdg-op-1588719346217.csv took 9740 ms	File Size 88555244 bytes	arguments[count=1000000, decimalIp=true, binaryIp=true]
```

# Future Diversified Improvements
- Generate JSON files
- Generate XML files
- Generate SQL queries
- Prefix and Suffix for individual values will allow Queries to be generated from this.
