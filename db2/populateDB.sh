#!/bin/sh +

whoami
sudo -i -u db2inst1 bash -c '/opt/ibm/db2/V11.5/bin/db2 CONNECT TO orderdb && /opt/ibm/db2/V11.5/bin/db2 -stvf /var/custom/createOrderDB.sql -z create.log'
sudo -i -u db2inst1 bash -c '/opt/ibm/db2/V11.5/bin/db2 CONNECT TO orderdb && /opt/ibm/db2/V11.5/bin/db2 -stvf /var/custom/orderdb-data.sql -z populate.log'
#/opt/ibm/db2/V11.5/bin/db2 -stvf createOrderDB.sql -z create.log
#/opt/ibm/db2/V11.5/bin/db2 -svft orderdb-data.sql -z populate.log

