#!/bin/bash
impala-shell -i "compute-1-1" -q "use jl11046; show tables; describe 311_phase3_raw; select * from 311_phase3_raw limit 5; describe 311_phase3_extracted; select * from 311_phase3_extracted limit 5;"
