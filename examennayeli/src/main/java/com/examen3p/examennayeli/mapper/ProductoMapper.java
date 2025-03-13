package com.examen3p.examennayeli.mapper;

import com.examen3p.examennayeli.dto.ProductoDTO;
import com.examen3p.examennayeli.model.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    ProductoDTO toDto(Producto producto);

    Producto toEntity(ProductoDTO productoDTO);

    void updateEntityFromDto(ProductoDTO productoDTO, @MappingTarget Producto producto);
}