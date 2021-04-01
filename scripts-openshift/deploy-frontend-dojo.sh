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
  rm -rf ${root_folder}/frontend-dojo/target
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

      cp ${root_folder}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/ProductController.js ${root_folder}/frontend-dojo/ProductController.js 
      rm ${root_folder}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/ProductController.js
      sed "s/jaxrs\/Category/http:\/\/${ROUTE_CATALOG}\/CustomerOrderServicesWeb\/jaxrs\/Category/g" ${root_folder}/frontend-dojo/ProductController.js > ${root_folder}/frontend-dojo/ProductController2.js
      sed "s/jaxrs\/Customer\/OpenOrder\/LineItem/http:\/\/${ROUTE_MONOLITH}\/CustomerOrderServicesWeb\/jaxrs\/Customer\/OpenOrder\/LineItem/g" ${root_folder}/frontend-dojo/ProductController2.js > ${root_folder}/frontend-dojo/ProductController3.js
      sed "s/\/\/this.addToCorsIssue/this.addToCorsIssue/g" ${root_folder}/frontend-dojo/ProductController3.js > ${root_folder}/frontend-dojo/ProductController4.js
      sed "s/this.addToCartNormal/\/\/this.addToCartNormal/g" ${root_folder}/frontend-dojo/ProductController4.js > ${root_folder}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/ProductController.js
      cp ${root_folder}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/AccountController.js ${root_folder}/frontend-dojo/AccountController.js 
      rm ${root_folder}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/AccountController.js
      sed "s/\/CustomerOrderServicesWeb\/jaxrs\/Customer/http:\/\/${ROUTE_MONOLITH}\/CustomerOrderServicesWeb\/jaxrs\/Customer/g" ${root_folder}/frontend-dojo/AccountController.js > ${root_folder}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/AccountController.js
      cp ${root_folder}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/AddressController.js ${root_folder}/frontend-dojo/AddressController.js 
      rm ${root_folder}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/AddressController.js
      sed "s/jaxrs\/Customer\/Address/http:\/\/${ROUTE_MONOLITH}\/CustomerOrderServicesWeb\/jaxrs\/Customer\/Address/g" ${root_folder}/frontend-dojo/AddressController.js > ${root_folder}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/AddressController.js
      cp ${root_folder}/frontend-dojo/CustomerOrderServicesWeb/WebContent/product/product.html ${root_folder}/frontend-dojo/product.html
      rm ${root_folder}/frontend-dojo/CustomerOrderServicesWeb/WebContent/product/product.html
      sed "s/jaxrs\/Product/http:\/\/${ROUTE_CATALOG}\/CustomerOrderServicesWeb\/jaxrs\/Product/g" ${root_folder}/frontend-dojo/product.html > ${root_folder}/frontend-dojo/CustomerOrderServicesWeb/WebContent/product/product.html
      cp ${root_folder}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/OrderHistoryController.js ${root_folder}/frontend-dojo/OrderHistoryController.js 
      rm ${root_folder}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/OrderHistoryController.js
      sed "s/\/CustomerOrderServicesWeb\/jaxrs\/Customer\/Orders/http:\/\/${ROUTE_MONOLITH}\/CustomerOrderServicesWeb\/jaxrs\/Customer\/Orders/g" ${root_folder}/frontend-dojo/OrderHistoryController.js > ${root_folder}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/OrderHistoryController.js
      cp ${root_folder}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/AccountTypeFormController.js ${root_folder}/frontend-dojo/AccountTypeFormController.js 
      rm ${root_folder}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/AccountTypeFormController.js
      sed "s/jaxrs\/Customer\/TypeForm/http:\/\/${ROUTE_MONOLITH}\/CustomerOrderServicesWeb\/jaxrs\/Customer\/TypeForm/g" ${root_folder}/frontend-dojo/AccountTypeFormController.js > ${root_folder}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/AccountTypeFormController.js
      
      oc project app-mod-dev  > /dev/null 2>&1
      if [ $? != 0 ]; then 
          oc new-project app-mod-dev
      fi
      
      cd ${root_folder}/frontend-dojo
      oc new-build --name build-frontend-dojo --binary --strategy=docker
      oc start-build build-frontend-dojo --from-dir=. --follow
      
      oc apply -f deployment/kubernetes.yaml
      oc expose svc/frontend-dojo

      cd ${root_folder}/frontend-dojo
      rm Dockerfile
      cp Dockerfile.temp Dockerfile
      rm Dockerfile.temp
      rm -rf ${root_folder}/frontend-dojo/target

      rm ${root_folder}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/ProductController.js
      cp ${root_folder}/frontend-dojo/ProductController.js ${root_folder}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/ProductController.js  
      rm ${root_folder}/frontend-dojo/ProductController.js 
      rm ${root_folder}/frontend-dojo/ProductController2.js 
      rm ${root_folder}/frontend-dojo/ProductController3.js
      rm ${root_folder}/frontend-dojo/ProductController4.js
      rm ${root_folder}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/AccountController.js
      cp ${root_folder}/frontend-dojo/AccountController.js ${root_folder}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/AccountController.js 
      rm ${root_folder}/frontend-dojo/AccountController.js 
      rm ${root_folder}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/OrderHistoryController.js
      cp ${root_folder}/frontend-dojo/OrderHistoryController.js ${root_folder}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/OrderHistoryController.js 
      rm ${root_folder}/frontend-dojo/OrderHistoryController.js 
      rm ${root_folder}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/AccountTypeFormController.js
      cp ${root_folder}/frontend-dojo/AccountTypeFormController.js ${root_folder}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/AccountTypeFormController.js 
      rm ${root_folder}/frontend-dojo/AccountTypeFormController.js 
      rm ${root_folder}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/AddressController.js
      cp ${root_folder}/frontend-dojo/AddressController.js ${root_folder}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_depot/depot/AddressController.js 
      rm ${root_folder}/frontend-dojo/AddressController.js 
      rm ${root_folder}/frontend-dojo/CustomerOrderServicesWeb/WebContent/product/product.html
      cp ${root_folder}/frontend-dojo/product.html ${root_folder}/frontend-dojo/CustomerOrderServicesWeb/WebContent/product/product.html
      rm ${root_folder}/frontend-dojo/product.html 
    
      _out Done deploying frontend-dojo
      ROUTE=$(oc get route frontend-dojo -n app-mod-dev --template='{{ .spec.host }}')
      _out Wait until the pod has been started: "oc get pod --watch | grep frontend-dojo"
      
      _out "Open the web app:"
      echo "http://${ROUTE}/CustomerOrderServicesWeb/"
    fi
  fi
}

setup