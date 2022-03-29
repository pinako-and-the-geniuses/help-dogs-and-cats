#!/usr/bin/env bash

# 아웃풋 디렉토리 삭제
hdfs dfs -rm -r helpdogcat/output/recentdata

# 맵리듀스 실행
hadoop jar /home/hadoop/a302hadoop/helpdogcatjar/helpdogcat.jar help helpdogcat/input/recentdata/recentdata.txt helpdogcat/output/recentdata

## 실행 결과 조회
#hdfs dfs -cat helpdogcat/output/*
hdfs dfs -ls -R helpdogcat/output/recentdata
