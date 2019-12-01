#!/bin/bash
impala-shell -i "compute-1-1" -q "use jl11046; show tables; describe rts_phase1; select * from rts_phase1; describe 311_phase1; select * from 311_phase1; describe crime_phase1; select * from crime_phase1; describe phase1_joined; select * from phase1_joined;"
