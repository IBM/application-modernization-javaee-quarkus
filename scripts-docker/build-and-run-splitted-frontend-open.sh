#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  cd ${root_folder}
  sh scripts-docker/stop-services.sh
  
  cd ${root_folder}/proxy
  docker build -f Dockerfile-splitted-frontend-open -t proxy-nginx .

  cd ${root_folder}/monolith-open-liberty/
  docker build -f Dockerfile.multistage -t storefront-backend-open .

  cd ${root_folder}/frontend-dojo/
  docker build -f Dockerfile.multistage -t storefront-frontend .

  cd ${root_folder}/scripts-docker
  docker-compose -f docker-compose-splitted-frontend-open.yml up -d

  echo "Notes:"
  echo "--- Launching Db2 takes up to 3-5 minutes"
  echo "Prerequisites:"
  echo "--- Docker needs to be installed locally"
  echo "--- git needs to be installed locally"
  echo "--- sh ${root_folder}/scripts-docker/run-monolith-db2.sh"
  echo "Stop containers:"
  echo "--- sh ${root_folder}/scripts-docker/stop-services.sh"
  echo "--- sh ${root_folder}/scripts-docker/stop-everything.sh"
  echo "Open web application:"
  echo "--- http://localhost/CustomerOrderServicesWeb"
  echo "Invoke the endpoints:"
  echo "--- curl http://localhost/CustomerOrderServicesWeb/jaxrs/Category"
  echo "--- curl http://localhost/CustomerOrderServicesWeb/jaxrs/Product/?categoryId=2"
  echo "--- curl http://localhost/CustomerOrderServicesWeb/jaxrs/Customer/Orders"
  echo "--- curl http://localhost/CustomerOrderServicesWeb/jaxrs/Customer/TypeForm"
  echo "Follow the logs:"
  echo "--- docker-compose -f ${root_folder}/scripts-docker/docker-compose-splitted-frontend-open.yml logs -f"
  echo "--- docker-compose -f ${root_folder}/scripts-docker/docker-compose-monolith-db2.yml logs -f"
}

setup