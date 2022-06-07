package br.com.ecore.tom.integration.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import br.com.ecore.tom.integration.MemberConsumerService;

@Component
@EnableScheduling
public class MembersConsumerJob {

  // Será sempre executado aos 0 segundos do minuto 0 da hora 3 de todos os dias de todos os meses,
  // independente do dia da semana
  // TODO Colocar a hora certa
  private static final String CRON = "0 40 18 * * ?";

  @Autowired
  private MemberConsumerService consumerService;

  // TODO: Usar log
  // TODO: configuração para rodar em apenas uma instância (olhar proconsumidor)
  @Scheduled(cron = CRON)
  public void executeSync() {
    consumerService.fetchMembers();
  }
}
