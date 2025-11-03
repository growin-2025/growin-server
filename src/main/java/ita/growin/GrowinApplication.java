package ita.growin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GrowinApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrowinApplication.class, args);
    }

}
