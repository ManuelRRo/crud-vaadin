package com.example.webalumno.controlador;

import com.example.webalumno.entidad.Estudiante;
import com.example.webalumno.servicios.EstudianteServicio;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


public class EstudianteControlador {

    private EstudianteServicio servicio;

    @GetMapping("/")
    public String home(){
        return "home";
    }

  
    @GetMapping("/estudiantes")
    public String estudiantesListado(Model model){
        model.addAttribute("estudiantes",servicio.listarTodosLosEstudiantes());
        return "estudiantes";
    }

    @GetMapping("/estudiantes/nuevo")
    public String crearEstudianteFormulario(Model modelo){
        Estudiante estudiante = new Estudiante();
        modelo.addAttribute("estudiante",estudiante);
        return "crear_estudiante";
    }

    @PostMapping("/estudiantes")
    public String guardarEstudiante(@ModelAttribute("estudiante") Estudiante estudiante){
        servicio.guardarEstudiante(estudiante);
        return  "redirect:estudiantes";
    }

    @GetMapping("/estudiantes/editar/{id}")
    public  String mostrarFormularioDeEditar(@PathVariable Long id, Model modelo){
        modelo.addAttribute("estudiante",servicio.obtenerEstudiantePorId(id));
        return "editar_estudiante";
    }

    @PostMapping("/estudiantes/{id}")
    public  String actualizarEstudiante(@PathVariable Long id,@ModelAttribute("estudiante") Estudiante estudiante,Model modelo){
        Estudiante estudianteExistente = servicio.obtenerEstudiantePorId(id);
        estudianteExistente.setNombre(estudiante.getNombre());
        estudianteExistente.setApellido(estudiante.getApellido());
        estudianteExistente.setEmail(estudiante.getEmail());

        servicio.actualizarEstudiante(estudianteExistente);
        return "redirect:/estudiantes";

    }

    @GetMapping("/estudiantes/{id}")
    public String eliminarEstudiante(@PathVariable Long id) {
        servicio.eliminarEstudiante(id);
        return "redirect:/estudiantes";
    }
    
    
}
