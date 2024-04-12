
package com.example.webalumno.entidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "estudiantes")
public class Estudiante {
    
    //Agregar el id por defecto y supongo que
    //el otro es para que que sea autoincrement
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@Columns crea las columnas de la base de datos
    //y define sus propiedades
    @Column(name = "nombre",nullable = false)
    private String nombre;
    
    @Column(name = "apellido",nullable = false)
    private String apellido;
    
    @Column(name = "email",nullable = false,length=50,unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "grado_id")
    private Grado grado;

    public Grado getGrado() {
        return grado;
    }
    //RECORDAR LOS SET DE campos de otra tabla deben ser de tipo
    //void
    public void setGrado(Grado grado) {
        this.grado = grado;
    }
    
    
    public Estudiante(){}
    
    public Estudiante(Long id, String nombre, String apellido, String email,Grado grado) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.grado = grado;
    }
    
    public Estudiante(String nombre, String apellido, String email) {
        super();
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Estudiante{" + "id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", email=" + email + '}';
    }
    
    
    
}

