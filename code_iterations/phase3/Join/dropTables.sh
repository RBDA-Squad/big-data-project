#!/bin/bash
impala-shell -i "compute-1-1" -q "use jl11046; drop table rts_phase3; drop table 311_phase3; drop table crime_phase3; drop table phase3_joined;"
