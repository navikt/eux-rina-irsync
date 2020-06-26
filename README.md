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
java -Djava.util.concurrent.ForkJoinPool.common.parallelism=16 -jar eux-rina-irsync-5.6.2-SNAPSHOT.jar
```

If everything is setup correctly, you will first see this
```bash
$ java -Djava.util.concurrent.ForkJoinPool.common.parallelism=16 -jar eux-rina-irsync-5.6.2-SNAPSHOT.jar
12:04:08.156 [main] INFO no.nav.eux.rina.admin.EuxRinaIrSyncApplication - availableProcessors = 8
12:04:08.172 [main] INFO no.nav.eux.rina.admin.EuxRinaIrSyncApplication - parallism of pool   = 16
2020-06-26 09:48:26.687  INFO 9124 --- [           main] n.n.e.r.admin.EuxRinaIrSyncApplication   : The following profiles are active: local
2020-06-26 09:48:29.398  INFO 9124 --- [           main] ptablePropertiesBeanFactoryPostProcessor : Post-processing PropertySource instances
2020-06-26 09:48:29.505  INFO 9124 --- [           main] c.u.j.EncryptablePropertySourceConverter : Converting PropertySource configurationProperties [org.springframework.boot.context.properties.source.ConfigurationPropertySourcesPropertySource] to AOP Proxy
2020-06-26 09:48:29.510  INFO 9124 --- [           main] c.u.j.EncryptablePropertySourceConverter : Converting PropertySource servletConfigInitParams [org.springframework.core.env.PropertySource$StubPropertySource] to EncryptablePropertySourceWrapper
2020-06-26 09:48:29.515  INFO 9124 --- [           main] c.u.j.EncryptablePropertySourceConverter : Converting PropertySource servletContextInitParams [org.springframework.core.env.PropertySource$StubPropertySource] to EncryptablePropertySourceWrapper
2020-06-26 09:48:29.515  INFO 9124 --- [           main] c.u.j.EncryptablePropertySourceConverter : Converting PropertySource systemProperties [org.springframework.core.env.PropertiesPropertySource] to EncryptableMapPropertySourceWrapper
2020-06-26 09:48:29.515  INFO 9124 --- [           main] c.u.j.EncryptablePropertySourceConverter : Converting PropertySource systemEnvironment [org.springframework.boot.env.SystemEnvironmentPropertySourceEnvironmentPostProcessor$OriginAwareSystemEnvironmentPropertySource] to EncryptableMapPropertySourceWrapper
2020-06-26 09:48:29.515  INFO 9124 --- [           main] c.u.j.EncryptablePropertySourceConverter : Converting PropertySource random [org.springframework.boot.env.RandomValuePropertySource] to EncryptablePropertySourceWrapper
2020-06-26 09:48:29.515  INFO 9124 --- [           main] c.u.j.EncryptablePropertySourceConverter : Converting PropertySource applicationConfig: [classpath:/application-local.yaml] [org.springframework.boot.env.OriginTrackedMapPropertySource] to EncryptableMapPropertySourceWrapper
2020-06-26 09:48:29.515  INFO 9124 --- [           main] c.u.j.EncryptablePropertySourceConverter : Converting PropertySource applicationConfig: [classpath:/application.yaml] [org.springframework.boot.env.OriginTrackedMapPropertySource] to EncryptableMapPropertySourceWrapper
2020-06-26 09:48:29.515  INFO 9124 --- [           main] c.u.j.EncryptablePropertySourceConverter : Converting PropertySource class path resource [application-local.secrets.properties] [org.springframework.core.io.support.ResourcePropertySource] to EncryptableMapPropertySourceWrapper
2020-06-26 09:48:30.032  INFO 9124 --- [           main] org.eclipse.jetty.util.log               : Logging initialized @6693ms to org.eclipse.jetty.util.log.Slf4jLog
2020-06-26 09:48:30.394  INFO 9124 --- [           main] o.s.b.w.e.j.JettyServletWebServerFactory : Server initialized with port: 8080
2020-06-26 09:48:30.404  INFO 9124 --- [           main] org.eclipse.jetty.server.Server          : jetty-9.4.24.v20191120; built: 2019-11-20T21:37:49.771Z; git: 363d5f2df3a8a28de40604320230664b9c793c16; jvm 1.8.0_241-b07
2020-06-26 09:48:30.462  INFO 9124 --- [           main] o.e.j.s.h.ContextHandler.application     : Initializing Spring embedded WebApplicationContext
2020-06-26 09:48:30.462  INFO 9124 --- [           main] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 3691 ms
2020-06-26 09:48:30.789  INFO 9124 --- [           main] c.u.j.r.DefaultLazyPropertyResolver      : Property Resolver custom Bean not found with name 'encryptablePropertyResolver'. Initializing Default Property Resolver
2020-06-26 09:48:30.794  INFO 9124 --- [           main] c.u.j.d.DefaultLazyPropertyDetector      : Property Detector custom Bean not found with name 'encryptablePropertyDetector'. Initializing Default Property Detector
2020-06-26 09:48:31.224  INFO 9124 --- [           main] org.eclipse.jetty.server.session         : DefaultSessionIdManager workerName=node0
2020-06-26 09:48:31.224  INFO 9124 --- [           main] org.eclipse.jetty.server.session         : No SessionScavenger set, using defaults
2020-06-26 09:48:31.224  INFO 9124 --- [           main] org.eclipse.jetty.server.session         : node0 Scavenging every 660000ms
2020-06-26 09:48:31.243  INFO 9124 --- [           main] o.e.jetty.server.handler.ContextHandler  : Started o.s.b.w.e.j.JettyEmbeddedWebAppContext@53a84ff4{application,/,[file:///C:/Users/K114434/AppData/Local/Temp/jetty-docbase.5580008935191033928.8080/, jar:file:/C:/Users/K114434/.m2/repository/io/springfox/springfox-swagger-ui/2.9.2/springfox-swagger-ui-2.9.2.jar!/META-INF/resources],AVAILABLE}
2020-06-26 09:48:31.243  INFO 9124 --- [           main] org.eclipse.jetty.server.Server          : Started @7902ms
2020-06-26 09:48:31.277  INFO 9124 --- [           main] c.u.j.encryptor.DefaultLazyEncryptor     : String Encryptor custom Bean not found with name 'jasyptStringEncryptor'. Initializing Default String Encryptor
2020-06-26 09:48:31.307  INFO 9124 --- [           main] c.u.j.encryptor.DefaultLazyEncryptor     : Encryptor config not found for property jasypt.encryptor.algorithm, using default value: PBEWithMD5AndDES
2020-06-26 09:48:31.307  INFO 9124 --- [           main] c.u.j.encryptor.DefaultLazyEncryptor     : Encryptor config not found for property jasypt.encryptor.keyObtentionIterations, using default value: 1000
2020-06-26 09:48:31.307  INFO 9124 --- [           main] c.u.j.encryptor.DefaultLazyEncryptor     : Encryptor config not found for property jasypt.encryptor.poolSize, using default value: 1
2020-06-26 09:48:31.307  INFO 9124 --- [           main] c.u.j.encryptor.DefaultLazyEncryptor     : Encryptor config not found for property jasypt.encryptor.providerName, using default value: null
2020-06-26 09:48:31.307  INFO 9124 --- [           main] c.u.j.encryptor.DefaultLazyEncryptor     : Encryptor config not found for property jasypt.encryptor.providerClassName, using default value: null
2020-06-26 09:48:31.307  INFO 9124 --- [           main] c.u.j.encryptor.DefaultLazyEncryptor     : Encryptor config not found for property jasypt.encryptor.saltGeneratorClassname, using default value: org.jasypt.salt.RandomSaltGenerator
2020-06-26 09:48:31.307  INFO 9124 --- [           main] c.u.j.encryptor.DefaultLazyEncryptor     : Encryptor config not found for property jasypt.encryptor.stringOutputType, using default value: base64
2020-06-26 09:48:32.352  INFO 9124 --- [           main] e.e.d.eessi.rina.sdk.cpi.RinaCpiClient   : Building the Object Mapper
2020-06-26 09:48:32.420  INFO 9124 --- [           main] e.e.d.eessi.rina.sdk.cpi.RinaCpiClient   : Building the Object Mapper
2020-06-26 09:48:32.425  INFO 9124 --- [           main] e.e.d.eessi.rina.sdk.cpi.RinaCpiClient   : Building the Object Mapper
2020-06-26 09:48:33.050  INFO 9124 --- [           main] o.s.b.a.e.web.EndpointLinksResolver      : Exposing 5 endpoint(s) beneath base path '/actuator'
2020-06-26 09:48:33.299  INFO 9124 --- [           main] pertySourcedRequestMappingHandlerMapping : Mapped URL path [/v2/api-docs] onto method [springfox.documentation.swagger2.web.Swagger2Controller#getDocumentation(String, HttpServletRequest)]
2020-06-26 09:48:33.455  INFO 9124 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2020-06-26 09:48:33.719  INFO 9124 --- [           main] o.s.s.c.ThreadPoolTaskScheduler          : Initializing ExecutorService 'taskScheduler'
2020-06-26 09:48:33.753  INFO 9124 --- [           main] d.s.w.p.DocumentationPluginsBootstrapper : Context refreshed
2020-06-26 09:48:33.812  INFO 9124 --- [           main] d.s.w.p.DocumentationPluginsBootstrapper : Found 1 custom documentation plugin(s)
2020-06-26 09:48:33.895  INFO 9124 --- [           main] s.d.s.w.s.ApiListingReferenceScanner     : Scanning for api listing references
2020-06-26 09:48:34.246  INFO 9124 --- [           main] o.e.j.s.h.ContextHandler.application     : Initializing Spring DispatcherServlet 'dispatcherServlet'
2020-06-26 09:48:34.251  INFO 9124 --- [           main] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2020-06-26 09:48:34.271  INFO 9124 --- [           main] o.s.web.servlet.DispatcherServlet        : Completed initialization in 20 ms
2020-06-26 09:48:34.329  INFO 9124 --- [           main] n.n.e.r.admin.EuxRinaIrSyncApplication   : Started EuxRinaIrSyncApplication in 9.419 seconds (JVM running for 10.987)
2020-06-26 09:48:34.334  INFO 9124 --- [           main] n.n.e.r.admin.EuxRinaIrSyncApplication   : availableProcessors = 4
2020-06-26 09:48:34.334  INFO 9124 --- [           main] n.n.e.r.admin.EuxRinaIrSyncApplication   : parallism of pool   = 16
2020-06-26 09:48:34.334  INFO 9124 --- [           main] n.n.e.r.admin.EuxRinaIrSyncApplication   : cron.expression = 0 0,50 4-11 * * *
2020-06-26 09:48:34.334  INFO 9124 --- [           main] n.n.e.r.admin.EuxRinaIrSyncApplication   : update.wait = 240 seconds, which is 4 minutes
2020-06-26 09:50:00.007  INFO 9124 --- [   scheduling-1] n.e.r.a.r.RinaAutomaticUpdatesController : running scheduled
```

and eventually stuff like this
```
2020-06-26 09:50:00.418  INFO 9124 --- [tp1088649990-18] n.e.r.a.r.RinaAutomaticUpdatesController : calling automaticUpdate() ...
2020-06-26 09:50:00.418  INFO 9124 --- [tp1088649990-18] n.e.r.a.r.RinaAutomaticUpdatesController : starting actual automaticUpdate()
2020-06-26 09:50:00.418  INFO 9124 --- [tp1088649990-18] n.e.r.a.r.RinaAutomaticUpdatesController : institutions = [NO:NAVAT05, NO:NAVAT07, NO:NAVAT08]
2020-06-26 09:50:01.341  INFO 9124 --- [tp1088649990-18] n.e.r.a.r.RinaAutomaticUpdatesController : Requesting version [3.0.67452|as|3.0.67452] for CI [NO:NAVAT07]
2020-06-26 09:50:01.673  INFO 9124 --- [tp1088649990-18] n.e.r.a.r.RinaAutomaticUpdatesController : Requesting version [3.0.69133|as|3.0.69133] for CI [NO:NAVAT08]
2020-06-26 09:50:01.961  INFO 9124 --- [tp1088649990-18] n.e.r.a.r.RinaAutomaticUpdatesController : Requesting version [3.0.67452|as|3.0.67452] for CI [NO:NAVAT05]
2020-06-26 09:50:02.092  INFO 9124 --- [tp1088649990-18] n.e.r.a.r.RinaAutomaticUpdatesController : Waiting for 4 minutes for new versions to arrive in RINAs
2020-06-26 09:54:02.095  INFO 9124 --- [tp1088649990-18] n.e.r.a.r.RinaAutomaticUpdatesController : Done waiting. Installing new versions.
2020-06-26 09:54:02.256  INFO 9124 --- [tp1088649990-18] n.e.r.a.r.RinaAutomaticUpdatesController : CI: NO:NAVAT07 Installing now... resource version: 3.0.69144-20200626T075316
2020-06-26 09:54:02.394  INFO 9124 --- [nPool-worker-13] n.e.r.a.r.RinaAutomaticUpdatesController : CI: NO:NAVAT08 Installing now... resource version: 3.0.69144-20200626T075332
2020-06-26 09:54:57.702  INFO 9124 --- [tp1088649990-18] n.e.r.a.r.RinaAutomaticUpdatesController : CI: NO:NAVAT07 Installed resourceId[dataorganisations] resourceType [organisation] resourceVersion [3.0.69144-20200626T075316]
2020-06-26 09:54:58.510  INFO 9124 --- [nPool-worker-13] n.e.r.a.r.RinaAutomaticUpdatesController : CI: NO:NAVAT08 Installed resourceId[dataorganisations] resourceType [organisation] resourceVersion [3.0.69144-20200626T075332]
2020-06-26 09:54:58.510  INFO 9124 --- [tp1088649990-18] n.e.r.a.r.RinaAutomaticUpdatesController : Done installing.
2020-06-26 09:54:58.608  INFO 9124 --- [nPool-worker-13] .n.e.r.a.r.RinaCpiSynchronizationService :  UP TO DATE : NO:NAVAT05 installedVersion 3.0.67452-20200618T111412 is >= available 3.0.67452-20200618T111836
2020-06-26 09:54:58.622  INFO 9124 --- [tp1088649990-18] .n.e.r.a.r.RinaCpiSynchronizationService :  UP TO DATE : NO:NAVAT07 installedVersion 3.0.69144-20200626T075316 is >= available 3.0.69144-20200626T075316
2020-06-26 09:54:58.676  INFO 9124 --- [onPool-worker-4] .n.e.r.a.r.RinaCpiSynchronizationService :  UP TO DATE : NO:NAVAT08 installedVersion 3.0.69144-20200626T075332 is >= available 3.0.69144-20200626T075332
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
