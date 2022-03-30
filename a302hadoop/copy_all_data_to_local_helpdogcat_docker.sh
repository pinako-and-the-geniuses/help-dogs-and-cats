#!/usr/bin/env bash

rm -rf /home/hadoop/a302hadoop/hadoopoutput/alldata
mkdir /home/hadoop/a302hadoop/hadoopoutput/alldata

helpdogcatall
# hdfs에 있는 아웃풋 데이터 로컬로 복사
hdfs dfs -get -f helpdogcatall/output/part-r-00000 /home/hadoop/a302hadoop/hadoopoutput/alldata
hdfs dfs -get -f helpdogcatall/output/part-r-00001 /home/hadoop/a302hadoop/hadoopoutput/alldata
hdfs dfs -get -f helpdogcatall/output/part-r-00002 /home/hadoop/a302hadoop/hadoopoutput/alldata
hdfs dfs -get -f helpdogcatall/output/part-r-00003 /home/hadoop/a302hadoop/hadoopoutput/alldata

ls -al "/home/hadoop/a302hadoop/hadoopoutput/alldata"