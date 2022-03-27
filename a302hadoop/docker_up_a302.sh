#!/usr/bin/env bash
export PJT_DIR_NAME=a302hadoop

if [ ! "$( docker container inspect -f '{{.State.Running}}' hadoop )" == "true" ]; then
    docker run -d -v /home/ubuntu/S06P22A302/a302hadoop:/home/hadoop/a302hadoop \
#    docker run -d -v c:/ssafy/S06P22A302/a302hadoop:/home/hadoop/a302hadoop \
    -p 9870:9870 -p 8088:8088 -p 16010:16010 -p 10002:10002 -p 14040:4040 -p 9995:9995 \
    --name hadoop hibuz/zeppelin-dev yarn,hbase,hive
    sleep 2
    docker start hadoop
fi

if [[ "$OSTYPE" =~ ^msys  || "$OSTYPE" =~ ^cygwin ]]; then
    winpty docker exec -it hadoop bash -c "cd ~/a302hadoop && /bin/bash"
else
    docker exec -it hadoop bash -c "cd ~/a302hadoop && /bin/bash"
fi
