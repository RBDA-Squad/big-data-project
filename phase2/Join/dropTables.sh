#!/bin/bash
impala-shell -i "compute-1-1" -q "use jl11046; drop table rts_phase2; drop table 311_phase2; drop table crime_phase2; drop table phase2_joined;"
