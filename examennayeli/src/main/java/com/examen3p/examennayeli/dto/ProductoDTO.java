package com.examen3p.examennayeli.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductoDTO {

    @NotBlank(message = "El código del producto es obligatorio")
    @Size(max = 32, message = "El código del producto no puede exceder los 32 caracteres")
    private String codProducto;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 64, message = "El nombre del producto no puede exceder los 64 caracteres")
    private String nombre;

    @NotNull(message = "La existencia es obligatoria")
    private Integer existencia;

    @NotNull(message = "El precio es obligatorio")
    private BigDecimal precio;
} 