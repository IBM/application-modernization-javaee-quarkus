#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  _out Installing Db2
  
  oc project db2  > /dev/null 2>&1
  if [ $? != 0 ]; then 
      oc new-project db2
  fi
    
  oc adm policy add-scc-to-user anyuid -z db2
  oc adm policy add-scc-to-user anyuid -z default

  oc create sa mysvcacct
  oc adm policy add-scc-to-user anyuid -z mysvcacct -n db2
  oc adm policy add-scc-to-user privileged -z default -n db2
  oc adm policy add-scc-to-user privileged -z mysvcacct -n db2
  oc apply -f ${root_folder}/db2/deployment/db2-dc.yaml -n db2 
  oc apply -f ${root_folder}/db2/deployment/db2-service.yaml -n db2 

  oc expose svc/storefront-db2 --port=50000
   
  _out Done installing Db2
  _out Wait until the pods have been started
  _out Run this command \(potentially multiple times\): \"oc wait db2/storefront-db2 --for=condition=Ready --timeout=300s -n db2\"
  _out After this run \"sh scripts-openshift/show-urls.sh\" to get the Db2 configuration
}

setup

