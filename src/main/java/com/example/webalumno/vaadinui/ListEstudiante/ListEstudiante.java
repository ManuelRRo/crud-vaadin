package com.example.webalumno.vaadinui.ListEstudiante;

import com.example.webalumno.servicios.GradoServicio;
import com.example.webalumno.vaadinui.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.webalumno.entidad.Estudiante;
import com.example.webalumno.entidad.Grado;
import com.example.webalumno.servicios.EstudianteServicio;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value="",layout = MainLayout.class)
@PageTitle("Lista Estudiantes")
public class ListEstudiante extends VerticalLayout {

    // #1 Crear Data Grid con la clase de la entidad del servicio
    private Grid<Estudiante> gridEstudiante = new Grid<>(Estudiante.class);
    private TextField filterText = new TextField();

    private EstudianteForm form;
    // #2
    // Instanciar interface controlador
    @Autowired
    private EstudianteServicio estudianteServicio;

    public ListEstudiante(EstudianteServicio estudianteServicio, GradoServicio gradoServicio) {

        this.estudianteServicio = estudianteServicio;

        addClassName("list-view");
        setSizeFull();
        
        configureGrid();

        form = new EstudianteForm(gradoServicio.listarTodosLosGrados());
        form.addListener(EstudianteForm.SaveEvent.class, this::saveEstudiante);
        form.addListener(EstudianteForm.DeleteEvent.class, this::deleteEstudiante);
        form.addListener(EstudianteForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(gridEstudiante, form);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);

        updateList();

        closeEditor();
    }

    private HorizontalLayout getToolBar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        // invocar el updateList(); cada vez que l caja de texto cambie de estado
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add contact");
        addContactButton.addClickListener(click -> addEstudiante());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
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

    public void addEstudiante(){
        gridEstudiante.asSingleSelect().clear();
        editEstudiante(new Estudiante());
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
