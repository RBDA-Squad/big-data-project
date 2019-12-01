#!/bin/bash
impala-shell -i "compute-1-1" -f JoinTables.iql
impala-shell -B -o ~/RBDA_Project_NYU_F19/phase1/data/phase1_joined.tsv -i "compute-1-1" -f GenerateOutput.iql
hdfs dfs -put ~/RBDA_Project_NYU_F19/phase1/data/phase1_joined.tsv Final/phase1/Join/output
