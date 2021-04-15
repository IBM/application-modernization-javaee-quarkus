#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  _out Deploying Db2
  
  oc project db2 > /dev/null 2>&1
  if [ $? != 0 ]; then 
      oc new-project db2
  fi

  oc adm policy add-scc-to-user anyuid -z db2
  oc adm policy add-scc-to-user anyuid -z default
  oc create sa mysvcacct
  oc adm policy add-scc-to-user anyuid -z mysvcacct -n db2
  oc adm policy add-scc-to-user privileged -z default -n db2
  oc adm policy add-scc-to-user privileged -z mysvcacct -n db2

  oc apply -f tekton/db2/tasks
  oc apply -f tekton/db2/pipelines
  oc apply -f tekton/db2/pipelineruns
}

setup