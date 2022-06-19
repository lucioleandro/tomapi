package br.com.ecore.tom.integration.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import br.com.ecore.tom.integration.TeamConsumerService;

@Component
@EnableScheduling
public class TeamsConsumerJob {

  private static final Logger logger = LoggerFactory.getLogger(TeamsConsumerJob.class);

  // Será sempre executado aos 0 segundos do minuto 0 da hora 3 de todos os dias de todos os meses,
  // independente do dia da semana
  private static final String CRON = "0 02 16 * * ?";

  @Autowired
  private TeamConsumerService consumerService;

  // TODO: configuração para rodar em apenas uma instância
  @Scheduled(cron = CRON)
  public void executeSync() {
    logger.info("Starting: Teams Consumer Job");
    consumerService.fetchTeams();
    logger.info("Finishing: Teams Consumer Job");
  }
}
