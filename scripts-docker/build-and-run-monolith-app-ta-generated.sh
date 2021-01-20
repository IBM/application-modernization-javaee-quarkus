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
  docker build -f Dockerfile-monolith -t proxy-nginx .

  cd ${root_folder}/monolith-websphere-liberty/
  docker build -f Dockerfile.transformation-advisor -t storefront-monolith --no-cache .

  cd ${root_folder}/scripts-docker
  docker-compose -f docker-compose-monolith-app.yml up -d

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
  echo "--- docker-compose -f ${root_folder}/scripts-docker/docker-compose-monolith-app.yml logs -f"
  echo "--- docker-compose -f ${root_folder}/scripts-docker/docker-compose-monolith-db2.yml logs -f"
}

setup