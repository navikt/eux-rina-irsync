# eux-rina-irsync

Small app offering scheduled RINA IR SYNC (and a REST API with Swagger) using RINA SDK.

We finally have a new version, 6.2.5-SNAPSHOT, which supports both RINA 2019 and RINA 2020

## bug?

It seems that when changing cas.properties in RINA 2020, the new CAS_SERVICE_ID has to be passed.

public class RinaCpiAuthenticationService {
RINA 2019: private static final String CAS_SERVICE_ID = "../portal/cas/cpi";
RINA 2020: private static final String CAS_SERVICE_ID = "../portal_new/cas/cpi";

We're currently investigating why this is and if we can easily tweak this in cas.properties, but we might also make this another tenant property and just make it configurable. 

That would be our final release of this stream of IR SYNC. Soon™

## change log

* working log to file, configured in config/application.yml and logback-spring.xml.
NB If you have redirected stdout in NSSM so far, you will likely have to turn that off now.

* working port change in application.yml
If you want to run IR SYNC on a different port, you now can and it works ;-)

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
└── eux-rina-irsync-6.2.5-SNAPSHOT.jar
```
* download the eux-rina-irsync-6.2.5-SNAPSHOT.jar
* adopt above directory structure
* adopt config/application.yml to your environment
* run (we use RINA's Java 8 to run this)
```bash
java -Djasypt.encryptor.password=t8-secret -Djava.util.concurrent.ForkJoinPool.common.parallelism=16 -Dlogback.statusListenerClass=ch.qos.logback.core.status.OnConsoleStatusListener -jar eux-rina-irsync-6.2.4-SNAPSHOT.jar
```

If everything is setup correctly, you will first see this
```bash
"C:\Program Files\Java\jdk1.8.0_281\bin\java.exe" -Djasypt.encryptor.password=t8-secret -Djava.util.concurrent.ForkJoinPool.common.parallelism=16 -Dlogback.statusListenerClass=ch.qos.logback.core.status.OnConsoleStatusListener -Dspring.main.banner-mode=OFF -Dspring.profiles.active=local -Dspring.output.ansi.enabled=always "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA 2020.3.1\lib\idea_rt.jar=62853:C:\Program Files\JetBrains\IntelliJ IDEA 2020.3.1\bin" -Dfile.encoding=UTF-8 -classpath "C:\Program Files\Java\jdk1.8.0_281\jre\lib\charsets.jar;C:\Program Files\Java\jdk1.8.0_281\jre\lib\deploy.jar;C:\Program Files\Java\jdk1.8.0_281\jre\lib\ext\access-bridge-64.jar;C:\Program Files\Java\jdk1.8.0_281\jre\lib\ext\cldrdata.jar;C:\Program Files\Java\jdk1.8.0_281\jre\lib\ext\dnsns.jar;C:\Program Files\Java\jdk1.8.0_281\jre\lib\ext\jaccess.jar;C:\Program Files\Java\jdk1.8.0_281\jre\lib\ext\jfxrt.jar;C:\Program Files\Java\jdk1.8.0_281\jre\lib\ext\localedata.jar;C:\Program Files\Java\jdk1.8.0_281\jre\lib\ext\nashorn.jar;C:\Program Files\Java\jdk1.8.0_281\jre\lib\ext\sunec.jar;C:\Program Files\Java\jdk1.8.0_281\jre\lib\ext\sunjce_provider.jar;C:\Program Files\Java\jdk1.8.0_281\jre\lib\ext\sunmscapi.jar;C:\Program Files\Java\jdk1.8.0_281\jre\lib\ext\sunpkcs11.jar;C:\Program Files\Java\jdk1.8.0_281\jre\lib\ext\zipfs.jar;C:\Program Files\Java\jdk1.8.0_281\jre\lib\javaws.jar;C:\Program Files\Java\jdk1.8.0_281\jre\lib\jce.jar;C:\Program Files\Java\jdk1.8.0_281\jre\lib\jfr.jar;C:\Program Files\Java\jdk1.8.0_281\jre\lib\jfxswt.jar;C:\Program Files\Java\jdk1.8.0_281\jre\lib\jsse.jar;C:\Program Files\Java\jdk1.8.0_281\jre\lib\management-agent.jar;C:\Program Files\Java\jdk1.8.0_281\jre\lib\plugin.jar;C:\Program Files\Java\jdk1.8.0_281\jre\lib\resources.jar;C:\Program Files\Java\jdk1.8.0_281\jre\lib\rt.jar;W:\IdeaProjects\eux-rina-irsync\target\classes;C:\Users\K114434\.m2\repository\eu\ec\dgempl\eessi\sdk\eessi-rina-cpi-sdk\5.6.3\eessi-rina-cpi-sdk-5.6.3.jar;C:\Users\K114434\.m2\repository\com\fasterxml\jackson\datatype\jackson-datatype-jdk8\2.10.3\jackson-datatype-jdk8-2.10.3.jar;C:\Users\K114434\.m2\repository\org\assertj\assertj-guava\3.2.0\assertj-guava-3.2.0.jar;C:\Users\K114434\.m2\repository\commons-io\commons-io\2.6\commons-io-2.6.jar;C:\Users\K114434\.m2\repository\org\apache\logging\log4j\log4j-api\2.12.1\log4j-api-2.12.1.jar;C:\Users\K114434\.m2\repository\org\apache\logging\log4j\log4j-core\2.12.1\log4j-core-2.12.1.jar;C:\Users\K114434\.m2\repository\org\apache\logging\log4j\log4j-jcl\2.12.1\log4j-jcl-2.12.1.jar;C:\Users\K114434\.m2\repository\io\swagger\swagger-annotations\1.5.15\swagger-annotations-1.5.15.jar;C:\Users\K114434\.m2\repository\org\springframework\spring-web\5.2.5.RELEASE\spring-web-5.2.5.RELEASE.jar;C:\Users\K114434\.m2\repository\org\springframework\spring-beans\5.2.5.RELEASE\spring-beans-5.2.5.RELEASE.jar;C:\Users\K114434\.m2\repository\com\fasterxml\jackson\jaxrs\jackson-jaxrs-json-provider\2.10.3\jackson-jaxrs-json-provider-2.10.3.jar;C:\Users\K114434\.m2\repository\com\fasterxml\jackson\jaxrs\jackson-jaxrs-base\2.10.3\jackson-jaxrs-base-2.10.3.jar;C:\Users\K114434\.m2\repository\com\fasterxml\jackson\module\jackson-module-jaxb-annotations\2.10.3\jackson-module-jaxb-annotations-2.10.3.jar;C:\Users\K114434\.m2\repository\com\fasterxml\jackson\datatype\jackson-datatype-jsr310\2.10.3\jackson-datatype-jsr310-2.10.3.jar;C:\Users\K114434\.m2\repository\org\apache\commons\commons-lang3\3.8.1\commons-lang3-3.8.1.jar;C:\Users\K114434\.m2\repository\commons-beanutils\commons-beanutils\1.9.4\commons-beanutils-1.9.4.jar;C:\Users\K114434\.m2\repository\commons-logging\commons-logging\1.2\commons-logging-1.2.jar;C:\Users\K114434\.m2\repository\commons-collections\commons-collections\3.2.2\commons-collections-3.2.2.jar;C:\Users\K114434\.m2\repository\org\springframework\boot\spring-boot-starter-web\2.2.6.RELEASE\spring-boot-starter-web-2.2.6.RELEASE.jar;C:\Users\K114434\.m2\repository\org\springframework\boot\spring-boot-starter\2.2.6.RELEASE\spring-boot-starter-2.2.6.RELEASE.jar;C:\Users\K114434\.m2\repository\org\springframework\boot\spring-boot\2.2.6.RELEASE\spring-boot-2.2.6.RELEASE.jar;C:\Users\K114434\.m2\repository\org\springframework\boot\spring-boot-autoconfigure\2.2.6.RELEASE\spring-boot-autoconfigure-2.2.6.RELEASE.jar;C:\Users\K114434\.m2\repository\org\springframework\boot\spring-boot-starter-logging\2.2.6.RELEASE\spring-boot-starter-logging-2.2.6.RELEASE.jar;C:\Users\K114434\.m2\repository\org\apache\logging\log4j\log4j-to-slf4j\2.12.1\log4j-to-slf4j-2.12.1.jar;C:\Users\K114434\.m2\repository\org\slf4j\jul-to-slf4j\1.7.30\jul-to-slf4j-1.7.30.jar;C:\Users\K114434\.m2\repository\jakarta\annotation\jakarta.annotation-api\1.3.5\jakarta.annotation-api-1.3.5.jar;C:\Users\K114434\.m2\repository\org\yaml\snakeyaml\1.25\snakeyaml-1.25.jar;C:\Users\K114434\.m2\repository\org\springframework\boot\spring-boot-starter-json\2.2.6.RELEASE\spring-boot-starter-json-2.2.6.RELEASE.jar;C:\Users\K114434\.m2\repository\com\fasterxml\jackson\module\jackson-module-parameter-names\2.10.3\jackson-module-parameter-names-2.10.3.jar;C:\Users\K114434\.m2\repository\org\springframework\boot\spring-boot-starter-validation\2.2.6.RELEASE\spring-boot-starter-validation-2.2.6.RELEASE.jar;C:\Users\K114434\.m2\repository\jakarta\validation\jakarta.validation-api\2.0.2\jakarta.validation-api-2.0.2.jar;C:\Users\K114434\.m2\repository\org\hibernate\validator\hibernate-validator\6.0.18.Final\hibernate-validator-6.0.18.Final.jar;C:\Users\K114434\.m2\repository\org\jboss\logging\jboss-logging\3.4.1.Final\jboss-logging-3.4.1.Final.jar;C:\Users\K114434\.m2\repository\org\springframework\spring-webmvc\5.2.5.RELEASE\spring-webmvc-5.2.5.RELEASE.jar;C:\Users\K114434\.m2\repository\org\springframework\spring-aop\5.2.5.RELEASE\spring-aop-5.2.5.RELEASE.jar;C:\Users\K114434\.m2\repository\org\springframework\spring-context\5.2.5.RELEASE\spring-context-5.2.5.RELEASE.jar;C:\Users\K114434\.m2\repository\org\springframework\spring-expression\5.2.5.RELEASE\spring-expression-5.2.5.RELEASE.jar;C:\Users\K114434\.m2\repository\org\springframework\boot\spring-boot-starter-jetty\2.2.6.RELEASE\spring-boot-starter-jetty-2.2.6.RELEASE.jar;C:\Users\K114434\.m2\repository\jakarta\servlet\jakarta.servlet-api\4.0.3\jakarta.servlet-api-4.0.3.jar;C:\Users\K114434\.m2\repository\jakarta\websocket\jakarta.websocket-api\1.1.2\jakarta.websocket-api-1.1.2.jar;C:\Users\K114434\.m2\repository\org\eclipse\jetty\jetty-servlets\9.4.27.v20200227\jetty-servlets-9.4.27.v20200227.jar;C:\Users\K114434\.m2\repository\org\eclipse\jetty\jetty-continuation\9.4.27.v20200227\jetty-continuation-9.4.27.v20200227.jar;C:\Users\K114434\.m2\repository\org\eclipse\jetty\jetty-http\9.4.27.v20200227\jetty-http-9.4.27.v20200227.jar;C:\Users\K114434\.m2\repository\org\eclipse\jetty\jetty-util\9.4.27.v20200227\jetty-util-9.4.27.v20200227.jar;C:\Users\K114434\.m2\repository\org\eclipse\jetty\jetty-io\9.4.27.v20200227\jetty-io-9.4.27.v20200227.jar;C:\Users\K114434\.m2\repository\org\eclipse\jetty\jetty-webapp\9.4.27.v20200227\jetty-webapp-9.4.27.v20200227.jar;C:\Users\K114434\.m2\repository\org\eclipse\jetty\jetty-xml\9.4.27.v20200227\jetty-xml-9.4.27.v20200227.jar;C:\Users\K114434\.m2\repository\org\eclipse\jetty\jetty-servlet\9.4.27.v20200227\jetty-servlet-9.4.27.v20200227.jar;C:\Users\K114434\.m2\repository\org\eclipse\jetty\jetty-security\9.4.27.v20200227\jetty-security-9.4.27.v20200227.jar;C:\Users\K114434\.m2\repository\org\eclipse\jetty\jetty-server\9.4.27.v20200227\jetty-server-9.4.27.v20200227.jar;C:\Users\K114434\.m2\repository\org\eclipse\jetty\websocket\websocket-server\9.4.27.v20200227\websocket-server-9.4.27.v20200227.jar;C:\Users\K114434\.m2\repository\org\eclipse\jetty\websocket\websocket-common\9.4.27.v20200227\websocket-common-9.4.27.v20200227.jar;C:\Users\K114434\.m2\repository\org\eclipse\jetty\websocket\websocket-api\9.4.27.v20200227\websocket-api-9.4.27.v20200227.jar;C:\Users\K114434\.m2\repository\org\eclipse\jetty\websocket\websocket-client\9.4.27.v20200227\websocket-client-9.4.27.v20200227.jar;C:\Users\K114434\.m2\repository\org\eclipse\jetty\jetty-client\9.4.27.v20200227\jetty-client-9.4.27.v20200227.jar;C:\Users\K114434\.m2\repository\org\eclipse\jetty\websocket\websocket-servlet\9.4.27.v20200227\websocket-servlet-9.4.27.v20200227.jar;C:\Users\K114434\.m2\repository\org\eclipse\jetty\websocket\javax-websocket-server-impl\9.4.27.v20200227\javax-websocket-server-impl-9.4.27.v20200227.jar;C:\Users\K114434\.m2\repository\org\eclipse\jetty\jetty-annotations\9.4.27.v20200227\jetty-annotations-9.4.27.v20200227.jar;C:\Users\K114434\.m2\repository\org\eclipse\jetty\jetty-plus\9.4.27.v20200227\jetty-plus-9.4.27.v20200227.jar;C:\Users\K114434\.m2\repository\org\ow2\asm\asm\7.2\asm-7.2.jar;C:\Users\K114434\.m2\repository\org\ow2\asm\asm-commons\7.2\asm-commons-7.2.jar;C:\Users\K114434\.m2\repository\org\ow2\asm\asm-tree\7.2\asm-tree-7.2.jar;C:\Users\K114434\.m2\repository\org\ow2\asm\asm-analysis\7.2\asm-analysis-7.2.jar;C:\Users\K114434\.m2\repository\org\eclipse\jetty\websocket\javax-websocket-client-impl\9.4.27.v20200227\javax-websocket-client-impl-9.4.27.v20200227.jar;C:\Users\K114434\.m2\repository\org\mortbay\jasper\apache-el\8.5.49\apache-el-8.5.49.jar;C:\Users\K114434\.m2\repository\org\springframework\boot\spring-boot-starter-actuator\2.2.6.RELEASE\spring-boot-starter-actuator-2.2.6.RELEASE.jar;C:\Users\K114434\.m2\repository\org\springframework\boot\spring-boot-actuator-autoconfigure\2.2.6.RELEASE\spring-boot-actuator-autoconfigure-2.2.6.RELEASE.jar;C:\Users\K114434\.m2\repository\org\springframework\boot\spring-boot-actuator\2.2.6.RELEASE\spring-boot-actuator-2.2.6.RELEASE.jar;C:\Users\K114434\.m2\repository\io\micrometer\micrometer-core\1.3.6\micrometer-core-1.3.6.jar;C:\Users\K114434\.m2\repository\org\hdrhistogram\HdrHistogram\2.1.11\HdrHistogram-2.1.11.jar;C:\Users\K114434\.m2\repository\org\latencyutils\LatencyUtils\2.0.3\LatencyUtils-2.0.3.jar;C:\Users\K114434\.m2\repository\org\springframework\boot\spring-boot-starter-cache\2.2.6.RELEASE\spring-boot-starter-cache-2.2.6.RELEASE.jar;C:\Users\K114434\.m2\repository\org\springframework\spring-context-support\5.2.5.RELEASE\spring-context-support-5.2.5.RELEASE.jar;C:\Users\K114434\.m2\repository\com\github\ulisesbocchio\jasypt-spring-boot-starter\2.0.0\jasypt-spring-boot-starter-2.0.0.jar;C:\Users\K114434\.m2\repository\com\github\ulisesbocchio\jasypt-spring-boot\2.0.0\jasypt-spring-boot-2.0.0.jar;C:\Users\K114434\.m2\repository\org\jasypt\jasypt\1.9.2\jasypt-1.9.2.jar;C:\Users\K114434\.m2\repository\com\github\ben-manes\caffeine\caffeine\2.6.2\caffeine-2.6.2.jar;C:\Users\K114434\.m2\repository\io\springfox\springfox-swagger2\2.9.2\springfox-swagger2-2.9.2.jar;C:\Users\K114434\.m2\repository\io\swagger\swagger-models\1.5.20\swagger-models-1.5.20.jar;C:\Users\K114434\.m2\repository\io\springfox\springfox-spi\2.9.2\springfox-spi-2.9.2.jar;C:\Users\K114434\.m2\repository\io\springfox\springfox-core\2.9.2\springfox-core-2.9.2.jar;C:\Users\K114434\.m2\repository\io\springfox\springfox-schema\2.9.2\springfox-schema-2.9.2.jar;C:\Users\K114434\.m2\repository\io\springfox\springfox-swagger-common\2.9.2\springfox-swagger-common-2.9.2.jar;C:\Users\K114434\.m2\repository\io\springfox\springfox-spring-web\2.9.2\springfox-spring-web-2.9.2.jar;C:\Users\K114434\.m2\repository\com\google\guava\guava\20.0\guava-20.0.jar;C:\Users\K114434\.m2\repository\com\fasterxml\classmate\1.5.1\classmate-1.5.1.jar;C:\Users\K114434\.m2\repository\org\springframework\plugin\spring-plugin-core\1.2.0.RELEASE\spring-plugin-core-1.2.0.RELEASE.jar;C:\Users\K114434\.m2\repository\org\springframework\plugin\spring-plugin-metadata\1.2.0.RELEASE\spring-plugin-metadata-1.2.0.RELEASE.jar;C:\Users\K114434\.m2\repository\org\mapstruct\mapstruct\1.2.0.Final\mapstruct-1.2.0.Final.jar;C:\Users\K114434\.m2\repository\io\springfox\springfox-swagger-ui\2.9.2\springfox-swagger-ui-2.9.2.jar;C:\Users\K114434\.m2\repository\io\micrometer\micrometer-registry-prometheus\1.3.6\micrometer-registry-prometheus-1.3.6.jar;C:\Users\K114434\.m2\repository\io\prometheus\simpleclient_common\0.7.0\simpleclient_common-0.7.0.jar;C:\Users\K114434\.m2\repository\io\prometheus\simpleclient\0.7.0\simpleclient-0.7.0.jar;C:\Users\K114434\.m2\repository\ch\qos\logback\logback-classic\1.2.3\logback-classic-1.2.3.jar;C:\Users\K114434\.m2\repository\ch\qos\logback\logback-core\1.2.3\logback-core-1.2.3.jar;C:\Users\K114434\.m2\repository\net\logstash\logback\logstash-logback-encoder\6.3\logstash-logback-encoder-6.3.jar;C:\Users\K114434\.m2\repository\com\fasterxml\jackson\core\jackson-core\2.12.2\jackson-core-2.12.2.jar;C:\Users\K114434\.m2\repository\com\fasterxml\jackson\core\jackson-databind\2.12.2\jackson-databind-2.12.2.jar;C:\Users\K114434\.m2\repository\com\fasterxml\jackson\core\jackson-annotations\2.12.2\jackson-annotations-2.12.2.jar;C:\Users\K114434\.m2\repository\org\slf4j\slf4j-api\1.7.30\slf4j-api-1.7.30.jar;C:\Users\K114434\.m2\repository\org\projectlombok\lombok\1.18.12\lombok-1.18.12.jar;C:\Users\K114434\.m2\repository\jakarta\xml\bind\jakarta.xml.bind-api\2.3.3\jakarta.xml.bind-api-2.3.3.jar;C:\Users\K114434\.m2\repository\jakarta\activation\jakarta.activation-api\1.2.2\jakarta.activation-api-1.2.2.jar;C:\Users\K114434\.m2\repository\org\assertj\assertj-core\3.13.2\assertj-core-3.13.2.jar;C:\Users\K114434\.m2\repository\net\bytebuddy\byte-buddy\1.10.8\byte-buddy-1.10.8.jar;C:\Users\K114434\.m2\repository\org\springframework\spring-core\5.2.5.RELEASE\spring-core-5.2.5.RELEASE.jar;C:\Users\K114434\.m2\repository\org\springframework\spring-jcl\5.2.5.RELEASE\spring-jcl-5.2.5.RELEASE.jar;C:\Users\K114434\.m2\repository\com\vdurmont\semver4j\3.1.0\semver4j-3.1.0.jar;C:\Users\K114434\.m2\repository\org\apache\httpcomponents\httpclient\4.5.10\httpclient-4.5.10.jar;C:\Users\K114434\.m2\repository\org\apache\httpcomponents\httpcore\4.4.13\httpcore-4.4.13.jar;C:\Users\K114434\.m2\repository\commons-codec\commons-codec\1.13\commons-codec-1.13.jar" no.nav.eux.rina.admin.EuxRinaIrSyncApplication
08:47:54,935 |-INFO in ch.qos.logback.classic.LoggerContext[default] - Could NOT find resource [logback-test.xml]
08:47:54,970 |-INFO in ch.qos.logback.classic.LoggerContext[default] - Could NOT find resource [logback.groovy]
08:47:54,970 |-INFO in ch.qos.logback.classic.LoggerContext[default] - Could NOT find resource [logback.xml]
08:47:54,980 |-INFO in ch.qos.logback.classic.BasicConfigurator@5fcfe4b2 - Setting up default configuration.
2021-04-13 08:47:56  [main] INFO  o.s.boot.StartupInfoLogger.logStarting 55 - Starting EuxRinaIrSyncApplication on a34duvw03454 with PID 25840 (W:\IdeaProjects\eux-rina-irsync\target\classes started by K114434 in W:\IdeaProjects\eux-rina-irsync)
2021-04-13 08:47:56  [main] INFO  o.s.boot.SpringApplication.logStartupProfileInfo 655 - The following profiles are active: local
2021-04-13 08:48:00  [main] INFO  c.u.j.c.EnableEncryptablePropertiesBeanFactoryPostProcessor.postProcessBeanFactory 48 - Post-processing PropertySource instances
2021-04-13 08:48:00  [main] INFO  c.u.j.EncryptablePropertySourceConverter.makeEncryptable 38 - Converting PropertySource configurationProperties [org.springframework.boot.context.properties.source.ConfigurationPropertySourcesPropertySource] to AOP Proxy
2021-04-13 08:48:00  [main] INFO  c.u.j.EncryptablePropertySourceConverter.makeEncryptable 38 - Converting PropertySource servletConfigInitParams [org.springframework.core.env.PropertySource$StubPropertySource] to EncryptablePropertySourceWrapper
2021-04-13 08:48:00  [main] INFO  c.u.j.EncryptablePropertySourceConverter.makeEncryptable 38 - Converting PropertySource servletContextInitParams [org.springframework.core.env.PropertySource$StubPropertySource] to EncryptablePropertySourceWrapper
2021-04-13 08:48:00  [main] INFO  c.u.j.EncryptablePropertySourceConverter.makeEncryptable 38 - Converting PropertySource systemProperties [org.springframework.core.env.PropertiesPropertySource] to EncryptableMapPropertySourceWrapper
2021-04-13 08:48:00  [main] INFO  c.u.j.EncryptablePropertySourceConverter.makeEncryptable 38 - Converting PropertySource systemEnvironment [org.springframework.boot.env.SystemEnvironmentPropertySourceEnvironmentPostProcessor$OriginAwareSystemEnvironmentPropertySource] to EncryptableMapPropertySourceWrapper
2021-04-13 08:48:00  [main] INFO  c.u.j.EncryptablePropertySourceConverter.makeEncryptable 38 - Converting PropertySource random [org.springframework.boot.env.RandomValuePropertySource] to EncryptablePropertySourceWrapper
2021-04-13 08:48:00  [main] INFO  c.u.j.EncryptablePropertySourceConverter.makeEncryptable 38 - Converting PropertySource applicationConfig: [file:./config/application.yml] [org.springframework.boot.env.OriginTrackedMapPropertySource] to EncryptableMapPropertySourceWrapper
2021-04-13 08:48:00  [main] INFO  c.u.j.r.DefaultLazyPropertyResolver.lambda$new$2 34 - Property Resolver custom Bean not found with name 'encryptablePropertyResolver'. Initializing Default Property Resolver
2021-04-13 08:48:00  [main] INFO  c.u.j.d.DefaultLazyPropertyDetector.lambda$new$2 31 - Property Detector custom Bean not found with name 'encryptablePropertyDetector'. Initializing Default Property Detector
2021-04-13 08:48:00  [main] INFO  org.eclipse.jetty.util.log.Log.initialized 169 - Logging initialized @6485ms to org.eclipse.jetty.util.log.Slf4jLog
2021-04-13 08:48:00  [main] INFO  o.s.b.w.e.j.JettyServletWebServerFactory.getWebServer 145 - Server initialized with port: 9090
2021-04-13 08:48:01  [main] INFO  org.eclipse.jetty.server.Server.doStart 359 - jetty-9.4.27.v20200227; built: 2020-02-27T18:37:21.340Z; git: a304fd9f351f337e7c0e2a7c28878dd536149c6c; jvm 1.8.0_281-b09
2021-04-13 08:48:01  [main] INFO  o.e.j.s.h.ContextHandler$Context.log 2226 - Initializing Spring embedded WebApplicationContext
2021-04-13 08:48:01  [main] INFO  o.s.b.w.s.c.ServletWebServerApplicationContext.prepareWebApplicationContext 284 - Root WebApplicationContext: initialization completed in 4065 ms
2021-04-13 08:48:01  [main] INFO  o.e.j.s.s.DefaultSessionIdManager.doStart 334 - DefaultSessionIdManager workerName=node0
2021-04-13 08:48:01  [main] INFO  o.e.j.s.s.DefaultSessionIdManager.doStart 339 - No SessionScavenger set, using defaults
2021-04-13 08:48:01  [main] INFO  o.e.jetty.server.session.HouseKeeper.startScavenging 140 - node0 Scavenging every 660000ms
2021-04-13 08:48:01  [main] INFO  o.e.j.server.handler.ContextHandler.doStart 828 - Started o.s.b.w.e.j.JettyEmbeddedWebAppContext@49d30c6f{application,/,[file:///C:/Users/K114434/AppData/Local/Temp/jetty-docbase.3759859175494791383.9090/, jar:file:/C:/Users/K114434/.m2/repository/io/springfox/springfox-swagger-ui/2.9.2/springfox-swagger-ui-2.9.2.jar!/META-INF/resources],AVAILABLE}
2021-04-13 08:48:01  [main] INFO  org.eclipse.jetty.server.Server.doStart 399 - Started @7443ms
2021-04-13 08:48:01  [main] INFO  c.u.j.encryptor.DefaultLazyEncryptor.lambda$new$2 33 - String Encryptor custom Bean not found with name 'jasyptStringEncryptor'. Initializing Default String Encryptor
2021-04-13 08:48:01  [main] INFO  c.u.j.encryptor.DefaultLazyEncryptor.getProperty 59 - Encryptor config not found for property jasypt.encryptor.algorithm, using default value: PBEWithMD5AndDES
2021-04-13 08:48:01  [main] INFO  c.u.j.encryptor.DefaultLazyEncryptor.getProperty 59 - Encryptor config not found for property jasypt.encryptor.keyObtentionIterations, using default value: 1000
2021-04-13 08:48:01  [main] INFO  c.u.j.encryptor.DefaultLazyEncryptor.getProperty 59 - Encryptor config not found for property jasypt.encryptor.poolSize, using default value: 1
2021-04-13 08:48:01  [main] INFO  c.u.j.encryptor.DefaultLazyEncryptor.getProperty 59 - Encryptor config not found for property jasypt.encryptor.providerName, using default value: null
2021-04-13 08:48:01  [main] INFO  c.u.j.encryptor.DefaultLazyEncryptor.getProperty 59 - Encryptor config not found for property jasypt.encryptor.providerClassName, using default value: null
2021-04-13 08:48:01  [main] INFO  c.u.j.encryptor.DefaultLazyEncryptor.getProperty 59 - Encryptor config not found for property jasypt.encryptor.saltGeneratorClassname, using default value: org.jasypt.salt.RandomSaltGenerator
2021-04-13 08:48:01  [main] INFO  c.u.j.encryptor.DefaultLazyEncryptor.getProperty 59 - Encryptor config not found for property jasypt.encryptor.stringOutputType, using default value: base64
2021-04-13 08:48:02  [main] INFO  n.n.e.r.a.http.RestTemplateFactory.afterPropertiesSet 51 - will call myself at http://127.0.0.1:9090
2021-04-13 08:48:02  [main] INFO  e.e.d.e.rina.sdk.cpi.RinaCpiClient.buildObjectMapper 61 - Building the Object Mapper
2021-04-13 08:48:02  [main] INFO  e.e.d.e.rina.sdk.cpi.RinaCpiClient.buildObjectMapper 61 - Building the Object Mapper
2021-04-13 08:48:02  [main] INFO  e.e.d.e.rina.sdk.cpi.RinaCpiClient.buildObjectMapper 61 - Building the Object Mapper
2021-04-13 08:48:02  [main] INFO  e.e.d.e.rina.sdk.cpi.RinaCpiClient.buildObjectMapper 61 - Building the Object Mapper
2021-04-13 08:48:03  [main] INFO  o.s.b.a.e.web.EndpointLinksResolver.<init> 58 - Exposing 5 endpoint(s) beneath base path '/actuator'
2021-04-13 08:48:03  [main] INFO  s.d.s.w.PropertySourcedRequestMappingHandlerMapping.initHandlerMethods 69 - Mapped URL path [/v2/api-docs] onto method [springfox.documentation.swagger2.web.Swagger2Controller#getDocumentation(String, HttpServletRequest)]
2021-04-13 08:48:03  [main] INFO  o.s.s.c.ExecutorConfigurationSupport.initialize 181 - Initializing ExecutorService 'applicationTaskExecutor'
2021-04-13 08:48:04  [main] INFO  o.s.s.c.ExecutorConfigurationSupport.initialize 181 - Initializing ExecutorService 'taskScheduler'
2021-04-13 08:48:04  [main] INFO  s.d.s.w.p.DocumentationPluginsBootstrapper.start 160 - Context refreshed
2021-04-13 08:48:04  [main] INFO  s.d.s.w.p.DocumentationPluginsBootstrapper.start 163 - Found 1 custom documentation plugin(s)
2021-04-13 08:48:04  [main] INFO  s.d.s.w.s.ApiListingReferenceScanner.scan 41 - Scanning for api listing references
2021-04-13 08:48:04  [main] INFO  o.e.j.s.h.ContextHandler$Context.log 2226 - Initializing Spring DispatcherServlet 'dispatcherServlet'
2021-04-13 08:48:04  [main] INFO  o.s.web.servlet.FrameworkServlet.initServletBean 525 - Initializing Servlet 'dispatcherServlet'
2021-04-13 08:48:04  [main] INFO  o.s.web.servlet.FrameworkServlet.initServletBean 547 - Completed initialization in 15 ms
2021-04-13 08:48:04  [main] INFO  o.e.jetty.server.AbstractConnector.doStart 331 - Started ServerConnector@1422ac7f{HTTP/1.1, (http/1.1)}{127.0.0.1:9090}
2021-04-13 08:48:04  [main] INFO  o.s.b.w.e.jetty.JettyWebServer.start 157 - Jetty started on port(s) 9090 (http/1.1) with context path '/'
2021-04-13 08:48:04  [main] INFO  o.s.boot.StartupInfoLogger.logStarted 61 - Started EuxRinaIrSyncApplication in 9.054 seconds (JVM running for 10.43)
2021-04-13 08:48:04  [main] INFO  n.n.e.r.a.EuxRinaIrSyncApplication.run 27 - availableProcessors = 4
2021-04-13 08:48:04  [main] INFO  n.n.e.r.a.EuxRinaIrSyncApplication.run 28 - parallism of pool   = 16
2021-04-13 08:48:04  [main] INFO  n.n.e.r.a.EuxRinaIrSyncApplication.run 29 - cron.expression = 0 0/30 2,4,6,9 * * *
2021-04-13 08:48:04  [main] INFO  n.n.e.r.a.EuxRinaIrSyncApplication.run 30 - update.wait = 120 seconds, which is 2 minutes
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
nssm install IRSYNC "%JAVA_HOME%"\bin\java.exe -Djasypt.encryptor.password=t8-secret -Djava.util.concurrent.ForkJoinPool.common.parallelism=16 -Dlogback.statusListenerClass=ch.qos.logback.core.status.OnConsoleStatusListener"

nssm set IRSYNC AppDirectory "%CD%"

nssm edit IRSYNC
```

## TODOs

* Ubuntu service implementation
* Reintroduce support for multiple RINAs
* or
* remove the possibly complicated code and config for admin username and password.
* example of the already supported encrypted storage of admin username and password.
