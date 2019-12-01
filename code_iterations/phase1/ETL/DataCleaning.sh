#!/bin/bash
impala-shell -i "compute-1-1" -f ConstructTable.iql
impala-shell -B -o ~/big-data-project/code_iterations/phase1/data/phase1_rts_count.tsv --output_delimiter='\t' -i "compute-1-1" -f GenerateOutput.iql
hdfs dfs -put ~/big-data-project/code_iterations/phase1/data/phase1_rts_count.tsv /user/jl11046/Final/phase1/ETL/output
