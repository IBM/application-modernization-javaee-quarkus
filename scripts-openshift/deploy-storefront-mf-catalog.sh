#!/bin/bash

SCRIPT_FOLDER="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"
PROJECT_FOLDER="$(cd $SCRIPT_FOLDER; cd ..; pwd )"

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  _out Deploying storefront-mf-catalog
  
  _out Cleanup
  rm -rf ${PROJECT_FOLDER}/frontend-single-spa/catalog/dist
  rm -rf ${PROJECT_FOLDER}/frontend-single-spa/catalog/node_modules
  cd ${PROJECT_FOLDER}/frontend-single-spa/catalog
  oc delete -f deployment/kubernetes.yaml --ignore-not-found
  oc delete route storefront-mf-catalog --ignore-not-found
  oc delete is build-storefront-mf-catalog --ignore-not-found
  oc delete bc/build-storefront-mf-catalog --ignore-not-found
  
  ROUTE_MONOLITH=$(oc get route monolith-open-liberty-cloud-native -n app-mod-dev --template='{{ .spec.host }}')
  ROUTE_CATALOG=$(oc get route service-catalog-quarkus-reactive -n app-mod-dev --template='{{ .spec.host }}')
  if [ -z "$ROUTE_CATALOG" ]; then
    _out service-catalog-quarkus-reactive is not available. Run the command: \"sh scripts-openshift/deploy-service-catalog-quarkus-reactive.sh\"
  else 
    if [ -z "$ROUTE_MONOLITH" ]; then
      _out monolith-open-liberty-cloud-native is not available. Run the command: \"sh scripts-openshift/deploy-monolith-open-liberty-cloud-native.sh\"
    else 
      cd ${PROJECT_FOLDER}/frontend-single-spa/catalog
      cp Dockerfile Dockerfile.temp
      rm Dockerfile
      cp Dockerfile.os4 Dockerfile
      
      oc project app-mod-dev  > /dev/null 2>&1
      if [ $? != 0 ]; then 
          oc new-project app-mod-dev
      fi

      cp ${PROJECT_FOLDER}/frontend-single-spa/catalog/src/components/Home.vue ${PROJECT_FOLDER}/frontend-single-spa/catalog/Home.vue
      sed "s/http:\/\/localhost:9083\/CustomerOrderServicesWeb\/jaxrs\/Product/http:\/\/${ROUTE_CATALOG}\/CustomerOrderServicesWeb\/jaxrs\/Product/g" ${PROJECT_FOLDER}/frontend-single-spa/catalog/Home.vue > ${PROJECT_FOLDER}/frontend-single-spa/catalog/src/components/Home.vue
      
      cd ${PROJECT_FOLDER}/frontend-single-spa/catalog
      oc new-build --name build-storefront-mf-catalog --binary --strategy=docker
      oc start-build build-storefront-mf-catalog --from-dir=. --follow
      
      oc apply -f deployment/kubernetes.yaml
      oc expose svc/storefront-mf-catalog

      cd ${PROJECT_FOLDER}/frontend-single-spa/catalog
      rm Dockerfile
      cp Dockerfile.temp Dockerfile
      rm Dockerfile.temp

      rm ${PROJECT_FOLDER}/frontend-single-spa/catalog/src/components/Home.vue
      cp ${PROJECT_FOLDER}/frontend-single-spa/catalog/Home.vue ${PROJECT_FOLDER}/frontend-single-spa/catalog/src/components/Home.vue
      rm ${PROJECT_FOLDER}/frontend-single-spa/catalog/Home.vue

      _out Done deploying storefront-mf-catalog
      ROUTE=$(oc get route storefront-mf-catalog -n app-mod-dev --template='{{ .spec.host }}')
      _out Wait until the pod has been started: "oc get pod --watch | grep storefront-mf-catalog"
      
      _out "app.js:"
      _out "http://${ROUTE}/js/app.js"
    fi
  fi
}

setup