package com.examen3p.examennayeli.controller;

import com.examen3p.examennayeli.dto.CrearFacturaDTO;
import com.examen3p.examennayeli.model.Factura;
import com.examen3p.examennayeli.service.FacturaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/facturas")
@RequiredArgsConstructor
public class FacturaController {

    private static final Logger log = LoggerFactory.getLogger(FacturaController.class);
    private final FacturaService facturaService;

    @PostMapping
    public ResponseEntity<Factura> crearFactura(@Valid @RequestBody CrearFacturaDTO crearFacturaDTO) {
        log.info("Recibida solicitud de creación de factura para: {}", crearFacturaDTO.getNombre());
        return ResponseEntity.ok(facturaService.crearFactura(crearFacturaDTO));
    }
}