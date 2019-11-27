# Realtime Big Data - Final Project

## 311 Data:

### The Steps for 311 Dataset Ingest

1. Login to Dumbo
2. Download the dataset (csv format) through the URL:  
   `curl -O https://nycopendata.socrata.com/api/views/erm2-nwe9/rows.csv?accessType=DOWNLOAD`
3. Make a new directory in HDFS:  
   `hdfs dfs -mkdir /user/<your netid>/project`
4. Put the dataset file into HDFS:  
   `hdfs dfs -put 311_Service_Requests_from_2010_to_Present.csv /user/<your netid>/project`

<br>

### Data Cleaning Process

1. Go to the 311/cleaning directory. `cd 311/cleaning`
2. Execute the below commands to clean the 311 dataset
   ```
   javac -classpath `yarn classpath` -d . CleaningMapper.java
   javac -classpath `yarn classpath`:. -d . CleaningDriver.java
   jar -cvf CleaningDriver.jar *.class
   hadoop jar CleaningDriver.jar CleaningDriver /user/<your netid>/project/311_Service_Requests_from_2010_to_Present.csv /user/<your netid>/project/311_cleaned
   ```
   <br>

### 311 Data Schema:

| Column name    | Type   | Description                                 | Valid length |
| -------------- | ------ | ------------------------------------------- | ------------ |
| Time           | String | The time that the complaint took place      | 22           |
| Complaint Type | String | The type of complaint                       | 1 - 39       |
| Zipcode        | String | The zipcode that the complaint fell in      | 1 - 43       |
| Latitude       | Double | The latitude that the complaint took place  |              |
| Longitude      | Double | The longitude that the complaint took place |              |

<br>

### Phase 1 - The Complaint counts in each zipcode (for Analytics)

1. Go to the directory. `cd 311/complaint_count_by_zipcode`
2. Execute the below commands to generate the count of complaint by zipcode.
   ```
   javac -classpath `yarn classpath` -d . ZipcodeCountMapper.java
   javac -classpath `yarn classpath` -d . ZipcodeCountReducer.java
   javac -classpath `yarn classpath`:. -d . ZipcodeCountDriver.java
   jar -cvf ZipcodeCountDriver.jar *.class
   hadoop jar ZipcodeCountDriver.jar ZipcodeCountDriver /user/<your netid>/project/311_cleaned /user/<your netid>/project/311_complaint_count_by_zipcode
   ```

<br>

### Phase 1 Data Schema:

| Column name | Type   |
| ----------- | ------ |
| Zipcode     | String |
| Count       | Long   |

<br>

### Phase 2 - The Complaint counts for each type in each zipcode (for Analytics)

1. Go to the directory. `cd 311/complaint_types_count_by_zipcode`
2. Execute the below commands to generate the count of complaint types by zipcode.
   ```
   javac -classpath `yarn classpath` -d . ComplaintTypeCountMapper.java
   javac -classpath `yarn classpath` -d . ComplaintTypeCountReducer.java
   javac -classpath `yarn classpath`:. -d . ComplaintTypeCountDriver.java
   jar -cvf ComplaintTypeCountDriver.jar *.class
   hadoop jar ComplaintTypeCountDriver.jar ComplaintTypeCountDriver /user/<your netid>/project/311_cleaned /user/<your netid>/project/311_complaint_type_count_by_zipcode
   ```

<br>

### Phase 2 Data Schema:

| Column name   | Type   |
| ------------- | ------ |
| Zipcode       | String |
| ComplaintType | String |
| Count         | String |

<br>

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

<br>

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

<br>

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

<br>

### Phase 1 - The Crime Data Counts in each Zip Code area (for Analytics)

1. Go to the `cd CrimeData/ZipCodeCount` directory
2. Execute the below commands to get the Crime Data Counts in each Zip Code area
   ```
   javac -classpath `yarn classpath` -d . ZipCodeCountMapper.java; javac -classpath `yarn classpath` -d . ZipCodeCountReducer.java; javac -classpath `yarn classpath`:. -d . ZipCodeCount.java; jar -cvf ZipCodeCount.jar *.class; hdfs dfs -rm -r /user/syc574/FinalProject/output5; hadoop jar ZipCodeCount.jar ZipCodeCount /user/syc574/FinalProject/output4/part-r-00000 /user/syc574/FinalProject/output5
   ```
3. Type `hdfs dfs -cat /user/syc574/FinalProject/output5/part-r-00000`, then you can see the Phase 1 data for our analytics

<br>

### Phase 1 Data Schema:

| Column name | Type    |
| ----------- | ------- |
| ZipCode     | String  |
| CrimeCount  | Integer |

<br>

### Phase 2 - The Crime Data Counts for each Crime Type in each Zip Code area (using Crime Type as the Columns)

1. Go to the `cd CrimeData/ZipCodeCount2` directory
2. Execute the below commands to get the Crime Data Counts for each Crime Type in each Zip Code area
   ```
   javac -classpath `yarn classpath` -d . ZipCodeCountMapper2.java; javac -classpath `yarn classpath` -d . ZipCodeCountReducer2.java; javac -classpath `yarn classpath`:. -d . ZipCodeCount2.java; jar -cvf ZipCodeCount2.jar *.class; hdfs dfs -rm -r /user/syc574/FinalProject/output6; hadoop jar ZipCodeCount2.jar ZipCodeCount2 /user/syc574/FinalProject/output4/part-r-00000 /user/syc574/FinalProject/output6
   ```
3. Type `hdfs dfs -cat /user/syc574/FinalProject/output6/part-r-00000`, then you can see the Phase 2 data for our analytics

<br>

### Phase 2 Data Schema:

| Column name                                 | Type    |
| ------------------------------------------- | ------- |
| ZIP CODE                                    | String  |
| ADMINISTRATIVE CODE                         | Integer |
| ANTICIPATORY OFFENSES                       | Integer |
| ARSON                                       | Integer |
| ASSAULT 3 & RELATED OFFENSES                | Integer |
| BURGLARY                                    | Integer |
| CRIMINAL MISCHIEF & RELATED OFFENSES        | Integer |
| CRIMINAL TRESPASS                           | Integer |
| DANGEROUS DRUGS                             | Integer |
| DANGEROUS WEAPONS                           | Integer |
| DISORDERLY CONDUCT                          | Integer |
| DISRUPTION OF A RELIGIOUS SERVICE           | Integer |
| ENDAN WELFARE INCOMP                        | Integer |
| F.C.A. P.I.N.O.S.                           | Integer |
| FELONY ASSAULT                              | Integer |
| FORCIBLE TOUCHING                           | Integer |
| FORGERY                                     | Integer |
| FRAUDS                                      | Integer |
| FRAUDULENT                                  | Integer |
| GAMBLING                                    | Integer |
| GRAND LARCENY                               | Integer |
| GRAND LARCENY OF MOTOR VEHICLE              | Integer |
| HARRASSMENT 2                               | Integer |
| HOMICIDE-NEGLIGENT-VEHICLE                  | Integer |
| INTOXICATED & IMPAIRED DRIVING              | Integer |
| KIDNAPPING & RELATED OFFENSES               | Integer |
| LOITERING                                   | Integer |
| MISCELLANEOUS PENAL LAW                     | Integer |
| MOVING INFRACTIONS                          | Integer |
| MURDER & NON-NEGL. MANSLAUGHTER             | Integer |
| NEW YORK CITY HEALTH CODE                   | Integer |
| NYS LAWS-UNCLASSIFIED FELONY                | Integer |
| OFF. AGNST PUB ORD SENSBLTY & RGHTS TO PRIV | Integer |
| OFFENSES AGAINST MARRIAGE UNCLASSIFIED      | Integer |
| OFFENSES AGAINST PUBLIC ADMINISTRATION      | Integer |
| OFFENSES AGAINST PUBLIC SAFETY              | Integer |
| OFFENSES AGAINST THE PERSON                 | Integer |
| OFFENSES INVOLVING FRAUD                    | Integer |
| OFFENSES RELATED TO CHILDREN                | Integer |
| OTHER OFFENSES RELATED TO THEFT             | Integer |
| OTHER STATE LAWS                            | Integer |
| OTHER STATE LAWS (NON PENAL LAW)            | Integer |
| OTHER TRAFFIC INFRACTION                    | Integer |
| PARKING OFFENSES                            | Integer |
| PETIT LARCENY                               | Integer |
| POSSESSION OF STOLEN PROPERTY 5             | Integer |
| PROSTITUTION & RELATED OFFENSES             | Integer |
| ROBBERY                                     | Integer |
| SEX CRIMES                                  | Integer |
| THEFT-FRAUD                                 | Integer |
| VEHICLE AND TRAFFIC LAWS                    | Integer |

<br>

### Attemption for tunring Latitude and Longitude into Zip Code through Google Map API

1. Python script is in `ZipCodeAPI/ZipCodeAPI.py`
2. Java script is in `ZipCodeAPI/ZipCodeAPI.java`

<br>

## Reference for Haversine formula (turning Latitude and Longitude into Zip Code)

- https://www.geeksforgeeks.org/haversine-formula-to-find-distance-between-two-points-on-a-sphere/
- https://king39461.pixnet.net/blog/post/400140310-java-%E5%B7%B2%E7%9F%A5%E5%85%A9%E5%80%8B%E5%9C%B0%E9%BB%9E%E7%B6%93%E7%B7%AF%E5%BA%A6%E7%AE%97%E8%B7%9D%E9%9B%A2%EF%BC%88%E9%9D%9E%E5%B8%B8%E7%B2%BE%E7%A2%BA%EF%BC%89

<br>

## The evidence of the high charge for using Google Map API

![pic1](pic1.png)

<br>

![pic2](pic2.png)
