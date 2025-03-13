package com.examen3p.examennayeli.controller;

import com.examen3p.examennayeli.dto.ProductoDTO;
import com.examen3p.examennayeli.exception.ProductoNotFoundException;
import com.examen3p.examennayeli.mapper.ProductoMapper;
import com.examen3p.examennayeli.model.Producto;
import com.examen3p.examennayeli.repository.ProductoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    @GetMapping("/{codProducto}")
    public ResponseEntity<ProductoDTO> buscarProducto(@PathVariable String codProducto) {
        Producto producto = productoRepository.findById(codProducto)
                .orElseThrow(() -> new ProductoNotFoundException(codProducto));
        return ResponseEntity.ok(productoMapper.toDto(producto));
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> crearProducto(@Valid @RequestBody ProductoDTO productoDTO) {
        Producto producto = productoMapper.toEntity(productoDTO);
        producto = productoRepository.save(producto);
        return ResponseEntity.ok(productoMapper.toDto(producto));
    }

    @PutMapping("/{codProducto}")
    public ResponseEntity<ProductoDTO> modificarProducto(
            @PathVariable String codProducto,
            @Valid @RequestBody ProductoDTO productoDTO) {
        Producto producto = productoRepository.findById(codProducto)
                .orElseThrow(() -> new ProductoNotFoundException(codProducto));

        productoMapper.updateEntityFromDto(productoDTO, producto);
        producto = productoRepository.save(producto);
        return ResponseEntity.ok(productoMapper.toDto(producto));
    }
}
