# eux-rina-irsync

Small app offering scheduled RINA IR SYNC (and a REST API with Swagger) using RINA SDK.

We finally have a new version which supports both RINA 2019 and RINA 2020 ready and will publish it later today, April 8th, 2021.

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
java -Djava.util.concurrent.ForkJoinPool.common.parallelism=16 -jar eux-rina-irsync-5.6.2-SNAPSHOT.jar
```

If everything is setup correctly, you will first see this
```bash
D:\bin\IRSYNC>java -Djava.util.concurrent.ForkJoinPool.common.parallelism=16 -jar eux-rina-irsync-5.6.2-SNAPSHOT.jar
10:38:55.955 [main] INFO no.nav.eux.rina.admin.EuxRinaIrSyncApplication - availableProcessors = 12
10:38:55.970 [main] INFO no.nav.eux.rina.admin.EuxRinaIrSyncApplication - parallism of pool   = 16
2020-07-17 10:38:56.564  INFO 17112 --- [           main] n.n.e.r.admin.EuxRinaIrSyncApplication   : Starting EuxRinaIrSyncApplication v5.6.2-SNAPSHOT on A01APVW145 with PID 17112 (D:\bin\IRSYNC\eux-rina-irsync-5.6.2-SNAPSHOT.jar started by RA_K114434 in D:\bin\IRSYNC)
2020-07-17 10:38:56.564  INFO 17112 --- [           main] n.n.e.r.admin.EuxRinaIrSyncApplication   : No active profile set, falling back to default profiles: default
2020-07-17 10:38:57.705  INFO 17112 --- [           main] ptablePropertiesBeanFactoryPostProcessor : Post-processing PropertySource instances
2020-07-17 10:38:57.752  INFO 17112 --- [           main] c.u.j.EncryptablePropertySourceConverter : Converting PropertySource configurationProperties [org.springframework.boot.context.properties.source.ConfigurationPropertySourcesPropertySource] to AOP Proxy
2020-07-17 10:38:57.752  INFO 17112 --- [           main] c.u.j.EncryptablePropertySourceConverter : Converting PropertySource servletConfigInitParams [org.springframework.core.env.PropertySource$StubPropertySource] to EncryptablePropertySourceWrapper
2020-07-17 10:38:57.752  INFO 17112 --- [           main] c.u.j.EncryptablePropertySourceConverter : Converting PropertySource servletContextInitParams [org.springframework.core.env.PropertySource$StubPropertySource] to EncryptablePropertySourceWrapper
2020-07-17 10:38:57.752  INFO 17112 --- [           main] c.u.j.EncryptablePropertySourceConverter : Converting PropertySource systemProperties [org.springframework.core.env.PropertiesPropertySource] to EncryptableMapPropertySourceWrapper
2020-07-17 10:38:57.752  INFO 17112 --- [           main] c.u.j.EncryptablePropertySourceConverter : Converting PropertySource systemEnvironment [org.springframework.boot.env.SystemEnvironmentPropertySourceEnvironmentPostProcessor$OriginAwareSystemEnvironmentPropertySource] to EncryptableMapPropertySourceWrapper
2020-07-17 10:38:57.752  INFO 17112 --- [           main] c.u.j.EncryptablePropertySourceConverter : Converting PropertySource random [org.springframework.boot.env.RandomValuePropertySource] to EncryptablePropertySourceWrapper
2020-07-17 10:38:57.752  INFO 17112 --- [           main] c.u.j.EncryptablePropertySourceConverter : Converting PropertySource applicationConfig: [file:./config/application.yml] [org.springframework.boot.env.OriginTrackedMapPropertySource] to EncryptableMapPropertySourceWrapper
2020-07-17 10:38:57.877  INFO 17112 --- [           main] c.u.j.r.DefaultLazyPropertyResolver      : Property Resolver custom Bean not found with name 'encryptablePropertyResolver'. Initializing Default Property Resolver
2020-07-17 10:38:57.877  INFO 17112 --- [           main] c.u.j.d.DefaultLazyPropertyDetector      : Property Detector custom Bean not found with name 'encryptablePropertyDetector'. Initializing Default Property Detector
2020-07-17 10:38:57.986  INFO 17112 --- [           main] org.eclipse.jetty.util.log               : Logging initialized @2330ms to org.eclipse.jetty.util.log.Slf4jLog
2020-07-17 10:38:58.095  INFO 17112 --- [           main] o.s.b.w.e.j.JettyServletWebServerFactory : Server initialized with port: 9000
2020-07-17 10:38:58.095  INFO 17112 --- [           main] org.eclipse.jetty.server.Server          : jetty-9.4.27.v20200227; built: 2020-02-27T18:37:21.340Z; git: a304fd9f351f337e7c0e2a7c28878dd536149c6c; jvm 1.8.0_112-b15
2020-07-17 10:38:58.127  INFO 17112 --- [           main] o.e.j.s.h.ContextHandler.application     : Initializing Spring embedded WebApplicationContext
2020-07-17 10:38:58.127  INFO 17112 --- [           main] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 1516 ms
2020-07-17 10:38:58.470  INFO 17112 --- [           main] org.eclipse.jetty.server.session         : DefaultSessionIdManager workerName=node0
2020-07-17 10:38:58.470  INFO 17112 --- [           main] org.eclipse.jetty.server.session         : No SessionScavenger set, using defaults
2020-07-17 10:38:58.470  INFO 17112 --- [           main] org.eclipse.jetty.server.session         : node0 Scavenging every 660000ms
2020-07-17 10:38:58.486  INFO 17112 --- [           main] o.e.jetty.server.handler.ContextHandler  : Started o.s.b.w.e.j.JettyEmbeddedWebAppContext@6e0f5f7f{application,/,[file:///C:/Users/RA_K114434/AppData/Local/Temp/2/jetty-docbase.5663045820638747.9000/, jar:file:/D:/bin/IRSYNC/eux-rina-irsync-5.6.2-SNAPSHOT.jar!/BOOT-INF/lib/springfox-swagger-ui-2.9.2.jar!/META-INF/resources],AVAILABLE}
2020-07-17 10:38:58.486  INFO 17112 --- [           main] org.eclipse.jetty.server.Server          : Started @2823ms
2020-07-17 10:38:58.814  INFO 17112 --- [           main] e.e.d.eessi.rina.sdk.cpi.RinaCpiClient   : Building the Object Mapper
2020-07-17 10:38:59.127  INFO 17112 --- [           main] o.s.b.a.e.web.EndpointLinksResolver      : Exposing 5 endpoint(s) beneath base path '/actuator'
2020-07-17 10:38:59.220  INFO 17112 --- [           main] pertySourcedRequestMappingHandlerMapping : Mapped URL path [/v2/api-docs] onto method [springfox.documentation.swagger2.web.Swagger2Controller#getDocumentation(String, HttpServletRequest)]
2020-07-17 10:38:59.298  INFO 17112 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2020-07-17 10:38:59.408  INFO 17112 --- [           main] o.s.s.c.ThreadPoolTaskScheduler          : Initializing ExecutorService 'taskScheduler'
2020-07-17 10:38:59.423  INFO 17112 --- [           main] d.s.w.p.DocumentationPluginsBootstrapper : Context refreshed
2020-07-17 10:38:59.455  INFO 17112 --- [           main] d.s.w.p.DocumentationPluginsBootstrapper : Found 1 custom documentation plugin(s)
2020-07-17 10:38:59.486  INFO 17112 --- [           main] s.d.s.w.s.ApiListingReferenceScanner     : Scanning for api listing references
2020-07-17 10:38:59.627  INFO 17112 --- [           main] o.e.j.s.h.ContextHandler.application     : Initializing Spring DispatcherServlet 'dispatcherServlet'
2020-07-17 10:38:59.627  INFO 17112 --- [           main] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2020-07-17 10:38:59.627  INFO 17112 --- [           main] o.s.web.servlet.DispatcherServlet        : Completed initialization in 0 ms
2020-07-17 10:38:59.658  INFO 17112 --- [           main] o.e.jetty.server.AbstractConnector       : Started ServerConnector@22fcf7ab{HTTP/1.1, (http/1.1)}{127.0.0.1:9000}
2020-07-17 10:38:59.658  INFO 17112 --- [           main] o.s.b.web.embedded.jetty.JettyWebServer  : Jetty started on port(s) 9000 (http/1.1) with context path '/'
2020-07-17 10:38:59.658  INFO 17112 --- [           main] n.n.e.r.admin.EuxRinaIrSyncApplication   : Started EuxRinaIrSyncApplication in 3.549 seconds (JVM running for 4.006)
```

and eventually stuff like this
```
2020-07-17 10:49:01.008  INFO 17112 --- [  qtp2151717-28] n.e.r.a.r.RinaAutomaticUpdatesController :  UP TO DATE : NO:889640782 installedVersion 3.0.72645-20200717T081837 >= availableVersion 3.0.72645-20200717T081837
```
  
## new sync schedule

 Up to RINA v5.4.3, an IR SYNC request to the AP returned either a new IR, or nothing in case there was no newer IR.
 Since RINA v5.6.2, an IR SYNC request to the AP *always* returns the latest IR, regardless of whether it's newer than the one stated in the request. With previous versions of IR SYNC, this would quickly fill up the disks. 
 Now, we request an IR SYNC three times, a quarter over 04, 05 and 06 o'clock every day. You can easily limit this further by changing the cron expression, i.e. "4-6" to "5" to just sync at 05:15 every day, which should be plenty.

 IRSYNC now scheduled using cron expression in config/application.yml
 CSN pushes IR to APs at 2 CET/CEST, so polling for IR from RINA at 30 minutes past the hour of 2 and 4 o'clock is OK

```
cron:
  expression: 0 30 2,4 * * *
```

 IRSYNC wait for new IR contents from AP in seconds now configured in config/application.yml
```
update:
  wait: 360
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
nssm install IRSYNC "%JAVA_HOME%"\bin\java.exe -Djava.util.concurrent.ForkJoinPool.common.parallelism=16 -jar "%CD%\eux-rina-irsync-5.6.2-SNAPSHOT.jar"

nssm set IRSYNC AppDirectory "%CD%"

nssm edit IRSYNC
```

## TODOs

* Ubuntu service implementation
* Reintroduce support for multiple RINAs
* or
* remove the possibly complicated code and config for admin username and password.
* example of the already supported encrypted storage of admin username and password.
