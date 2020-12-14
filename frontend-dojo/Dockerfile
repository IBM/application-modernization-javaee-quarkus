FROM ibmcom/websphere-liberty:20.0.0.12-kernel-java8-openj9-ubi
USER root
COPY ./liberty/server.xml /config
COPY ./liberty/server.env /config
COPY ./liberty/jvm.options /config

ARG SSL=false
ARG MP_MONITORING=false
ARG HTTP_ENDPOINT=false

COPY ./CustomerOrderServicesApp/target/CustomerOrderServicesApp-0.1.0-SNAPSHOT.ear /config/apps/CustomerOrderServicesApp-0.1.0-SNAPSHOT.ear
COPY ./resources/ /opt/ibm/wlp/usr/shared/resources/
RUN chown -R 1001.0 /config /opt/ibm/wlp/usr/servers/defaultServer /opt/ibm/wlp/usr/shared/resources && chmod -R g+rw /config /opt/ibm/wlp/usr/servers/defaultServer  /opt/ibm/wlp/usr/shared/resources

USER 1001
RUN configure.sh