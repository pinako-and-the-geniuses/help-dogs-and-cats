#!/usr/bin/env bash
hdfs dfs -rm -r helpdogcat/input/alldata

hdfs dfs -mkdir -p helpdogcat/input/alldata

# HDFS에 데이터 복사
hdfs dfs -put -f /home/hadoop/a302hadoop/animaldata/alldata.txt helpdogcat/input/alldata


# input 파일 내용 조회
#hdfs dfs -cat helpdogcat/input/*

sleep 2


#파일 존재 여부 확인

#if [ $(hdfs dfs -test -e /helpdogcat/input/recentdata.txt) ];
#then
#  echo "data exist check done"
#else
#  echo "data not exist"
#fi

#데이터 잘 들어왔는지 확인

hdfs dfs -ls -R helpdogcat/input/alldata

if [ ! $(hdfs dfs -test -z /helpdogcat/input/alldata/alldata.txt) ];
then
  echo "data size check done"
else
  echo "data size is zero"
fi