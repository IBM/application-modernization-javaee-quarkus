## Application Modernization Sample - From Java EE (2008) to Quarkus (2021)

This is work in progress ...


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
