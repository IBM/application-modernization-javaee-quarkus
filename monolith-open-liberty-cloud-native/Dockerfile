FROM openliberty/open-liberty:kernel-java8-openj9-ubi

ARG VERSION=1.0
ARG REVISION=SNAPSHOT

LABEL \
  org.opencontainers.image.authors="My Name" \
  org.opencontainers.image.vendor="Open Liberty" \
  org.opencontainers.image.url="local" \
  org.opencontainers.image.version="$VERSION" \
  org.opencontainers.image.revision="$REVISION" \
  vendor="Open Liberty" \
  name="inventory" \
  version="$VERSION-$REVISION" 

COPY --chown=1001:0 src/main/liberty/config /config/
COPY --chown=1001:0 target/service.war /config/apps

EXPOSE 9088 9448

# It is recommended to run the configure.sh when build image for production.
# RUN configure.sh