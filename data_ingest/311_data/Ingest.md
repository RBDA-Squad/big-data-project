1. Login to Dumbo.
2. Download the dataset (csv format) through the URL:  
   `curl -O https://nycopendata.socrata.com/api/views/erm2-nwe9/rows.csv?accessType=DOWNLOAD`
3. Make a new directory in HDFS:  
   `hdfs dfs -mkdir /user/<your netid>/project`
4. Put the dataset file into HDFS:  
   `hdfs dfs -put 311_Service_Requests_from_2010_to_Present.csv /user/<your netid>/project`
