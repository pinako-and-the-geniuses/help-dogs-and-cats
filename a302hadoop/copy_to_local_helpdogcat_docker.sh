#!/usr/bin/env bash

# hdfs에 있는 아웃풋 데이터 로컬로 복사

rm -rf /home/hadoop/a302hadoop/hadoopoutput/recentdata
mkdir /home/hadoop/a302hadoop/hadoopoutput/recentdata

hdfs dfs -get -f helpdogcat/output/part-r-00000 /home/hadoop/a302hadoop/hadoopoutput/recentdata
hdfs dfs -get -f helpdogcat/output/part-r-00001 /home/hadoop/a302hadoop/hadoopoutput/recentdata
hdfs dfs -get -f helpdogcat/output/part-r-00002 /home/hadoop/a302hadoop/hadoopoutput/recentdata
hdfs dfs -get -f helpdogcat/output/part-r-00003 /home/hadoop/a302hadoop/hadoopoutput/recentdata


ls -al "/home/hadoop/a302hadoop/hadoopoutput/recentdata"