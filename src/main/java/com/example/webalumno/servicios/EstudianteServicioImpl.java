package com.example.webalumno.servicios;

import com.example.webalumno.entidad.Estudiante;
import com.example.webalumno.repositorio.EstudianteRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstudianteServicioImpl implements EstudianteServicio{
    @Autowired
    private EstudianteRepositorio repositorio;

    @Override
    public List<Estudiante> listarTodosLosEstudiantes() {
        return repositorio.findAll();
    }
    @Override
    public List<Estudiante> listarTodosLosEstudiantes(String stringFilter){
        if(stringFilter == null || stringFilter.isEmpty()){
            return repositorio.findAll();
        }else {
            return repositorio.BuscarEstudiante(stringFilter);
        }
    }

    @Override
    public Estudiante guardarEstudiante(Estudiante estudiante) {
        return repositorio.save(estudiante);
    }

    @Override
    public Estudiante obtenerEstudiantePorId(Long id) {
        return repositorio.findById(id).get();
    }

    @Override
    public Estudiante actualizarEstudiante(Estudiante estudiante) {
        return repositorio.save(estudiante);
    }

    @Override
    public void eliminarEstudiante(Long id) {
        repositorio.deleteById(id);
    }

    @Override
    public void eliminar(Estudiante estudiante){
        repositorio.delete(estudiante);
    }

    //Metodo Eliminar unsando una clase

    //Agregar logica pra un nuveo filtro


}
