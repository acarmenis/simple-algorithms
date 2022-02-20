package com.zinkworks.web;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * App the start point of springboot context.
 */
@SpringBootApplication
@EntityScan(basePackages = {"com.zinkworks.domain"})
@EnableJpaRepositories(basePackages = {"com.zinkworks.persistence"})
@ComponentScan(basePackages = {"com.zinkworks.*"})
public class App {
    public static void main(String [] args){
        SpringApplication.run(App.class);
    }
}
