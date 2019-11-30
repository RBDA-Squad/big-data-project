#!/bin/bash
impala-shell -i "compute-1-1" -q "use jl11046; drop table rts_raw; drop table rts_phase1;"
