package br.com.weltonfaria.todolist.config;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import jakarta.servlet.ServletRegistration;

@Configuration
@Profile("dev")
public class H2ConsoleConfig {

    @Bean
    public ServletContextInitializer h2ConsoleInitializer() {
        return servletContext -> {
            try {
                ServletRegistration.Dynamic registration = servletContext.addServlet("H2Console", "org.h2.server.web.WebServlet");
                if (registration != null) {
                    registration.addMapping("/h2-console/*");
                }
            } catch (Exception ex) {
                // ignore - if servlet class not present it will fail at runtime when not required
            }
        };
    }
}
