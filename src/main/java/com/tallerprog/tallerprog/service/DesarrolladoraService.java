package com.tallerprog.tallerprog.service;

import com.tallerprog.tallerprog.model.Desarrolladora;

import java.util.List;

public interface DesarrolladoraService {
    List<Desarrolladora> listarTodas();
    Desarrolladora crear(Desarrolladora desarrolladora);
    Desarrolladora obtenerPorId(Long id);
}