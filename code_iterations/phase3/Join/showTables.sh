#!/bin/bash
impala-shell -i "compute-1-1" -q "use jl11046; show tables; describe rts_phase3; select * from rts_phase3 limit 5; describe 311_phase3; select * from 311_phase3 limit 5; describe crime_phase3; select * from crime_phase3 limit 5; describe phase3_joined; select * from phase3_joined limit 5;"
