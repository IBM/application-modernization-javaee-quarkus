#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  _out Deploying postgres

  oc new-project postgres
  cd ${root_folder}/os-scripts
  oc new-build --name build-postgres --binary --strategy docker
  oc start-build build-postgres --from-dir=.

  sed -e "s+POSTGRES_IMAGE+image-registry.openshift-image-registry.svc:5000/postgres/build-postgres:latest+g" \
      -e "s+  type: NodePort+\#  type: NodePort+g" \
      -e "s+        imagePullPolicy: Never+#        imagePullPolicy: Never+g" \
      ./postgres-template.yaml > ./postgres-oc.yaml

  oc apply -f ./postgres-oc.yaml
  oc expose svc/postgres
  
  route=$(oc get route postgres --template='{{ .spec.host }}')  
  _out Done deploying postgress
  _out Wait until the pod has been started: "kubectl get pod --watch | grep postgres"
}

setup