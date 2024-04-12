package com.example.webalumno.entidad;

import java.util.LinkedList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="grados")
public class Grado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nombre_grado",nullable = true)
    private String nombre_grado;

    @OneToMany(mappedBy = "grado", fetch = FetchType.EAGER)
    private List<Estudiante> estudiantes = new LinkedList<>();

    
    public String getNombre_grado() {
        return nombre_grado;
    }

    public void setNombre_grado(String nombre_grado) {
        this.nombre_grado = nombre_grado;
    }

    public Grado() {
    }

    public Grado(Long id, String nombre_grado) {
        this.id = id;
        this.nombre_grado = nombre_grado;
    }

    public Grado(String nombre_grado) {
        this.nombre_grado = nombre_grado;
    }

    @Override
    public String toString() {
        return "Estudiante " + nombre_grado;
    }

    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }    

}
