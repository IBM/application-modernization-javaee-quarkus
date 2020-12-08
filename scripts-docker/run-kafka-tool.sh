#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {

  echo "Open http://localhost:9000/"

  docker run -d --rm -p 9000:9000 \
    -e KAFKA_BROKERCONNECT=kafka:9092 --network=store-front-network --name=kafka-tool \
    -e JVM_OPTS="-Xms32M -Xmx64M" \
    -e SERVER_SERVLET_CONTEXTPATH="/" \
    obsidiandynamics/kafdrop
}

setup