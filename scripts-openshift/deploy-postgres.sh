#!/bin/bash

SCRIPT_FOLDER="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"
PROJECT_FOLDER="$(cd $SCRIPT_FOLDER; cd ..; pwd )"

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  _out Deploying postgres

  oc new-project postgres
  oc project postgres
  cd ${PROJECT_FOLDER}/scripts-openshift
  oc new-build --name build-postgres --binary --strategy docker
  oc start-build build-postgres --from-dir=.

  oc apply -f ./postgres.yaml
  oc expose svc/postgres
  
  route=$(oc get route postgres --template='{{ .spec.host }}')  
  _out Done deploying postgress
  _out Wait until the pod has been started: "kubectl get pod --watch | grep postgres"
}

setup