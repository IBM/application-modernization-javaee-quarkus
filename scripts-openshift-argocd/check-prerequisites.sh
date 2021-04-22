#!/bin/bash
ROOT_FOLDER=$(cd $(dirname $0); cd ..; pwd)
exec 3>&1

function runScript() {
  echo You need the following prerequisites:
  echo 1. OpenShift
  echo 2. CLIs: oc, git, curl, sed, ssh-keygen
  echo 3. OpenShift Pipelines operator
  echo 4. OpenShift GitOps operator
  echo 5. ArgoCD Tekton token in environment variable TOKEN_ARGOCD
  echo 6. GitHub ssh private key in environment variable GITHUB_SSH_KEY_PRIVATE
  MISSING_TOOLS=""
  which sed &> /dev/null || MISSING_TOOLS="${MISSING_TOOLS} sed"
  which ssh-keygen &> /dev/null || MISSING_TOOLS="${MISSING_TOOLS} ssh-keygen"
  git --version &> /dev/null || MISSING_TOOLS="${MISSING_TOOLS} git"
  curl --version &> /dev/null || MISSING_TOOLS="${MISSING_TOOLS} curl"
  which oc &> /dev/null || MISSING_TOOLS="${MISSING_TOOLS} oc"
  if [[ -n "$MISSING_TOOLS" ]]; then
    echo "ERROR: Some tools (${MISSING_TOOLS# }) could not be found. Please install them first"
    exit 1
  else
    TEKTON_SERVICE_PORT=$(oc get service tekton-pipelines-controller -n openshift-pipelines --ignore-not-found --output 'jsonpath={.spec.ports[*].port}')
    if [ -z "$TEKTON_SERVICE_PORT" ]; then
      echo ERROR: Tekton is not available
      echo Install the OpenShift Pipelines operator with defaults
      echo See https://docs.openshift.com/container-platform/4.6/pipelines/installing-pipelines.html
    else
      ARGOCD_SERVICE_PORT=$(oc get service argocd-cluster-server -n openshift-gitops --ignore-not-found --output 'jsonpath={.spec.ports[*].port}')
      if [ -z "$ARGOCD_SERVICE_PORT" ]; then
        echo ERROR: ArgoCD is not available
        echo Install the OpenShift GitOps operator with defaults
        echo See https://docs.openshift.com/container-platform/4.7/cicd/gitops/installing-openshift-gitops.html
      else
        if [ -z "$TOKEN_ARGOCD" ]; then
          echo ERROR: The enviornment variable TOKEN_ARGOCD has not been set
          ARGOCD_CONSOLE=$(oc get route argocd-cluster-server -n openshift-gitops --template='{{ .spec.host }}')
          ARGOCD_PASSWORD=$(kubectl -n openshift-gitops get secret argocd-cluster-cluster -o 'go-template={{index .data "admin.password"}}' | base64 -d)
          echo 1. Open ArgoCD:
          echo - URL: $ARGOCD_CONSOLE/settings/accounts/tekton
          echo - User: admin
          echo - Password: $ARGOCD_PASSWORD 
          echo 2. Click \'Generate New\' and copy the token to the clipboard
          echo 3. In this shell invoke the command: \'export TOKEN_ARGOCD=your_token\'
        else
          if [ -z "$GITHUB_SSH_KEY_PRIVATE" ]; then
            echo ERROR: The enviornment variable GITHUB_SSH_KEY_PRIVATE has not been set
            echo 1. Invoke the command: \'ssh-keygen -t rsa -b 4096 -C \"tekton@tekton.dev\"\'
            echo 2. Enter the file name, for example \'/Users/niklasheidloff/.ssh/tekton\'
            echo 3. Define no password
            echo 4. Invoke the command: \'cat /Users/niklasheidloff/.ssh/tekton \| base64\' and copy the key to the clipboard
            echo 5. In this shell invoke the command \'export GITHUB_SSH_KEY_PRIVATE=your_private_key\'
            echo 6. Get the public key via a command like this: \'cat /Users/niklasheidloff/.ssh/tekton.pub\' and copy the key to the clipboard
            echo 7. Open your GitHub settings: https://github.com/settings/keys
            echo 8. Create a new SSH key and paste it. Call the entry 'tekton'
          else
            echo SUCCESS: You have all necessary prerequisites installed
          fi
        fi
      fi
    fi
  fi
}

runScript