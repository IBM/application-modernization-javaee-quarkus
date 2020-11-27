FROM ibmcom/db2

RUN mkdir /var/custom
COPY createOrderDB.sql /var/custom
COPY orderdb-data.sql /var/custom
COPY populateDB.sh /var/custom
RUN chmod a+x /var/custom/populateDB.sh

