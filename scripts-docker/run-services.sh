#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {

  cd ${root_folder}/CustomerOrderServicesProject
  mvn clean package

  cd ${root_folder}/CustomerOrderServicesApp/target/
  docker build -t customerorderservices-local:1.0 .
  
  docker build -t customerorderservices-local-backend:1.0 .

  cd ${root_folder}/catalog
  
  cd ${root_folder}/scripts-docker
  docker-compose up
}

setup