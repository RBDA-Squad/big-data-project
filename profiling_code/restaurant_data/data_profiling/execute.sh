#!/bin/bash
javac -classpath `yarn classpath` -d . DataProfilingMapper.java
javac -classpath `yarn classpath` -d . DataProfilingReducer.java
javac -classpath `yarn classpath`:. -d . DataProfiling.java
jar -cvf DataProfiling.jar *.class
hadoop jar DataProfiling.jar DataProfiling /user/jl11046/Final/phase0/ETL/input /user/jl11046/Final/phase0/ETL/DataProfilingOutput
