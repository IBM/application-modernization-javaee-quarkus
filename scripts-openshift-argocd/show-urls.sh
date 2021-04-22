#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  PROJECT_NAME_PRE=$1
  PROJECT_NAME=${PROJECT_NAME_PRE}dev
  echo Project: $PROJECT_NAME

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

  _out Db2
  nodeport=$(oc get svc storefront-db2 -n db2 --ignore-not-found --output 'jsonpath={.spec.ports[*].port}')
  if [ -z "$nodeport" ]; then
    _out Db2 is not available. Run the command: \"sh scripts-openshift/deploy-db2.sh\"
  else 
    _out Db2 - internal URL: storefront-db2.db2:50000
  fi
  _out ------------------------------------------------------------------------------------
  
  _out service-catalog-quarkus-reactive dev
  nodeport=$(oc get svc service-catalog-quarkus-reactive -n $PROJECT_NAME --ignore-not-found --output 'jsonpath={.spec.ports[*].port}')
  if [ -z "$nodeport" ]; then
    _out ${PROJECT_NAME}.service-catalog-quarkus-reactive is not available. 
  else 
    ROUTE=$(oc get route service-catalog-quarkus-reactive -n $PROJECT_NAME --template='{{ .spec.host }}')
    _out \"curl http://${ROUTE}/CustomerOrderServicesWeb/jaxrs/Category\"
    _out \"curl \'http://${ROUTE}/CustomerOrderServicesWeb/jaxrs/Product/?categoryId=2\'\"
    CREATE_NEW="http://${ROUTE}/CustomerOrderServicesWeb/jaxrs/Product/1 -H 'accept: application/json' -H 'Content-Type: application/json' -d '{\"id\":1, \"price\":50}'"
    _out \"curl -X PUT ${CREATE_NEW}\"
  fi
  _out ------------------------------------------------------------------------------------

  PROJECT_NAME=${PROJECT_NAME_PRE}test
  _out service-catalog-quarkus-reactive test
  nodeport=$(oc get svc service-catalog-quarkus-reactive -n $PROJECT_NAME --ignore-not-found --output 'jsonpath={.spec.ports[*].port}')
  if [ -z "$nodeport" ]; then
    _out ${PROJECT_NAME}.service-catalog-quarkus-reactive is not available. 
  else 
    ROUTE=$(oc get route service-catalog-quarkus-reactive -n $PROJECT_NAME --template='{{ .spec.host }}')
    _out \"curl http://${ROUTE}/CustomerOrderServicesWeb/jaxrs/Category\"
    _out \"curl \'http://${ROUTE}/CustomerOrderServicesWeb/jaxrs/Product/?categoryId=2\'\"
    CREATE_NEW="http://${ROUTE}/CustomerOrderServicesWeb/jaxrs/Product/1 -H 'accept: application/json' -H 'Content-Type: application/json' -d '{\"id\":1, \"price\":50}'"
    _out \"curl -X PUT ${CREATE_NEW}\"
  fi
  _out ------------------------------------------------------------------------------------

  PROJECT_NAME=${PROJECT_NAME_PRE}prod
  _out service-catalog-quarkus-reactive prod
  nodeport=$(oc get svc service-catalog-quarkus-reactive -n $PROJECT_NAME --ignore-not-found --output 'jsonpath={.spec.ports[*].port}')
  if [ -z "$nodeport" ]; then
    _out ${PROJECT_NAME}.service-catalog-quarkus-reactive is not available. 
  else 
    ROUTE=$(oc get route service-catalog-quarkus-reactive -n $PROJECT_NAME --template='{{ .spec.host }}')
    _out \"curl http://${ROUTE}/CustomerOrderServicesWeb/jaxrs/Category\"
    _out \"curl \'http://${ROUTE}/CustomerOrderServicesWeb/jaxrs/Product/?categoryId=2\'\"
    CREATE_NEW="http://${ROUTE}/CustomerOrderServicesWeb/jaxrs/Product/1 -H 'accept: application/json' -H 'Content-Type: application/json' -d '{\"id\":1, \"price\":50}'"
    _out \"curl -X PUT ${CREATE_NEW}\"
  fi
}

if [ -z "$1" ]
then
  setup "app-mod-argocd-"
else
  setup $1 
fi
