#!/bin/bash

function setup() {
    
    PROJECT_NAME=app-mod-dev-tekton
    MAX_ITERATIONS=120
    ITERATION=0
    WAIT_SECONDS=10
    
    oc project $PROJECT_NAME > /dev/null 2>&1
    if [ $? != 0 ]; then 
        oc new-project $PROJECT_NAME
    fi

    SERVICES_UP=1
    while true
    do
	    ((ITERATION++))
        echo Waiting 10 seconds ...
        sleep $WAIT_SECONDS
        echo Checking backend status ...
        ROUTE_MONOLITH=$(oc get route monolith-open-liberty-cloud-native --template='{{ .spec.host }}')
        ROUTE_CATALOG=$(oc get route service-catalog-quarkus-reactive --template='{{ .spec.host }}')

        if [ -z "$ROUTE_CATALOG" ]
        then
            echo service-catalog-quarkus-reactive is NOT available
        else
            if [ -z "$ROUTE_MONOLITH" ]
            then
                echo monolith-open-liberty-cloud-native is NOT available
            else
                echo service-catalog-quarkus-reactive and monolith-open-liberty-cloud-native are available
                break
            fi
        fi  

        if [ "$ITERATION" -ge "$MAX_ITERATIONS" ]; then
		    echo service-catalog-quarkus-reactive and monolith-open-liberty-cloud-native are NOT available
            echo Timeout occured
		    exit 1
	    fi      
    done   
}

setup