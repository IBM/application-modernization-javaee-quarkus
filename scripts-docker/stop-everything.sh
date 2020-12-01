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
  docker rm -f zookeeper
  docker rm -f kafka
  docker rm -f postgres
  docker rm -f storefront-db2
}

setup