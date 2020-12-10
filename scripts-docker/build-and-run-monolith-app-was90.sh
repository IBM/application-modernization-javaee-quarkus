#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  echo "Run sh scripts/install-dojo.sh first"
  #echo "Run sh scripts-docker/run-monolith-db2.sh first"
  echo "Note: This script starts only the app without database"
  echo "Note: This script doesn't build the Java code"
  
  echo "Open https://localhost:9443/CustomerOrderServicesWeb/#shopPage"
  echo "User: skywalker, password: force"

  echo "Open https://localhost:9043/ibm/console/login.do?action=secure"
  echo "User: wsadmin, password: passw0rd"

  docker rm -f storefront-was90

  cd ${root_folder}/monolith-websphere-90
  #mvn clean package
  
  docker build --tag storefront-was90 .

  docker run -p 9043:9043 -p 9443:9443 --name storefront-was90 storefront-was90
}

setup