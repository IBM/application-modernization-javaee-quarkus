FROM ibmcom/websphere-traditional:9.0.5.0-ubi

COPY --chown=1001:0 resources/db2/ /opt/IBM/db2drivers/

COPY --chown=1001:0 config/PASSWORD /tmp/PASSWORD

COPY --chown=1001:0 config/cosConfig.py /work/config/

COPY --chown=1001:0 config/app-update.props  /work/config/

COPY --chown=1001:0 CustomerOrderServicesApp-0.1.0-SNAPSHOT.ear /work/apps/CustomerOrderServicesApp.ear

RUN /work/configure.sh
