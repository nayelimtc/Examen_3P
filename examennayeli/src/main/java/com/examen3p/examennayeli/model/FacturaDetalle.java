package com.examen3p.examennayeli.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class FacturaDetalle {

    @NotBlank(message = "El código de producto es obligatorio")
    @Size(max = 32, message = "El código de producto no puede exceder los 32 caracteres")
    private String codProducto;

    @NotNull(message = "La cantidad es obligatoria")
    private BigDecimal cantidad;

    @NotNull(message = "El precio es obligatorio")
    private BigDecimal precio;

    @NotNull(message = "El subtotal es obligatorio")
    private BigDecimal subtotal;

    @NotNull(message = "El IVA es obligatorio")
    private BigDecimal iva;

    @NotNull(message = "El total es obligatorio")
    private BigDecimal total;
}