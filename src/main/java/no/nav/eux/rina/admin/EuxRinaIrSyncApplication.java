package no.nav.eux.rina.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.ForkJoinPool;

@Slf4j
@EnableScheduling
@SpringBootApplication
public class EuxRinaIrSyncApplication {

	public static void main(String[] args) {
		log.info("availableProcessors = " + Runtime.getRuntime().availableProcessors());
		log.info("parallism of pool   = " + ForkJoinPool.getCommonPoolParallelism());
	  SpringApplication.run(EuxRinaIrSyncApplication.class, args);
	}
 
}

