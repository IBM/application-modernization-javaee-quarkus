#!/bin/bash

SCRIPT_FOLDER="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"
PROJECT_FOLDER="$(cd $SCRIPT_FOLDER; cd ..; pwd )"

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  _out Deploying frontend-dojo
  sh ${PROJECT_FOLDER}/scripts/install-was-dependencies.sh
  
  _out Cleanup
  rm -rf ${PROJECT_FOLDER}/frontend-dojo/target
  cd ${PROJECT_FOLDER}/frontend-dojo
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
      cd ${PROJECT_FOLDER}/frontend-dojo
      cp Dockerfile Dockerfile.temp
      rm Dockerfile
      cp Dockerfile.multistage Dockerfile

      cp ${PROJECT_FOLDER}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/ProductController.js ${PROJECT_FOLDER}/frontend-dojo/ProductController.js 
      rm ${PROJECT_FOLDER}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/ProductController.js
      sed "s/jaxrs\/Category/http:\/\/${ROUTE_CATALOG}\/CustomerOrderServicesWeb\/jaxrs\/Category/g" ${PROJECT_FOLDER}/frontend-dojo/ProductController.js > ${PROJECT_FOLDER}/frontend-dojo/ProductController2.js
      sed "s/jaxrs\/Customer\/OpenOrder\/LineItem/http:\/\/${ROUTE_MONOLITH}\/CustomerOrderServicesWeb\/jaxrs\/Customer\/OpenOrder\/LineItem/g" ${PROJECT_FOLDER}/frontend-dojo/ProductController2.js > ${PROJECT_FOLDER}/frontend-dojo/ProductController3.js
      sed "s/\/\/this.addToCorsIssue/this.addToCorsIssue/g" ${PROJECT_FOLDER}/frontend-dojo/ProductController3.js > ${PROJECT_FOLDER}/frontend-dojo/ProductController4.js
      sed "s/this.addToCartNormal/\/\/this.addToCartNormal/g" ${PROJECT_FOLDER}/frontend-dojo/ProductController4.js > ${PROJECT_FOLDER}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/ProductController.js
      cp ${PROJECT_FOLDER}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/AccountController.js ${PROJECT_FOLDER}/frontend-dojo/AccountController.js 
      rm ${PROJECT_FOLDER}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/AccountController.js
      sed "s/\/CustomerOrderServicesWeb\/jaxrs\/Customer/http:\/\/${ROUTE_MONOLITH}\/CustomerOrderServicesWeb\/jaxrs\/Customer/g" ${PROJECT_FOLDER}/frontend-dojo/AccountController.js > ${PROJECT_FOLDER}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/AccountController.js
      cp ${PROJECT_FOLDER}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/AddressController.js ${PROJECT_FOLDER}/frontend-dojo/AddressController.js 
      rm ${PROJECT_FOLDER}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/AddressController.js
      sed "s/jaxrs\/Customer\/Address/http:\/\/${ROUTE_MONOLITH}\/CustomerOrderServicesWeb\/jaxrs\/Customer\/Address/g" ${PROJECT_FOLDER}/frontend-dojo/AddressController.js > ${PROJECT_FOLDER}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/AddressController.js
      cp ${PROJECT_FOLDER}/frontend-dojo/CustomerOrderServicesWeb/WebContent/product/product.html ${PROJECT_FOLDER}/frontend-dojo/product.html
      rm ${PROJECT_FOLDER}/frontend-dojo/CustomerOrderServicesWeb/WebContent/product/product.html
      sed "s/jaxrs\/Product/http:\/\/${ROUTE_CATALOG}\/CustomerOrderServicesWeb\/jaxrs\/Product/g" ${PROJECT_FOLDER}/frontend-dojo/product.html > ${PROJECT_FOLDER}/frontend-dojo/CustomerOrderServicesWeb/WebContent/product/product.html
      cp ${PROJECT_FOLDER}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/OrderHistoryController.js ${PROJECT_FOLDER}/frontend-dojo/OrderHistoryController.js 
      rm ${PROJECT_FOLDER}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/OrderHistoryController.js
      sed "s/\/CustomerOrderServicesWeb\/jaxrs\/Customer\/Orders/http:\/\/${ROUTE_MONOLITH}\/CustomerOrderServicesWeb\/jaxrs\/Customer\/Orders/g" ${PROJECT_FOLDER}/frontend-dojo/OrderHistoryController.js > ${PROJECT_FOLDER}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/OrderHistoryController.js
      cp ${PROJECT_FOLDER}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/AccountTypeFormController.js ${PROJECT_FOLDER}/frontend-dojo/AccountTypeFormController.js 
      rm ${PROJECT_FOLDER}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/AccountTypeFormController.js
      sed "s/jaxrs\/Customer\/TypeForm/http:\/\/${ROUTE_MONOLITH}\/CustomerOrderServicesWeb\/jaxrs\/Customer\/TypeForm/g" ${PROJECT_FOLDER}/frontend-dojo/AccountTypeFormController.js > ${PROJECT_FOLDER}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/AccountTypeFormController.js
      
      oc project app-mod-dev  > /dev/null 2>&1
      if [ $? != 0 ]; then 
          oc new-project app-mod-dev
      fi
      
      cd ${PROJECT_FOLDER}/frontend-dojo
      oc new-build --name build-frontend-dojo --binary --strategy=docker
      oc start-build build-frontend-dojo --from-dir=. --follow
      
      oc apply -f deployment/kubernetes.yaml
      oc expose svc/frontend-dojo

      cd ${PROJECT_FOLDER}/frontend-dojo
      rm Dockerfile
      cp Dockerfile.temp Dockerfile
      rm Dockerfile.temp
      rm -rf ${PROJECT_FOLDER}/frontend-dojo/target

      rm ${PROJECT_FOLDER}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/ProductController.js
      cp ${PROJECT_FOLDER}/frontend-dojo/ProductController.js ${PROJECT_FOLDER}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/ProductController.js  
      rm ${PROJECT_FOLDER}/frontend-dojo/ProductController.js 
      rm ${PROJECT_FOLDER}/frontend-dojo/ProductController2.js 
      rm ${PROJECT_FOLDER}/frontend-dojo/ProductController3.js
      rm ${PROJECT_FOLDER}/frontend-dojo/ProductController4.js
      rm ${PROJECT_FOLDER}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/AccountController.js
      cp ${PROJECT_FOLDER}/frontend-dojo/AccountController.js ${PROJECT_FOLDER}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/AccountController.js 
      rm ${PROJECT_FOLDER}/frontend-dojo/AccountController.js 
      rm ${PROJECT_FOLDER}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/OrderHistoryController.js
      cp ${PROJECT_FOLDER}/frontend-dojo/OrderHistoryController.js ${PROJECT_FOLDER}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/OrderHistoryController.js 
      rm ${PROJECT_FOLDER}/frontend-dojo/OrderHistoryController.js 
      rm ${PROJECT_FOLDER}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/AccountTypeFormController.js
      cp ${PROJECT_FOLDER}/frontend-dojo/AccountTypeFormController.js ${PROJECT_FOLDER}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/AccountTypeFormController.js 
      rm ${PROJECT_FOLDER}/frontend-dojo/AccountTypeFormController.js 
      rm ${PROJECT_FOLDER}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/AddressController.js
      cp ${PROJECT_FOLDER}/frontend-dojo/AddressController.js ${PROJECT_FOLDER}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/AddressController.js 
      rm ${PROJECT_FOLDER}/frontend-dojo/AddressController.js 
      rm ${PROJECT_FOLDER}/frontend-dojo/CustomerOrderServicesWeb/WebContent/product/product.html
      cp ${PROJECT_FOLDER}/frontend-dojo/product.html ${PROJECT_FOLDER}/frontend-dojo/CustomerOrderServicesWeb/WebContent/product/product.html
      rm ${PROJECT_FOLDER}/frontend-dojo/product.html 
    
      _out Done deploying frontend-dojo
      ROUTE=$(oc get route frontend-dojo -n app-mod-dev --template='{{ .spec.host }}')
      _out Wait until the pod has been started: "oc get pod --watch | grep frontend-dojo"
      
      _out "Open the web app:"
      echo "http://${ROUTE}/CustomerOrderServicesWeb/"
    fi
  fi
}

setup