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
                        title = "Credit Card Microservice",
                        description = "credit cards microservice",
                        version = "1.0.0"
                )
        )
public class CreditCardsApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(CreditCardsApplication.class,args);
    }
}
