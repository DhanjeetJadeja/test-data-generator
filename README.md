# test-data-generator
Test data determines to Quality of Tests, Environments, Products, Services and Platforms. Over the years we have sufficed with Excel, Shell (bash, sed, awk) scripts, SQL Queries etc. for Test Data. An attempt to simplify the test-data-generation with this..

## We need a csv file containing unique IPv4 addresses.

This works like below

`$ java -jar test-data-generator-1.0.jar *from*=192 *to*=255 *count*=10 *index*=true` 

```
Reading properties from tdg.properties
~~ Success ~~ !! wrote 10 IPs in ips-UnIverse-1588637192532.csv taking 45 ms
```

## Lets look at the file.

`$ cat ips-UnIverse-1588637192532.csv`

```
00000001,c0c0c0c0,
00000002,c0c0c0c1,
00000003,c0c0c0c2,
00000004,c0c0c0c3,
00000005,c0c0c0c4,
00000006,c0c0c0c5,
00000007,c0c0c0c6,
00000008,c0c0c0c7,
00000009,c0c0c0c8,
00000010,c0c0c0c9,
```

## Lets see more examples where we generate millions of IPs.

`$ java -jar test-data-generator-1.0.jar from=100 to=255 count=*1000000* index=true`

```
Reading properties from tdg.properties
~~ Success ~~ !! wrote 1000000 IPs in ips-UnIverse-1588637232821.csv taking 3527 ms

$ tail ips-UnIverse-1588637232821.csv 
00999991,648d7282,
00999992,648d7283,
00999993,648d7284,
00999994,648d7285,
00999995,648d7286,
00999996,648d7287,
00999997,648d7288,
00999998,648d7289,
00999999,648d728a,
01000000,648d728b,
```
## 5m, 10m, 30m
```
$ java -jar test-data-generator-1.0.jar from=100 to=255 count=*5000000* index=true`

Reading properties from tdg.properties
~~ Success ~~ !! wrote 5000000 IPs in ips-UnIverse-1588637269627.csv taking 9374 ms


$ java -jar test-data-generator-1.0.jar from=100 to=255 count=*10000000* index=true 

Reading properties from tdg.properties
~~ Success ~~ !! wrote 10000000 IPs in ips-UnIverse-1588637297481.csv taking 15129 ms



$ java -jar test-data-generator-1.0.jar from=100 to=255 count=*30000000* index=true 

Reading properties from tdg.properties
~~ Success ~~ !! wrote 30000000 IPs in ips-UnIverse-1588637366908.csv taking 56390 ms

```

## Other examples to try
> from=100 to=255 count=10 index=true decimalIp=true hexIp=true binaryIp=true

The file sizes generated are
>> 16 bytes for Hexadecimal IP + 2 bytes for comma + 1 byte for Line Separator X count

# Improvements

- Individual Octets, Delimitter to be parameterized. 
- Prefix and Suffix for individual values will allow Queries to be generated from this.
