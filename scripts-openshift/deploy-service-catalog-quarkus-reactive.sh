#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  _out Deploying service-catalog-quarkus-reactive
  
  _out Cleanup
  cd ${root_folder}/service-catalog-quarkus-reactive
  #oc delete -f deployment/os4-kubernetes.yaml --ignore-not-found
  #oc delete route service-catalog-quarkus-reactive --ignore-not-found
  #oc delete is service-catalog-quarkus-reactive --ignore-not-found
  
  cd ${root_folder}/service-catalog-quarkus-reactive/src/main/resources
  rm application.properties
  cp application-openshift.properties application.properties

  oc new-project app-mod-dev
  cd ${root_folder}/service-catalog-quarkus-reactive
  oc new-build --name build-service-catalog-quarkus-reactive --binary --strategy docker
  oc start-build build-service-catalog-quarkus-reactive --from-dir=.
  
  sed -e "s+service-catalog-quarkus-reactive:latest+image-registry.openshift-image-registry.svc:5000/cloud-native-starter/build-service-catalog-quarkus-reactive:latest+g" \
      -e "s+        imagePullPolicy: Never+#        imagePullPolicy: Never+g" \
      deployment/kubernetes.yaml > deployment/os4-kubernetes.yaml

  oc apply -f deployment/kubernetes.yaml
  oc expose svc/service-catalog-quarkus-reactive

  cd ${root_folder}/service-catalog-quarkus-reactive/src/main/resources
  rm application.properties
  cp application-docker.properties application.properties

  _out Done deploying service-catalog-quarkus-reactive
  ROUTE=$(oc get route service-catalog-quarkus-reactive --template='{{ .spec.host }}')
  _out Wait until the pod has been started: \"kubectl get pod --watch | grep service-catalog-quarkus-reactive\"
  
  _out "Invoke the endpoints:"
  _out "--- curl http://localhost/CustomerOrderServicesWeb/jaxrs/Category"
  _out "--- curl http://localhost/CustomerOrderServicesWeb/jaxrs/Product/?categoryId=2"
  _out "--- curl http://localhost/CustomerOrderServicesWeb/jaxrs/Customer/Orders"
  _out "--- curl http://localhost/CustomerOrderServicesWeb/jaxrs/Customer/TypeForm"
  CREATE_NEW="http://localhost/CustomerOrderServicesWeb/jaxrs/Product/1 -H 'accept: application/json' -H 'Content-Type: application/json' -d '{\"id\":1, \"price\":50}'"
  _out "--- curl -X PUT ${CREATE_NEW}"
}

setup