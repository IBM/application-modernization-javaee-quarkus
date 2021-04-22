#!/bin/bash
ROOT_FOLDER=$(cd $(dirname $0); cd ..; pwd)
exec 3>&1

function runScript() {
  oc project app-mod-argocd-dev > /dev/null 2>&1
  if [ $? != 0 ]; then 
      oc new-project app-mod-argocd-dev
  fi
  oc project app-mod-argocd-test > /dev/null 2>&1
  if [ $? != 0 ]; then 
      oc new-project app-mod-argocd-test
  fi
  oc project app-mod-argocd-prod > /dev/null 2>&1
  if [ $? != 0 ]; then 
      oc new-project app-mod-argocd-prod
  fi
  oc project app-mod-argocd-pipelines > /dev/null 2>&1
  if [ $? != 0 ]; then 
      oc new-project app-mod-argocd-pipelines
  fi

  ARGOCD_SECRET=$(oc get secret -n app-mod-argocd-pipelines argocd-env-secret --ignore-not-found --output 'jsonpath={.data.ARGOCD_AUTH_TOKEN}')
  if [ -z "$ARGOCD_SECRET" ]; then
    oc create secret -n app-mod-argocd-pipelines generic argocd-env-secret '--from-literal=ARGOCD_AUTH_TOKEN=${TOKEN_ARGOCD}'
  fi

  rm -f ${ROOT_FOLDER}/scripts-openshift-argocd/argocd-config/tekton-git-ssh-secret.yaml
  sed "s/<base64data>/${GITHUB_SSH_KEY_PRIVATE}/g" ${ROOT_FOLDER}/scripts-openshift-argocd/argocd-config/tekton-git-ssh-secret.yaml.template > ${ROOT_FOLDER}scripts-openshift-argocd/argocd-config/tekton-git-ssh-secret.yaml
  oc apply -f ${ROOT_FOLDER}/scripts-openshift-argocd/argocd-config/tekton-git-ssh-secret.yaml

  oc apply -f ${ROOT_FOLDER}/scripts-openshift-argocd/argocd-config/service-account.yaml
  oc policy add-role-to-user edit system:serviceaccount:app-mod-argocd-pipelines:pipeline -n app-mod-argocd-dev
  oc policy add-role-to-user edit system:serviceaccount:app-mod-argocd-pipelines:pipeline -n app-mod-argocd-test
  oc policy add-role-to-user edit system:serviceaccount:app-mod-argocd-pipelines:pipeline -n app-mod-argocd-prod

  oc apply -f ${ROOT_FOLDER}/scripts-openshift-argocd/argocd-config/argocd-config-map.yaml
  oc apply -f ${ROOT_FOLDER}/scripts-openshift-argocd/argocd-config/argocd-rbac-config-map.yaml 

  oc apply -f ${ROOT_FOLDER}/scripts-openshift-tekton/ClusterRole.yaml
  oc create clusterrolebinding routes-and-services-reader \
  --clusterrole=routes-and-services-reader  \
  --serviceaccount=app-mod-argocd-pipelines:pipeline
  oc apply -f ${ROOT_FOLDER}/scripts-openshift-argocd/argocd-config/cluster-role.yaml
  oc create clusterrolebinding routes-and-services-argocd \
  --clusterrole=routes-and-services-argocd  \
  --serviceaccount=openshift-gitops:argocd-cluster-argocd-application-controller

  oc apply -f ${ROOT_FOLDER}/scripts-openshift-argocd/argocd-config/default-appproject.yaml
  rm -f ${ROOT_FOLDER}/scripts-openshift-argocd/argocd-config/argocd-app-dev.yaml
  sed "s/your-github-config-repo-https-url/${GITHUB_CONFIG_URL}/g" ${ROOT_FOLDER}/scripts-openshift-argocd/argocd-config/argocd-app-dev.yaml.template > ${ROOT_FOLDER}/scripts-openshift-argocd/argocd-config/argocd-app-dev.yaml
  oc apply -f ${ROOT_FOLDER}/scripts-openshift-argocd/argocd-config/argocd-app-dev.yaml
  rm -f ${ROOT_FOLDER}/scripts-openshift-argocd/argocd-config/argocd-app-test.yaml
  sed "s/your-github-config-repo-https-url/${GITHUB_CONFIG_URL}/g" ${ROOT_FOLDER}/scripts-openshift-argocd/argocd-config/argocd-app-test.yaml.template > ${ROOT_FOLDER}/scripts-openshift-argocd/argocd-config/argocd-app-test.yaml
  oc apply -f ${ROOT_FOLDER}/scripts-openshift-argocd/argocd-config/argocd-app-test.yaml
  rm -f ${ROOT_FOLDER}/scripts-openshift-argocd/argocd-config/argocd-app-prod.yaml
  sed "s/your-github-config-repo-https-url/${GITHUB_CONFIG_URL}/g" ${ROOT_FOLDER}/scripts-openshift-argocd/argocd-config/argocd-app-prod.yaml.template > ${ROOT_FOLDER}/scripts-openshift-argocd/argocd-config/argocd-app-prod.yaml
  oc apply -f ${ROOT_FOLDER}/scripts-openshift-argocd/argocd-config/argocd-app-prod.yaml

  # to be done
  #_out Deploying Tekton tasks
  #oc apply -f scripts-openshift-tekton/application/tasks
  #oc apply -f scripts-openshift-argocd/tasks
  #_out Start service-catalog-quarkus-reactive pipeline
  #oc apply -f scripts-openshift-argocd/pipelines/service-catalog-quarkus-reactive.yaml
  #oc apply -f scripts-openshift-argocd/pipelineruns/service-catalog-quarkus-reactive.yaml
}

runScript