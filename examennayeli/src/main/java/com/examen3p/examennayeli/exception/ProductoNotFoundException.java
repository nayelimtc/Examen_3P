package com.examen3p.examennayeli.exception;

public class ProductoNotFoundException extends RuntimeException {

    public ProductoNotFoundException(String codProducto) {
        super("No se encontró el producto con código: " + codProducto);
    }
}