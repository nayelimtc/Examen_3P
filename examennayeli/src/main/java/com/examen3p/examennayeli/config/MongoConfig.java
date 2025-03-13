package com.examen3p.examennayeli.config;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

    private static final Logger log = LoggerFactory.getLogger(MongoConfig.class);

    @Bean
    CommandLineRunner mongoDbConnectionValidator(MongoTemplate mongoTemplate) {
        return args -> {
            try {
                Document pingCommand = new Document("ping", 1);
                Document result = mongoTemplate.getDb().runCommand(pingCommand);
                log.info("Conexión exitosa a MongoDB en: {}",
                        mongoTemplate.getDb().getName());
                log.debug("Resultado del ping: {}", result.toJson());
            } catch (Exception e) {
                log.error("Error al conectar con MongoDB: {}", e.getMessage());
                throw e;
            }
        };
    }
}