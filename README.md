# eux-rina-irsync

Small app offering scheduled RINA IR SYNC (and a REST API with Swagger) using RINA SDK.

We finally have a new version, 6.2.4-SNAPSHOT, which supports both RINA 2019 and RINA 2020 ready and will publish it later today, April 8th, 2021.

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
└── eux-rina-irsync-6.2.4-SNAPSHOT.jar
```
* download the eux-rina-irsync-6.2.4-SNAPSHOT.jar
* adopt above directory structure
* adopt config/application.yml to your environment
* run (we use RINA's Java 8 to run this)
```bash
java -Djasypt.encryptor.password=t8-secret -Djava.util.concurrent.ForkJoinPool.common.parallelism=16 -Dlogback.statusListenerClass=ch.qos.logback.core.status.OnConsoleStatusListener -jar eux-rina-irsync-6.2.4-SNAPSHOT.jar
```

If everything is setup correctly, you will first see this
```bash
"C:\Program Files\Java\jdk1.8.0_281\bin\java.exe" -Djasypt.encryptor.password=t8-secret -Djava.util.concurrent.ForkJoinPool.common.parallelism=16 -Dlogback.statusListenerClass=ch.qos.logback.core.status.OnConsoleStatusListener -Dspring.main.banner-mode=OFF -Dspring.profiles.active=local -Dspring.output.ansi.enabled=always ... no.nav.eux.rina.admin.EuxRinaIrSyncApplication
16:11:55,399 |-INFO in ch.qos.logback.classic.LoggerContext[default] - Could NOT find resource [logback-test.xml]
16:11:55,432 |-INFO in ch.qos.logback.classic.LoggerContext[default] - Could NOT find resource [logback.groovy]
16:11:55,432 |-INFO in ch.qos.logback.classic.LoggerContext[default] - Could NOT find resource [logback.xml]
16:11:55,446 |-INFO in ch.qos.logback.classic.BasicConfigurator@5fcfe4b2 - Setting up default configuration.
2021-04-08 16:11:57.229  INFO 19176 --- [           main] n.n.e.r.admin.EuxRinaIrSyncApplication   : Starting EuxRinaIrSyncApplication on a34duvw03454 with PID 19176 (W:\IdeaProjects\eux-rina-irsync\target\classes started by K114434 in W:\IdeaProjects\eux-rina-irsync)
2021-04-08 16:11:57.238  INFO 19176 --- [           main] n.n.e.r.admin.EuxRinaIrSyncApplication   : The following profiles are active: local
2021-04-08 16:12:00.405  INFO 19176 --- [           main] ptablePropertiesBeanFactoryPostProcessor : Post-processing PropertySource instances
2021-04-08 16:12:00.503  INFO 19176 --- [           main] c.u.j.EncryptablePropertySourceConverter : Converting PropertySource configurationProperties [org.springframework.boot.context.properties.source.ConfigurationPropertySourcesPropertySource] to AOP Proxy
2021-04-08 16:12:00.503  INFO 19176 --- [           main] c.u.j.EncryptablePropertySourceConverter : Converting PropertySource servletConfigInitParams [org.springframework.core.env.PropertySource$StubPropertySource] to EncryptablePropertySourceWrapper
2021-04-08 16:12:00.504  INFO 19176 --- [           main] c.u.j.EncryptablePropertySourceConverter : Converting PropertySource servletContextInitParams [org.springframework.core.env.PropertySource$StubPropertySource] to EncryptablePropertySourceWrapper
2021-04-08 16:12:00.504  INFO 19176 --- [           main] c.u.j.EncryptablePropertySourceConverter : Converting PropertySource systemProperties [org.springframework.core.env.PropertiesPropertySource] to EncryptableMapPropertySourceWrapper
2021-04-08 16:12:00.504  INFO 19176 --- [           main] c.u.j.EncryptablePropertySourceConverter : Converting PropertySource systemEnvironment [org.springframework.boot.env.SystemEnvironmentPropertySourceEnvironmentPostProcessor$OriginAwareSystemEnvironmentPropertySource] to EncryptableMapPropertySourceWrapper
2021-04-08 16:12:00.504  INFO 19176 --- [           main] c.u.j.EncryptablePropertySourceConverter : Converting PropertySource random [org.springframework.boot.env.RandomValuePropertySource] to EncryptablePropertySourceWrapper
2021-04-08 16:12:00.504  INFO 19176 --- [           main] c.u.j.EncryptablePropertySourceConverter : Converting PropertySource applicationConfig: [file:./config/application.yml] [org.springframework.boot.env.OriginTrackedMapPropertySource] to EncryptableMapPropertySourceWrapper
2021-04-08 16:12:00.769  INFO 19176 --- [           main] c.u.j.r.DefaultLazyPropertyResolver      : Property Resolver custom Bean not found with name 'encryptablePropertyResolver'. Initializing Default Property Resolver
2021-04-08 16:12:00.773  INFO 19176 --- [           main] c.u.j.d.DefaultLazyPropertyDetector      : Property Detector custom Bean not found with name 'encryptablePropertyDetector'. Initializing Default Property Detector
2021-04-08 16:12:00.978  INFO 19176 --- [           main] org.eclipse.jetty.util.log               : Logging initialized @6254ms to org.eclipse.jetty.util.log.Slf4jLog
2021-04-08 16:12:01.181  INFO 19176 --- [           main] o.s.b.w.e.j.JettyServletWebServerFactory : Server initialized with port: 8080
2021-04-08 16:12:01.199  INFO 19176 --- [           main] org.eclipse.jetty.server.Server          : jetty-9.4.27.v20200227; built: 2020-02-27T18:37:21.340Z; git: a304fd9f351f337e7c0e2a7c28878dd536149c6c; jvm 1.8.0_281-b09
2021-04-08 16:12:01.260  INFO 19176 --- [           main] o.e.j.s.h.ContextHandler.application     : Initializing Spring embedded WebApplicationContext
2021-04-08 16:12:01.261  INFO 19176 --- [           main] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 3935 ms
2021-04-08 16:12:01.915  INFO 19176 --- [           main] org.eclipse.jetty.server.session         : DefaultSessionIdManager workerName=node0
2021-04-08 16:12:01.915  INFO 19176 --- [           main] org.eclipse.jetty.server.session         : No SessionScavenger set, using defaults
2021-04-08 16:12:01.917  INFO 19176 --- [           main] org.eclipse.jetty.server.session         : node0 Scavenging every 660000ms
2021-04-08 16:12:01.933  INFO 19176 --- [           main] o.e.jetty.server.handler.ContextHandler  : Started o.s.b.w.e.j.JettyEmbeddedWebAppContext@4832f03b{application,/,[file:///C:/Users/K114434/AppData/Local/Temp/jetty-docbase.254437337969278096.8080/, jar:file:/C:/Users/K114434/.m2/repository/io/springfox/springfox-swagger-ui/2.9.2/springfox-swagger-ui-2.9.2.jar!/META-INF/resources],AVAILABLE}
2021-04-08 16:12:01.934  INFO 19176 --- [           main] org.eclipse.jetty.server.Server          : Started @7210ms
2021-04-08 16:12:01.962  INFO 19176 --- [           main] c.u.j.encryptor.DefaultLazyEncryptor     : String Encryptor custom Bean not found with name 'jasyptStringEncryptor'. Initializing Default String Encryptor
2021-04-08 16:12:01.986  INFO 19176 --- [           main] c.u.j.encryptor.DefaultLazyEncryptor     : Encryptor config not found for property jasypt.encryptor.algorithm, using default value: PBEWithMD5AndDES
2021-04-08 16:12:01.986  INFO 19176 --- [           main] c.u.j.encryptor.DefaultLazyEncryptor     : Encryptor config not found for property jasypt.encryptor.keyObtentionIterations, using default value: 1000
2021-04-08 16:12:01.986  INFO 19176 --- [           main] c.u.j.encryptor.DefaultLazyEncryptor     : Encryptor config not found for property jasypt.encryptor.poolSize, using default value: 1
2021-04-08 16:12:01.987  INFO 19176 --- [           main] c.u.j.encryptor.DefaultLazyEncryptor     : Encryptor config not found for property jasypt.encryptor.providerName, using default value: null
2021-04-08 16:12:01.987  INFO 19176 --- [           main] c.u.j.encryptor.DefaultLazyEncryptor     : Encryptor config not found for property jasypt.encryptor.providerClassName, using default value: null
2021-04-08 16:12:01.987  INFO 19176 --- [           main] c.u.j.encryptor.DefaultLazyEncryptor     : Encryptor config not found for property jasypt.encryptor.saltGeneratorClassname, using default value: org.jasypt.salt.RandomSaltGenerator
2021-04-08 16:12:01.988  INFO 19176 --- [           main] c.u.j.encryptor.DefaultLazyEncryptor     : Encryptor config not found for property jasypt.encryptor.stringOutputType, using default value: base64
2021-04-08 16:12:02.378  INFO 19176 --- [           main] n.n.e.r.admin.http.RestTemplateFactory   : will call myself at http://127.0.0.1:8080
2021-04-08 16:12:02.822  INFO 19176 --- [           main] e.e.d.eessi.rina.sdk.cpi.RinaCpiClient   : Building the Object Mapper
2021-04-08 16:12:02.866  INFO 19176 --- [           main] e.e.d.eessi.rina.sdk.cpi.RinaCpiClient   : Building the Object Mapper
2021-04-08 16:12:02.870  INFO 19176 --- [           main] e.e.d.eessi.rina.sdk.cpi.RinaCpiClient   : Building the Object Mapper
2021-04-08 16:12:02.873  INFO 19176 --- [           main] e.e.d.eessi.rina.sdk.cpi.RinaCpiClient   : Building the Object Mapper
2021-04-08 16:12:03.449  INFO 19176 --- [           main] o.s.b.a.e.web.EndpointLinksResolver      : Exposing 5 endpoint(s) beneath base path '/actuator'
2021-04-08 16:12:03.672  INFO 19176 --- [           main] pertySourcedRequestMappingHandlerMapping : Mapped URL path [/v2/api-docs] onto method [springfox.documentation.swagger2.web.Swagger2Controller#getDocumentation(String, HttpServletRequest)]
2021-04-08 16:12:03.809  INFO 19176 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2021-04-08 16:12:04.060  INFO 19176 --- [           main] o.s.s.c.ThreadPoolTaskScheduler          : Initializing ExecutorService 'taskScheduler'
2021-04-08 16:12:04.089  INFO 19176 --- [           main] d.s.w.p.DocumentationPluginsBootstrapper : Context refreshed
2021-04-08 16:12:04.156  INFO 19176 --- [           main] d.s.w.p.DocumentationPluginsBootstrapper : Found 1 custom documentation plugin(s)
2021-04-08 16:12:04.226  INFO 19176 --- [           main] s.d.s.w.s.ApiListingReferenceScanner     : Scanning for api listing references
2021-04-08 16:12:04.538  INFO 19176 --- [           main] o.e.j.s.h.ContextHandler.application     : Initializing Spring DispatcherServlet 'dispatcherServlet'
2021-04-08 16:12:04.539  INFO 19176 --- [           main] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2021-04-08 16:12:04.560  INFO 19176 --- [           main] o.s.web.servlet.DispatcherServlet        : Completed initialization in 20 ms
2021-04-08 16:12:04.613  INFO 19176 --- [           main] o.e.jetty.server.AbstractConnector       : Started ServerConnector@5b9396d3{HTTP/1.1, (http/1.1)}{127.0.0.1:8080}
2021-04-08 16:12:04.616  INFO 19176 --- [           main] o.s.b.web.embedded.jetty.JettyWebServer  : Jetty started on port(s) 8080 (http/1.1) with context path '/'
2021-04-08 16:12:04.625  INFO 19176 --- [           main] n.n.e.r.admin.EuxRinaIrSyncApplication   : Started EuxRinaIrSyncApplication in 8.556 seconds (JVM running for 9.902)
2021-04-08 16:12:04.629  INFO 19176 --- [           main] n.n.e.r.admin.EuxRinaIrSyncApplication   : availableProcessors = 4
2021-04-08 16:12:04.630  INFO 19176 --- [           main] n.n.e.r.admin.EuxRinaIrSyncApplication   : parallism of pool   = 16
2021-04-08 16:12:04.630  INFO 19176 --- [           main] n.n.e.r.admin.EuxRinaIrSyncApplication   : cron.expression = 0 0/15 2-18 * * *
2021-04-08 16:12:04.630  INFO 19176 --- [           main] n.n.e.r.admin.EuxRinaIrSyncApplication   : update.wait = 120 seconds, which is 2 minutes
2021-04-08 16:15:00.023  INFO 19176 --- [   scheduling-1] n.e.r.a.r.RinaAutomaticUpdatesController : running scheduled
2021-04-08 16:15:00.691  INFO 19176 --- [qtp162667475-19] n.e.r.a.r.RinaAutomaticUpdatesController : calling automaticUpdate() ...
2021-04-08 16:15:00.691  INFO 19176 --- [qtp162667475-19] n.e.r.a.r.RinaAutomaticUpdatesController : starting actual automaticUpdate()
2021-04-08 16:15:00.691  INFO 19176 --- [qtp162667475-19] n.e.r.a.r.RinaAutomaticUpdatesController : institutions = [NO:NAVAT07, NO:NAVAT08, NO:NAVAT05, NO:NAVAT06]
2021-04-08 16:15:01.451  INFO 19176 --- [qtp162667475-19] n.e.r.a.r.RinaAutomaticUpdatesController : ciVersions: NO:NAVAT07:3.0.81915
2021-04-08 16:15:01.451  INFO 19176 --- [qtp162667475-19] n.e.r.a.r.RinaAutomaticUpdatesController : ciVersions: NO:NAVAT08:3.0.81915
2021-04-08 16:15:01.451  INFO 19176 --- [qtp162667475-19] n.e.r.a.r.RinaAutomaticUpdatesController : ciVersions: NO:NAVAT05:3.0.81915
2021-04-08 16:15:01.451  INFO 19176 --- [qtp162667475-19] n.e.r.a.r.RinaAutomaticUpdatesController : ciVersions: NO:NAVAT06:3.0.81915
2021-04-08 16:15:01.574  INFO 19176 --- [qtp162667475-19] n.e.r.a.r.RinaAutomaticUpdatesController : Requesting version [3.0.81915|as|3.0.81915] for CI [NO:NAVAT07]
2021-04-08 16:15:01.806  INFO 19176 --- [qtp162667475-19] n.e.r.a.r.RinaAutomaticUpdatesController : Requesting version [3.0.81915|as|3.0.81915] for CI [NO:NAVAT08]
2021-04-08 16:15:02.034  INFO 19176 --- [qtp162667475-19] n.e.r.a.r.RinaAutomaticUpdatesController : Requesting version [3.0.81915|as|3.0.81915] for CI [NO:NAVAT05]
2021-04-08 16:15:02.200  INFO 19176 --- [qtp162667475-19] n.e.r.a.r.RinaAutomaticUpdatesController : Requesting version [3.0.81915|as|3.0.81915] for CI [NO:NAVAT06]
2021-04-08 16:15:02.286  INFO 19176 --- [qtp162667475-19] n.e.r.a.r.RinaAutomaticUpdatesController : Waiting for 2 minutes for new versions to arrive in RINAs
2021-04-08 16:17:02.288  INFO 19176 --- [qtp162667475-19] n.e.r.a.r.RinaAutomaticUpdatesController : Done waiting. Installing new versions.
2021-04-08 16:17:02.568  INFO 19176 --- [qtp162667475-19] n.e.r.a.r.RinaAutomaticUpdatesController : Done installing.
2021-04-08 16:17:02.617  INFO 19176 --- [onPool-worker-6] .n.e.r.a.r.RinaCpiSynchronizationService :  UP TO DATE : NO:NAVAT08 installedVersion 3.0.81915-20210408T123819 is >= available 3.0.81915-20210408T141651
2021-04-08 16:17:02.723  INFO 19176 --- [onPool-worker-8] .n.e.r.a.r.RinaCpiSynchronizationService :  UP TO DATE : NO:NAVAT07 installedVersion 3.0.81915-20210408T125236 is >= available 3.0.81915-20210408T140751
2021-04-08 16:17:02.733  INFO 19176 --- [nPool-worker-13] .n.e.r.a.r.RinaCpiSynchronizationService :  UP TO DATE : NO:NAVAT06 installedVersion 3.0.81915-20210408T072138 is >= available 3.0.81915-20210408T072138
2021-04-08 16:17:02.798  INFO 19176 --- [qtp162667475-19] .n.e.r.a.r.RinaCpiSynchronizationService :  UP TO DATE : NO:NAVAT05 installedVersion 3.0.81915-20210408T121036 is >= available 3.0.81915-20210408T140701

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

* using 6.2.4-SNAPSHOT, you can address many RINAs, but they all have to have to same password... I think.

* we use http://www.jasypt.org/ (jasypt-spring-boot-starter) to support encrypted passwords.

## Windows service implementation

* We use 
```
nssm install IRSYNC "%JAVA_HOME%"\bin\java.exe -Djava.util.concurrent.ForkJoinPool.common.parallelism=16 -jar "%CD%\eux-rina-irsync-6.2.4-SNAPSHOT.jar"

nssm set IRSYNC AppDirectory "%CD%"

nssm edit IRSYNC
```

## TODOs

* Ubuntu service implementation
* Reintroduce support for multiple RINAs
* or
* remove the possibly complicated code and config for admin username and password.
* example of the already supported encrypted storage of admin username and password.
