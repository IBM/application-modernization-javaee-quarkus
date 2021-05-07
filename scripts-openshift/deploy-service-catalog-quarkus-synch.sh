#!/bin/bash

SCRIPT_FOLDER="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"
PROJECT_FOLDER="$(cd $SCRIPT_FOLDER; cd ..; pwd )"

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  _out Deploying service-catalog-quarkus-synch
  
  _out Cleanup
  oc delete secret ibm-cloud-postgres-cert --ignore-not-found

  rm -r ${PROJECT_FOLDER}/service-catalog-quarkus-reactive/target
  cd ${PROJECT_FOLDER}/service-catalog-quarkus-reactive
  oc delete -f deployment/kubernetes.yaml --ignore-not-found
  oc delete route service-catalog-quarkus-reactive --ignore-not-found
  oc delete is build-service-catalog-quarkus-reactive --ignore-not-found
  oc delete bc/build-service-catalog-quarkus-reactive --ignore-not-found

  rm -r ${PROJECT_FOLDER}/service-catalog-quarkus-synch/target
  cd ${PROJECT_FOLDER}/service-catalog-quarkus-synch
  oc delete -f deployment/kubernetes.yaml --ignore-not-found
  oc delete route service-catalog-quarkus-reactive --ignore-not-found
  oc delete is build-service-catalog-quarkus-synch --ignore-not-found
  oc delete bc/build-service-catalog-quarkus-synch --ignore-not-found

  cd ${PROJECT_FOLDER}/service-catalog-quarkus-synch/src/main/resources
  rm application.properties
  cp application-openshift.properties application.properties

  oc new-project app-mod-dev
  oc project app-mod-dev
  cd ${PROJECT_FOLDER}/service-catalog-quarkus-synch
  oc create secret generic ibm-cloud-postgres-cert --from-file ibm-cloud-postgres-cert

  oc new-build --name build-service-catalog-quarkus-synch --binary --strategy docker
  oc start-build build-service-catalog-quarkus-synch --from-dir=. --follow
  
  oc apply -f deployment/kubernetes.yaml

  cd ${PROJECT_FOLDER}/service-catalog-quarkus-synch/src/main/resources
  rm application.properties
  cp application.properties.container-unmanaged application.properties
  rm -r ${PROJECT_FOLDER}/service-catalog-quarkus-synch/target

  _out Done deploying service-catalog-quarkus-synch
  ROUTE=$(oc get route service-catalog-quarkus-reactive --template='{{ .spec.host }}')
  _out Wait until the pod has been started: "oc get pod --watch | grep service-catalog-quarkus-synch"
  
  _out "Invoke the endpoints:"
  _out "--- curl http://${ROUTE}/CustomerOrderServicesWeb/jaxrs/Category"
  _out "--- curl http://${ROUTE}/CustomerOrderServicesWeb/jaxrs/Product/?categoryId=2"
  CREATE_NEW="http://${ROUTE}/CustomerOrderServicesWeb/jaxrs/Product/1 -H 'accept: application/json' -H 'Content-Type: application/json' -d '{\"id\":1, \"price\":50}'"
  _out "--- curl -X PUT ${CREATE_NEW}"
}

setup