## Deployments

This page describes more deployment options.

* [Deployment to OpenShift on IBM Cloud with Tekton and ArgoCD](#deployment-to-openshift-on-ibm-cloud-with-tekton-and-argocd)
* [Deployment to OpenShift on IBM Cloud with local Scripts](#deployment-to-openshift-on-ibm-cloud-with-local-scripts)
* [Managed Postgres](#managed-postgres)
* [Monolith - WebSphere Liberty](#monolith---websphere-liberty)
* [Separated Frontend - WebSphere Liberty](#separated-frontend---websphere-liberty)
* [Separated Frontend - Open Liberty (EJB)](#separated-frontend---open-liberty-ejb)
* [Strangled Catalog Service with Open Liberty (CDI)](#strangled-catalog-service-with-open-liberty-cdi)
* [Strangled Catalog Service with Quarkus](#strangled-catalog-service-with-quarkus)
* [Micro-Frontend based Web Application](#micro-frontend-based-web-application)
* [Monolith - WebSphere Traditional 9.0](#monolith---websphere-traditional-90)
* [Monolith - WebSphere Traditional 8.5.5](#monolith---websphere-traditional-855)



### Deployment to OpenShift on IBM Cloud with Tekton and ArgoCD

The following scripts deploy the modernized application on Red Hat [OpenShift on IBM Cloud](https://cloud.ibm.com/kubernetes/overview?platformType=openshift). However the same instructions should work for other OpenShift and OCP deployments, for example [CodeReady Containers](https://developers.redhat.com/products/codeready-containers/overview).

First create an [IBM Cloud Account](https://cloud.ibm.com/registration). Then create an OpenShift cluster, for example via the [IBM Cloud Dashboard](https://cloud.ibm.com/kubernetes/catalog/create?platformType=openshift). I've tested classic infrastructure, single zone, OpenShift 4.6.17, b3c.8x32 and 3 worker nodes.

Additionally you need to install ArgoCD and Tekton. The easiest option is to use the '[OpenShift GitOps](https://docs.openshift.com/container-platform/4.7/cicd/gitops/installing-openshift-gitops.html)' operator from the OperatorHub view in the OpenShift Console ([screenshots](deploy-argocd-1.png)). Simply accept all defaults. No local installations are necessary.

The following scripts install the infrastructure components and the catalog service.

```
$ git clone https://github.com/nheidloff/application-modernization-javaee-quarkus.git && cd application-modernization-javaee-quarkus
$ ROOT_FOLDER=$(pwd)
$ oc login ...
$ sh ${ROOT_FOLDER}/scripts-openshift-argocd/check-prerequisites.sh
$ sh ${ROOT_FOLDER}/scripts-openshift-tekton/deploy-db2.sh
$ sh ${ROOT_FOLDER}/scripts-openshift-tekton/deploy-kafka.sh
$ sh ${ROOT_FOLDER}/scripts-openshift-tekton/deploy-postgres.sh
$ sh ${ROOT_FOLDER}/scripts-openshift-argocd/configure-argocd.sh
$ sh ${ROOT_FOLDER}/scripts-openshift-argocd/deploy-service-catalog-quarkus-reactive.sh
$ sh ${ROOT_FOLDER}/scripts-openshift-argocd/show-urls.sh
```



### Deployment to OpenShift on IBM Cloud with local Scripts

The following scripts deploy the modernized application on Red Hat [OpenShift on IBM Cloud](https://cloud.ibm.com/kubernetes/overview?platformType=openshift). However the same instructions should work for other OpenShift and OCP deployments, for example [CodeReady Containers](https://developers.redhat.com/products/codeready-containers/overview).

First create an [IBM Cloud Account](https://cloud.ibm.com/registration). Then create an OpenShift cluster, for example via the [IBM Cloud Dashboard](https://cloud.ibm.com/kubernetes/catalog/create?platformType=openshift). I've tested classic infrastructure, single zone, OpenShift 4.6.17, b3c.8x32 and 3 worker nodes.

Execute the following commands:

```
$ git clone https://github.com/nheidloff/application-modernization-javaee-quarkus.git && cd application-modernization-javaee-quarkus
$ ROOT_FOLDER=$(pwd)
$ oc login ...
$ sh ${ROOT_FOLDER}/scripts-openshift/check-prerequisites.sh
$ sh ${ROOT_FOLDER}/scripts-openshift/deploy-db2.sh
$ sh ${ROOT_FOLDER}/scripts-openshift/deploy-kafka.sh
$ sh ${ROOT_FOLDER}/scripts-openshift/deploy-postgres.sh
```

Wait 5 - 10 minutes for the infrastructure components to be started.

```
$ sh ${ROOT_FOLDER}/scripts-openshift/deploy-monolith-open-liberty-cloud-native.sh
$ sh ${ROOT_FOLDER}/scripts-openshift/deploy-service-catalog-quarkus-reactive.sh
```

Wait 5 minutes for the backend services to be started.

```
$ sh ${ROOT_FOLDER}/scripts-openshift/deploy-frontend-dojo.sh
$ sh ${ROOT_FOLDER}/scripts-openshift/show-urls.sh
```

```
$ sh ${ROOT_FOLDER}/scripts-openshift/deploy-storefront-mf-messaging.sh
$ sh ${ROOT_FOLDER}/scripts-openshift/deploy-storefront-mf-account.sh
$ sh ${ROOT_FOLDER}/scripts-openshift/deploy-storefront-mf-catalog.sh
$ sh ${ROOT_FOLDER}/scripts-openshift/deploy-storefront-mf-navigator.sh
$ sh ${ROOT_FOLDER}/scripts-openshift/deploy-storefront-mf-order.sh
```

Wait 5 minutes for the micro frontends to be started.

```
$ sh ${ROOT_FOLDER}/scripts-openshift/deploy-storefront-mf-shell.sh
$ sh ${ROOT_FOLDER}/scripts-openshift/show-urls.sh
```



### Managed Postgres

As alternative to Postgres running in an OpenShift cluster the managed Postgres service [IBM Cloud Databases for PostgreSQL](https://cloud.ibm.com/docs/databases-for-postgresql?topic=databases-for-postgresql-getting-started) can be used.

Create a new instance on the IBM Cloud and get the configuration/connection data:

* Username
* Password
* Hostname
* TLS certificate

Define username, password and hostname in [kubernetes.yaml](../service-catalog-quarkus-synch/deployment/kubernetes.yaml). Download the certificate and copy it to service-catalog-quarkus-synch/ibm-cloud-postgres-cert.

Run the scripts above [Deployment to OpenShift on IBM Cloud with local Scripts](#deployment-to-openshift-on-ibm-cloud-with-local-scripts). After this run this command:

```
$ sh ${ROOT_FOLDER}/scripts-openshift/deploy-service-catalog-quarkus-synch.sh
```


### Monolith - WebSphere Liberty

```
$ git clone https://github.com/nheidloff/application-modernization-javaee-quarkus.git && cd application-modernization-javaee-quarkus
$ ROOT_FOLDER=$(pwd)
$ sh ${ROOT_FOLDER}/scripts-docker/build-and-run-monolith-db2.sh
$ sh ${ROOT_FOLDER}/scripts-docker/build-and-run-monolith-app.sh
```

Open http://localhost/CustomerOrderServicesWeb



### Separated Frontend - WebSphere Liberty

```
$ git clone https://github.com/nheidloff/application-modernization-javaee-quarkus.git && cd application-modernization-javaee-quarkus
$ ROOT_FOLDER=$(pwd)
$ sh ${ROOT_FOLDER}/scripts-docker/build-and-run-monolith-db2.sh
$ sh ${ROOT_FOLDER}/scripts-docker/build-and-run-splitted-frontend.sh
```

Open http://localhost/CustomerOrderServicesWeb



### Separated Frontend - Open Liberty (EJB)

```
$ git clone https://github.com/nheidloff/application-modernization-javaee-quarkus.git && cd application-modernization-javaee-quarkus
$ ROOT_FOLDER=$(pwd)
$ sh ${ROOT_FOLDER}/scripts-docker/build-and-run-monolith-db2.sh
$ sh ${ROOT_FOLDER}/scripts-docker/build-and-run-splitted-frontend-open.sh
```

Open http://localhost/CustomerOrderServicesWeb



### Strangled Catalog Service with Open Liberty (CDI)

```
$ git clone https://github.com/nheidloff/application-modernization-javaee-quarkus.git && cd application-modernization-javaee-quarkus
$ ROOT_FOLDER=$(pwd)
$ sh ${ROOT_FOLDER}/scripts-docker/build-and-run-monolith-db2.sh
$ sh ${ROOT_FOLDER}/scripts-docker/run-database-postgres-catalog.sh
$ sh ${ROOT_FOLDER}/scripts-docker/run-kafka.sh
$ sh ${ROOT_FOLDER}/scripts-docker/build-and-run-catalog.sh
```

Open http://localhost/CustomerOrderServicesWeb

Add the item "Return of the Jedi" to the shopping cart and update the price.


*Local Development - Catalog*

Change the Postgres and Kafka URLs in application.properties. Plus change KAFKA_ADVERTISED_LISTENERS in docker-compose-kafka.yml.

```
$ cd catalog
$ mvn quarkus:dev
```

*Local Development - Open Liberty (CDI)*

Change the Kafka URL in microprofile-config.properties. Change database host and driver location in server.xml.

```
$ cd monolith-open-liberty-cloud-native
$ mvn liberty:dev
```



### Strangled Catalog Service with Quarkus

```
$ sh ${ROOT_FOLDER}/scripts-docker/build-and-run-monolith-db2.sh
$ sh ${ROOT_FOLDER}/scripts-docker/run-database-postgres-catalog.sh
$ sh ${ROOT_FOLDER}/scripts-docker/run-kafka.sh
$ sh ${ROOT_FOLDER}/scripts-docker/build-and-run-all-quarkus.sh
```

Open http://localhost/CustomerOrderServicesWeb

Add the item "Return of the Jedi" to the shopping cart and update the price.



### Micro-Frontend based Web Application

In addition to the previous commands run this command:

```
$ sh ${ROOT_FOLDER}/scripts-docker/build-and-run-single-spa.sh
```

Open http://localhost:8080



### Monolith - WebSphere Traditional 9.0

The following scripts launch the application in a container. However the application doesn't connect to the database yet.

```
$ sh scripts/install-dojo.sh
$ sh scripts/install-was-dependencies.sh
$ sh scripts-docker/build-and-run-monolith-app-was90.sh
```

Open https://localhost:9443/CustomerOrderServicesWeb/ (user: skywalker, password: force)

Open https://localhost:9043/ibm/console/login.do?action=secure (user: wsadmin, password: passw0rd)



### Monolith - WebSphere Traditional 8.5.5

The original version runs on bare metal (or in a virtual machine). Check the original [documentation](monolith-websphere-855/README.md) for setup instructions.

To simplify the setup, a container is used instead. Since the code in 'monolith-websphere-855' and 'monolith-websphere-90' is identical, similar commands as above can be executed:

```
$ sh scripts/install-dojo.sh
$ sh scripts/install-was-dependencies.sh
$ sh scripts-docker/build-and-run-monolith-app-was855.sh
```

Open https://localhost:9443/CustomerOrderServicesWeb/ (user: skywalker, password: force)

Open https://localhost:9043/ibm/console/login.do?action=secure (user: wsadmin, password: passw0rd)