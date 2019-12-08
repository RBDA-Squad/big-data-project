#!/bin/bash
impala-shell -i "compute-1-1" -B -o ~/RBDA_Project_NYU_F19/phase3/data/phase3_joined.tsv -i "compute-1-1" -f GenerateOutput.iql
hdfs dfs -put ~/RBDA_Project_NYU_F19/phase3/data/phase3_joined.tsv Final/phase3/Join/output
