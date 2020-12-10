#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  echo "Run sh scripts-docker/run-monolith-db2.sh first"
  echo "Open http://localhost/CustomerOrderServicesWeb"
  
  cd ${root_folder}
  sh scripts-docker/stop-services.sh

  cd ${root_folder}/proxy
  docker build -f Dockerfile-catalog-native -t proxy-nginx .

  cd ${root_folder}/monolith-open-liberty-cloud-native
  mvn clean package
  docker build -t storefront-backend-open-native .

  cd ${root_folder}/scripts-docker
  docker-compose -f docker-compose-catalog-native.yml up
}

setup