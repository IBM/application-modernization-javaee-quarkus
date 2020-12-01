FROM openliberty/open-liberty:kernel-slim-java11-openj9-ubi
USER root
COPY ./liberty/server.xml /config
COPY ./liberty/server.env /config
COPY ./liberty/jvm.options /config

ARG SSL=false
ARG MP_MONITORING=false
ARG HTTP_ENDPOINT=false

COPY ./CustomerOrderServicesApp/target/CustomerOrderServicesApp-0.1.0-SNAPSHOT.ear /config/apps/CustomerOrderServicesApp-0.1.0-SNAPSHOT.ear
COPY ./resources/ /opt/ol/wlp/usr/shared/resources/
RUN chown -R 1001.0 /config /opt/ol/wlp/usr/servers/defaultServer /opt/ol/wlp/usr/shared/resources && chmod -R g+rw /config /opt/ol/wlp/usr/servers/defaultServer  /opt/ol/wlp/usr/shared/resources

USER 1001
RUN configure.sh