package com.example.stocksample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.stocksample.dao")
public class StockSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockSampleApplication.class, args);
    }

}