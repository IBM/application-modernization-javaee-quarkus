#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  echo Prerequisites:
  echo 1. GitHub credentials
  echo 2. ArgoCD crendentals

  _out Configuring Tekton and ArgoCD
  cd ${root_folder}/
  oc new-project app-mod-argocd-dev
  oc project app-mod-argocd-pipelines > /dev/null 2>&1
  if [ $? != 0 ]; then 
      oc new-project app-mod-argocd-pipelines
  fi
  oc apply -f scripts-openshift-tekton/ClusterRole.yaml
  oc create clusterrolebinding routes-and-services-reader \
  --clusterrole=routes-and-services-reader  \
  --serviceaccount=app-mod-argocd-pipelines:pipeline

  oc apply -f scripts-openshift-argocd/tekton-git-ssh-secret.yaml
  oc apply -f scripts-openshift-argocd/serviceaccount.yaml

  oc apply -f scripts-openshift-argocd/argocd-config-map.yaml
  oc apply -f scripts-openshift-argocd/argocd-rbac-config-map.yaml 

  #kubectl create secret -n app-mod-argocd-pipelines generic argocd-env-secret '--from-literal=ARGOCD_AUTH_TOKEN=<token>'

  _out Deploying Tekton tasks
  oc apply -f scripts-openshift-tekton/application/tasks
  oc apply -f scripts-openshift-argocd/tasks

  _out Start service-catalog-quarkus-reactive pipeline
  oc apply -f scripts-openshift-argocd/pipelines/service-catalog-quarkus-reactive.yaml
  oc apply -f scripts-openshift-argocd/pipelineruns/service-catalog-quarkus-reactive.yaml
}

setup

