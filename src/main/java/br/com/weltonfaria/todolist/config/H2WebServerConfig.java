package br.com.weltonfaria.todolist.config;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.sql.SQLException;

@Configuration
@Profile("dev")
public class H2WebServerConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2WebServer() throws SQLException {
        // start H2 web console on port 8082 (separate from embedded Tomcat)
        return Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082");
    }
}
