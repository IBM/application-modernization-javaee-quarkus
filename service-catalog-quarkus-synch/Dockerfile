FROM adoptopenjdk/maven-openjdk11 as BUILD
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
WORKDIR /usr/src/app
RUN mvn clean package

FROM adoptopenjdk/openjdk11-openj9:ubi-minimal
ENV JAVA_OPTIONS="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENV AB_ENABLED=jmx_exporter
RUN mkdir /opt/shareclasses
RUN chmod a+rwx -R /opt/shareclasses
RUN mkdir /opt/app

COPY --from=BUILD --chown=1001 /usr/src/app/target/quarkus-app/lib/ /deployments/lib/
COPY --from=BUILD --chown=1001 /usr/src/app/target/quarkus-app/*.jar /deployments/
COPY --from=BUILD --chown=1001 /usr/src/app/target/quarkus-app/app/ /deployments/app/
COPY --from=BUILD --chown=1001 /usr/src/app/target/quarkus-app/quarkus/ /deployments/quarkus/

CMD ["java", "-Xmx128m", "-XX:+IdleTuningGcOnIdle", "-Xtune:virtualized", "-Xscmx128m", "-Xscmaxaot100m", "-Xshareclasses:cacheDir=/opt/shareclasses", "-jar", "/deployments/quarkus-run.jar"]

