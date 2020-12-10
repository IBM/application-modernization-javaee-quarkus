#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  echo "Run sh scripts/install-dojo.sh first"
  echo "Run sh scripts-docker/run-monolith-db2.sh first"
  echo "Run sh scripts-docker/run-database-postgres-catalog.shh first"
  echo "Run sh scripts-docker/run-kafka.sh first"
  echo "Open http://localhost/CustomerOrderServicesWeb"
  
  cd ${root_folder}
  sh scripts-docker/stop-services.sh
  
  cd ${root_folder}/proxy
  docker build -f Dockerfile-catalog -t proxy-nginx .

  cd ${root_folder}/monolith-open-liberty/CustomerOrderServicesProject
  mvn clean package
  cd ${root_folder}/monolith-open-liberty/
  docker build -t storefront-backend-open .

  cd ${root_folder}/frontend-dojo/CustomerOrderServicesProject
  mvn clean package
  cd ${root_folder}/frontend-dojo/
  docker build -t storefront-frontend .

  cd ${root_folder}/service-catalog-quarkus
  docker build -f Dockerfile -t storefront-catalog .

  cd ${root_folder}/scripts-docker
  docker-compose -f docker-compose-catalog.yml up
}

setup
