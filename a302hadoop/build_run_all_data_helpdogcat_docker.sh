#!/usr/bin/env bash

# 아웃풋 디렉토리 삭제
hdfs dfs -rm -r helpdogcat/output/alldata

# 맵리듀스 실행
hadoop jar /home/hadoop/a302hadoop/helpdogcatjar/helpdogcat.jar help helpdogcat/input/alldata/alldata.txt helpdogcat/output/alldata

## 실행 결과 조회
#hdfs dfs -cat helpdogcat/output/*
hdfs dfs -ls -R helpdogcat/output/alldata
