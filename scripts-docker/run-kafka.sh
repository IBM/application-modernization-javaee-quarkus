#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {

  docker rm -f kafka zookeeper
  
  cd ${root_folder}/scripts-docker
  docker-compose -f docker-compose-kafka.yml up
}

setup