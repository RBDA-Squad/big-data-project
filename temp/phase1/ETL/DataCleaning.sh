#!/bin/bash
impala-shell -i "compute-1-1" -f ConstructTable.iql
impala-shell -B -o ~/RBDA_Project_NYU_F19/phase1/data/phase1_rts_count.tsv --output_delimiter='\t' -i "compute-1-1" -f GenerateOutput.iql
hdfs dfs -put ~/RBDA_Project_NYU_F19/phase1/data/phase1_rts_count.tsv Final/phase1/ETL/output
