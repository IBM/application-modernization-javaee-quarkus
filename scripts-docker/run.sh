#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {   
  cd ${root_folder}
  sh scripts-docker/stop-everything.sh

  docker network create store-front-network

  cd ${root_folder}/scripts-docker
  docker-compose -f docker-compose-all-quarkus-reactive-dockerhub.yml up -d

  cd ${root_folder}/scripts-docker
  #docker-compose -f docker-compose-single-spa.yml up -d
  
  echo "Notes:"
  echo "--- Docker Desktop requires 12 GB memory and 8 CPUs"
  echo "--- Launching everything takes roughly 10 minutes the first time"
  echo "Prerequisites:"
  echo "--- Docker needs to be installed locally"
  echo "--- git needs to be installed locally"
  echo "Stop containers:"
  echo "--- sh ${root_folder}/scripts-docker/stop-services.sh"
  echo "--- sh ${root_folder}/scripts-docker/stop-everything.sh"
  echo "--- sh ${root_folder}/scripts-docker/stop-single-spa-frontend.sh"
  echo "Open web applications:"
  echo "--- Legacy frontend: http://localhost/CustomerOrderServicesWeb"
  echo "--- Micro frontends: http://localhost:8080"
  echo "--- http://localhost/explorer"
  echo "Invoke the endpoints:"
  echo "--- curl http://localhost/CustomerOrderServicesWeb/jaxrs/Category"
  echo "--- curl http://localhost/CustomerOrderServicesWeb/jaxrs/Product/?categoryId=2"
  echo "--- curl http://localhost/CustomerOrderServicesWeb/jaxrs/Customer/Orders"
  echo "--- curl http://localhost/CustomerOrderServicesWeb/jaxrs/Customer/TypeForm"
  CREATE_NEW="http://localhost/CustomerOrderServicesWeb/jaxrs/Product/1 -H 'accept: application/json' -H 'Content-Type: application/json' -d '{\"id\":1, \"price\":50}'"
  echo "--- curl -X PUT ${CREATE_NEW}"
  echo "Follow the logs:"
  echo "--- docker-compose -f ${root_folder}/scripts-docker/docker-compose-all-quarkus-reactive.yml logs -f"
}

setup
