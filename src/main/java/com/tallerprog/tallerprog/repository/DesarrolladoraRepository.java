package com.tallerprog.tallerprog.repository;

import com.tallerprog.tallerprog.model.Desarrolladora;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DesarrolladoraRepository extends JpaRepository<Desarrolladora, Long> {
    boolean existsByNombreIgnoreCase(String nombre);
}