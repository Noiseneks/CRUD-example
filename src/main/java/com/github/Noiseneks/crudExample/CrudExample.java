package com.github.Noiseneks.crudExample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class CrudExample {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(CrudExample.class);
        app.run(args);
    }

}
