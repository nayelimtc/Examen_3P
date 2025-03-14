package com.examen3p.examennayeli.client;

import com.examen3p.examennayeli.dto.ProductoDTO;
import com.examen3p.examennayeli.exception.ProductoNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ProductoClient {

    private static final Logger log = LoggerFactory.getLogger(ProductoClient.class);
    private final RestTemplate restTemplate;

    @Value("${microservicio.producto.url}")
    private String productoServiceUrl;

    public ProductoDTO obtenerProducto(String codProducto) {
        log.debug("Consultando producto con código: {}", codProducto);
        try {
            return restTemplate.getForObject(
                    productoServiceUrl + "/api/productos/{codProducto}",
                    ProductoDTO.class,
                    codProducto);
        } catch (Exception e) {
            log.error("Error al consultar el producto {}: {}", codProducto, e.getMessage());
            throw new ProductoNotFoundException(codProducto);
        }
    }

    public void actualizarExistencias(String codProducto, BigDecimal cantidad) {
        log.debug("Actualizando existencias del producto {}: {}", codProducto, cantidad);
        try {
            ProductoDTO producto = obtenerProducto(codProducto);
            producto.setExistencia(producto.getExistencia().subtract(cantidad));

            ResponseEntity<ProductoDTO> response = restTemplate.postForEntity(
                    productoServiceUrl + "/api/productos/{codProducto}",
                    producto,
                    ProductoDTO.class,
                    codProducto);

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Error al actualizar existencias");
            }
        } catch (Exception e) {
            log.error("Error al actualizar existencias del producto {}: {}", codProducto, e.getMessage());
            throw new RuntimeException("Error al actualizar existencias: " + e.getMessage());
        }
    }
}