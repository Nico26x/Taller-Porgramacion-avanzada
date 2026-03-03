package com.tallerprog.tallerprog.service;

import com.tallerprog.tallerprog.model.Desarrolladora;
import com.tallerprog.tallerprog.model.ResourceNotFoundException;
import com.tallerprog.tallerprog.repository.DesarrolladoraRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DesarrolladoraServiceImpl implements DesarrolladoraService {

    private final DesarrolladoraRepository desarrolladoraRepository;

    public DesarrolladoraServiceImpl(DesarrolladoraRepository desarrolladoraRepository) {
        this.desarrolladoraRepository = desarrolladoraRepository;
    }

    @Override
    public List<Desarrolladora> listarTodas() {
        return desarrolladoraRepository.findAll();
    }

    @Override
    public Desarrolladora crear(Desarrolladora desarrolladora) {
        if (desarrolladora == null) {
            throw new IllegalArgumentException("La desarrolladora no puede ser nula.");
        }

        if (desarrolladora.getNombre() == null || desarrolladora.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la desarrolladora es obligatorio.");
        }

        if (desarrolladoraRepository.existsByNombreIgnoreCase(desarrolladora.getNombre().trim())) {
            throw new IllegalArgumentException("Ya existe una desarrolladora con ese nombre.");
        }

        desarrolladora.setId(null);
        desarrolladora.setNombre(desarrolladora.getNombre().trim());

        return desarrolladoraRepository.save(desarrolladora);
    }

    @Override
    public Desarrolladora obtenerPorId(Long id) {
        return desarrolladoraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Desarrolladora no encontrada con id: " + id));
    }
}