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
  docker rm -f ibm-transformationAdvisor-couchDB
  docker rm -f storefront-backend-open
  docker rm -f kafka-tool
  docker rm -f storefront-backend-open-native
  docker rm -f storefront-backend-quarkus
  docker rm -f storefront-catalog-reactive
  docker rm -f storefront-mf-account
  docker rm -f storefront-mf-shell
  docker rm -f storefront-mf-order
  docker rm -f storefront-mf-catalog
  docker rm -f storefront-mf-messaging
  docker rm -f storefront-mf-navigator
}

setup