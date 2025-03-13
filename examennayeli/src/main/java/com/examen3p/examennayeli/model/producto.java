package com.examen3p.examennayeli.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.util.Objects;

@Document(collection = "productos")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Producto {

    @Id
    @NotBlank(message = "El código del producto es obligatorio")
    @Size(max = 32, message = "El código del producto no puede exceder los 32 caracteres")
    private String codProducto;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 64, message = "El nombre del producto no puede exceder los 64 caracteres")
    private String nombre;

    @NotNull(message = "La existencia es obligatoria")
    private BigDecimal existencia;

    @NotNull(message = "El precio es obligatorio")
    private BigDecimal precio;

    public Producto(String codProducto) {
        this.codProducto = codProducto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return Objects.equals(codProducto, producto.codProducto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codProducto);
    }
}
