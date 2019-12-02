#!/bin/bash
javac -classpath `yarn classpath` -d . DataCleaningMapper.java
javac -classpath `yarn classpath`:. -d . DataCleaning.java
jar -cvf DataCleaning.jar *.class
hadoop jar DataCleaning.jar DataCleaning /user/jl11046/Final/phase0/ETL/input /user/jl11046/Final/phase0/ETL/DataCleaningOutput
