package com.examen3p.examennayeli.mapper;

import com.examen3p.examennayeli.dto.CrearFacturaDTO;
import com.examen3p.examennayeli.model.Factura;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FacturaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fecha", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "subtotal", ignore = true)
    @Mapping(target = "iva", ignore = true)
    @Mapping(target = "total", ignore = true)
    @Mapping(target = "detalles", ignore = true)
    Factura toEntity(CrearFacturaDTO crearFacturaDTO);
}