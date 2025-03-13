package com.examen3p.examennayeli.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.List;

@Data
public class CrearFacturaDTO {

    @NotBlank(message = "El tipo de identificación es obligatorio")
    @Size(max = 3, message = "El tipo de identificación no puede exceder los 3 caracteres")
    private String tipoIdentificacion;

    @NotBlank(message = "La identificación es obligatoria")
    @Size(max = 20, message = "La identificación no puede exceder los 20 caracteres")
    private String identificacion;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String nombre;

    @NotEmpty(message = "La factura debe tener al menos un detalle")
    private List<@Valid FacturaDetalleDTO> detalles;
}