#!/bin/bash

########
# Help #
########

usage () {

    echo
    echo "This script will configure your WebSphere Application Server"
    echo
    echo "The following parameters must be specified:"
    echo
    echo "--------------"
    echo "- Parameters -"
    echo "--------------"
    echo
    echo "-f:                      Read values from specified file."
    echo "-u:                      Print usage."

}

###################
# Read parameters #
###################

while [ -n "$1" ]; do
    case $1 in
        -f )                    shift
                                READ_FROM_FILE=true
                                FILE_TO_READ=$1
                                break
                                ;;
        -u )                    usage
                                exit 0
                                ;;
        * )                     echo "[ERROR]: Param $1 is not valid."
                                usage
                                exit 1
    esac
    shift
done

##################
# Read from file #
##################

if [ ${READ_FROM_FILE} ]; then
    if [ ! -f ${FILE_TO_READ} ]; then
        echo "[ERROR]: The file ${FILE_TO_READ} does not exist."
        exit 1
    fi
    . ${FILE_TO_READ}
    if [ $? -ne 0 ]; then
        echo "[ERROR]: An error occurred reading the properties file"
    fi
fi

#########################
# Parameters validation #
#########################

if ([ -z "${DB2JCC4}" ] || [ -z "${DB2JCC_LICENSE_CISUZ}" ] || [ -z "${DB2JCC_LICENSE_CU}" ] || [ -z "${WAS_INSTALL_ROOT}" ] ); then
    echo "[ERROR]: The value for one of the parameters in the file is null."
    echo "[ERROR]: Please check your parameters in the specified file."
    usage
    exit 1
fi

######################
# Create jython file #
######################

FILE="WAS_config.py"

if [ -f $FILE ] ; then
    rm $FILE
fi

touch ${FILE}

if [ $? -ne 0 ]; then
    echo "[ERROR]: An error occurred while creating the jython file."
    exit 1
fi

echo "Node=AdminConfig.getid('/Cell:' + AdminControl.getCell() + '/Node:' + AdminControl.getNode() + '/')" >> ${FILE}
echo "Server=AdminConfig.getid('/Cell:' + AdminControl.getCell() + '/Node:' + AdminControl.getNode() + '/Server:server1')" >> ${FILE}
echo "Server=AdminConfig.getid('/Cell:' + AdminControl.getCell() + '/Node:' + AdminControl.getNode() + '/Server:server1')" >> ${FILE}
echo "Node=AdminConfig.getid('/Cell:' + AdminControl.getCell() + '/Node:' + AdminControl.getNode() + '/')" >> ${FILE}
echo "Cell=AdminConfig.getid('/Cell:' + AdminControl.getCell() + '/')" >> ${FILE}
echo "NodeName=AdminControl.getNode()" >> ${FILE}

echo "# Enabling Application Security" >> ${FILE}
echo "AdminTask.isGlobalSecurityEnabled()" >> ${FILE}
echo "AdminTask.isAppSecurityEnabled()" >> ${FILE}
echo "securityConfigID=AdminConfig.getid('/Cell:' + AdminControl.getCell() + '/' + 'Security:/')" >> ${FILE}
echo "AdminConfig.modify(securityConfigID,[['appEnabled','true']])" >> ${FILE}
echo "AdminTask.isAppSecurityEnabled()" >> ${FILE}

echo "# Setting the Users and Groups"

echo "##########################################"
echo "  You can choose it to be default or LDAP "
echo "   Enter \"default\" for default    "
echo "   Enter \"LDAP\" for LDAP   "
echo "##########################################"
echo "Please enter you choice: default or LDAP"
read input

while [ "$input" != "default" ] && [ "$input" != "LDAP" ]
do
    echo "Please enter a proper choice: default or LDAP"
    read input
done

if [ "$input" = "default" ]
then
  echo "# Starting Creating Users" >> ${FILE}
  echo "AdminTask.createUser ('[-uid kbrown -password bl0wfish -confirmPassword bl0wfish -cn Kyle -sn Brown]')" >> ${FILE}
  echo "AdminTask.createUser ('[-uid rbarcia -password bl0wfish -confirmPassword bl0wfish -cn Roland  -sn Barcia]')" >> ${FILE}

  echo "# Creating a Group" >> ${FILE}
  echo "AdminTask.createGroup ('[-cn SecureShopper]')" >> ${FILE}

  echo "# Adding member to group" >> ${FILE}
  echo "AdminTask.addMemberToGroup('[-memberUniqueName uid=kbrown,o=defaultWIMFileBasedRealm -groupUniqueName cn=SecureShopper,o=defaultWIMFileBasedRealm]')" >> ${FILE}
  echo "AdminTask.addMemberToGroup('[-memberUniqueName uid=rbarcia,o=defaultWIMFileBasedRealm -groupUniqueName cn=SecureShopper,o=defaultWIMFileBasedRealm]')" >> ${FILE}

  echo "AdminConfig.save()" >> ${FILE}

elif [ "$input" = "LDAP" ]
then

  echo "This script will configure your WebSphere Application Server to use an standalone LDAP Server for user Authentication and Authorization"

  echo "##########################################"
  echo "   Please specify the configuration inputs "
  echo "   Enter file for using a properties file    "
  echo "   Enter manual for specifying them manually   "
  echo "##########################################"
  echo "Please enter you choice: file or manual"
  read inp

  echo "You entered: $inp"

  while [ "$inp" != "file" ] && [ "$inp" != "manual" ]
  do
      echo "Please enter a proper choice: file or manual"
      read inp
  done

  if [ "$inp" = "file" ]
  then
      echo "Please enter the path of your properties file ... dir/file"
      read path
      sh ../LDAP/create_ldap_jython.sh -f "$path"

  elif [ "$inp" = "manual" ]
  then
      echo "Please enter Standalone LDAP Server host url"
      read ldap_host
      echo "Please enter Standalone LDAP Server port number"
      read ldap_port
      echo "Please enter Realm definition. Example: IBM_DIRECTORY_SERVER"
      read ldap_type
      echo "Please enter LDAP administrator user id"
      read bind_dn
      echo "Please enter LDAP administrator user password"
      read bind_pw
      echo "Please enter WebShere Application Server administrator id"
      read admin_id
      sh ../LDAP/create_ldap_jython.sh -ldap_host "$ldap_host" -ldap_port "$ldap_port" -ldap_type "$ldap_type" -bind_dn "$bind_dn" -bind_pw "$bind_pw" -admin_id "$admin_id"
  fi

cat WAS_LDAP_config.py >> ${FILE}

fi

echo "# Starting Creating JVM Properties" >> ${FILE}
re='^[0-9]+$'
echo "AdminTask.setJVMProperties(Server, ["\"-classpath\", "\"{}\" , "\"-maximumHeapSize\", "\"0\" , "\"-initialHeapSize\", "\"0\" , "\"-genericJvmArguments\", "\"'-Xquickstart'\" ])" >> ${FILE}
echo "AdminConfigVar_8=AdminConfig.list('JavaVirtualMachine', Server)" >> ${FILE}
echo "systemPropertiesAttr=[]" >> ${FILE}
echo "systemPropertiesAttr.append([['name', 'com.ibm.security.jgss.debug'], ['value', 'off']])" >> ${FILE}
echo "systemPropertiesAttr.append([['name', 'com.ibm.security.krb5.Krb5Debug'], ['value', 'off']])" >> ${FILE}
echo "systemPropertiesAttr.append([['name', 'com.ibm.ws.management.event.pull_notification_timeout'], ['value', '120000']])" >> ${FILE}
echo "AdminConfig.modify(AdminConfigVar_8, [['systemProperties', systemPropertiesAttr]])" >> ${FILE}

echo "# Starting Creating Authenication Alias" >> ${FILE}
echo "#####################################################"
echo "              AUTHENTICATION ALIAS                   "
echo "#####################################################"
echo "Please enter the number of authentication alias you want to configure"
read auth
while ! [[ $auth =~ $re ]]; do
  echo "error: Not a number. Please enter a valid number."
  read auth
done
global_var=1
echo "AdminConfigVar_11=AdminConfig.create('JDBCProvider', Cell, [['providerType', 'DB2 Using IBM JCC Driver (XA)'], ['name', 'DB2_Using_IBM_JCC_Driver_(XA)'], ['description', 'Two-phase commit DB2 JCC provider that supports JDBC 4.0 using the IBM Data Server Driver for JDBC and SQLJ. IBM Data Server Driver is the next generation of the DB2 Universal JCC driver. Data sources created under this provider support the use of XA to perform 2-phase commit processing. Use of JDBC driver type 2 on WebSphere Application Server for Z/OS is not supported for data sources created under this provider. This provider is configurable in version 7.0 and later nodes.'], ['implementationClassName', 'com.ibm.db2.jcc.DB2XADataSource'], ['classpath', '${DB2JCC4};${DB2JCC_LICENSE_CU};${DB2JCC_LICENSE_CISUZ}'], ['xa', 'true']])" >> ${FILE}
for(( j=1; j<=$auth; j++ ))
do
  echo "GlobalSecurityVar_$global_var=AdminConfig.getid('/Cell:' + AdminControl.getCell() + '/' + 'Security:/')" >> ${FILE}
  echo "#####################################################"
  echo "               JAAS Authentication Data              "
  echo "#####################################################"
  echo "Enter the Alias"
  read al
  echo "Enter the USER_ID"
  read uid
  echo "Enter the password"
  read pass
  echo "AdminConfig.create('JAASAuthData', GlobalSecurityVar_$global_var, [['password', '$pass'], ['userId', '$uid'], ['alias', Node + '$al'], ['description', '']])" >> ${FILE}

  echo "# Starting Creating Connection Factories" >> ${FILE}

  echo "# Starting Creating JDBC Providers" >> ${FILE}
  echo "#####################################################"
  echo "                DATASOURCE CREATION                  "
  echo "#####################################################"
  echo "Enter the number of datasources you want to configure"
  read d

  while ! [[ $d =~ $re ]]; do
    echo "error: Not a number. Please enter a valid number."
    read d
  done

  config_var=12
  for(( i=1; i<=$d; i++ ))
  do
    next_config_var=$((config_var+1))
    conn_pool_var=$((next_config_var+1))
  #x="DATASOURCE_NAME_$i"
  #echo "$x"
    echo "Please enter the below details"
    echo "=============================="
    echo "Enter datasource name"
    read datasrc
    echo "Enter JNDI name"
    read jndiname
    echo "Enter database name"
    read dbname
    echo "Enter driver type"
    read drivertype
    while ! [[ $drivertype =~ $re ]]; do
      echo "error: Not a number. Please enter a valid number."
      read drivertype
    done
    echo "Enter server name"
    read servername
    echo "Enter port number"
    read portnum
    while ! [[ $portnum =~ $re ]]; do
      echo "error: Not a number. Please enter a valid number."
      read portnum
    done

    echo "AdminConfigVar_$config_var=AdminTask.createDatasource(AdminConfigVar_11, ["\"-name\", "\"$datasrc\" , "\"-jndiName\", "\"$jndiname\" , "\"-dataStoreHelperClassName\", "\"com.ibm.websphere.rsadapter.DB2UniversalDataStoreHelper\" , "\"-componentManagedAuthenticationAlias\"," Node+'$al' , "\"-xaRecoveryAuthAlias\"," Node+'$al' , "\"-configureResourceProperties\", "\"[[databaseName java.lang.String $dbname] [driverType java.lang.Integer $drivertype] [serverName java.lang.String $servername] [portNumber java.lang.Integer $portnum] ]\" ])" >> ${FILE}
    echo "AdminConfigVar_$next_config_var=AdminConfig.showAttribute(AdminConfigVar_$config_var, 'propertySet')" >> ${FILE}
    echo "AdminConfig.create('J2EEResourceProperty', AdminConfigVar_$next_config_var, [['name', 'retrieveMessagesFromServerOnGetMessage'], ['value', 'true'], ['type', 'java.lang.String']])" >> ${FILE}
    echo "AdminConfig.create('J2EEResourceProperty', AdminConfigVar_$next_config_var, [['name', 'beginTranForVendorAPIs'], ['value', 'false'], ['type', 'java.lang.String']])" >> ${FILE}
    echo "AdminConfig.create('J2EEResourceProperty', AdminConfigVar_$next_config_var, [['name', 'validateNewConnectionRetryCount'], ['value', '100'], ['type', 'java.lang.String']])" >> ${FILE}
    echo "AdminConfig.create('J2EEResourceProperty', AdminConfigVar_$next_config_var, [['name', 'useTransactionRedirect'], ['value', 'false'], ['type', 'java.lang.String']])" >> ${FILE}
    echo "AdminConfig.create('J2EEResourceProperty', AdminConfigVar_$next_config_var, [['name', 'jmsOnePhaseOptimization'], ['value', 'false'], ['type', 'java.lang.String']])" >> ${FILE}
    echo "AdminConfig.create('J2EEResourceProperty', AdminConfigVar_$next_config_var, [['name', 'enableMultithreadedAccessDetection'], ['value', 'false'], ['type', 'java.lang.String']])" >> ${FILE}
    echo "AdminConfig.create('J2EEResourceProperty', AdminConfigVar_$next_config_var, [['name', 'reauthentication'], ['value', 'false'], ['type', 'java.lang.String']])" >> ${FILE}
    echo "AdminConfig.create('J2EEResourceProperty', AdminConfigVar_$next_config_var, [['name', 'connectionSharing'], ['value', '1'], ['type', 'java.lang.String']])" >> ${FILE}
    echo "AdminConfig.create('J2EEResourceProperty', AdminConfigVar_$next_config_var, [['name', 'nonTransactionalDataSource'], ['value', 'false'], ['type', 'java.lang.String']])" >> ${FILE}
    echo "AdminConfig.create('J2EEResourceProperty', AdminConfigVar_$next_config_var, [['name', 'validateNewConnectionRetryInterval'], ['value', '3'], ['type', 'java.lang.String']])" >> ${FILE}
    echo "AdminConfig.create('J2EEResourceProperty', AdminConfigVar_$next_config_var, [['name', 'name'], ['value', '$datasrc'], ['type', 'java.lang.String']])" >> ${FILE}
    echo "AdminConfig.create('J2EEResourceProperty', AdminConfigVar_$next_config_var, [['name', 'freeResourcesOnClose'], ['value', 'false'], ['type', 'java.lang.String']])" >> ${FILE}
    echo "AdminConfig.create('J2EEResourceProperty', AdminConfigVar_$next_config_var, [['name', 'traceLevel'], ['value', '-1'], ['type', 'java.lang.Integer']])" >> ${FILE}
    echo "AdminConfig.create('J2EEResourceProperty', AdminConfigVar_$next_config_var, [['name', 'beginTranForResultSetScrollingAPIs'], ['value', 'false'], ['type', 'java.lang.String']])" >> ${FILE}
    echo "AdminConfig.create('J2EEResourceProperty', AdminConfigVar_$next_config_var, [['name', 'unbindClientRerouteListFromJndi'], ['value', 'false'], ['type', 'java.lang.String']])" >> ${FILE}
    echo "AdminConfig.create('J2EEResourceProperty', AdminConfigVar_$next_config_var, [['name', 'preTestSQLString'], ['value', 'SELECT CURRENT SQLID FROM SYSIBM.SYSDUMMY1'], ['type', 'java.lang.String']])" >> ${FILE}
    echo "AdminConfig.create('J2EEResourceProperty', AdminConfigVar_$next_config_var, [['name', 'validateNewConnection'], ['value', 'false'], ['type', 'java.lang.String']])" >> ${FILE}
    echo "AdminConfig.create('J2EEResourceProperty', AdminConfigVar_$next_config_var, [['name', 'errorDetectionModel'], ['value', 'ExceptionMapping'], ['type', 'java.lang.String']])" >> ${FILE}
    echo "AdminConfigVar_$conn_pool_var=AdminConfig.showAttribute(AdminConfigVar_$config_var, 'connectionPool')" >> ${FILE}
    echo "AdminConfig.modify(AdminConfigVar_$conn_pool_var, [['reapTime', '180'], ['surgeCreationInterval', '0'], ['surgeThreshold', '-1'], ['unusedTimeout', '1800'], ['purgePolicy', 'EntirePool'], ['testConnection', 'false'], ['numberOfSharedPoolPartitions', '0'], ['minConnections', '1'], ['stuckTimerTime', '0'], ['freePoolDistributionTableSize', '0'], ['numberOfUnsharedPoolPartitions', '0'], ['numberOfFreePoolPartitions', '0'], ['stuckTime', '0'], ['testConnectionInterval', '0'], ['connectionTimeout', '180'], ['agedTimeout', '0'], ['maxConnections', '10'], ['stuckThreshold', '0']])" >> ${FILE}
    #adminvar_num=$((adminvar_num+1))
    config_var=$((config_var+3))
  done
  global_var=$((global_var+1))
done

echo "# Starting Creating websphereVariables" >> ${FILE}
echo "varSubstitutions =AdminConfig.list(\"VariableSubstitutionEntry\",Cell).split(java.lang.System.getProperty(\"line.separator\"))" >> ${FILE}
echo "for varSubst in varSubstitutions:
	getVarName = AdminConfig.showAttribute(varSubst, \"symbolicName\")
	if getVarName == \"DB2_JCC_DRIVER_PATH\":
		AdminConfig.modify(varSubst, [['value', '/opt/ibm/db2/V11.1/java']])
	if getVarName == \"DERBY_JDBC_DRIVER_PATH\":
		AdminConfig.modify(varSubst, [['value', '${WAS_INSTALL_ROOT}/derby/lib']])
	if getVarName == \"UNIVERSAL_JDBC_DRIVER_PATH\":
		AdminConfig.modify(varSubst, [['value', '${WAS_INSTALL_ROOT}/universalDriver/lib']])
	if getVarName == \"DB2_JCC_DRIVER_NATIVEPATH\":
		AdminConfig.modify(varSubst, [['value', '']])" >> ${FILE}

echo "# Configuring JPA Specification" >> ${FILE}

echo "AdminTask.listSupportedJPASpecifications('[-versionOnly]')" >> ${FILE}
echo "AdminTask.showJPASpecLevel(Server)">> ${FILE}
echo "AdminTask.modifyJPASpecLevel(Server, '[ -specLevel 2.0]')">> ${FILE}
echo "AdminTask.showJPASpecLevel(Server)">> ${FILE}

echo "# Configuring JAX-RS Specification" >> ${FILE}

echo "AdminTask.modifyJaxrsProvider(Server, '[ -provider 1.1]')" >> ${FILE}


echo "AdminConfig.save()" >> ${FILE}
