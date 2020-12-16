#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {

  cd ${root_folder}/scripts-docker

  docker rm -f storefront-frontend
  docker rm -f storefront-backend
  docker rm -f proxy-nginx 
  docker rm -f storefront-monolith
  docker rm -f storefront-catalog
  docker rm -f storefront-backend-open
  docker rm -f storefront-backend-open-native
  docker rm -f storefront-backend-quarkus
  docker rm -f storefront-catalog-reactive
  docker network create store-front-network
}

setup