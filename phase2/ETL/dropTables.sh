#!/bin/bash
impala-shell -i "compute-1-1" -q "use jl11046; drop table rts_phase2_raw; drop table 311_phase2_raw;drop table crime_phase2_raw;"
