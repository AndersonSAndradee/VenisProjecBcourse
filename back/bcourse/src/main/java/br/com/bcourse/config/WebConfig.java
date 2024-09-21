package br.com.bcourse.config;


import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer{

    @SuppressWarnings("null")
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Permite todas as rotas da API
                .allowedOrigins("http://localhost:3000", "http://10.0.0.110:3000")  // Permite as origens do front-end
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Permite os métodos HTTP
                .allowedHeaders("*")  // Permite todos os headers
                .allowCredentials(true);  // Permite credenciais (opcional)

}
}
