#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  ARGOCD_SERVICE_PORT=$(oc get service argocd-cluster-server -n openshift-gitops --ignore-not-found --output 'jsonpath={.spec.ports[*].port}')
  if [ -z "$ARGOCD_SERVICE_PORT" ]; then
    _out ArgoCD is not available. 
    _out Install the OpenShift GitOps operator (with defaults).
  else
    echo Prerequisites:
    echo 0. gitops op (incl tekton)
    echo 1. GitHub credentials
    echo 2. ArgoCD crendentals

    _out Configuring Tekton and ArgoCD
    cd ${root_folder}/
    oc new-project app-mod-argocd-dev
    oc new-project app-mod-argocd-test
    oc new-project app-mod-argocd-prod
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

    oc policy add-role-to-user edit system:serviceaccount:app-mod-argocd-pipelines:pipeline -n app-mod-argocd-dev
    oc policy add-role-to-user edit system:serviceaccount:app-mod-argocd-pipelines:pipeline -n app-mod-argocd-test
    oc policy add-role-to-user edit system:serviceaccount:app-mod-argocd-pipelines:pipeline -n app-mod-argocd-prod

    oc apply -f scripts-openshift-argocd/argocd-config-map.yaml
    oc apply -f scripts-openshift-argocd/argocd-rbac-config-map.yaml 

    oc apply -f scripts-openshift-argocd/argocd-app-dev.yaml

    oc apply -f scripts-openshift-argocd/ClusterRole.yaml
    oc create clusterrolebinding routes-and-services-reader-argocd \
    --clusterrole=routes-and-services-reader-argocd  \
    --serviceaccount=openshift-gitops:argocd-cluster-argocd-application-controller


    kubectl create secret -n app-mod-argocd-pipelines generic argocd-env-secret '--from-literal=ARGOCD_AUTH_TOKEN=${ARGOCDTOKEN}'

    _out Deploying Tekton tasks
    oc apply -f scripts-openshift-tekton/application/tasks
    oc apply -f scripts-openshift-argocd/tasks

    _out Start service-catalog-quarkus-reactive pipeline
    oc apply -f scripts-openshift-argocd/pipelines/service-catalog-quarkus-reactive.yaml
    oc apply -f scripts-openshift-argocd/pipelineruns/service-catalog-quarkus-reactive.yaml
  fi
}

setup

