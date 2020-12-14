## Application Modernization Sample - From Java EE (2008) to Quarkus (2021)

This is work in progress ...

Application modernization is done in multiple steps. This sample demonstrates how to modernize 12+ years old Java EE applications with cloud-native technologies like Quarkus and Open Liberty.

1. Monolith - WebSphere Traditional 8.5.5
    - Starting point: Java EE app from 2008 running in VM or bare metal
    - Db2 is used as data store
2. Monolith - WebSphere Traditional 9 in container
    - Application converted with Transformation Advisor
3. Monolith - WebSphere Liberty (latest)
    - Application converted with Transformation Advisor
    - Same project structure and EJBs
4. Separated Frontend - WebSphere Liberty (latest)
    - Dojo frontend in separate container
    - Backend and frontend connected via proxy
5. Separated Frontend - Open Liberty (latest)
    - Still same project structure and EJBs
6. Strangled Catalog Service with Open Liberty
    - Strangled catalog service
        - Developed with Quarkus
        - Run in JVM
        - Postgres is used as data store
        - Kafka events are sent when prices change
    - Remaining Open Liberty monolith
        - Modern project structure and CDI
        - Receives Kafka events when prices change
7. Strangled Catalog Service with Quarkus (synch)
    - Strangled Quarkus catalog service 
    - Remaining Quarkus monolith
        - Synchronous
        - Native
8. To be done: Micro Frontends
9. To be done: Much more - API management, reactive, security, ...

Screenshot of storefront application:

<kbd><img src="documentation/storefront-shop.png" /></kbd>


### Monolith - WebSphere Liberty

```
$ sh scripts-docker/build-and-run-monolith-db2.sh
$ sh scripts-docker/build-and-run-monolith-app.sh
```

Open http://localhost/CustomerOrderServicesWeb


### Separated Frontend - WebSphere Liberty

```
$ sh scripts-docker/build-and-run-monolith-db2.sh
$ sh scripts-docker/build-and-run-splitted-frontend.sh
```

Open http://localhost/CustomerOrderServicesWeb


### Separated Frontend - Open Liberty (EJB)

```
$ sh scripts-docker/build-and-run-monolith-db2.sh
$ sh scripts-docker/build-and-run-splitted-frontend-open.sh
```

Open http://localhost/CustomerOrderServicesWeb


### Strangled Catalog Service with Open Liberty (CDI)

```
$ sh scripts-docker/build-and-run-monolith-db2.sh
$ sh scripts-docker/run-database-postgres-catalog.sh
$ sh scripts-docker/run-kafka.sh
$ sh scripts-docker/build-and-run-catalog.sh
```

Note: For some reason sometimes the messages don't arrive. In that case run this command:

```
$ sh scripts-docker/build-ol-native-backend-and-run-catalog.sh
```

Open http://localhost/CustomerOrderServicesWeb

Open http://localhost/explorer

Invoke these endpoints and check the logs:

```
$ curl http://localhost/CustomerOrderServicesWeb/jaxrs/Category
$ curl http://localhost/CustomerOrderServicesWeb/jaxrs/Customer
$ curl -X PUT "http://localhost/CustomerOrderServicesWeb/jaxrs/Product/1" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"id\":1,\"categories\":[{\"id\":0,\"name\":\"string\",\"subCategories\":[null]}],\"description\":\"string\",\"image\":\"string\",\"name\":\"string\",\"price\":30}"
```

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
$ sh scripts-docker/build-and-run-monolith-db2.sh
$ sh scripts-docker/run-database-postgres-catalog.sh
$ sh scripts-docker/run-kafka.sh
$ sh scripts-docker/build-and-run-all-quarkus.sh
```

Open http://localhost/CustomerOrderServicesWeb

Invoke these endpoints and check the logs:

```
$ curl http://localhost/CustomerOrderServicesWeb/jaxrs/Category
$ curl http://localhost/CustomerOrderServicesWeb/jaxrs/Customer
$ curl -X PUT "http://localhost/CustomerOrderServicesWeb/jaxrs/Product/1" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"id\":1,\"categories\":[{\"id\":0,\"name\":\"string\",\"subCategories\":[null]}],\"description\":\"string\",\"image\":\"string\",\"name\":\"string\",\"price\":30}"
```


### Monolith - WebSphere Traditional 9.0

The following scripts launch the application in a container. However the Java code is not built yet and the application doesn't connect to the database yet.

```
$ sh scripts/install-dojo.sh
$ sh scripts/install-dependencies.sh
$ sh scripts-docker/build-and-run-monolith-app-was90.sh
```

Open https://localhost:9443/CustomerOrderServicesWeb/ (user: skywalker, password: force)

Open https://localhost:9043/ibm/console/login.do?action=secure (user: wsadmin, password: passw0rd)


### Monolith - WebSphere Traditional 8.5.5

The original version runs on bare metal (or in a virtual machine). Check the [documentation](monolith-websphere-855/README.md) for setup instructions.
