# Realtime Big Data - Final Project

## 311 Data

### The Steps of 311 Data Ingest

1. Login to Dumbo.
2. Download the dataset (csv format) through the URL:  
   `curl -O https://nycopendata.socrata.com/api/views/erm2-nwe9/rows.csv?accessType=DOWNLOAD`
3. Make a new directory in HDFS:  
   `hdfs dfs -mkdir /user/<your netid>/project`
4. Put the dataset file into HDFS:  
   `hdfs dfs -put 311_Service_Requests_from_2010_to_Present.csv /user/<your netid>/project`

<br>

### The Steps of 311 Data Cleaning

1. Go to the 311/cleaning directory.  
   `cd 311/cleaning`
2. Clean the 311 dataset.
   ```
   javac -classpath `yarn classpath` -d . CleaningMapper.java
   javac -classpath `yarn classpath`:. -d . CleaningDriver.java
   jar -cvf CleaningDriver.jar *.class
   hadoop jar CleaningDriver.jar CleaningDriver /user/<your netid>/project/311_Service_Requests_from_2010_to_Present.csv /user/<your netid>/project/311_cleaned
   ```

<br>

### 311 Data Schema

| Column name    | Type   | Description                                 | Valid length |
| -------------- | ------ | ------------------------------------------- | ------------ |
| Time           | String | The time that the complaint took place      | 22           |
| Complaint Type | String | The type of complaint                       | 1 - 39       |
| Zipcode        | String | The zipcode that the complaint fell in      | 1 - 43       |
| Latitude       | Double | The latitude that the complaint took place  |              |
| Longitude      | Double | The longitude that the complaint took place |              |

<br>

### Phase 1 - Count the complaint data for different zip code's areas (analytics purpose)

1. Go to the directory.  
   `cd 311/complaint_count_by_zipcode`
2. Generate the count of complaint by zipcode.
   ```
   javac -classpath `yarn classpath` -d . ZipcodeCountMapper.java
   javac -classpath `yarn classpath` -d . ZipcodeCountReducer.java
   javac -classpath `yarn classpath`:. -d . ZipcodeCountDriver.java
   jar -cvf ZipcodeCountDriver.jar *.class
   hadoop jar ZipcodeCountDriver.jar ZipcodeCountDriver /user/<your netid>/project/311_cleaned /user/<your netid>/project/311_complaint_count_by_zipcode
   ```

<br>

### 311 Data Schema (Phase 1)

| Column name | Type   |
| ----------- | ------ |
| Zipcode     | String |
| Count       | Long   |

<br>

### Phase 2 - Count the complaint data for different complaint types in each zip code area (analytics purpose)

1. Go to the directory.  
   `cd 311/complaint_types_count_by_zipcode`
2. Generate the count of complaint types by zipcode.
   ```
   javac -classpath `yarn classpath` -d . ComplaintTypeCountMapper.java
   javac -classpath `yarn classpath` -d . ComplaintTypeCountReducer.java
   javac -classpath `yarn classpath`:. -d . ComplaintTypeCountDriver.java
   jar -cvf ComplaintTypeCountDriver.jar *.class
   hadoop jar ComplaintTypeCountDriver.jar ComplaintTypeCountDriver /user/<your netid>/project/311_cleaned /user/<your netid>/project/311_complaint_type_count_by_zipcode
   ```

<br>

### 311 Data Schema (Phase 2)

| Column name   | Type   |
| ------------- | ------ |
| Zipcode       | String |
| ComplaintType | String |
| Count         | String |

<br>

## Restaurant Data

<br>

### The Steps of Restaurant Data Ingest

<br>

### The Steps of Restaurant Data Cleaning

<br>

### Restaurant Data Schema

<br>

### Phase 1 - Count the restaurant data for different zip code's areas (analytics purpose)

<br>

### Restaurant Data Schema (Phase 1)

<br>

### Phase 2 - Count the restaurant data for different restaurant types in each zip code area (analytics purpose)

<br>

### Restaurant Data Schema (Phase 2)

<br><br>

## Crime Data

### The Steps of Crime Data Ingest

1. Login to Dumbo.
2. Download the dataset (csv format) through the URL.  
   `curl -O https://data.cityofnewyork.us/api/views/8h9b-rp9u/rows.csv?accessType=DOWNLOAD`
3. Change the file name to a shorter one.  
   `mv rows.csv?accessType=DOWNLOAD rows.csv`
4. Make a new directory in HDFS.  
   `hdfs dfs -mkdir /user/<your netid>/project`
5. Put the dataset file into HDFS.  
   `hdfs dfs -put rows.csv /user/<your netid>/project`
6. Check if the dataset was added successfully.  
   `hdfs dfs -cat /user/<your netid>/project/rows.csv`

<br>

### The Steps of Crime Data Cleaning

1. Go to the directory.  
   `cd etl_code/crime_data/data_cleaning`
2. Clean the Crime dataset.
   ```
   javac -classpath`yarn classpath`-d . DataCleaningMapper.java;
   javac -classpath`yarn classpath`-d . DataCleaningReducer.java;
   javac -classpath`yarn classpath`:. -d . DataCleaning.java;
   jar -cvf DataCleaning.jar *.class;
   hdfs dfs -rm -r /user/<your netid>/project/output1;
   hadoop jar DataCleaning.jar DataCleaning /user/<your netid>/project/rows.csv /user/<your netid>/project/output1
   ```
3. Go to the directory.  
   `cd etl_code/crime_data/zip_code_table`
4. Generate the Zip Code matching table.
   ```
   javac -classpath `yarn classpath` -d . ZipCodeTableMapper.java;
   javac -classpath `yarn classpath` -d . ZipCodeTableReducer.java;
   javac -classpath `yarn classpath`:. -d . ZipCodeTable.java;
   jar -cvf ZipCodeTable.jar *.class;
   hdfs dfs -rm -r /user/<your netid>/project/output2;
   hadoop jar ZipCodeTable.jar ZipCodeTable /user/<your netid>/project/zipcode.csv /user/<your netid>/project/output2
   ```
5. Go to the directory.  
   `cd etl_code/crime_data/lon_lat_to_zip_code`
6. Turn Latitude and Longitude into Zip Code.
   ```
   javac -classpath `yarn classpath` -d . LonLatToZipCodeMapper.java;
   javac -classpath `yarn classpath` -d . LonLatToZipCodeReducer.java;
   javac -classpath `yarn classpath`:. -d . LonLatToZipCode.java;
   jar -cvf LonLatToZipCode.jar *.class;
   hdfs dfs -rm -r /user/<your netid>/project/output3;
   hadoop jar LonLatToZipCode.jar LonLatToZipCode /user/<your netid>/project/output1/part-r-00000 /user/<your netid>/project/output3
   ```
7. Check the final dataset after the cleaning process.  
   `hdfs dfs -cat /user/<your netid>/project/output3/part-r-00000`

<br>

### Crime Data Schema

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

### Phase 1 - Count the crime data for different zip code's areas (analytics purpose)

1. Go to the directory.  
   `cd etl_code/crime_data/zip_code_count_1`
2. Count Crime Data in each Zip Code area.
   ```
   javac -classpath `yarn classpath` -d . ZipCodeCountMapper.java;
   javac -classpath `yarn classpath` -d . ZipCodeCountReducer.java;
   javac -classpath `yarn classpath`:. -d . ZipCodeCount.java;
   jar -cvf ZipCodeCount.jar *.class;
   hdfs dfs -rm -r /user/<your netid>/project/output4;
   hadoop jar ZipCodeCount.jar ZipCodeCount /user/<your netid>/project/output3/part-r-00000 /user/<your netid>/project/output4
   ```
3. Check the Phase 1 data for analytics.  
   `hdfs dfs -cat /user/syc574/FinalProject/output4/part-r-00000`

<br>

### Crime Data Schema (Phase 1)

| Column name | Type    |
| ----------- | ------- |
| Zip Code    | String  |
| Crime Count | Integer |

<br>

### Phase 2 - Count the crime data for different crime types in each zip code area (analytics purpose)

1. Go to the directory.  
   `cd etl_code/crime_data/zip_code_count_2`
2. Count Crime Data for each Crime Type in each Zip Code area.
   ```
   javac -classpath `yarn classpath` -d . ZipCodeCountMapper2.java;
   javac -classpath `yarn classpath` -d . ZipCodeCountReducer2.java;
   javac -classpath `yarn classpath`:. -d . ZipCodeCount2.java;
   jar -cvf ZipCodeCount2.jar *.class;
   hdfs dfs -rm -r /user/<your netid>/project/output5;
   hadoop jar ZipCodeCount2.jar ZipCodeCount2 /user/<your netid>/project/output3/part-r-00000 /user/<your netid>/project/output5
   ```
3. Check the Phase 2 data for analytics.  
   `hdfs dfs -cat /user/<your netid>/project/output5/part-r-00000`

<br>

### Crime Data Schema (Phase 2)

| Column name | Type    |
| ----------- | ------- |
| Zip Code    | String  |
| Crime Type  | String  |
| Crime Count | Integer |

<br>

### Phase 3 - Count the crime data for different crime types in each zip code area (crime type as a column) (analytics purpose)

1. Go to the directory.  
   `cd etl_code/crime_data/zip_code_count_3`
2. Get the Crime Data Counts for each Crime Type in each Zip Code area.
   ```
   javac -classpath `yarn classpath` -d . ZipCodeCountMapper3.java;
   javac -classpath `yarn classpath` -d . ZipCodeCountReducer3.java;
   javac -classpath `yarn classpath`:. -d . ZipCodeCount3.java;
   jar -cvf ZipCodeCount3.jar *.class;
   hdfs dfs -rm -r /user/user/<your netid>/output6;
   hadoop jar ZipCodeCount3.jar ZipCodeCount3 /user/user/<your netid>/output3/part-r-00000 /user/user/<your netid>/output6
   ```
3. Check the Phase 3 data for our analytics.  
   `hdfs dfs -cat /user/user/<your netid>/output6/part-r-00000`

<br>

### Crime Data Schema (Phase 3)

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

### The attempts for turning latitude and longitude into zip code through Google Map API

1. Python script is in the following path:  
   `ZipCodeAPI/ZipCodeAPI.py`
2. Java script is in the following path:  
   `ZipCodeAPI/ZipCodeAPI.java`

<br>

### The references for Haversine formula (turning latitude and longitude into zip code)

- https://www.geeksforgeeks.org/haversine-formula-to-find-distance-between-two-points-on-a-sphere/
- https://king39461.pixnet.net/blog/post/400140310-java-%E5%B7%B2%E7%9F%A5%E5%85%A9%E5%80%8B%E5%9C%B0%E9%BB%9E%E7%B6%93%E7%B7%AF%E5%BA%A6%E7%AE%97%E8%B7%9D%E9%9B%A2%EF%BC%88%E9%9D%9E%E5%B8%B8%E7%B2%BE%E7%A2%BA%EF%BC%89

<br>

### The evidences of the high charge when leveraging Google Map API

![rm1](screenshots/rm1.png)

<br>

![rm2](screenshots/rm2.png)
