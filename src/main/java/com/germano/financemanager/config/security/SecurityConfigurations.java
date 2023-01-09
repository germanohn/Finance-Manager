package com.germano.financemanager.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations {

	@Bean
	public UserDetailsService userDetailsService() {
		return new AuthenticationService();
	}
	
	@Bean 
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// Configurações de Autorização das urls (o que é público e o que tem
	// controle de acesso)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        	.antMatchers(HttpMethod.GET, "/despesas").permitAll()
        	.antMatchers(HttpMethod.GET, "/despesas/*").permitAll()
        	.antMatchers(HttpMethod.GET, "/receitas").permitAll()
        	.antMatchers(HttpMethod.GET, "/receitas/*").permitAll()
        	.antMatchers(HttpMethod.GET, "/resumo/*").permitAll()
        	.anyRequest().authenticated()
        	.and().formLogin();
        return http.build();
    }

}
