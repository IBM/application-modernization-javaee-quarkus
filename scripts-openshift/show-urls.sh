#!/bin/bash

SCRIPT_FOLDER="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"
PROJECT_FOLDER="$(cd $SCRIPT_FOLDER; cd ..; pwd )"

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  PROJECT_NAME=$1
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
  
  _out service-catalog-quarkus-reactive  
  nodeport=$(oc get svc service-catalog-quarkus-reactive -n $PROJECT_NAME --ignore-not-found --output 'jsonpath={.spec.ports[*].port}')
  if [ -z "$nodeport" ]; then
    _out service-catalog-quarkus-reactive is not available. Run the command: \"sh scripts-openshift/deploy-service-catalog-quarkus-reactive.sh\"
  else 
    ROUTE=$(oc get route service-catalog-quarkus-reactive -n $PROJECT_NAME --template='{{ .spec.host }}')
    _out \"curl http://${ROUTE}/CustomerOrderServicesWeb/jaxrs/Category\"
    _out \"curl \'http://${ROUTE}/CustomerOrderServicesWeb/jaxrs/Product/?categoryId=2\'\"
    CREATE_NEW="http://${ROUTE}/CustomerOrderServicesWeb/jaxrs/Product/1 -H 'accept: application/json' -H 'Content-Type: application/json' -d '{\"id\":1, \"price\":50}'"
    _out \"curl -X PUT ${CREATE_NEW}\"
  fi
  _out ------------------------------------------------------------------------------------

  _out monolith-open-liberty-cloud-native  
  nodeport=$(oc get svc monolith-open-liberty-cloud-native -n $PROJECT_NAME --ignore-not-found --output 'jsonpath={.spec.ports[*].port}')
  if [ -z "$nodeport" ]; then
    _out monolith-open-liberty-cloud-native is not available. Run the command: \"sh scripts-openshift/deploy-monolith-open-liberty-cloud-native.sh\"
  else 
    ROUTE=$(oc get route monolith-open-liberty-cloud-native -n $PROJECT_NAME --template='{{ .spec.host }}')
    _out \"curl http://${ROUTE}/CustomerOrderServicesWeb/jaxrs/Customer/Orders\"
    _out \"curl http://${ROUTE}/CustomerOrderServicesWeb/jaxrs/Customer/TypeForm\"    
  fi
  _out ------------------------------------------------------------------------------------

  _out frontend-dojo
  nodeport=$(oc get svc frontend-dojo -n $PROJECT_NAME --ignore-not-found --output 'jsonpath={.spec.ports[*].port}')
  if [ -z "$nodeport" ]; then
    _out frontend-dojo is not available. Run the command: \"sh scripts-openshift/deploy-frontend-dojo.sh\"
  else 
    ROUTE=$(oc get route frontend-dojo -n $PROJECT_NAME --template='{{ .spec.host }}')
  
    _out "Open the web app:"
    _out "http://${ROUTE}/CustomerOrderServicesWeb/"
  fi
  _out ------------------------------------------------------------------------------------

  _out frontend-single-spa
  nodeport=$(oc get svc storefront-mf-shell -n $PROJECT_NAME --ignore-not-found --output 'jsonpath={.spec.ports[*].port}')
  if [ -z "$nodeport" ]; then
    _out frontend-single-spa is not available. Run the command: \"sh scripts-openshift/deploy-storefront-mf-shell.sh\"
  else 
    ROUTE=$(oc get route storefront-mf-shell -n $PROJECT_NAME --template='{{ .spec.host }}')
  
    _out "Open the web app:"
    _out "http://${ROUTE}"
  fi
}

if [ -z "$1" ]
then
  setup "app-mod-dev"
else
  setup $1 
fi
