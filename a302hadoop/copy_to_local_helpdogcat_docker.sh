#!/usr/bin/env bash

# hdfs에 있는 아웃풋 데이터 로컬로 복사
hdfs dfs -get -f helpdogcat/output/part-r-00000 /home/hadoop/a302hadoop/hadoopoutput
hdfs dfs -get -f helpdogcat/output/part-r-00001 /home/hadoop/a302hadoop/hadoopoutput
hdfs dfs -get -f helpdogcat/output/part-r-00002 /home/hadoop/a302hadoop/hadoopoutput
hdfs dfs -get -f helpdogcat/output/part-r-00003 /home/hadoop/a302hadoop/hadoopoutput


ls -al "/home/hadoop/a302hadoop/hadoopoutput"