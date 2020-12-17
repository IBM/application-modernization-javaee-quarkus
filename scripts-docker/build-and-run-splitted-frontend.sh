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
  docker build -f Dockerfile-splitted-frontend -t proxy-nginx .

  cd ${root_folder}/monolith-websphere-liberty/
  docker build -f Dockerfile.multistage -t storefront-backend .
  
  cd ${root_folder}/frontend-dojo/
  docker build -f Dockerfile.multistage -t storefront-frontend .

  cd ${root_folder}/scripts-docker
  docker-compose -f docker-compose-splitted-frontend.yml up -d

  echo "Prerequisites:"
  echo "--- sh ${ROOT_FOLDER}/scripts-docker/run-monolith-db2.sh"
  echo "Open web application:"
  echo "--- http://localhost/CustomerOrderServicesWeb"
  echo "Invoke the endpoints:"
  echo "--- curl http://localhost/CustomerOrderServicesWeb/jaxrs/Category"
  echo "--- curl http://localhost/CustomerOrderServicesWeb/jaxrs/Product/?categoryId=2"
  echo "--- curl http://localhost/CustomerOrderServicesWeb/jaxrs/Customer/Orders"
  echo "--- curl http://localhost/CustomerOrderServicesWeb/jaxrs/Customer/TypeForm"
  echo "Follow the logs:"
  echo "--- ${ROOT_FOLDER}/scripts-docker/docker-compose -f ${ROOT_FOLDER}/scripts-docker/docker-compose-splitted-frontend.yml logs -f"
  echo "--- ${ROOT_FOLDER}/scripts-docker/docker-compose -f ${ROOT_FOLDER}/scripts-docker/docker-compose-monolith-db2.yml logs -f"
}

setup