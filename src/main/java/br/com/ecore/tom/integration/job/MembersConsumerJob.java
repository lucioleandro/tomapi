package br.com.ecore.tom.integration.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import br.com.ecore.tom.integration.MemberConsumerService;

@Component
@EnableScheduling
public class MembersConsumerJob {

  private static final Logger logger = LoggerFactory.getLogger(MembersConsumerJob.class);

  // Será sempre executado aos 0 segundos do minuto 0 da hora 1 de todos os dias de todos os meses,
  // independente do dia da semana
  private static final String CRON = "0 23 22 * * ?";

  @Autowired
  private MemberConsumerService consumerService;

  @Scheduled(cron = CRON)
  public void executeSync() {
    logger.info("Starting: Members Consumer Job");
    consumerService.fetchMembers();
    logger.info("Finishing: Members Consumer Job");
  }
}
