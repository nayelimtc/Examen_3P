package com.examen3p.examennayeli.health;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class MongoHealthChecker {

    private static final Logger log = LoggerFactory.getLogger(MongoHealthChecker.class);
    private final MongoTemplate mongoTemplate;

    public MongoHealthChecker(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public boolean isMongoHealthy() {
        try {
            Document result = mongoTemplate.getDb().runCommand(new Document("ping", 1));
            log.info("MongoDB health check - Database: {}, Status: {}",
                    mongoTemplate.getDb().getName(), result.toJson());
            return true;
        } catch (Exception e) {
            log.error("MongoDB health check failed: {}", e.getMessage());
            return false;
        }
    }
}