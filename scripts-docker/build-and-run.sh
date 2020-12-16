#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {  
  echo "Open http://localhost/CustomerOrderServicesWeb"
  
  cd ${root_folder}
  sh scripts-docker/stop-everything.sh

  docker network create store-front-network

  sh scripts-docker/build-monolith-db2.sh

  sh scripts-docker/run-kafka.sh

  sh scripts-docker/run-database-postgres-catalog.sh
  
  cd ${root_folder}/proxy
  docker build -f Dockerfile-all-quarkus -t proxy-nginx .

  cd ${root_folder}/monolith-quarkus-synch  
  docker build -f src/main/docker/Dockerfile.native.multistage -t storefront-backend-quarkus .

  cd ${root_folder}/frontend-dojo/
  docker build -f Dockerfile.multistage -t storefront-frontend .
  
  cd ${root_folder}/service-catalog-quarkus-reactive
  docker build -f Dockerfile -t storefront-catalog-reactive .

  cd ${root_folder}/scripts-docker
  docker-compose -f docker-compose-all-quarkus-reactive.yml up
}

setup
