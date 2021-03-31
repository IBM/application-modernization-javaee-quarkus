FROM ibmcom/db2:11.5.5.0

COPY ./database /database

# https://www.ibm.com/support/knowledgecenter/SSEPGG_11.1.0/com.ibm.db2.luw.qb.server.doc/doc/t0006742.html
RUN groupadd db2iadm1
RUN groupadd db2fadm1
RUN groupadd dasadm1

RUN useradd -g db2iadm1 -m -d /home/db2inst1 db2inst1
RUN useradd -g db2fadm1 -m -d /home/db2fenc1 db2fenc1
RUN useradd -g dasadm1 -m -d /home/dasusr1 dasusr1

RUN echo "db2inst1:db2inst1" | chpasswd
RUN echo "db2fenc1:db2fenc1" | chpasswd
RUN echo "dasusr1:dasusr1" | chpasswd

RUN chown -R db2inst1:db2iadm1 /database
RUN chown -R db2inst1:db2iadm1 /database/data
RUN chown -R db2inst1:db2iadm1 /database/config/db2inst1

RUN chmod -R 750 /database
RUN chmod -R 750 /database/data