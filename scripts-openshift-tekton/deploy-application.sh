#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  _out Deploying Tekton tasks and pipelines
  # check tekton
  
  oc project app-mod-tekton-dev > /dev/null 2>&1
  if [ $? != 0 ]; then 
      oc new-project app-mod-tekton-dev
  fi

  oc apply -f scripts-openshift-tekton/ClusterRole.yaml
  oc create clusterrolebinding routes-and-services-reader \
  --clusterrole=routes-and-services-reader  \
  --serviceaccount=app-mod-tekton-dev:pipeline

  oc apply -f scripts-openshift-tekton/application/tasks
  oc apply -f scripts-openshift-tekton/application/pipelines
  oc apply -f scripts-openshift-tekton/application/pipelineruns
}

setup

