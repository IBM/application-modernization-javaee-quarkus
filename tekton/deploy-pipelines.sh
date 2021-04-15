#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  _out Deploying Tekton tasks and pipelines
  
  oc project app-mod-dev-tekton  > /dev/null 2>&1
  if [ $? != 0 ]; then 
      oc new-project app-mod-dev-tekton
  fi
  
  oc apply -f tekton/tasks
  oc apply -f tekton/pipelines
}

setup