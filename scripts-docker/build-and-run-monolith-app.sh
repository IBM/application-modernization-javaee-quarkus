#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  echo "Run sh scripts-docker/run-monolith-db2.sh first"
  echo "Open http://localhost/CustomerOrderServicesWeb"
  
  cd ${root_folder}
  sh scripts-docker/stop-services.sh

  sh scripts/install-was-dependencies.sh

  cd ${root_folder}/proxy
  docker build -f Dockerfile-monolith -t proxy-nginx .

  cd ${root_folder}/monolith-websphere-liberty/
  docker build -f Dockerfile.multistage -t storefront-monolith --no-cache .

  cd ${root_folder}/scripts-docker
  docker-compose -f docker-compose-monolith-app.yml up
}

setup