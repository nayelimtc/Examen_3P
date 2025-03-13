package com.examen3p.examennayeli.exception;

public class ProductoSinExistenciasException extends RuntimeException {

    public ProductoSinExistenciasException(String codProducto) {
        super("El producto con código " + codProducto + " no tiene existencias suficientes");
    }
}