package com.tallerprog.tallerprog.repository;

import com.tallerprog.tallerprog.model.Genero;
import com.tallerprog.tallerprog.model.Videojuego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VideojuegoRepository extends JpaRepository<Videojuego, Long> {

    // Derived query: por género
    List<Videojuego> findByGenero(Genero genero);

    // Derived query: título contiene texto (ignora mayúsculas/minúsculas)
    List<Videojuego> findByTituloContainingIgnoreCase(String titulo);

    // JPQL: rango de precios
    @Query("SELECT v FROM Videojuego v WHERE v.precio BETWEEN :min AND :max")
    List<Videojuego> buscarPorRangoPrecio(Double min, Double max);

    // Nativa SQL: últimos 5 registrados
    @Query(value = "SELECT * FROM videojuegos ORDER BY fecha_creacion DESC LIMIT 5", nativeQuery = true)
    List<Videojuego> ultimosCincoRegistrados();
}