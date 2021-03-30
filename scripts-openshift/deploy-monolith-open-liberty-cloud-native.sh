#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  _out Deploying monolith-open-liberty-cloud-native
  
  _out Cleanup
  cd ${root_folder}/monolith-open-liberty-cloud-native
  oc delete -f deployment/kubernetes.yaml --ignore-not-found
  oc delete route monolith-open-liberty-cloud-native --ignore-not-found
  oc delete is build-monolith-open-liberty-cloud-native --ignore-not-found
  
  cd ${root_folder}/monolith-open-liberty-cloud-native/src/main/resources/META-INF
  rm microprofile-config.properties
  cp microprofile-config-openshift.properties microprofile-config.properties
  cd ${root_folder}/monolith-open-liberty-cloud-native/src/main/liberty/config
  rm server.xml
  cp server-openshift.xml server.xml
  cd ${root_folder}/monolith-open-liberty-cloud-native
  cp Dockerfile Dockerfile.temp
  rm Dockerfile
  cp Dockerfile.multistage Dockerfile

  oc new-project app-mod-dev
  oc project app-mod-dev
  cd ${root_folder}/monolith-open-liberty-cloud-native
  oc new-build --name build-monolith-open-liberty-cloud-native --binary --strategy=docker
  oc start-build build-monolith-open-liberty-cloud-native --from-dir=.
  
  oc apply -f deployment/kubernetes.yaml
  oc expose svc/monolith-open-liberty-cloud-native

  cd ${root_folder}/monolith-open-liberty-cloud-native/src/main/resources/META-INF
  rm microprofile-config.properties
  cp microprofile-config-docker.properties microprofile-config.properties
  cd ${root_folder}/monolith-open-liberty-cloud-native/src/main/liberty/config
  rm server.xml
  cp server-docker.xml server.xml
  cd ${root_folder}/monolith-open-liberty-cloud-native
  rm Dockerfile
  cp Dockerfile.temp Dockerfile
  rm Dockerfile.temp

  _out Done deploying monolith-open-liberty-cloud-native
  ROUTE=$(oc get route monolith-open-liberty-cloud-native --template='{{ .spec.host }}')
  _out Wait until the pod has been started: "oc get pod --watch | grep monolith-open-liberty-cloud-native"
  
  _out "Invoke the endpoints:"
  echo "--- curl http://${ROUTE}/CustomerOrderServicesWeb/jaxrs/Customer/Orders"
  echo "--- curl http://${ROUTE}/CustomerOrderServicesWeb/jaxrs/Customer/TypeForm"
}

setup