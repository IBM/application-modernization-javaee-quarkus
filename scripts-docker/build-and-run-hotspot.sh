#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {   
  cd ${root_folder}
  sh scripts-docker/stop-everything.sh

  sh scripts/install-was-dependencies.sh

  docker network create store-front-network

  sh scripts-docker/build-monolith-db2.sh
  
  cd ${root_folder}/proxy
  docker build -f Dockerfile-all-quarkus -t proxy-nginx .

  cd ${root_folder}/monolith-quarkus-synch  
  docker build -f src/main/docker/Dockerfile.native.multistage -t storefront-backend-quarkus .

  cd ${root_folder}/frontend-dojo/
  docker build -f Dockerfile.multistage -t storefront-frontend .
  
  cd ${root_folder}/service-catalog-quarkus-reactive
  docker build -f Dockerfile -t storefront-catalog-reactive .

  cd ${root_folder}/scripts-docker
  docker-compose -f docker-compose-all-quarkus-reactive.yml up -d

  cd ${root_folder}/frontend-single-spa/account
  docker build -t storefront-mf-account .

  cd ${root_folder}/frontend-single-spa/navigator
  docker build -t storefront-mf-navigator .

  cd ${root_folder}/frontend-single-spa/shell
  docker build -t storefront-mf-shell .

  cd ${root_folder}/frontend-single-spa/messaging
  docker build -t storefront-mf-messaging .

  cd ${root_folder}/frontend-single-spa/catalog
  docker build -t storefront-mf-catalog .

  cd ${root_folder}/frontend-single-spa/order
  docker build -t storefront-mf-order .

  cd ${root_folder}/scripts-docker
  docker-compose -f docker-compose-single-spa.yml up -d
  
  echo "Notes:"
  echo "--- Docker Desktop requires 12 GB memory and 8 CPUs"
  echo "--- Launching everything takes roughly 10 minutes the first time"
  echo "Prerequisites:"
  echo "--- Docker needs to be installed locally"
  echo "--- git needs to be installed locally"
  echo "Stop containers:"
  echo "--- sh ${root_folder}/scripts-docker/stop-services.sh"
  echo "--- sh ${root_folder}/scripts-docker/stop-everything.sh"
  echo "--- sh ${root_folder}/scripts-docker/stop-single-spa-frontend.sh"
  echo "Open web applications:"
  echo "--- Legacy frontend: http://localhost/CustomerOrderServicesWeb"
  echo "--- Micro frontends: http://localhost:8080"
  echo "--- http://localhost/explorer"
  echo "Invoke the endpoints:"
  echo "--- curl http://localhost/CustomerOrderServicesWeb/jaxrs/Category"
  echo "--- curl http://localhost/CustomerOrderServicesWeb/jaxrs/Product/?categoryId=2"
  echo "--- curl http://localhost/CustomerOrderServicesWeb/jaxrs/Customer/Orders"
  echo "--- curl http://localhost/CustomerOrderServicesWeb/jaxrs/Customer/TypeForm"
  CREATE_NEW="http://localhost/CustomerOrderServicesWeb/jaxrs/Product/1 -H 'accept: application/json' -H 'Content-Type: application/json' -d '{\"id\":1, \"price\":50}'"
  echo "--- curl -X PUT ${CREATE_NEW}"
  echo "Follow the logs:"
  echo "--- docker-compose -f ${root_folder}/scripts-docker/docker-compose-all-quarkus-reactive.yml logs -f"
}

setup
