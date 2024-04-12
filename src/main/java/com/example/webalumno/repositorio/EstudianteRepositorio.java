package com.example.webalumno.repositorio;

import com.example.webalumno.entidad.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EstudianteRepositorio extends JpaRepository<Estudiante, Long> {
    //Agregar query para el filtro
    //Tener cuidado con el espacio la final de los filtros
    @Query("select e from Estudiante e " +
            "where lower(e.nombre) like lower(concat('%',:searchTerm,'%')) " +
            "or lower(e.apellido) like lower(concat('%',:searchTerm,'%'))")
    List<Estudiante> BuscarEstudiante(@Param("searchTerm") String searchTerm);

    
}
