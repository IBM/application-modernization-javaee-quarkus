#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {

  _out ------------------------------------------------------------------------------------
  
  _out Kafka
  nodeport=$(oc get svc my-cluster-kafka-external-bootstrap -n kafka --ignore-not-found --output 'jsonpath={.spec.ports[*].port}')
  if [ -z "$nodeport" ]; then
    _out Kafka is not available. Run the command: \"sh scripts-openshift/deploy-kafka.sh\"
  else     
    _out Kafka bootstrap server - internal URL: my-cluster-kafka-external-bootstrap.kafka:9094
  fi
  _out ------------------------------------------------------------------------------------

  _out Postgres
  nodeport=$(oc get svc postgres -n postgres --ignore-not-found --output 'jsonpath={.spec.ports[*].port}')
  if [ -z "$nodeport" ]; then
    _out Postgres is not available. Run the command: \"sh scripts-openshift/deploy-postgres.sh\"
  else 
    _out Postgres - internal URL: postgres.postgres:5432
  fi
  _out ------------------------------------------------------------------------------------
  
  _out service-catalog-quarkus-reactive  
  nodeport=$(oc get svc service-catalog-quarkus-reactive -n app-mod-dev --ignore-not-found --output 'jsonpath={.spec.ports[*].port}')
  if [ -z "$nodeport" ]; then
    _out service-catalog-quarkus-reactive is not available. Run the command: \"sh scripts-openshift/deploy-service-catalog-quarkus-reactive.sh\"
  else 
    ROUTE=$(oc get route service-catalog-quarkus-reactive --template='{{ .spec.host }}')
    _out "curl http://${ROUTE}/CustomerOrderServicesWeb/jaxrs/Category"
    _out "curl http://${ROUTE}/CustomerOrderServicesWeb/jaxrs/Product/?categoryId=2"
    _out "curl http://${ROUTE}/CustomerOrderServicesWeb/jaxrs/Customer/Orders"
    _out "curl http://${ROUTE}/CustomerOrderServicesWeb/jaxrs/Customer/TypeForm"
    CREATE_NEW="http://${ROUTE}/CustomerOrderServicesWeb/jaxrs/Product/1 -H 'accept: application/json' -H 'Content-Type: application/json' -d '{\"id\":1, \"price\":50}'"
    _out "curl -X PUT ${CREATE_NEW}"
  fi
}

setup