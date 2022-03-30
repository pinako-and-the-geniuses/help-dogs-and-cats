#!/usr/bin/env bash

# 아웃풋 디렉토리 삭제
hdfs dfs -rm -r helpdogcatall/output/

# 맵리듀스 실행
hadoop jar /home/hadoop/a302hadoop/helpdogcatjar/helpdogcat.jar help helpdogcatall/input/alldata.txt helpdogcatall/output

## 실행 결과 조회
#hdfs dfs -cat helpdogcatall/output/*
hdfs dfs -ls -R helpdogcatall/output
