#!/bin/bash
ROOT_FOLDER=$(cd $(dirname $0); cd ..; pwd)
exec 3>&1

function runScript() {
  echo Deploy service-catalog-quarkus-reactive
  oc apply -f ${ROOT_FOLDER}/scripts-openshift-argocd/pipelineruns/service-catalog-quarkus-reactive-argocd-pr.yaml
}

runScript