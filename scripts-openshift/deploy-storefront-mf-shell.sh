#!/bin/bash

SCRIPT_FOLDER="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"
PROJECT_FOLDER="$(cd $SCRIPT_FOLDER; cd ..; pwd )"

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  _out Deploying storefront-mf-shell
  
  _out Cleanup
  rm -rf ${PROJECT_FOLDER}/frontend-single-spa/shell/dist
  rm -rf ${PROJECT_FOLDER}/frontend-single-spa/shell/node_modules
  cd ${PROJECT_FOLDER}/frontend-single-spa/shell
  oc delete -f deployment/kubernetes.yaml --ignore-not-found
  oc delete route storefront-mf-shell --ignore-not-found
  oc delete is build-storefront-mf-shell --ignore-not-found
  oc delete bc/build-storefront-mf-shell --ignore-not-found
  
  ROUTE_MONOLITH=$(oc get route monolith-open-liberty-cloud-native -n app-mod-dev --template='{{ .spec.host }}')
  ROUTE_CATALOG=$(oc get route service-catalog-quarkus-reactive -n app-mod-dev --template='{{ .spec.host }}')
  MICROFRONTEND_MESSAGING=$(oc get route storefront-mf-messaging -n app-mod-dev --template='{{ .spec.host }}')
  MICROFRONTEND_NAVIGATOR=$(oc get route storefront-mf-navigator -n app-mod-dev --template='{{ .spec.host }}')
  MICROFRONTEND_ORDER=$(oc get route storefront-mf-order -n app-mod-dev --template='{{ .spec.host }}')
  MICROFRONTEND_CATALOG=$(oc get route storefront-mf-catalog -n app-mod-dev --template='{{ .spec.host }}')
  MICROFRONTEND_ACCOUNT=$(oc get route storefront-mf-account -n app-mod-dev --template='{{ .spec.host }}')
  if [ -z "$ROUTE_CATALOG" ]; then
    _out service-catalog-quarkus-reactive is not available. Run the command: \"sh scripts-openshift/deploy-service-catalog-quarkus-reactive.sh\"
  else 
    if [ -z "$ROUTE_MONOLITH" ]; then
      _out monolith-open-liberty-cloud-native is not available. Run the command: \"sh scripts-openshift/deploy-monolith-open-liberty-cloud-native.sh\"
    else 
      if [ -z "$MICROFRONTEND_NAVIGATOR" ]; then
        _out route storefront-mf-navigator is not available. Run the command: \"sh scripts-openshift/deploy-storefront-mf-navigator.sh\"
      else 
        if [ -z "$MICROFRONTEND_MESSAGING" ]; then
          _out storefront-mf-messaging is not available. Run the command: \"sh scripts-openshift/deploy-storefront-mf-messaging.sh\"
        else 
          if [ -z "$MICROFRONTEND_ORDER" ]; then
            _out storefront-mf-order is not available. Run the command: \"sh scripts-openshift/deploy-storefront-mf-order.sh\"
          else 
            if [ -z "$MICROFRONTEND_CATALOG" ]; then
              _out storefront-mf-catalog is not available. Run the command: \"sh scripts-openshift/deploy-storefront-mf-catalog.sh\"
            else
              if [ -z "$MICROFRONTEND_ACCOUNT" ]; then
                _out storefront-mf-account is not available. Run the command: \"sh scripts-openshift/deploy-storefront-mf-account.sh\"
              else
                cd ${PROJECT_FOLDER}/frontend-single-spa/shell
                cp Dockerfile Dockerfile.temp
                rm Dockerfile
                cp Dockerfile.os4 Dockerfile

                cp ${PROJECT_FOLDER}/frontend-single-spa/shell/src/index.ejs ${PROJECT_FOLDER}/frontend-single-spa/shell/index-org.ejs
                rm ${PROJECT_FOLDER}/frontend-single-spa/shell/src/index.ejs
                sed "s/MICROFRONTEND_MESSAGING/${MICROFRONTEND_MESSAGING}/g" ${PROJECT_FOLDER}/frontend-single-spa/shell/index.ejs > ${PROJECT_FOLDER}/frontend-single-spa/shell/index2.ejs
                sed "s/MICROFRONTEND_ORDER/${MICROFRONTEND_ORDER}/g" ${PROJECT_FOLDER}/frontend-single-spa/shell/index2.ejs > ${PROJECT_FOLDER}/frontend-single-spa/shell/index3.ejs
                sed "s/MICROFRONTEND_CATALOG/${MICROFRONTEND_CATALOG}/g" ${PROJECT_FOLDER}/frontend-single-spa/shell/index3.ejs > ${PROJECT_FOLDER}/frontend-single-spa/shell/index4.ejs
                sed "s/MICROFRONTEND_ACCOUNT/${MICROFRONTEND_ACCOUNT}/g" ${PROJECT_FOLDER}/frontend-single-spa/shell/index4.ejs > ${PROJECT_FOLDER}/frontend-single-spa/shell/index5.ejs
                sed "s/MICROFRONTEND_NAVIGATOR/${MICROFRONTEND_NAVIGATOR}/g" ${PROJECT_FOLDER}/frontend-single-spa/shell/index5.ejs > ${PROJECT_FOLDER}/frontend-single-spa/shell/src/index.ejs
                
                oc project app-mod-dev  > /dev/null 2>&1
                if [ $? != 0 ]; then 
                    oc new-project app-mod-dev
                fi
                
                cd ${PROJECT_FOLDER}/frontend-single-spa/shell
                oc new-build --name build-storefront-mf-shell --binary --strategy=docker
                oc start-build build-storefront-mf-shell --from-dir=. --follow
                
                oc apply -f deployment/kubernetes.yaml
                oc expose svc/storefront-mf-shell

                cd ${PROJECT_FOLDER}/frontend-single-spa/shell
                rm Dockerfile
                cp Dockerfile.temp Dockerfile
                rm Dockerfile.temp

                rm ${PROJECT_FOLDER}/frontend-single-spa/shell/src/index.ejs
                rm ${PROJECT_FOLDER}/frontend-single-spa/shell/index2.ejs
                rm ${PROJECT_FOLDER}/frontend-single-spa/shell/index3.ejs
                rm ${PROJECT_FOLDER}/frontend-single-spa/shell/index4.ejs
                rm ${PROJECT_FOLDER}/frontend-single-spa/shell/index5.ejs
                cp ${PROJECT_FOLDER}/frontend-single-spa/shell/index-org.ejs ${PROJECT_FOLDER}/frontend-single-spa/shell/src/index.ejs
                rm ${PROJECT_FOLDER}/frontend-single-spa/shell/index-org.ejs
                
                _out Done deploying storefront-mf-shell
                ROUTE=$(oc get route storefront-mf-shell -n app-mod-dev --template='{{ .spec.host }}')
                _out Wait until the pod has been started: "oc get pod --watch | grep storefront-mf-shell"
                
                _out "vue-app-mod-shell.js: "
                _out "http://${ROUTE}/vue-app-mod-shell.js"
                _out "Open web application: "
                _out "http://${ROUTE}"
              fi
            fi
          fi
        fi
      fi
    fi
  fi
}

setup