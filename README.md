# test-data-generator
Test data determines the Quality of Tests, Environments, Products, Services and Platforms. Over the years we have sufficed with Excel, Shell (bash, sed, awk) scripts, SQL Queries etc. for Test Data. An attempt to simplify the test-data-generation with this..

## We need a csv file containing unique IPv4,Hex or Decimal IP addresses.

This works like below

```
$ java -jar tdg-1.0.jar from=192 to=255 count=10 index=true
Dec 12, 2022 10:16:51 PM org.masty.util.TestDataGenerator main
INFO: TestDataGenerator started on - 172686104315250
Dec 12, 2022 10:16:51 PM org.masty.util.TestDataGenerator main
INFO: Success!! 172686138403833
Dec 12, 2022 10:16:51 PM org.masty.util.TestDataGenerator main
INFO: Test Data Generator generated 10 IPs in tdg-op-1670883411846.csv taking 34088583 ns
```
## Lets look at the file.

```
$ cat cat tdg-op-1670883411846.csv 
00000001:0xc0c0c0c0
00000002:0xc0c0c0c1
00000003:0xc0c0c0c2
00000004:0xc0c0c0c3
00000005:0xc0c0c0c4
00000006:0xc0c0c0c5
00000007:0xc0c0c0c6
00000008:0xc0c0c0c7
00000009:0xc0c0c0c8
00000010:0xc0c0c0c9
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
1000 IPs in tdg-op-1670883350331.csv took 46710417 ns	File Size 11000 bytes	arguments[count=1000]
2000 IPs in tdg-op-1670883350382.csv took 44942541 ns	File Size 22000 bytes	arguments[count=2000]
5000 IPs in tdg-op-1670883350427.csv took 44552083 ns	File Size 55000 bytes	arguments[count=5000]
10000 IPs in tdg-op-1670883350473.csv took 44814708 ns	File Size 110000 bytes	arguments[count=10000]
100000 IPs in tdg-op-1670883350501.csv took 33051458 ns	File Size 1100000 bytes	arguments[count=100000]
500000 IPs in tdg-op-1670883350581.csv took 98600334 ns	File Size 5500000 bytes	arguments[count=500000]
1000000 IPs in tdg-op-1670883350780.csv took 181440584 ns	File Size 11000000 bytes	arguments[count=1000000]
1000 IPs in tdg-op-1670883350901.csv took 34472708 ns	File Size 20568 bytes	arguments[count=1000, decimalIp=true]
2000 IPs in tdg-op-1670883350934.csv took 32394584 ns	File Size 41136 bytes	arguments[count=2000, decimalIp=true]
5000 IPs in tdg-op-1670883350968.csv took 33904292 ns	File Size 105554 bytes	arguments[count=5000, decimalIp=true]
10000 IPs in tdg-op-1670883351003.csv took 35877042 ns	File Size 213399 bytes	arguments[count=10000, decimalIp=true]
100000 IPs in tdg-op-1670883351072.csv took 76179792 ns	File Size 2202584 bytes	arguments[count=100000, decimalIp=true]
500000 IPs in tdg-op-1670883351231.csv took 163506625 ns	File Size 11067892 bytes	arguments[count=500000, decimalIp=true]
1000000 IPs in tdg-op-1670883351513.csv took 277781417 ns	File Size 22555244 bytes	arguments[count=1000000, decimalIp=true]
1000 IPs in tdg-op-1670883351628.csv took 43491667 ns	File Size 88568 bytes	arguments[count=1000, decimalIp=true, binaryIp=true]
2000 IPs in tdg-op-1670883351666.csv took 37171250 ns	File Size 177136 bytes	arguments[count=2000, decimalIp=true, binaryIp=true]
5000 IPs in tdg-op-1670883351707.csv took 41503167 ns	File Size 445554 bytes	arguments[count=5000, decimalIp=true, binaryIp=true]
10000 IPs in tdg-op-1670883351755.csv took 47796792 ns	File Size 893399 bytes	arguments[count=10000, decimalIp=true, binaryIp=true]
100000 IPs in tdg-op-1670883351953.csv took 207491083 ns	File Size 9002584 bytes	arguments[count=100000, decimalIp=true, binaryIp=true]
500000 IPs in tdg-op-1670883352599.csv took 676326375 ns	File Size 45067892 bytes	arguments[count=500000, decimalIp=true, binaryIp=true]
1000000 IPs in tdg-op-1670883353934.csv took 1350554292 ns	File Size 90555244 bytes	arguments[count=1000000, decimalIp=true, binaryIp=true]
```

# Future Diversified Improvements
- Generate JSON files
- Generate XML files
- Generate SQL queries
- Prefix and Suffix for individual values will allow Queries to be generated from this.
