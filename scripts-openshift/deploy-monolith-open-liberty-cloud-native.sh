#!/bin/bash

SCRIPT_FOLDER="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"
PROJECT_FOLDER="$(cd $SCRIPT_FOLDER; cd ..; pwd )"

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  _out Deploying monolith-open-liberty-cloud-native
  rm -r ${PROJECT_FOLDER}/monolith-open-liberty-cloud-native/target
  
  _out Cleanup
  cd ${PROJECT_FOLDER}/monolith-open-liberty-cloud-native
  oc delete -f deployment/kubernetes.yaml --ignore-not-found
  oc delete route monolith-open-liberty-cloud-native --ignore-not-found
  oc delete is build-monolith-open-liberty-cloud-native --ignore-not-found
  oc delete bc/build-monolith-open-liberty-cloud-native --ignore-not-found
  
  cd ${PROJECT_FOLDER}/monolith-open-liberty-cloud-native/src/main/resources/META-INF
  rm microprofile-config.properties
  cp microprofile-config-openshift.properties microprofile-config.properties
  cd ${PROJECT_FOLDER}/monolith-open-liberty-cloud-native/src/main/liberty/config
  rm server.xml
  cp server-openshift.xml server.xml
  cd ${PROJECT_FOLDER}/monolith-open-liberty-cloud-native
  cp Dockerfile Dockerfile.temp
  rm Dockerfile
  cp Dockerfile.multistage Dockerfile

  oc project app-mod-dev  > /dev/null 2>&1
  if [ $? != 0 ]; then 
      oc new-project app-mod-dev
  fi
  cd ${PROJECT_FOLDER}/monolith-open-liberty-cloud-native
  oc new-build --name build-monolith-open-liberty-cloud-native --binary --strategy=docker
  oc start-build build-monolith-open-liberty-cloud-native --from-dir=. --follow

  oc apply -f deployment/kubernetes.yaml
  oc expose svc/monolith-open-liberty-cloud-native

  cd ${PROJECT_FOLDER}/monolith-open-liberty-cloud-native/src/main/resources/META-INF
  rm microprofile-config.properties
  cp microprofile-config-docker.properties microprofile-config.properties
  cd ${PROJECT_FOLDER}/monolith-open-liberty-cloud-native/src/main/liberty/config
  rm server.xml
  cp server-docker.xml server.xml
  cd ${PROJECT_FOLDER}/monolith-open-liberty-cloud-native
  rm Dockerfile
  cp Dockerfile.temp Dockerfile
  rm Dockerfile.temp
  rm -r ${PROJECT_FOLDER}/monolith-open-liberty-cloud-native/target

  _out Done deploying monolith-open-liberty-cloud-native
  ROUTE=$(oc get route monolith-open-liberty-cloud-native --template='{{ .spec.host }}')
  _out Wait until the pod has been started: "oc get pod --watch | grep monolith-open-liberty-cloud-native"
  
  _out "Invoke the endpoints:"
  echo "--- curl http://${ROUTE}/CustomerOrderServicesWeb/jaxrs/Customer/Orders"
  echo "--- curl http://${ROUTE}/CustomerOrderServicesWeb/jaxrs/Customer/TypeForm"
}

setup