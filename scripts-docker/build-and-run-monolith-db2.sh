#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {

  docker network create store-front-network

  cd ${root_folder}/db2
  docker build . -t storefront-db2
  
  cd ${root_folder}/scripts-docker
  docker-compose -f docker-compose-monolith-db2.yml up
}

setup