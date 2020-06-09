# eux-rina-irsync

Small app offering scheduled RINA IR SYNC (and a REST API with Swagger) using RINA SDK.

## main functionality

* sync IR

## quickstart

* easiest: deploy on RINA server. IR SYNC uses a local Jetty HTTP server running on address 127.0.0.1 and port 9000 (configurable), and talks to RINA like any other CPI client, configurable under rina.tenants, so in this case using localhost.

* you want this directory structure, including an empty logs directory
```
eux-rina-irsync/
├── config/
│   ├── application.yml
│   ├── logback-spring.xml
├── logs/
│  
└── eux-rina-irsync-5.6.2-SNAPSHOT.jar
```
* download the eux-rina-irsync-5.6.2-SNAPSHOT.zip
* unzip eux-rina-irsync-5.6.2-SNAPSHOT.zip somewhere on your RINA server to get above directory structure
* adopt config/application.yml to your environment
* run (we use RINA's Java 8 to run this)
```bash
java -Djava.util.concurrent.ForkJoinPool.common.parallelism=16 -jar eux-rina-irsync-0.9.4-SNAPSHOT.jar
```

If everything is setup correctly, you will first see this
```bash
$ java -Djava.util.concurrent.ForkJoinPool.common.parallelism=16 -jar eux-rina-irsync-0.9.4-SNAPSHOT.jar
12:04:08.156 [main] INFO no.nav.eux.rina.admin.EuxRinaIrSyncApplication - availableProcessors = 8
12:04:08.172 [main] INFO no.nav.eux.rina.admin.EuxRinaIrSyncApplication - parallism of pool   = 16
LOGBACK: No context given for c.q.l.core.rolling.SizeAndTimeBasedRollingPolicy@757108857
03-01-2020 12:04:08.830 [main] INFO  no.nav.eux.rina.admin.EuxRinaIrSyncApplication.logStarting - Starting EuxRinaIrSyncApplication v0.9.4-SNAPSHOT on A01APVW145 with PID 15104 (D:\bin\IRSYNC\eux-rina-irsync-0.9.4-SNAPSHOT.jar started by RA_K114434 in D:\bin\IRSYNC)
03-01-2020 12:04:08.830 [main] INFO  no.nav.eux.rina.admin.EuxRinaIrSyncApplication.logStartupProfileInfo - No active profile set, falling back to default profiles: default
03-01-2020 12:04:09.988 [main] INFO  com.ulisesbocchio.jasyptspringboot.configuration.EnableEncryptablePropertiesBeanFactoryPostProcessor.postProcessBeanFactory - Post-processing PropertySource instances
03-01-2020 12:04:10.050 [main] INFO  com.ulisesbocchio.jasyptspringboot.EncryptablePropertySourceConverter.makeEncryptable - Converting PropertySource configurationProperties [org.springframework.boot.context.properties.source.ConfigurationPropertySourcesPropertySource] to AOP Proxy
03-01-2020 12:04:10.050 [main] INFO  com.ulisesbocchio.jasyptspringboot.EncryptablePropertySourceConverter.makeEncryptable - Converting PropertySource servletConfigInitParams [org.springframework.core.env.PropertySource$StubPropertySource] to EncryptablePropertySourceWrapper
03-01-2020 12:04:10.050 [main] INFO  com.ulisesbocchio.jasyptspringboot.EncryptablePropertySourceConverter.makeEncryptable - Converting PropertySource servletContextInitParams [org.springframework.core.env.PropertySource$StubPropertySource] to EncryptablePropertySourceWrapper
03-01-2020 12:04:10.050 [main] INFO  com.ulisesbocchio.jasyptspringboot.EncryptablePropertySourceConverter.makeEncryptable - Converting PropertySource systemProperties [org.springframework.core.env.PropertiesPropertySource] to EncryptableMapPropertySourceWrapper
03-01-2020 12:04:10.050 [main] INFO  com.ulisesbocchio.jasyptspringboot.EncryptablePropertySourceConverter.makeEncryptable - Converting PropertySource systemEnvironment [org.springframework.boot.env.SystemEnvironmentPropertySourceEnvironmentPostProcessor$OriginAwareSystemEnvironmentPropertySource] to EncryptableMapPropertySourceWrapper
03-01-2020 12:04:10.050 [main] INFO  com.ulisesbocchio.jasyptspringboot.EncryptablePropertySourceConverter.makeEncryptable - Converting PropertySource random [org.springframework.boot.env.RandomValuePropertySource] to EncryptablePropertySourceWrapper
03-01-2020 12:04:10.050 [main] INFO  com.ulisesbocchio.jasyptspringboot.EncryptablePropertySourceConverter.makeEncryptable - Converting PropertySource applicationConfig: [file:./config/application.yml] [org.springframework.boot.env.OriginTrackedMapPropertySource] to EncryptableMapPropertySourceWrapper
03-01-2020 12:04:10.196 [main] INFO  com.ulisesbocchio.jasyptspringboot.resolver.DefaultLazyPropertyResolver.lambda$new$2 - Property Resolver custom Bean not found with name 'encryptablePropertyResolver'. Initializing Default Property Resolver
03-01-2020 12:04:10.199 [main] INFO  com.ulisesbocchio.jasyptspringboot.detector.DefaultLazyPropertyDetector.lambda$new$2 - Property Detector custom Bean not found with name 'encryptablePropertyDetector'. Initializing Default Property Detector
03-01-2020 12:04:10.316 [main] INFO  org.eclipse.jetty.util.log.initialized - Logging initialized @2450ms to org.eclipse.jetty.util.log.Slf4jLog
03-01-2020 12:04:10.422 [main] INFO  org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory.getWebServer - Server initialized with port: 9000
03-01-2020 12:04:10.432 [main] INFO  org.eclipse.jetty.server.Server.doStart - jetty-9.4.24.v20191120; built: 2019-11-20T21:37:49.771Z; git: 363d5f2df3a8a28de40604320230664b9c793c16; jvm 1.8.0_112-b15
03-01-2020 12:04:10.458 [main] INFO  org.eclipse.jetty.server.handler.ContextHandler.application.log - Initializing Spring embedded WebApplicationContext
03-01-2020 12:04:10.459 [main] INFO  org.springframework.web.context.ContextLoader.prepareWebApplicationContext - Root WebApplicationContext: initialization completed in 1598 ms
03-01-2020 12:04:10.863 [main] INFO  org.eclipse.jetty.server.session.doStart - DefaultSessionIdManager workerName=node0
03-01-2020 12:04:10.864 [main] INFO  org.eclipse.jetty.server.session.doStart - No SessionScavenger set, using defaults
03-01-2020 12:04:10.865 [main] INFO  org.eclipse.jetty.server.session.startScavenging - node0 Scavenging every 660000ms
03-01-2020 12:04:10.871 [main] INFO  org.eclipse.jetty.server.handler.ContextHandler.doStart - Started o.s.b.w.e.j.JettyEmbeddedWebAppContext@7bab3f1a{application,/,[file:///C:/Users/RA_K114434/AppData/Local/Temp/2/jetty-docbase.3217235774749337829.9000/, jar:file:/D:/bin/IRSYNC/eux-rina-irsync-0.9.4-SNAPSHOT.jar!/BOOT-INF/lib/springfox-swagger-ui-2.9.2.jar!/META-INF/resources],AVAILABLE}
03-01-2020 12:04:10.872 [main] INFO  org.eclipse.jetty.server.Server.doStart - Started @3005ms
03-01-2020 12:04:11.228 [main] INFO  eu.ec.dgempl.eessi.rina.sdk.cpi.RinaCpiClient.buildObjectMapper - Building the Object Mapper
03-01-2020 12:04:11.541 [main] INFO  org.springframework.boot.actuate.endpoint.web.EndpointLinksResolver.<init> - Exposing 5 endpoint(s) beneath base path '/actuator'
03-01-2020 12:04:11.619 [main] INFO  springfox.documentation.spring.web.PropertySourcedRequestMappingHandlerMapping.initHandlerMethods - Mapped URL path [/v2/api-docs] onto method [springfox.documentation.swagger2.web.Swagger2Controller#getDocumentation(String, HttpServletRequest)]
03-01-2020 12:04:11.713 [main] INFO  org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor.initialize - Initializing ExecutorService 'applicationTaskExecutor'
03-01-2020 12:04:11.822 [main] INFO  org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler.initialize - Initializing ExecutorService 'taskScheduler'
03-01-2020 12:04:11.853 [main] INFO  springfox.documentation.spring.web.plugins.DocumentationPluginsBootstrapper.start - Context refreshed
03-01-2020 12:04:11.885 [main] INFO  springfox.documentation.spring.web.plugins.DocumentationPluginsBootstrapper.start - Found 1 custom documentation plugin(s)
03-01-2020 12:04:11.916 [main] INFO  springfox.documentation.spring.web.scanners.ApiListingReferenceScanner.scan - Scanning for api listing references
03-01-2020 12:04:12.041 [main] INFO  org.eclipse.jetty.server.handler.ContextHandler.application.log - Initializing Spring DispatcherServlet 'dispatcherServlet'
03-01-2020 12:04:12.041 [main] INFO  org.springframework.web.servlet.DispatcherServlet.initServletBean - Initializing Servlet 'dispatcherServlet'
03-01-2020 12:04:12.041 [main] INFO  org.springframework.web.servlet.DispatcherServlet.initServletBean - Completed initialization in 0 ms
03-01-2020 12:04:12.072 [main] INFO  org.eclipse.jetty.server.AbstractConnector.doStart - Started ServerConnector@4c39bec8{HTTP/1.1,[http/1.1]}{localhost:9000}
03-01-2020 12:04:12.072 [main] INFO  org.springframework.boot.web.embedded.jetty.JettyWebServer.start - Jetty started on port(s) 9000 (http/1.1) with context path '/'
03-01-2020 12:04:12.072 [main] INFO  no.nav.eux.rina.admin.EuxRinaIrSyncApplication.logStarted - Started EuxRinaIrSyncApplication in 3.765 seconds (JVM running for 4.216)
```

and eventually stuff like this
```
03-01-2020 04:17:28.853 [scheduling-1] INFO  no.nav.eux.rina.admin.rest.RinaAutomaticUpdatesController.scheduledAutomaticUpdate - running scheduled to [http://localhost:9000/rina/ir/silentUpdate]
03-01-2020 04:17:28.856 [qtp1630678941-21] INFO  no.nav.eux.rina.admin.rest.RinaAutomaticUpdatesController.silentUpdate - calling automaticUpdate() and discarding the result...
03-01-2020 04:17:28.931 [qtp1630678941-21] INFO  no.nav.eux.rina.admin.rest.RinaAutomaticUpdatesController.lambda$orderNewInstitutionVersions$6 - Requesting version [3.0.50579|as|3.0.50579] for CI [NO:889640782]
03-01-2020 04:17:29.082 [qtp1630678941-21] INFO  no.nav.eux.rina.admin.rest.RinaAutomaticUpdatesController.automaticUpdate - Waiting for 4  minutes for new versions to arrive in RINAs
03-01-2020 04:21:29.091 [qtp1630678941-21] INFO  no.nav.eux.rina.admin.rest.RinaAutomaticUpdatesController.automaticUpdate - Done waiting. Installing new versions.
03-01-2020 04:21:29.138 [qtp1630678941-21] INFO  no.nav.eux.rina.admin.rest.RinaAutomaticUpdatesController.lambda$installNewInstitutionVersion$4 -  UP TO DATE : NO:889640782 installedVersion 3.0.50579-20191231T032326 is GREATER THAN OR EQUAL TO available 3.0.50579-20200102T180451
03-01-2020 04:21:29.138 [qtp1630678941-21] INFO  no.nav.eux.rina.admin.rest.RinaAutomaticUpdatesController.automaticUpdate - Done installing.
03-01-2020 04:21:29.216 [qtp1630678941-21] INFO  no.nav.eux.rina.admin.rina.RinaCpiSynchronizationService.getInstitutionVersions -  UP TO DATE : NO:889640782 installedVersion 3.0.50579-20191231T032326 is GREATER THAN or EUQAL TO available 3.0.50579-20200102T180451
03-01-2020 04:37:28.883 [scheduling-1] INFO  no.nav.eux.rina.admin.rest.RinaAutomaticUpdatesController.scheduledAutomaticUpdate - running scheduled to [http://localhost:9000/rina/ir/silentUpdate]
03-01-2020 04:37:28.883 [qtp1630678941-20] INFO  no.nav.eux.rina.admin.rest.RinaAutomaticUpdatesController.silentUpdate - calling automaticUpdate() and discarding the result...
03-01-2020 04:37:29.070 [qtp1630678941-20] INFO  no.nav.eux.rina.admin.rest.RinaAutomaticUpdatesController.lambda$orderNewInstitutionVersions$6 - Requesting version [3.0.51687|as|3.0.51687] for CI [NO:889640782]
03-01-2020 04:37:29.242 [qtp1630678941-20] INFO  no.nav.eux.rina.admin.rest.RinaAutomaticUpdatesController.automaticUpdate - Waiting for 4  minutes for new versions to arrive in RINAs
03-01-2020 04:41:29.249 [qtp1630678941-20] INFO  no.nav.eux.rina.admin.rest.RinaAutomaticUpdatesController.automaticUpdate - Done waiting. Installing new versions.
03-01-2020 04:41:29.296 [qtp1630678941-20] INFO  no.nav.eux.rina.admin.rest.RinaAutomaticUpdatesController.lambda$installNewInstitutionVersion$4 - CI: NO:889640782 OUT OF DATE : availableVersion 3.0.51687-20200103T032426 is newer / greater than 3.0.50579-20191231T032326
03-01-2020 04:41:29.296 [qtp1630678941-20] INFO  no.nav.eux.rina.admin.rest.RinaAutomaticUpdatesController.lambda$installNewInstitutionVersion$4 - CI: NO:889640782 Installing now... resource version: 3.0.51687-20200103T032426
03-01-2020 04:44:10.942 [qtp1630678941-20] INFO  no.nav.eux.rina.admin.rest.RinaAutomaticUpdatesController.lambda$installNewInstitutionVersion$4 - CI: NO:889640782 Installed resourceId[dataorganisations] resourceType [organisation] resourceVersion [3.0.51687-20200103T032426]
03-01-2020 04:44:10.942 [qtp1630678941-20] INFO  no.nav.eux.rina.admin.rest.RinaAutomaticUpdatesController.automaticUpdate - Done installing.
03-01-2020 04:44:11.005 [qtp1630678941-20] INFO  no.nav.eux.rina.admin.rina.RinaCpiSynchronizationService.getInstitutionVersions -  UP TO DATE : NO:889640782 installedVersion 3.0.51687-20200103T032426 is GREATER THAN or EUQAL TO available 3.0.51687-20200103T032426
```
  
## new sync schedule

 Up to RINA v5.4.3, an IR SYNC request to the AP returned either a new IR, or nothing in case there was no newer IR.
 Since RINA v5.6.2, an IR SYNC request to the AP *always* returns the latest IR, regardless of whether it's newer than the one stated in the request. With previous versions of IR SYNC, this would quickly fill up the disks. 
 Now, we request an IR SYNC three times, a quarter over 04, 05 and 06 o'clock every day. You can easily limit this further by changing the cron expression, i.e. "4-6" to "5" to just sync at 05:15 every day, which should be plenty.

 IRSYNC now scheduled using cron expression in config/application.yml
 CSN pushes IR to APs at 4 CET/CEST, so polling for IR from RINA at 15 minutes past the hour between 4 and 6 o'clock is OK

```
cron:
  expression: 0 15 4-6 * * *
```

 IRSYNC wait for new IR contents from AP in seconds now configured in config/application.yml
```
update:
  wait: 240
```

## small print

* the installation of a new IR is a synchroneous CPI call to RINA, regardless of whether it comes from the Admin Portal or IR SYNC. IR is now so big, that unless you have a very, very fast RINA, the update takes much longer than the synchroneous call timeout configured in RINA (Apache LoadBalancer, etc.). So you might see a timeout error being returned and logged.
 *do not worry* RINA continues to install the IR update successfully, and the next time you check, either by refreshing the Admin Portal page, or waiting for the next IR SYNC scheduled run, you will see that RINA has updated IR just fine.
This behaviour of not supporting longrunning synchroneous CPI calls is known and reported to DG EMPL by both NO and DK.

* eux-rina-irsync needs the RINA CPI SDK patches from ResourcesApi.path in order to work. They are included in the source here at
  eu.ec.dgempl.eessi... and compiled into the fat jar if you use e.g. maven install.

* this is a scaled down branch of NAV's EUX RINA ADMIN tools to administer several things in RINA, like users/password, roles, NIE, etc.
  The full functionality is rather customized to NAV, and honestly we don't have the spare time to generalize it.
  
* currently, only one RINA can be addressed. Full EUX RINA ADMIN supports many RINAs called "tenants" in application.yml.
  IR SYNC only supports one, because of how we use admin username and password in our TEST and ACCEPTANCE environments. 

## Windows service implementation

* We use 
```
nssm install IRSYNC "%JAVA_HOME%"\bin\java.exe -Djava.util.concurrent.ForkJoinPool.common.parallelism=16 -jar "%CD%\eux-rina-irsync-0.9.5-SNAPSHOT.jar"

nssm set IRSYNC AppDirectory "%CD%"

nssm edit IRSYNC
```

## TODOs

* Ubuntu service implementation
* Reintroduce support for multiple RINAs
* or
* remove the possibly complicated code and config for admin username and password.
* example of the already supported encrypted storage of admin username and password.
