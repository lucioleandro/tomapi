package br.com.ecore.tom.integration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import br.com.ecore.tom.domain.Member;
import br.com.ecore.tom.exceptions.EntityNotFoundException;
import br.com.ecore.tom.service.MemberService;

@Service
public class MemberConsumerService {

  private final String RESOURCE = "users";

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

  public Member fetchMemberById(UUID id) {
    UserConsumerDTO user = findById(id);
    if (user == null) {
      throw new EntityNotFoundException(id, Member.class);
    }
    return this.memberService.create(user.transformToMember());
  }

  // TODO: Fazer calculo de algoritmo para analisar o quanto demora pra rodar
  public void fetchMembers() {
    List<Member> members = this.memberService.findAll();
    Set<Member> setOfMembers = new HashSet<>(members);

    UserConsumerDTO[] users =
        this.restTemplate.getForEntity(API_URL + "/" + RESOURCE, UserConsumerDTO[].class).getBody();

    for (UserConsumerDTO user : users) {
      setOfMembers.add(user.transformToMember());
    }

    List<Member> newMembers = getOnlyNewMembers(setOfMembers);

    for (int i = 0; i < newMembers.size(); i++) {
      UserConsumerDTO completedUser = this.findById(newMembers.get(i).getUuid());
      if (completedUser != null) {
        newMembers.set(i, completedUser.transformToMember());
      }
    }
    memberService.createAll(newMembers);
  }

  private List<Member> getOnlyNewMembers(Set<Member> setOfMembers) {
    return setOfMembers.stream().filter(member -> member.getId() == null)
        .collect(Collectors.toList());
  }

  private UserConsumerDTO findById(UUID id) {
    return this.restTemplate.getForObject(API_URL + "/" + RESOURCE + "/" + id,
        UserConsumerDTO.class);
  }

}
