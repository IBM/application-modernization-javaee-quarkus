#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  echo "Run sh scripts-docker/run-monolith-db2.sh first"
  echo "Open http://localhost:9080/CustomerOrderServicesWeb"
  
  cd ${root_folder}/scripts-docker
  docker-compose -f docker-compose-monolith-app.yml up
}

setup