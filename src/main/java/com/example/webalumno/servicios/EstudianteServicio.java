/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.webalumno.servicios;

import com.example.webalumno.entidad.Estudiante;
import java.util.List;

/**
 *
 * @author memo
 */
public interface EstudianteServicio {
    public List<Estudiante> listarTodosLosEstudiantes();

    public List<Estudiante> listarTodosLosEstudiantes(String stringFilter);

    public Estudiante guardarEstudiante(Estudiante estudiante);

    public Estudiante obtenerEstudiantePorId(Long id);

    public Estudiante actualizarEstudiante(Estudiante estudiante);

    public void eliminarEstudiante(Long id);

    public void eliminar(Estudiante estudiante);

}
