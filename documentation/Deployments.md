## Deployments

This page describes how to deploy specific intermediate versions of the sample and how to run services locally.


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