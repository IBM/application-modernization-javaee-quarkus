#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  _out Running Tekton pipelines
  
  oc project app-mod-dev-tekton
  
  oc apply -f tekton/pipelineruns
}

setup