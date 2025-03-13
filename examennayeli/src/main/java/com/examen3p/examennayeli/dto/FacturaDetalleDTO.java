package com.examen3p.examennayeli.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class FacturaDetalleDTO {

    @NotBlank(message = "El código de producto es obligatorio")
    @Size(max = 32, message = "El código de producto no puede exceder los 32 caracteres")
    private String codProducto;

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser mayor a cero")
    private BigDecimal cantidad;
}