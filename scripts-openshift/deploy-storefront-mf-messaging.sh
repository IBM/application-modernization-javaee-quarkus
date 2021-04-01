#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  _out Deploying storefront-mf-messaging
  
  _out Cleanup
  rm -rf ${root_folder}/frontend-single-spa/messaging/dist
  rm -rf ${root_folder}/frontend-single-spa/messaging/node_modules
  cd ${root_folder}/frontend-single-spa/messaging
  oc delete -f deployment/kubernetes.yaml --ignore-not-found
  oc delete route storefront-mf-messaging --ignore-not-found
  oc delete is build-storefront-mf-messaging --ignore-not-found
  oc delete bc/build-storefront-mf-messaging --ignore-not-found
  
  ROUTE_MONOLITH=$(oc get route monolith-open-liberty-cloud-native -n app-mod-dev --template='{{ .spec.host }}')
  ROUTE_CATALOG=$(oc get route service-catalog-quarkus-reactive -n app-mod-dev --template='{{ .spec.host }}')
  if [ -z "$ROUTE_CATALOG" ]; then
    _out service-catalog-quarkus-reactive is not available. Run the command: \"sh scripts-openshift/deploy-service-catalog-quarkus-reactive.sh\"
  else 
    if [ -z "$ROUTE_MONOLITH" ]; then
      _out monolith-open-liberty-cloud-native is not available. Run the command: \"sh scripts-openshift/deploy-monolith-open-liberty-cloud-native.sh\"
    else 
      cd ${root_folder}/frontend-single-spa/messaging
      cp Dockerfile Dockerfile.temp
      rm Dockerfile
      cp Dockerfile.os4 Dockerfile
      
      oc project app-mod-dev  > /dev/null 2>&1
      if [ $? != 0 ]; then 
          oc new-project app-mod-dev
      fi
      
      cd ${root_folder}/frontend-single-spa/messaging
      oc new-build --name build-storefront-mf-messaging --binary --strategy=docker
      oc start-build build-storefront-mf-messaging --from-dir=. --follow
      
      oc apply -f deployment/kubernetes.yaml
      oc expose svc/storefront-mf-messaging

      cd ${root_folder}/frontend-single-spa/messaging
      rm Dockerfile
      cp Dockerfile.temp Dockerfile
      rm Dockerfile.temp
      
      _out Done deploying storefront-mf-messaging
      ROUTE=$(oc get route storefront-mf-messaging -n app-mod-dev --template='{{ .spec.host }}')
      _out Wait until the pod has been started: "oc get pod --watch | grep storefront-mf-messaging"
      
      _out "vue-app-mod-messaging.js:"
      _out "http://${ROUTE}/vue-app-mod-messaging.js"
    fi
  fi
}

setup