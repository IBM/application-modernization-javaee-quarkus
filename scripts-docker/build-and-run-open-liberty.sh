#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  echo "Run sh scripts-docker/run-monolith-db2.sh first"
  echo "curl http://localhost:9086/CustomerOrderServicesWeb/jaxrs/Category"
  
  cd ${root_folder}
  sh scripts-docker/stop-services.sh
  
  rm -r ${root_folder}/monolith-open-liberty/CustomerOrderServices/target
  rm -r ${root_folder}/monolith-open-liberty/CustomerOrderServicesApp/target
  rm -r ${root_folder}/monolith-open-liberty/CustomerOrderServicesWeb/target
  
  cd ${root_folder}/monolith-open-liberty/CustomerOrderServicesProject
  mvn clean package

  cd ${root_folder}/monolith-open-liberty/
  docker build -t storefront-backend-open .  
  
  docker run -d -p 9086:9080 --name storefront-backend-open storefront-backend-open  

  docker network connect store-front-network storefront-backend-open
}

setup