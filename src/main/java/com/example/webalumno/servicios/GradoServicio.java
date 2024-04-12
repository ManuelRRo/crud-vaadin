package com.example.webalumno.servicios;
import com.example.webalumno.entidad.Grado;

import java.util.List;

public interface GradoServicio {
    public List<Grado> listarTodosLosGrados();

    public Grado guardargrado(Grado grado);

    public Grado obtenerEstudiantePorId(Long id);

    public Grado actualizarEstudiante(Grado grado);

    public void eliminarGrado(Long id);
}
