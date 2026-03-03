package com.tallerprog.tallerprog.service;

import com.tallerprog.tallerprog.model.Desarrolladora;
import com.tallerprog.tallerprog.model.Genero;
import com.tallerprog.tallerprog.model.ResourceNotFoundException;
import com.tallerprog.tallerprog.model.Videojuego;
import com.tallerprog.tallerprog.repository.DesarrolladoraRepository;
import com.tallerprog.tallerprog.repository.VideojuegoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideojuegoServiceImpl implements VideojuegoService {

    private final VideojuegoRepository videojuegoRepository;
    private final DesarrolladoraRepository desarrolladoraRepository;

    public VideojuegoServiceImpl(VideojuegoRepository videojuegoRepository,
                                 DesarrolladoraRepository desarrolladoraRepository) {
        this.videojuegoRepository = videojuegoRepository;
        this.desarrolladoraRepository = desarrolladoraRepository;
    }

    @Override
    public List<Videojuego> listarTodos() {
        List<Videojuego> lista = videojuegoRepository.findAll();
        lista.forEach(this::calcularPrecioConIva);
        return lista;
    }

    @Override
    public Videojuego obtenerPorId(Long id) {
        Videojuego videojuego = videojuegoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Videojuego no encontrado con id: " + id));
        calcularPrecioConIva(videojuego);
        return videojuego;
    }

    @Override
    public Videojuego crear(Videojuego videojuego) {
        validarVideojuego(videojuego);

        Long desarrolladoraId = videojuego.getDesarrolladora().getId();
        Desarrolladora desarrolladora = desarrolladoraRepository.findById(desarrolladoraId)
                .orElseThrow(() -> new ResourceNotFoundException("La desarrolladora con id " + desarrolladoraId + " no existe."));

        videojuego.setId(null);
        videojuego.setTitulo(videojuego.getTitulo().trim());
        videojuego.setCodigoRegistro(videojuego.getCodigoRegistro().trim());
        videojuego.setDesarrolladora(desarrolladora);

        Videojuego guardado = videojuegoRepository.save(videojuego);
        calcularPrecioConIva(guardado);
        return guardado;
    }

    @Override
    public Videojuego actualizar(Long id, Videojuego videojuego) {
        Videojuego existente = videojuegoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Videojuego no encontrado con id: " + id));

        validarVideojuego(videojuego);

        Long desarrolladoraId = videojuego.getDesarrolladora().getId();
        Desarrolladora desarrolladora = desarrolladoraRepository.findById(desarrolladoraId)
                .orElseThrow(() -> new ResourceNotFoundException("La desarrolladora con id " + desarrolladoraId + " no existe."));

        existente.setTitulo(videojuego.getTitulo().trim());
        existente.setPrecio(videojuego.getPrecio());
        existente.setCodigoRegistro(videojuego.getCodigoRegistro().trim());
        existente.setGenero(videojuego.getGenero());
        existente.setDesarrolladora(desarrolladora);

        Videojuego actualizado = videojuegoRepository.save(existente);
        calcularPrecioConIva(actualizado);
        return actualizado;
    }

    @Override
    public void eliminar(Long id) {
        Videojuego videojuego = videojuegoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Videojuego no encontrado con id: " + id));
        videojuegoRepository.delete(videojuego);
    }

    @Override
    public List<Videojuego> buscarPorTitulo(String titulo) {
        List<Videojuego> lista = videojuegoRepository.findByTituloContainingIgnoreCase(titulo);
        lista.forEach(this::calcularPrecioConIva);
        return lista;
    }

    @Override
    public List<Videojuego> buscarPorRangoPrecio(Double min, Double max) {
        if (min == null || max == null) {
            throw new IllegalArgumentException("Los parámetros min y max son obligatorios.");
        }
        if (min < 0 || max < 0) {
            throw new IllegalArgumentException("El rango de precio no puede tener valores negativos.");
        }
        if (min > max) {
            throw new IllegalArgumentException("El valor mínimo no puede ser mayor que el máximo.");
        }

        List<Videojuego> lista = videojuegoRepository.buscarPorRangoPrecio(min, max);
        lista.forEach(this::calcularPrecioConIva);
        return lista;
    }

    @Override
    public List<Videojuego> buscarPorGenero(Genero genero) {
        List<Videojuego> lista = videojuegoRepository.findByGenero(genero);
        lista.forEach(this::calcularPrecioConIva);
        return lista;
    }

    @Override
    public List<Videojuego> obtenerUltimosCinco() {
        List<Videojuego> lista = videojuegoRepository.ultimosCincoRegistrados();
        lista.forEach(this::calcularPrecioConIva);
        return lista;
    }

    @Override
    public Videojuego aplicarDescuento(Long id, Double porcentaje) {
        if (porcentaje == null) {
            throw new IllegalArgumentException("El porcentaje de descuento es obligatorio.");
        }
        if (porcentaje <= 0 || porcentaje >= 100) {
            throw new IllegalArgumentException("El porcentaje debe ser mayor a 0 y menor a 100.");
        }

        Videojuego videojuego = videojuegoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Videojuego no encontrado con id: " + id));

        double nuevoPrecio = videojuego.getPrecio() * (1 - porcentaje / 100.0);
        videojuego.setPrecio(Math.round(nuevoPrecio * 100.0) / 100.0);

        Videojuego actualizado = videojuegoRepository.save(videojuego);
        calcularPrecioConIva(actualizado);
        return actualizado;
    }

    private void validarVideojuego(Videojuego videojuego) {
        if (videojuego == null) {
            throw new IllegalArgumentException("El videojuego no puede ser nulo.");
        }

        if (videojuego.getTitulo() == null || videojuego.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío.");
        }

        if (videojuego.getTitulo().trim().length() > 100) {
            throw new IllegalArgumentException("El título no puede superar los 100 caracteres.");
        }

        if (videojuego.getPrecio() == null) {
            throw new IllegalArgumentException("El precio es obligatorio.");
        }

        if (videojuego.getPrecio() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }

        if (videojuego.getCodigoRegistro() == null || videojuego.getCodigoRegistro().trim().isEmpty()) {
            throw new IllegalArgumentException("El código de registro es obligatorio.");
        }

        if (videojuego.getGenero() == null) {
            throw new IllegalArgumentException("El género es obligatorio.");
        }

        if (videojuego.getDesarrolladora() == null || videojuego.getDesarrolladora().getId() == null) {
            throw new IllegalArgumentException("Debe enviar la desarrolladora con un id válido.");
        }
    }

    private void calcularPrecioConIva(Videojuego videojuego) {
        if (videojuego != null && videojuego.getPrecio() != null) {
            double valor = videojuego.getPrecio() * 1.19;
            videojuego.setPrecioConIva(Math.round(valor * 100.0) / 100.0);
        }
    }
}