package br.com.ecore.tom.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import br.com.ecore.tom.integration.MemberConsumerService;

@Component
@EnableScheduling
public class SyncSystemsDataJob {

  // Será sempre executado aos 0 segundos do minuto 0 da hora 3 de todos os dias de todos os meses,
  // independente do dia da semana
  private static final String CRON = "0 0 3 * * ?";
  private static final String CRON2 = "0 * * * * *";

  @Autowired
  private MemberConsumerService consumerService;

  @Scheduled(cron = CRON2)
  public void executeSync() {
    System.out.println("Ta começando mais um ta gravando");
    consumerService.fetchMembers();
  }
}
