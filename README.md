# Columnize

A proof-of-concept project to build a transparent proxy for Cassandra, converting CQL schema 
and queries to columnar format. It may be useful for analytical queries requiring massive data scans (and Cassandra is not
really efficient for multi-row scans compared to Parquet/Avro).

Keep in mind that this project is work-in-progress (read: **DO NOT USE IT IN PRODUCTION**) and it's main target is to prove that external columnar storage inside 
C* is more efficient for analytical workload than plain old C* tables. 

## How it works

The original Cassandra groups columns on disk for each row. Inspired by original FiloDB idea, we suggest to group columns
 by the column name, not by row. But plugging directly inside Cassandra is too intrusive, so we decided to create a CQL
 proxy which converts CQL DDL and queries to a columnar format.
 
For example, a table defined as follows:

    create table foo (
      bar int,
      baz text,
      primary key (bar)
    );

the proxy will rotate with the following schema:

    create table foo (
      column ascii,
      shard int,
      bar int,
      value blob,
      primary key ((column, shard), bar)
    );

The main idea of this transformation is to simplify full table scan queries like `select baz from foo`. These sorts of queries
are typical for Spark/SparkSQL-based analytical requests.

## Performance

To be done.

## Features

The proxy is able to:

* decode cassandra binary protocol v4
* parse CQL table definitions
* convert CQL table DDL to a columnar format
 
It yet does not support:

* batches,
* custom types


