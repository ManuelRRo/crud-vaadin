package com.example.webalumno.vaadinui;

import com.example.webalumno.servicios.GradoServicio;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.HasValueChangeMode;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.webalumno.entidad.Estudiante;
import com.example.webalumno.entidad.Grado;
import com.example.webalumno.servicios.EstudianteServicio;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
@CssImport("./styles/shared-styles.css")
public class MainView extends VerticalLayout {

    // #1 Crear Data Grid con la clase de la entidad del servicio
    private Grid<Estudiante> gridEstudiante = new Grid<>(Estudiante.class);
    private TextField filterText = new TextField();

    private EstudianteForm form;
    // #2
    // Instanciar interface controlador
    @Autowired
    private EstudianteServicio estudianteServicio;

    public MainView(EstudianteServicio estudianteServicio, GradoServicio gradoServicio) {

        this.estudianteServicio = estudianteServicio;

        addClassName("list-view");
        setSizeFull();
        configureFilter();
        configureGrid();

        form = new EstudianteForm(gradoServicio.listarTodosLosGrados());
        form.addListener(EstudianteForm.SaveEvent.class, this::saveEstudiante);
        form.addListener(EstudianteForm.DeleteEvent.class, this::deleteEstudiante);
        form.addListener(EstudianteForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(gridEstudiante, form);
        content.addClassName("content");
        content.setSizeFull();

        add(filterText, content);

        updateList();

        closeEditor();
    }

    private void configureFilter() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        // invocar el updateList(); cada vez que l caja de texto cambie de estado
        filterText.addValueChangeListener(e -> updateList());
    }

    private void configureGrid() {
        gridEstudiante.addClassName("grid-estudiante");
        gridEstudiante.setSizeFull();

        // Remover columna que comparte
        gridEstudiante.removeColumnByKey("grado");
        // poner el nombre igual que en las entidades de clase
        gridEstudiante.setColumns("email", "apellido", "nombre");

        // Agregar columna personalizada para mostrar el nombre en lugar del ID
        gridEstudiante.addColumn(estudiante -> {
            Grado grado = estudiante.getGrado();
            return grado == null ? "-" : grado.getNombre_grado();
        }).setHeader("Grado");

        gridEstudiante.getColumns().forEach(col -> col.setAutoWidth(true));

        gridEstudiante.asSingleSelect().addValueChangeListener(event -> editEstudiante(event.getValue()));

    }

    public void editEstudiante(Estudiante estudiante) {
        if (estudiante == null) {
            closeEditor();
        } else {
            form.setEstudiante(estudiante);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setEstudiante(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void saveEstudiante(EstudianteForm.SaveEvent event) {
        estudianteServicio.guardarEstudiante(event.getEstudiante());
        updateList();
        closeEditor();
    }

    private void deleteEstudiante(EstudianteForm.DeleteEvent event) {
        estudianteServicio.eliminar(event.getEstudiante());
        updateList();
        closeEditor();
    }

    private void updateList() {
        gridEstudiante.setItems(estudianteServicio.listarTodosLosEstudiantes(filterText.getValue()));
    }

}
