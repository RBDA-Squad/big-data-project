#!/bin/bash
impala-shell -i "compute-1-1" -q "use jl11046; show tables; describe rts_raw; describe rts_phase1;"
