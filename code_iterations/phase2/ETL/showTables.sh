#!/bin/bash
impala-shell -i "compute-1-1" -q "use jl11046; show tables; describe rts_phase2_raw; select * from rts_phase2_raw limit 5; describe 311_phase2_raw; select * from 311_phase2_raw limit 5; describe crime_phase2_raw; select * from crime_phase2_raw limit 5;"
