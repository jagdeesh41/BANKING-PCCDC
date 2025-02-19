package com.learn;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition
        (
                info = @Info(
                        title = "Debit Card Microservice",
                        description = "debit cards microservice",
                        version = "1.0.0"
                )
        )
public class DebitCardApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(DebitCardApplication.class,args);

    }
}
