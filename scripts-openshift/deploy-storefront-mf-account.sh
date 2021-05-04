#!/bin/bash

root_folder="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  _out Deploying storefront-mf-account
  
  _out Cleanup
  rm -rf ${root_folder}/frontend-single-spa/account/dist
  rm -rf ${root_folder}/frontend-single-spa/account/node_modules
  cd ${root_folder}/frontend-single-spa/account
  oc delete -f deployment/kubernetes.yaml --ignore-not-found
  oc delete route storefront-mf-account --ignore-not-found
  oc delete is build-storefront-mf-account --ignore-not-found
  oc delete bc/build-storefront-mf-account --ignore-not-found
  
  ROUTE_MONOLITH=$(oc get route monolith-open-liberty-cloud-native -n app-mod-dev --template='{{ .spec.host }}')
  ROUTE_CATALOG=$(oc get route service-catalog-quarkus-reactive -n app-mod-dev --template='{{ .spec.host }}')
  if [ -z "$ROUTE_CATALOG" ]; then
    _out service-catalog-quarkus-reactive is not available. Run the command: \"sh scripts-openshift/deploy-service-catalog-quarkus-reactive.sh\"
  else 
    if [ -z "$ROUTE_MONOLITH" ]; then
      _out monolith-open-liberty-cloud-native is not available. Run the command: \"sh scripts-openshift/deploy-monolith-open-liberty-cloud-native.sh\"
    else 
      cd ${root_folder}/frontend-single-spa/account
      cp Dockerfile Dockerfile.temp
      rm Dockerfile
      cp Dockerfile.os4 Dockerfile
      
      oc project app-mod-dev  > /dev/null 2>&1
      if [ $? != 0 ]; then 
          oc new-project app-mod-dev
      fi
      
      cd ${root_folder}/frontend-single-spa/account
      oc new-build --name build-storefront-mf-account --binary --strategy=docker
      oc start-build build-storefront-mf-account --from-dir=. --follow
      
      oc apply -f deployment/kubernetes.yaml
      oc expose svc/storefront-mf-account

      cd ${root_folder}/frontend-single-spa/account
      rm Dockerfile
      cp Dockerfile.temp Dockerfile
      rm Dockerfile.temp
      
      _out Done deploying storefront-mf-account
      ROUTE=$(oc get route storefront-mf-account -n app-mod-dev --template='{{ .spec.host }}')
      _out Wait until the pod has been started: "oc get pod --watch | grep storefront-mf-account"
      
      _out "app.js:"
      _out "http://${ROUTE}/js/app.js"
    fi
  fi
}

setup