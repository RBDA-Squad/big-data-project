#!/bin/bash
curl -O https://data.cityofnewyork.us/api/views/43nn-pn8j/rows.tsv?accessType=DOWNLOAD&bom=true
mv rows.tsv\?accessType\=DOWNLOAD rst.tsv 
hdfs dfs -mkdir /user/jl11046/Final/input
hdfs dfs -put rst.tsv /user/jl11046/Final/input
