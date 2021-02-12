## Performance and Memory Tests


### Test Case 1: Quarkus - JVM Mode - OpenJ9 - Reactive

* Quarkus (directory: [service-catalog-quarkus-reactive](https://github.com/nheidloff/application-modernization-javaee-quarkus/tree/master/service-catalog-quarkus-reactive))
* Image: adoptopenjdk/openjdk11-openj9:ubi-minimal - [Dockerfile](../service-catalog-quarkus-reactive/Dockerfile)
* Reactive Postgres ([example](https://github.com/nheidloff/application-modernization-javaee-quarkus/blob/master/service-catalog-quarkus-reactive/src/main/java/com/ibm/catalog/ProductResource.java#L46))
* Postgres running in a container

```
$ git clone https://github.com/nheidloff/application-modernization-javaee-quarkus.git && cd application-modernization-javaee-quarkus
$ ROOT_FOLDER=$(pwd)
$ sh ${ROOT_FOLDER}/scripts-docker/build-and-run.sh
```

```
curl "http://localhost/CustomerOrderServicesWeb/jaxrs/Product/?categoryId=2"
```

```
docker exec storefront-catalog-reactive cat /sys/fs/cgroup/memory/memory.stat | grep rss
```

Results (30000 invocations - see [jmeter.jmx](jmeter.jmx)):
* 0:18 mins
* 102 MB RSS


### Test Case 2: Quarkus - JVM Mode - Hotspot - Reactive

* Quarkus (directory: [service-catalog-quarkus-reactive](https://github.com/nheidloff/application-modernization-javaee-quarkus/tree/master/service-catalog-quarkus-reactive))
* Image: fabric8/java-alpine-openjdk11-jre - [Dockerfile](../service-catalog-quarkus-reactive/Dockerfile.hotspot)
* Reactive Postgres ([example](https://github.com/nheidloff/application-modernization-javaee-quarkus/blob/master/service-catalog-quarkus-reactive/src/main/java/com/ibm/catalog/ProductResource.java#L46))
* Postgres running in a container

```
$ git clone https://github.com/nheidloff/application-modernization-javaee-quarkus.git && cd application-modernization-javaee-quarkus
$ ROOT_FOLDER=$(pwd)
$ sh ${ROOT_FOLDER}/scripts-docker/build-and-run-hotspot.sh
```

```
curl "http://localhost/CustomerOrderServicesWeb/jaxrs/Product/?categoryId=2"
```

```
docker exec storefront-catalog-reactive cat /sys/fs/cgroup/memory/memory.stat | grep rss
docker exec storefront-catalog-reactive ps -o rss,vsz 1 
```

Results (30000 invocations - see [jmeter.jmx](jmeter.jmx)):
* 0:18 mins
* 285 MB RSS


### Test Case 3: Open Liberty - OpenJ9 - Synchronous 

* Open Liberty (directory: [monolith-open-liberty-cloud-native](https://github.com/nheidloff/application-modernization-javaee-quarkus/tree/master/monolith-open-liberty-cloud-native))
* Image: open-liberty:kernel-java11-openj9-ubi - [Dockerfile](../monolith-open-liberty-cloud-native/Dockerfile.multistage)
* JPA - synchronous ([example](https://github.com/nheidloff/application-modernization-javaee-quarkus/blob/master/monolith-open-liberty-cloud-native/src/main/java/org/pwte/example/service/ProductSearchServiceImpl.java#L30))
* Db2 running in a container

```
$ git clone https://github.com/nheidloff/application-modernization-javaee-quarkus.git && cd application-modernization-javaee-quarkus
$ ROOT_FOLDER=$(pwd)
$ sh ${ROOT_FOLDER}/scripts-docker/build-and-run-monolith-db2.sh
$ sh ${ROOT_FOLDER}/scripts-docker/run-database-postgres-catalog.sh
$ sh ${ROOT_FOLDER}/scripts-docker/run-kafka.sh
$ sh ${ROOT_FOLDER}/scripts-docker/build-and-run-catalog.sh
```

```
curl "http://localhost:9088/CustomerOrderServicesWeb/jaxrs/Product/?categoryId=2"
```

```
docker exec storefront-backend-open-native cat /sys/fs/cgroup/memory/memory.stat | grep rss
```

Results (30000 invocations - see [jmeter.jmx](jmeter.jmx)):
*  0:38 mins
*  205 MB RSS

