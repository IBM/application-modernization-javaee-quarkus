Server=AdminConfig.getid('/Cell:' + AdminControl.getCell() + '/Node:' + AdminControl.getNode() + '/Server:server1')
Node=AdminConfig.getid('/Cell:' + AdminControl.getCell() + '/Node:' + AdminControl.getNode() + '/')
Cell=AdminConfig.getid('/Cell:' + AdminControl.getCell() + '/')
NodeName=AdminControl.getNode()

print 'Starting Creating Authenication Alias'
GlobalSecurityVar=AdminConfig.getid('/Cell:' + AdminControl.getCell() + '/' + 'Security:/')
AdminConfig.create('JAASAuthData', GlobalSecurityVar, [['userId', 'db2inst1'], ['password', 'db2inst1'], ['description', ''], ['alias', 'DBUser']])
AdminConfig.save()



print 'Start enable app security'
AdminTask.isGlobalSecurityEnabled()
AdminTask.isAppSecurityEnabled()
securityConfigID=AdminConfig.getid('/Cell:' + AdminControl.getCell() + '/' + 'Security:/')
AdminConfig.modify(securityConfigID,[['appEnabled','true']])
AdminTask.isAppSecurityEnabled()
print 'End enable app security'

print 'Start Creating Users'
AdminTask.createUser ('[-uid kbrown -password bl0wfish -confirmPassword bl0wfish -cn Kyle -sn Brown]')
AdminTask.createUser ('[-uid rbarcia -password bl0wfish -confirmPassword bl0wfish -cn Roland  -sn Barcia]')
AdminTask.createUser ('[-uid dmulley -password bl0wfish -confirmPassword bl0wfish -cn Roland  -sn Mulley]')
AdminTask.createUser ('[-uid dvandepol -password bl0wfish -confirmPassword bl0wfish -cn Roland  -sn VandePol]')
AdminTask.createUser ('[-uid gsmolko -password bl0wfish -confirmPassword bl0wfish -cn Roland  -sn Smolko]')
print 'End Creating Users'
AdminConfig.save()
print 'Start Creating Group'
AdminTask.createGroup ('[-cn SecureShopper]')
print 'End Creating Group'
AdminConfig.save()
print 'Start Add Members to Group'
AdminTask.addMemberToGroup('[-memberUniqueName uid=kbrown,o=defaultWIMFileBasedRealm -groupUniqueName cn=SecureShopper,o=defaultWIMFileBasedRealm]')
AdminTask.addMemberToGroup('[-memberUniqueName uid=rbarcia,o=defaultWIMFileBasedRealm -groupUniqueName cn=SecureShopper,o=defaultWIMFileBasedRealm]')
AdminTask.addMemberToGroup('[-memberUniqueName uid=dvandepol,o=defaultWIMFileBasedRealm -groupUniqueName cn=SecureShopper,o=defaultWIMFileBasedRealm]')
AdminTask.addMemberToGroup('[-memberUniqueName uid=dmulley,o=defaultWIMFileBasedRealm -groupUniqueName cn=SecureShopper,o=defaultWIMFileBasedRealm]')
AdminTask.addMemberToGroup('[-memberUniqueName uid=gsmolko,o=defaultWIMFileBasedRealm -groupUniqueName cn=SecureShopper,o=defaultWIMFileBasedRealm]')
print 'End Add Members to Group'
AdminConfig.save()

print 'Start Creating JDBC Providers'
AdminConfigVar_5=AdminConfig.create('JDBCProvider', Cell, [['implementationClassName', 'com.ibm.db2.jcc.DB2XADataSource'], ['providerType', 'DB2 Using IBM JCC Driver (XA)'], ['name', 'DB2_Using_IBM_JCC_Driver_(XA)'], ['xa', 'true'], ['description', ''], ['classpath', '/opt/IBM/db2drivers/db2jcc4.jar;/opt/IBM/db2drivers/db2jcc_license_cu.jar;/opt/IBM/db2drivers/db2jcc_license_cisuz.jar']])
AdminConfigVar_6=AdminTask.createDatasource(AdminConfigVar_5, ["-componentManagedAuthenticationAlias", "DBUser" , "-configureResourceProperties", "[[databaseName java.lang.String ORDERDB] [driverType java.lang.Integer 4] [serverName java.lang.String db2.db2.svc] [portNumber java.lang.Integer 50000] ]" , "-dataStoreHelperClassName", "com.ibm.websphere.rsadapter.DB2UniversalDataStoreHelper" , "-xaRecoveryAuthAlias", "DBUser" , "-name", "OrderDS" , "-jndiName", "jdbc/orderds" ])
AdminConfigVar_7=AdminConfig.showAttribute(AdminConfigVar_6, 'propertySet')
AdminConfig.create('J2EEResourceProperty', AdminConfigVar_7, [['name', 'name'], ['type', 'java.lang.String'], ['value', 'OrderDS']])
AdminConfigVar_9=AdminTask.createDatasource(AdminConfigVar_5, ["-componentManagedAuthenticationAlias", "DBUser" , "-configureResourceProperties", "[[databaseName java.lang.String INDB] [driverType java.lang.Integer 4] [serverName java.lang.String db2.db2.svc] [portNumber java.lang.Integer 50000] ]" , "-dataStoreHelperClassName", "com.ibm.websphere.rsadapter.DB2UniversalDataStoreHelper" , "-xaRecoveryAuthAlias", "DBUser" , "-name", "INDS" , "-jndiName", "jdbc/inds" ])
AdminConfigVar_10=AdminConfig.showAttribute(AdminConfigVar_9, 'propertySet')
AdminConfig.create('J2EEResourceProperty', AdminConfigVar_10, [['name', 'name'], ['type', 'java.lang.String'], ['value', 'INDS']])
print 'End Creating JDBC Providers'
AdminConfig.save()
# Configuring JPA Specification
print 'Start Configure JPA Specifications'
AdminTask.listSupportedJPASpecifications('[-versionOnly]')
AdminTask.showJPASpecLevel(Server)
AdminTask.modifyJPASpecLevel(Server, '[ -specLevel 2.0]')
AdminTask.showJPASpecLevel(Server)
print 'End Configure JPA Specifications'
print 'Start Configure JAX-RS Specifications'
# Configuring JAX-RS Specification
AdminTask.modifyJaxrsProvider(Server, '[ -provider 1.1]')
print 'End Configure JAX-RS Specifications'
AdminConfig.save()

