# Realtime Big Data - Final Project

## 311 Data:

TO-DO

## Restaurant Data:

TO-DO

## Crime Data:

### The Steps for Crime Dataset Ingest

1. Login to Dumbo
2. Download the dataset (csv format) through the URL:
   curl -O https://data.cityofnewyork.us/api/views/8h9b-rp9u/rows.csv?accessType=DOWNLOAD
3. Change the file name to a shorter one:
   mv rows.csv?accessType=DOWNLOAD rows.csv
4. Make a new directory in HDFS:
   hdfs dfs -mkdir /user/syc574/project
5. Put the dataset file into HDFS:
   hdfs dfs -put rows.csv /user/syc574/project
6. Check if the dataset is added successfully:
   hdfs dfs -cat /user/syc574/project/rows.csv

### Data Cleaning Process

1. Go to the `cd CrimeData/DataCleaning` directory
2. Execute the below commands to clean the Crime dataset
   ```
   javac -classpath`yarn classpath`-d . DataCleaningMapper.java; javac -classpath`yarn classpath`-d . DataCleaningReducer.java; javac -classpath`yarn classpath`:. -d . DataCleaning.java; jar -cvf DataCleaning.jar \*.class; hdfs dfs -rm -r /user/syc574/FinalProject/output2; hadoop jar DataCleaning.jar DataCleaning /user/syc574/FinalProject/rows.csv /user/syc574/FinalProject/output2
   ```
3. Go to the `cd CrimeData/ZipCodeTable` directory
4. Execute the below commands to generate the Zip Code matching table
   ```
   javac -classpath `yarn classpath` -d . ZipCodeTableMapper.java; javac -classpath `yarn classpath` -d . ZipCodeTableReducer.java; javac -classpath `yarn classpath`:. -d . ZipCodeTable.java; jar -cvf ZipCodeTable.jar *.class; hdfs dfs -rm -r /user/syc574/FinalProject/output3; hadoop jar ZipCodeTable.jar ZipCodeTable /user/syc574/FinalProject/zipcode.csv /user/syc574/FinalProject/output3
   ```
5. Go to the `cd CrimeData/LonLatToZipCode` directory
6. Execute the below commands to turn Latitude and Longitude into Zip Code
   ```
   javac -classpath `yarn classpath` -d . LonLatToZipCodeMapper.java; javac -classpath `yarn classpath` -d . LonLatToZipCodeReducer.java; javac -classpath `yarn classpath`:. -d . LonLatToZipCode.java; jar -cvf LonLatToZipCode.jar *.class; hdfs dfs -rm -r /user/syc574/FinalProject/output4; hadoop jar LonLatToZipCode.jar LonLatToZipCode /user/syc574/FinalProject/output2/part-r-00000 /user/syc574/FinalProject/output4
   ```
7. Type `hdfs dfs -cat /user/syc574/FinalProject/output4/part-r-00000`, then you can see the final dataset after the cleaning process

### Crime Data Schema:

| Column name | Type   | Description                                  | Valid length |
| ----------- | ------ | -------------------------------------------- | ------------ |
| CrimeID     | String | The unique identifier for each crime record. | 9 - 10       |
| Date        | String | The date that the arrest take place.         | 10           |
| CrimeType   | String | The type of crime.                           | 1 - 43       |
| Borough     | String | The borough that the arrest take place.      |              |
| Latitude    | Double | The latitude that the arrest take place.     | 17 - 18      |
| Longitude   | Double | The longitude that the arrest take place.    | 10 - 13, 18  |
| ZipCode     | String | The zip code that the arrest take place.     |              |

### Phase 1 - The Crime Data Counts in each Zip Code area (for Analytics)

1. Go to the `cd CrimeData/ZipCodeCount` directory
2. Execute the below commands to get the Crime Data Counts in each Zip Code area
   ```
   javac -classpath `yarn classpath` -d . ZipCodeCountMapper.java; javac -classpath `yarn classpath` -d . ZipCodeCountReducer.java; javac -classpath `yarn classpath`:. -d . ZipCodeCount.java; jar -cvf ZipCodeCount.jar *.class; hdfs dfs -rm -r /user/syc574/FinalProject/output5; hadoop jar ZipCodeCount.jar ZipCodeCount /user/syc574/FinalProject/output4/part-r-00000 /user/syc574/FinalProject/output5
   ```
3. Type `hdfs dfs -cat /user/syc574/FinalProject/output5/part-r-00000`, then you can see the Phase 1 data for our analytics

### Phase 1 Data Schema:

| Column name | Type    |
| ----------- | ------- |
| ZipCode     | String  |
| CrimeCount  | Integer |

### Phase 2 - The Crime Data Counts for each Crime Type in each Zip Code area (using Crime Type as the Columns)

1. Go to the `cd CrimeData/ZipCodeCount2` directory
2. Execute the below commands to get the Crime Data Counts for each Crime Type in each Zip Code area
   ```
   javac -classpath `yarn classpath` -d . ZipCodeCountMapper2.java; javac -classpath `yarn classpath` -d . ZipCodeCountReducer2.java; javac -classpath `yarn classpath`:. -d . ZipCodeCount2.java; jar -cvf ZipCodeCount2.jar *.class; hdfs dfs -rm -r /user/syc574/FinalProject/output6; hadoop jar ZipCodeCount2.jar ZipCodeCount2 /user/syc574/FinalProject/output4/part-r-00000 /user/syc574/FinalProject/output6
   ```
3. Type `hdfs dfs -cat /user/syc574/FinalProject/output6/part-r-00000`, then you can see the Phase 2 data for our analytics

### Phase 2 Data Schema:

| Column name                                 | Type   |
| ------------------------------------------- | ------ |
| ZIP CODE                                    | String |
| ADMINISTRATIVE CODE                         | String |
| ANTICIPATORY OFFENSES                       | String |
| ARSON                                       | String |
| ASSAULT 3 & RELATED OFFENSES                | String |
| BURGLARY                                    | String |
| CRIMINAL MISCHIEF & RELATED OFFENSES        | String |
| CRIMINAL TRESPASS                           | String |
| DANGEROUS DRUGS                             | String |
| DANGEROUS WEAPONS                           | String |
| DISORDERLY CONDUCT                          | String |
| DISRUPTION OF A RELIGIOUS SERVICE           | String |
| ENDAN WELFARE INCOMP                        | String |
| F.C.A. P.I.N.O.S.                           | String |
| FELONY ASSAULT                              | String |
| FORCIBLE TOUCHING                           | String |
| FORGERY                                     | String |
| FRAUDS                                      | String |
| FRAUDULENT                                  | String |
| GAMBLING                                    | String |
| GRAND LARCENY                               | String |
| GRAND LARCENY OF MOTOR VEHICLE              | String |
| HARRASSMENT 2                               | String |
| HOMICIDE-NEGLIGENT-VEHICLE                  | String |
| INTOXICATED & IMPAIRED DRIVING              | String |
| KIDNAPPING & RELATED OFFENSES               | String |
| LOITERING                                   | String |
| MISCELLANEOUS PENAL LAW                     | String |
| MOVING INFRACTIONS                          | String |
| MURDER & NON-NEGL. MANSLAUGHTER             | String |
| NEW YORK CITY HEALTH CODE                   | String |
| NYS LAWS-UNCLASSIFIED FELONY                | String |
| OFF. AGNST PUB ORD SENSBLTY & RGHTS TO PRIV | String |
| OFFENSES AGAINST MARRIAGE UNCLASSIFIED      | String |
| OFFENSES AGAINST PUBLIC ADMINISTRATION      | String |
| OFFENSES AGAINST PUBLIC SAFETY              | String |
| OFFENSES AGAINST THE PERSON                 | String |
| OFFENSES INVOLVING FRAUD                    | String |
| OFFENSES RELATED TO CHILDREN                | String |
| OTHER OFFENSES RELATED TO THEFT             | String |
| OTHER STATE LAWS                            | String |
| OTHER STATE LAWS (NON PENAL LAW)            | String |
| OTHER TRAFFIC INFRACTION                    | String |
| PARKING OFFENSES                            | String |
| PETIT LARCENY                               | String |
| POSSESSION OF STOLEN PROPERTY 5             | String |
| PROSTITUTION & RELATED OFFENSES             | String |
| ROBBERY                                     | String |
| SEX CRIMES                                  | String |
| THEFT-FRAUD                                 | String |
| VEHICLE AND TRAFFIC LAWS                    | String |

### Attemption for tunring Latitude and Longitude into Zip Code through Google Map API

1. Python script is in `ZipCodeAPI/ZipCodeAPI.py`
2. Java script is in `ZipCodeAPI/ZipCodeAPI.java`

## Reference for Haversine formula (tunring Latitude and Longitude into Zip Code)

- https://www.geeksforgeeks.org/haversine-formula-to-find-distance-between-two-points-on-a-sphere/
- https://king39461.pixnet.net/blog/post/400140310-java-%E5%B7%B2%E7%9F%A5%E5%85%A9%E5%80%8B%E5%9C%B0%E9%BB%9E%E7%B6%93%E7%B7%AF%E5%BA%A6%E7%AE%97%E8%B7%9D%E9%9B%A2%EF%BC%88%E9%9D%9E%E5%B8%B8%E7%B2%BE%E7%A2%BA%EF%BC%89
