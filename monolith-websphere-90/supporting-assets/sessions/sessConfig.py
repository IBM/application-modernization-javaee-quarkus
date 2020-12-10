Server=AdminConfig.getid('/Cell:' + AdminControl.getCell() + '/Node:' + AdminControl.getNode() + '/Server:server1')
Node=AdminConfig.getid('/Cell:' + AdminControl.getCell() + '/Node:' + AdminControl.getNode() + '/')
Cell=AdminConfig.getid('/Cell:' + AdminControl.getCell() + '/')
NodeName=AdminControl.getNode()

print 'Starting Creating Authenication Alias'
GlobalSecurityVar=AdminConfig.getid('/Cell:' + AdminControl.getCell() + '/' + 'Security:/')
AdminConfig.create('JAASAuthData', GlobalSecurityVar, [['userId', 'db2inst1'], ['password', 'db2inst1'], ['description', ''], ['alias', 'DBUser']])
AdminConfig.save()



print 'Start Creating JDBC Providers'

AdminConfigVar_5=AdminConfig.create('JDBCProvider', Cell, [['implementationClassName', 'com.ibm.db2.jcc.DB2XADataSource'], ['providerType', 'DB2 Using IBM JCC Driver (XA)'], ['name', 'DB2_Using_IBM_JCC_Driver_(XA)'], ['xa', 'true'], ['description', ''], ['classpath', '/opt/IBM/db2drivers/db2jcc4.jar;/opt/IBM/db2drivers/db2jcc_license_cu.jar;/opt/IBM/db2drivers/db2jcc_license_cisuz.jar']])
AdminConfigVar_6=AdminTask.createDatasource(AdminConfigVar_5, ["-componentManagedAuthenticationAlias", "DBUser" , "-configureResourceProperties", "[[databaseName java.lang.String ORDERDB] [driverType java.lang.Integer 4] [serverName java.lang.String db2.db2.svc] [portNumber java.lang.Integer 50000] ]" , "-dataStoreHelperClassName", "com.ibm.websphere.rsadapter.DB2UniversalDataStoreHelper" , "-xaRecoveryAuthAlias", "DBUser" , "-name", "Sessions" , "-jndiName", "jdbc/Sessions" ])
AdminConfigVar_7=AdminConfig.showAttribute(AdminConfigVar_6, 'propertySet')
AdminConfig.create('J2EEResourceProperty', AdminConfigVar_7, [['name', 'name'], ['type', 'java.lang.String'], ['value', 'Sessions']])

print 'End Creating JDBC Providers'
AdminConfig.save()

print 'Get SessionManager Configuration'
AdminConfigVar_8 = AdminConfig.list('SessionManager', Server)

print 'Start - Enable Session Database Persistence'
AdminConfig.modify(AdminConfigVar_8,'[[sessionPersistenceMode "DATABASE"]]')

AdminConfigVar_9 = AdminConfig.list('SessionDatabasePersistence',AdminConfigVar_8)

AdminConfig.modify(AdminConfigVar_9,'[[userId "db2inst1"] [password "db2inst1"] [tableSpaceName ""] [datasourceJNDIName "jdbc/Sessions"]]')

print 'End - Enable Session Database Persistence'
AdminConfig.save()

