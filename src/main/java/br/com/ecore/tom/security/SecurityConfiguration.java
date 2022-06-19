package br.com.ecore.tom.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import br.com.ecore.tom.authentication.service.UserService;



@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserService userService;

  @Autowired
  private JwtAuthenticationEntryPoint unauthorizedHandler;

  @Bean
  BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Autowired
  JwtAuthenticationFilter jwtAuthenticationFilter;

  @Override
  @Bean(BeanIds.AUTHENTICATION_MANAGER)
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    auth.inMemoryAuthentication().passwordEncoder(encoder).withUser("springtest")
        .password(encoder.encode("secret")).roles("USER");
  }


  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable().exceptionHandling()
        .authenticationEntryPoint(unauthorizedHandler).and().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
        .antMatchers("/authentication/**").permitAll().antMatchers("/roles/**").permitAll()
        .antMatchers("/memberships/**").permitAll().antMatchers("/members/**").permitAll()
        .antMatchers("/teams/**").permitAll().anyRequest().authenticated();

    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
  }

  // To access swagger use /swagger-ui.html#/ on the url
  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/v2/api-docs", "/configuration/**", "/swagger-resources/**",
        "/**.html", "/webjars/**");
  }

}
