#!/bin/bash
impala-shell -B -o ~/big-data-project/phase1/phase1_rts_count.tsv --output_delimiter='\t' -i "compute-1-1" -f DataCleaning.iql