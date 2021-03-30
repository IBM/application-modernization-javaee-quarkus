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
  
  rm -r ${root_folder}/monolith-open-liberty-cloud-native/target
  
  cd ${root_folder}/monolith-open-liberty-cloud-native/
  docker build -t storefront-backend-open-cloud-native -f Dockerfile.multistage .  
  
  docker run -d -p 9088:9088 --name storefront-backend-open-cloud-native storefront-backend-open-cloud-native  

  docker network connect store-front-network storefront-backend-open-cloud-native

  _out "curl localhost:9088/CustomerOrderServicesWeb/jaxrs/Customer/Orders"
}

setup