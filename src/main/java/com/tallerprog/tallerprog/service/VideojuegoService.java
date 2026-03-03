package com.tallerprog.tallerprog.service;

import com.tallerprog.tallerprog.model.Genero;
import com.tallerprog.tallerprog.model.Videojuego;

import java.util.List;

public interface VideojuegoService {
    List<Videojuego> listarTodos();
    Videojuego obtenerPorId(Long id);
    Videojuego crear(Videojuego videojuego);
    Videojuego actualizar(Long id, Videojuego videojuego);
    void eliminar(Long id);
    List<Videojuego> buscarPorTitulo(String titulo);
    List<Videojuego> buscarPorRangoPrecio(Double min, Double max);
    List<Videojuego> buscarPorGenero(Genero genero);
    List<Videojuego> obtenerUltimosCinco();
    Videojuego aplicarDescuento(Long id, Double porcentaje);
}