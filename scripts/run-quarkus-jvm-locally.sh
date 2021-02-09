#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() { 

  echo "Run sh scripts-docker/run-monolith-db2.sh first"
  echo "Run sh scripts-docker/run-database-postgres-catalog.sh first"
  echo "Run sh scripts-docker/run-kafka.sh first"
  echo curl  \"http://localhost:9088/CustomerOrderServicesWeb/jaxrs/Product/?categoryId=2\"

  cd ${root_folder}
  sh scripts-docker/stop-services.sh

  cd ${root_folder}/service-catalog-quarkus-reactive
  cp src/main/resources/application-local.properties src/main/resources/application.properties

  mvn compile quarkus:dev
}

setup
