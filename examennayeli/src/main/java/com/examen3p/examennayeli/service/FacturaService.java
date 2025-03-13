package com.examen3p.examennayeli.service;

import com.examen3p.examennayeli.client.ProductoClient;
import com.examen3p.examennayeli.dto.CrearFacturaDTO;
import com.examen3p.examennayeli.dto.FacturaDetalleDTO;
import com.examen3p.examennayeli.dto.ProductoDTO;
import com.examen3p.examennayeli.exception.ProductoSinExistenciasException;
import com.examen3p.examennayeli.mapper.FacturaMapper;
import com.examen3p.examennayeli.model.Factura;
import com.examen3p.examennayeli.model.FacturaDetalle;
import com.examen3p.examennayeli.repository.FacturaRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class FacturaService {

    private static final Logger log = LoggerFactory.getLogger(FacturaService.class);
    private static final BigDecimal IVA_RATE = new BigDecimal("0.12");
    private static final int SCALE = 2;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    private final FacturaRepository facturaRepository;
    private final ProductoClient productoClient;
    private final FacturaMapper facturaMapper;

    @Transactional
    public Factura crearFactura(CrearFacturaDTO crearFacturaDTO) {
        log.info("Iniciando proceso de creación de factura para: {}", crearFacturaDTO.getNombre());

        Factura factura = facturaMapper.toEntity(crearFacturaDTO);
        inicializarTotales(factura);

        for (FacturaDetalleDTO detalleDTO : crearFacturaDTO.getDetalles()) {
            procesarDetalle(factura, detalleDTO);
        }

        log.info("Factura creada exitosamente. Total: {}", factura.getTotal());
        return facturaRepository.save(factura);
    }

    private void inicializarTotales(Factura factura) {
        factura.setSubtotal(BigDecimal.ZERO);
        factura.setIva(BigDecimal.ZERO);
        factura.setTotal(BigDecimal.ZERO);
    }

    private void procesarDetalle(Factura factura, FacturaDetalleDTO detalleDTO) {
        log.debug("Procesando detalle de producto: {}", detalleDTO.getCodProducto());

        ProductoDTO producto = productoClient.obtenerProducto(detalleDTO.getCodProducto());
        validarExistencias(producto, detalleDTO);

        FacturaDetalle detalle = crearDetalle(producto, detalleDTO);
        actualizarTotalesFactura(factura, detalle);

        // Actualizar existencias del producto
        productoClient.actualizarExistencias(producto.getCodProducto(), detalleDTO.getCantidad());
    }

    private void validarExistencias(ProductoDTO producto, FacturaDetalleDTO detalleDTO) {
        if (producto.getExistencia().compareTo(detalleDTO.getCantidad()) < 0) {
            log.error("Existencias insuficientes para el producto {}. Disponible: {}, Solicitado: {}",
                    producto.getCodProducto(), producto.getExistencia(), detalleDTO.getCantidad());
            throw new ProductoSinExistenciasException(producto.getCodProducto());
        }
    }

    private FacturaDetalle crearDetalle(ProductoDTO producto, FacturaDetalleDTO detalleDTO) {
        FacturaDetalle detalle = new FacturaDetalle();
        detalle.setCodProducto(producto.getCodProducto());
        detalle.setCantidad(detalleDTO.getCantidad());
        detalle.setPrecio(producto.getPrecio());

        calcularTotalesDetalle(detalle);
        return detalle;
    }

    private void calcularTotalesDetalle(FacturaDetalle detalle) {
        detalle.setSubtotal(detalle.getPrecio().multiply(detalle.getCantidad())
                .setScale(SCALE, ROUNDING_MODE));
        detalle.setIva(detalle.getSubtotal().multiply(IVA_RATE)
                .setScale(SCALE, ROUNDING_MODE));
        detalle.setTotal(detalle.getSubtotal().add(detalle.getIva())
                .setScale(SCALE, ROUNDING_MODE));
    }

    private void actualizarTotalesFactura(Factura factura, FacturaDetalle detalle) {
        factura.getDetalles().add(detalle);
        factura.setSubtotal(factura.getSubtotal().add(detalle.getSubtotal())
                .setScale(SCALE, ROUNDING_MODE));
        factura.setIva(factura.getIva().add(detalle.getIva())
                .setScale(SCALE, ROUNDING_MODE));
        factura.setTotal(factura.getTotal().add(detalle.getTotal())
                .setScale(SCALE, ROUNDING_MODE));
    }
}