package br.com.ecore.tom.integration;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import br.com.ecore.tom.service.MemberService;

@Service
public class MemberConsumerService {

  @Value("${app.API_URL}")
  private String API_URL;

  @Autowired
  private MemberService memberService;

  private RestTemplate restTemplate;

  @Autowired
  public MemberConsumerService(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate =
        restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
  }

  // TODO: Verificar se o retorno não é nullo.
  public void fetchMemberById(UUID id) {
    UserConsumerDTO user =
        this.restTemplate.getForObject(API_URL + "/members/" + id, UserConsumerDTO.class);
    this.memberService.create(user.transformToMember());
  }

  public void fetchMembers() {
    UserConsumerDTO[] users =
        this.restTemplate.getForEntity(API_URL + "/members/", UserConsumerDTO[].class).getBody();

    for (UserConsumerDTO user : users) {
      this.memberService.create(user.transformToMember());
    }

  }
}
