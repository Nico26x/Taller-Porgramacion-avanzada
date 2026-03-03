package com.tallerprog.tallerprog.controller;

import com.tallerprog.tallerprog.model.Desarrolladora;
import com.tallerprog.tallerprog.service.DesarrolladoraService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/desarrolladoras")
public class DesarrolladoraController {

    private final DesarrolladoraService desarrolladoraService;

    public DesarrolladoraController(DesarrolladoraService desarrolladoraService) {
        this.desarrolladoraService = desarrolladoraService;
    }

    @GetMapping
    public ResponseEntity<List<Desarrolladora>> listarTodas() {
        return ResponseEntity.ok(desarrolladoraService.listarTodas());
    }

    @PostMapping
    public ResponseEntity<Desarrolladora> crear(@RequestBody Desarrolladora desarrolladora) {
        return ResponseEntity.status(HttpStatus.CREATED).body(desarrolladoraService.crear(desarrolladora));
    }
}