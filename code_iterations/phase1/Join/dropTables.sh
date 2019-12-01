#!/bin/bash
impala-shell -i "compute-1-1" -q "use jl11046; drop table 311_raw; drop table 311_phase1; drop table crime_phase1; drop table phase1_joined;"
