package com.tallerprog.tallerprog.controller;

import com.tallerprog.tallerprog.model.Genero;
import com.tallerprog.tallerprog.model.Videojuego;
import com.tallerprog.tallerprog.service.VideojuegoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/videojuegos")
public class VideojuegoController {

    private final VideojuegoService videojuegoService;

    public VideojuegoController(VideojuegoService videojuegoService) {
        this.videojuegoService = videojuegoService;
    }

    @GetMapping
    public ResponseEntity<List<Videojuego>> listarTodos() {
        return ResponseEntity.ok(videojuegoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Videojuego> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(videojuegoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<Videojuego> crear(@RequestBody Videojuego videojuego) {
        return ResponseEntity.status(HttpStatus.CREATED).body(videojuegoService.crear(videojuego));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Videojuego> actualizar(@PathVariable Long id, @RequestBody Videojuego videojuego) {
        return ResponseEntity.ok(videojuegoService.actualizar(id, videojuego));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        videojuegoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Videojuego>> buscarPorTitulo(@RequestParam String titulo) {
        return ResponseEntity.ok(videojuegoService.buscarPorTitulo(titulo));
    }

    @GetMapping("/rango-precio")
    public ResponseEntity<List<Videojuego>> buscarPorRangoPrecio(@RequestParam Double min, @RequestParam Double max) {
        return ResponseEntity.ok(videojuegoService.buscarPorRangoPrecio(min, max));
    }

    @PatchMapping("/{id}/descuento")
    public ResponseEntity<Videojuego> aplicarDescuento(@PathVariable Long id, @RequestParam Double porcentaje) {
        return ResponseEntity.ok(videojuegoService.aplicarDescuento(id, porcentaje));
    }

    // Extras útiles para evidenciar consultas del repositorio
    @GetMapping("/genero/{genero}")
    public ResponseEntity<List<Videojuego>> buscarPorGenero(@PathVariable Genero genero) {
        return ResponseEntity.ok(videojuegoService.buscarPorGenero(genero));
    }

    @GetMapping("/ultimos")
    public ResponseEntity<List<Videojuego>> ultimos() {
        return ResponseEntity.ok(videojuegoService.obtenerUltimosCinco());
    }
}