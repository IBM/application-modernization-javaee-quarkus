#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function checkPrerequisites() {
  MISSING_TOOLS=""
  git --version &> /dev/null || MISSING_TOOLS="${MISSING_TOOLS} git"
  curl --version &> /dev/null || MISSING_TOOLS="${MISSING_TOOLS} curl"
  which oc &> /dev/null || MISSING_TOOLS="${MISSING_TOOLS} oc"
  if [[ -n "$MISSING_TOOLS" ]]; then
    _out "Some tools (${MISSING_TOOLS# }) could not be found, please install them first"
    exit 1
  else
    oc project app-mod-argocd-pipelines > /dev/null 2>&1
    if [ $? != 0 ]; then 
        oc new-project app-mod-argocd-pipelines
    fi

    TEKTON_SERVICE_PORT=$(oc get service tekton-pipelines-controller -n openshift-pipelines --ignore-not-found --output 'jsonpath={.spec.ports[*].port}')
    if [ -z "$TEKTON_SERVICE_PORT" ]; then
      _out Tekton is not available. 
      _out Install the OpenShift Pipelines operator with defaults.
    else
      ARGOCD_SERVICE_PORT=$(oc get service argocd-cluster-server -n openshift-gitops --ignore-not-found --output 'jsonpath={.spec.ports[*].port}')
      if [ -z "$ARGOCD_SERVICE_PORT" ]; then
        _out ArgoCD is not available. 
        _out Install the OpenShift GitOps operator with defaults.
      else
        if [ -z "$TOKEN_ARGOCD" ]; then
          _out The enviornment variable TOKEN_ARGOCD has not been set.
          ARGOCD_CONSOLE=$(oc get route argocd-cluster-server -n openshift-gitops --template='{{ .spec.host }}')
          ARGOCD_PASSWORD=$(kubectl -n openshift-gitops get secret argocd-cluster-cluster -o 'go-template={{index .data "admin.password"}}' | base64 -d)
          _out 1. Open ArgoCD:
          _out - URL: $ARGOCD_CONSOLE/settings/accounts/tekton
          _out - User: admin
          _out - Password: $ARGOCD_PASSWORD 
          _out 2. Click \'Generate New\' and copy the token to the clipboard
          _out 3. In this shell invoke the command \'export TOKEN_ARGOCD=your_token\'
        else
          ARGOCD_SECRET=$(oc get secret -n app-mod-argocd-pipelines argocd-env-secret --ignore-not-found --output 'jsonpath={.data.ARGOCD_AUTH_TOKEN}')
          if [ -z "$ARGOCD_SECRET" ]; then
            oc create secret -n app-mod-argocd-pipelines generic argocd-env-secret '--from-literal=ARGOCD_AUTH_TOKEN=${TOKEN_ARGOCD}'
          fi
          _out You have all necessary prerequisites installed
        fi
      fi
    fi
  fi
}

checkPrerequisites
