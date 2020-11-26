FROM ibmcom/db2

RUN mkdir /var/custom
COPY ./createschema.sh /var/custom/
COPY ./createOrderDB.sql /var/custom/
RUN chmod a+x /var/custom/createschema.sh
RUN chmod a+x /var/custom/createOrderDB.sql