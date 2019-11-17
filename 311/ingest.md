# Ingest data
## Upload file to dumbo

```
scp 311_Service_Requests_from_2010_to_Present.csv dumbo:/home/ijl245/project_data
```

## Move data to HDFS

```
hdfs dfs -mkdir project_data
hdfs dfs -put /home/ijl245/project_data/311_Service_Requests_from_2010_to_Present.csv /user/ijl245/project_data
```
