#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  echo "Run sh scripts-docker/run-monolith-db2.sh first"
  echo "Open http://localhost/CustomerOrderServicesWeb"
  
  docker rm -f storefront-frontend
  docker rm -f storefront-backend
  docker rm -f proxy-nginx 
  docker rm -f storefront-monolith
  docker rm -f storefront-catalog
  docker rm -f storefront-backend-open

  cd ${root_folder}/monolith-open-liberty/CustomerOrderServicesProject
  mvn clean package
  cd ${root_folder}/monolith-open-liberty/
  docker build -t storefront-backend-open .

  cd ${root_folder}/scripts-docker
  docker-compose -f docker-compose-catalog.yml up
}

setup