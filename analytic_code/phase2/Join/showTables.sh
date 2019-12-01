#!/bin/bash
impala-shell -i "compute-1-1" -q "use jl11046; show tables; describe rts_phase2; select * from rts_phase2 limit 5; describe 311_phase2; select * from 311_phase2 limit 5; describe crime_phase2; select * from crime_phase2 limit 5; describe phase2_joined; select * from phase2_joined limit 5;"
