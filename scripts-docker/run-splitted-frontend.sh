#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  echo "Run sh scripts-docker/run-monolith-db2.sh first"
  echo "Open http://localhost/CustomerOrderServicesWeb"
  
  cd ${root_folder}/proxy
  docker build -f Dockerfile-splitted-frontend -t proxy-nginx .

  docker network connect store-front-network proxy-nginx
  docker network connect store-front-network storefront-backend
  docker network connect store-front-network storefront-frontend
  
  cd ${root_folder}/scripts-docker
  docker-compose -f docker-compose-splitted-frontend.yml up
}

setup