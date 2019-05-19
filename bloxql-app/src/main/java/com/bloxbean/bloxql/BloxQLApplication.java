package com.bloxbean.bloxql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@SpringBootApplication
@ConditionalOnProperty(name = "graphql.enable", havingValue = "true")
public class BloxQLApplication {
    public static void main(String[] args) {
        SpringApplication.run(BloxQLApplication.class, args);
    }
}