FROM ibmcom/websphere-traditional:9.0.0.11

#Hardcode password for admin console

COPY ./session-mgmt/PASSWORD /tmp/PASSWORD

COPY ./session-mgmt/db2/ /opt/IBM/db2drivers/

COPY ./session-mgmt/sessConfig.py /work/config/

COPY ./session-mgmt/app-install.props  /work/config/app-install.props

COPY ./session-mgmt/SessionSample-1.0.war /work/config/SessionSample-1.0.war

RUN /work/configure.sh
