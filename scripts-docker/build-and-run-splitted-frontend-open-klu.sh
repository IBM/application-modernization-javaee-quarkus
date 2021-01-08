#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  cd ${root_folder}
  sh scripts-docker/stop-services.sh
  
  sh scripts/install-was-dependencies.sh
  
  cd ${root_folder}/proxy
  docker build -f Dockerfile-splitted-frontend-open -t proxy-nginx .

  cd ${root_folder}/monolith-open-liberty-klu/
  docker build -f Dockerfile.multistage -t storefront-backend-open .

  cd ${root_folder}/frontend-dojo/
  docker build -f Dockerfile.multistage -t storefront-frontend .

  cd ${root_folder}/scripts-docker
  docker-compose -f docker-compose-splitted-frontend-open.yml up -d

}

setup