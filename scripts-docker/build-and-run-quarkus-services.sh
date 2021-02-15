#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {   
  
  docker network create store-front-network

  cd ${root_folder}
  sh scripts-docker/stop-services.sh
  
  cd ${root_folder}/proxy
  docker build -f Dockerfile-quarkus-services -t proxy-nginx .

  cd ${root_folder}/monolith-quarkus-synch  
  docker build -f src/main/docker/Dockerfile.native.multistage -t storefront-backend-quarkus .

  cd ${root_folder}/service-catalog-quarkus-reactive
  docker build -f Dockerfile -t storefront-catalog-reactive .
  #docker build -f src/main/docker/Dockerfile.native.multistage -t storefront-catalog-reactive .

  cd ${root_folder}/scripts-docker
  docker-compose -f docker-compose-quarkus-services.yml up -d

}

setup
