## Performance and Memory Tests

To compare memory usage and performance, the same Java, JDK and Maven versions are used locally:

```
$java -version
openjdk version "11.0.10" 2021-01-19
OpenJDK Runtime Environment AdoptOpenJDK (build 11.0.10+9)
Eclipse OpenJ9 VM AdoptOpenJDK (build openj9-0.24.0, JRE 11 Mac OS X amd64-64-Bit Compressed References 20210120_897 (JIT enabled, AOT enabled)
OpenJ9   - 345e1b09e
OMR      - 741e94ea8
JCL      - 0a86953833 based on jdk-11.0.10+9)

$ mvn -version
Apache Maven 3.6.3 (cecedd343002696d0abb50b32b541b8a6ba2883f)
Maven home: /usr/local/Cellar/maven/3.6.3_1/libexec
Java version: 15.0.1, vendor: N/A, runtime: /usr/local/Cellar/openjdk/15.0.1/libexec/openjdk.jdk/Contents/Home
Default locale: en_DE, platform encoding: UTF-8
OS name: "mac os x", version: "11.2", arch: "x86_64", family: "mac"
```

Make sure to set JAVA_HOME correctly, for example on MacOS:

code ~/.bash_profile

export JAVA_HOME=$(/usr/libexec/java_home)

source ~/.bash_profile
echo $JAVA_HOME


### Test Case 1: Open Liberty

* Open Liberty application launched locally
* Db2 running in a container
* Synchronous database access via JPA and Db2 driver

```
$ git clone https://github.com/nheidloff/application-modernization-javaee-quarkus.git && cd application-modernization-javaee-quarkus
$ ROOT_FOLDER=$(pwd)
$ sh ${ROOT_FOLDER}/scripts-docker/build-and-run-monolith-db2.sh
$ sh ${ROOT_FOLDER}/scripts-docker/run-database-postgres-catalog.sh
$ sh ${ROOT_FOLDER}/scripts-docker/run-kafka-locally.sh
$ sh ${ROOT_FOLDER}/scripts/run-openliberty-cloud-native-locally.sh
```

curl "http://localhost:9088/CustomerOrderServicesWeb/jaxrs/Product/?categoryId=2"


### Test Case 2: Quarkus - JVM Mode

* Quarkus application launched locally in JVM mode
* Postgres running in a container
* Reactive endpoints and reactive database access

```
$ git clone https://github.com/nheidloff/application-modernization-javaee-quarkus.git && cd application-modernization-javaee-quarkus
$ ROOT_FOLDER=$(pwd)
$ sh ${ROOT_FOLDER}/scripts-docker/build-and-run-monolith-db2.sh
$ sh ${ROOT_FOLDER}/scripts-docker/run-database-postgres-catalog.sh
$ sh ${ROOT_FOLDER}/scripts-docker/run-kafka-locally.sh
$ sh ${ROOT_FOLDER}/scripts/run-quarkus-jvm-locally.sh
```

curl "http://localhost:8082/CustomerOrderServicesWeb/jaxrs/Product/?categoryId=2"


### Test Case 3: Quarkus - Native Mode

* Quarkus application launched locally in native mode
* Postgres running in a container
* Reactive endpoints and reactive database access

```
$ git clone https://github.com/nheidloff/application-modernization-javaee-quarkus.git && cd application-modernization-javaee-quarkus
$ ROOT_FOLDER=$(pwd)
$ sh ${ROOT_FOLDER}/scripts-docker/build-and-run-monolith-db2.sh
$ sh ${ROOT_FOLDER}/scripts-docker/run-database-postgres-catalog.sh
$ sh ${ROOT_FOLDER}/scripts-docker/run-kafka-locally.sh
$ sh ${ROOT_FOLDER}/scripts/run-quarkus-native-locally.sh
```

curl "http://localhost:8082/CustomerOrderServicesWeb/jaxrs/Product/?categoryId=2"
