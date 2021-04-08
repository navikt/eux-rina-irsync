package no.nav.eux.rina.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.ForkJoinPool;

@Slf4j
@EnableScheduling
@SpringBootApplication
public class EuxRinaIrSyncApplication implements CommandLineRunner {
  @Autowired
  private String cronExpression;
  @Autowired
  private long updateWait;
  
  public static void main(String[] args) {
    SpringApplication.run(EuxRinaIrSyncApplication.class, args);
  }
  
  @Override
  public void run(String... args) throws Exception {
    log.info("availableProcessors = " + Runtime.getRuntime().availableProcessors());
    log.info("parallism of pool   = " + ForkJoinPool.getCommonPoolParallelism());
    log.info("cron.expression = {}", cronExpression);
    log.info("update.wait = {} seconds, which is {} minutes", updateWait, updateWait / 60);
  }
}