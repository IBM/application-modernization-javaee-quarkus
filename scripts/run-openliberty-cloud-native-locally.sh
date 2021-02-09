#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  echo "Run sh scripts-docker/run-monolith-db2.sh first"
    
  cd ${root_folder}
  sh scripts-docker/stop-services.sh
  
  cd ${root_folder}/monolith-open-liberty-cloud-native

  cp src/main/liberty/config/server-local.xml src/main/liberty/config/server.xml

  cp src/main/resources/META-INF/microprofile-config-local.properties src/main/resources/META-INF/microprofile-config.properties 

  mvn clean package
  mvn liberty:dev
}

setup
