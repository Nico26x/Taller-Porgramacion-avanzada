package com.tallerprog.tallerprog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "desarrolladoras", uniqueConstraints = {
        @UniqueConstraint(columnNames = "nombre")
})
public class Desarrolladora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    private String sitioWeb;

    @JsonIgnore
    @OneToMany(mappedBy = "desarrolladora", cascade = CascadeType.ALL)
    private List<Videojuego> videojuegos = new ArrayList<>();

    public Desarrolladora() {
    }

    public Desarrolladora(Long id, String nombre, String sitioWeb) {
        this.id = id;
        this.nombre = nombre;
        this.sitioWeb = sitioWeb;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getSitioWeb() {
        return sitioWeb;
    }

    public List<Videojuego> getVideojuegos() {
        return videojuegos;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    public void setVideojuegos(List<Videojuego> videojuegos) {
        this.videojuegos = videojuegos;
    }
}