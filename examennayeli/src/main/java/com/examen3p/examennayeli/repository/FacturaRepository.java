package com.examen3p.examennayeli.repository;

import com.examen3p.examennayeli.model.Factura;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaRepository extends MongoRepository<Factura, String> {
}