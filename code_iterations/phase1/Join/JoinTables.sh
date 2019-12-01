#!/bin/bash
impala-shell -i "compute-1-1" -f JoinTables.iql
impala-shell -B -o ~/big-data-project/code_iterations/phase1/data/phase1_joined.tsv -i "compute-1-1" -f GenerateOutput.iql
hdfs dfs -put ~/big-data-project/code_iterations/phase1/data/phase1_joined.tsv /user/jl11046/Final/phase1/Join/output
