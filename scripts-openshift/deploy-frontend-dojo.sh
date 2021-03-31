#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  _out Deploying frontend-dojo
  sh ${root_folder}/scripts/install-was-dependencies.sh
  
  _out Cleanup
  rm -r ${root_folder}/frontend-dojo/target
  cd ${root_folder}/frontend-dojo
  oc delete -f deployment/kubernetes.yaml --ignore-not-found
  oc delete route frontend-dojo --ignore-not-found
  oc delete is build-frontend-dojo --ignore-not-found
  oc delete bc/build-frontend-dojo --ignore-not-found
  
  ROUTE_MONOLITH=$(oc get route monolith-open-liberty-cloud-native -n app-mod-dev --template='{{ .spec.host }}')
  ROUTE_CATALOG=$(oc get route service-catalog-quarkus-reactive -n app-mod-dev --template='{{ .spec.host }}')
  if [ -z "$ROUTE_CATALOG" ]; then
    _out service-catalog-quarkus-reactive is not available. Run the command: \"sh scripts-openshift/deploy-service-catalog-quarkus-reactive.sh\"
  else 
    if [ -z "$ROUTE_MONOLITH" ]; then
      _out monolith-open-liberty-cloud-native is not available. Run the command: \"sh scripts-openshift/deploy-monolith-open-liberty-cloud-native.sh\"
    else 
      cd ${root_folder}/frontend-dojo
      cp Dockerfile Dockerfile.temp
      rm Dockerfile
      cp Dockerfile.multistage Dockerfile

      oc project app-mod-dev  > /dev/null 2>&1
      if [ $? != 0 ]; then 
          oc new-project app-mod-dev
      fi
      cd ${root_folder}/frontend-dojo
      oc new-build --name build-frontend-dojo --binary --strategy=docker
      oc start-build build-frontend-dojo --from-dir=.
      
      oc apply -f deployment/kubernetes.yaml
      oc expose svc/frontend-dojo

      cd ${root_folder}/frontend-dojo
      rm Dockerfile
      cp Dockerfile.temp Dockerfile
      rm Dockerfile.temp
      rm -r ${root_folder}/frontend-dojo/target

      _out Done deploying frontend-dojo
      ROUTE=$(oc get route frontend-dojo -n app-mod-dev --template='{{ .spec.host }}')
      _out Wait until the pod has been started: "oc get pod --watch | grep frontend-dojo"
      
      _out "Invoke the web app:"
      echo "http://${ROUTE}/CustomerOrderServicesWeb/"
  fi
}

setup