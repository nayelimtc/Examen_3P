package com.examen3p.examennayeli.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.retry.annotation.EnableRetry;

@Configuration
@EnableRetry
public class MongoConfig {
    private static final Logger log = LoggerFactory.getLogger(MongoConfig.class);

    @Bean
    public CommandLineRunner mongoDbConnectionValidator(MongoTemplate mongoTemplate) {
        return args -> {
            try {
                mongoTemplate.getDb().runCommand(new org.bson.Document("ping", 1));
                log.info("Conexión a MongoDB establecida correctamente");
            } catch (Exception e) {
                log.error("Error al conectar con MongoDB: {}", e.getMessage());
                // No lanzamos la excepción para permitir que la aplicación continúe
            }
        };
    }
}