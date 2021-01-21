#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  echo "Run sh scripts/install-dojo.sh first"
  echo "Run sh scripts/install-was-dependencies.sh first"
  echo "You need to have Java 1.8 installed locally"
  echo "You need to have Maven installed locally"
  #echo "Run sh scripts-docker/run-monolith-db2.sh first"
  echo "Note: This script starts only the app without database"
  
  echo "Open https://localhost:9443/CustomerOrderServicesWeb/#shopPage"
  echo "User: skywalker, password: force"

  echo "Open https://localhost:9043/ibm/console/login.do?action=secure"
  # which credentials?  

  docker rm -f storefront-was90

  cd ${root_folder}/monolith-websphere-90
  rm CustomerOrderServicesApp-0.1.0-SNAPSHOT.ear

  cd ${root_folder}/monolith-websphere-90/CustomerOrderServicesProject
  mvn clean package

  cp ${root_folder}/monolith-websphere-90/CustomerOrderServicesApp/target/CustomerOrderServicesApp-0.1.0-SNAPSHOT.ear ${root_folder}/monolith-websphere-90/CustomerOrderServicesApp-0.1.0-SNAPSHOT.ear 
  
  docker build --tag storefront-was90 .

  docker run -p 9043:9043 -p 9443:9443 --name storefront-was90 storefront-was90
}

setup