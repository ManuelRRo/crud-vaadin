package com.example.webalumno.servicios;

import com.example.webalumno.entidad.Estudiante;
import com.example.webalumno.entidad.Grado;
import com.example.webalumno.repositorio.GradoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GradoServicioImpl implements GradoServicio{
    @Autowired
    GradoRepositorio repositorio;
    @Override
    public List<Grado> listarTodosLosGrados() {
        return repositorio.findAll();
    }

    @Override
    public Grado guardargrado(Grado grado) {
        return repositorio.save(grado);
    }

    @Override
    public Grado obtenerEstudiantePorId(Long id) {
        return repositorio.findById(id).get();
    }

    @Override
    public Grado actualizarEstudiante(Grado grado) {
        return repositorio.save(grado);
    }

    @Override
    public void eliminarGrado(Long id) {
        repositorio.deleteById(id);
    }
}
