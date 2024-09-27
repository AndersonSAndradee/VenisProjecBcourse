package br.com.bcourse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class AuthorizeUrlsSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/**").hasRole("USER") // Defina as permissões de acesso
                .anyRequest().authenticated() // Requer autenticação para qualquer outra solicitação
            )
            .formLogin(form -> form // Usando uma lambda para configurar o login
                .permitAll() // Permite o acesso ao formulário de login para todos
            );

        return http.build();
    }

        
}
