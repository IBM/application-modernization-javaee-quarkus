#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  _out Deploying storefront-mf-navigator
  
  _out Cleanup
  rm -rf ${root_folder}/frontend-single-spa/navigator/dist
  rm -rf ${root_folder}/frontend-single-spa/navigator/node_modules
  cd ${root_folder}/frontend-single-spa/navigator
  oc delete -f deployment/kubernetes.yaml --ignore-not-found
  oc delete route storefront-mf-navigator --ignore-not-found
  oc delete is build-storefront-mf-navigator --ignore-not-found
  oc delete bc/build-storefront-mf-navigator --ignore-not-found
  
  ROUTE_MONOLITH=$(oc get route monolith-open-liberty-cloud-native -n app-mod-dev --template='{{ .spec.host }}')
  ROUTE_CATALOG=$(oc get route service-catalog-quarkus-reactive -n app-mod-dev --template='{{ .spec.host }}')
  if [ -z "$ROUTE_CATALOG" ]; then
    _out service-catalog-quarkus-reactive is not available. Run the command: \"sh scripts-openshift/deploy-service-catalog-quarkus-reactive.sh\"
  else 
    if [ -z "$ROUTE_MONOLITH" ]; then
      _out monolith-open-liberty-cloud-native is not available. Run the command: \"sh scripts-openshift/deploy-monolith-open-liberty-cloud-native.sh\"
    else 
      cd ${root_folder}/frontend-single-spa/navigator
      cp Dockerfile Dockerfile.temp
      rm Dockerfile
      cp Dockerfile.os4 Dockerfile
      
      oc project app-mod-dev  > /dev/null 2>&1
      if [ $? != 0 ]; then 
          oc new-project app-mod-dev
      fi

      cp ${root_folder}/frontend-single-spa/navigator/src/App.vue ${root_folder}/frontend-single-spa/navigator/App.vue
      rm ${root_folder}/frontend-single-spa/navigator/src/App.vue
      sed "s/http:\/\/localhost\/CustomerOrderServicesWeb\/jaxrs\/Category/http:\/\/${ROUTE_CATALOG}\/CustomerOrderServicesWeb\/jaxrs\/Category/g" ${root_folder}/frontend-single-spa/navigator/App.vue > ${root_folder}/frontend-single-spa/navigator/App2.vue
      sed "s/http:\/\/localhost\/CustomerOrderServicesWeb\/jaxrs\/Customer\/Orders/http:\/\/${ROUTE_MONOLITH}\/CustomerOrderServicesWeb\/jaxrs\/Customer\/Orders/g" ${root_folder}/frontend-single-spa/navigator/App2.vue > ${root_folder}/frontend-single-spa/navigator/src/App.vue
      
      cd ${root_folder}/frontend-single-spa/navigator
      oc new-build --name build-storefront-mf-navigator --binary --strategy=docker
      oc start-build build-storefront-mf-navigator --from-dir=. --follow
      
      oc apply -f deployment/kubernetes.yaml
      oc expose svc/storefront-mf-navigator

      cd ${root_folder}/frontend-single-spa/navigator
      rm Dockerfile
      cp Dockerfile.temp Dockerfile
      rm Dockerfile.temp

      rm ${root_folder}/frontend-single-spa/navigator/src/App.vue
      cp ${root_folder}/frontend-single-spa/navigator/App.vue ${root_folder}/frontend-single-spa/navigator/src/App.vue
      rm ${root_folder}/frontend-single-spa/navigator/App.vue
      rm ${root_folder}/frontend-single-spa/navigator/App2.vue
      
      _out Done deploying storefront-mf-navigator
      ROUTE=$(oc get route storefront-mf-navigator -n app-mod-dev --template='{{ .spec.host }}')
      _out Wait until the pod has been started: "oc get pod --watch | grep storefront-mf-navigator"
      
      _out "app.js:"
      _out "http://${ROUTE}/js/app.js"
    fi
  fi
}

setup