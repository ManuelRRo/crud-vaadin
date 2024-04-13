package com.example.webalumno.vaadinui.ListEstudiante;

import com.example.webalumno.entidad.Estudiante;
import com.vaadin.flow.component.Component;
import com.example.webalumno.entidad.Grado;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;




public class EstudianteForm extends FormLayout {
    TextField nombre = new TextField("First name");
    TextField apellido = new TextField("Last name");
    EmailField email = new EmailField("Email");

    ComboBox<Grado> grado = new ComboBox<>("Grado");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");
    Binder<Estudiante> binder = new BeanValidationBinder<>(Estudiante.class);

    //para el update
    private Estudiante estudiante;

    public EstudianteForm(List<Grado> grados){
        addClassName("estudiante-form");

        binder.bindInstanceFields(this);
        
        grado.setItems(grados);
        //usar getnombre grado para como valor de display en la opciones
        grado.setItemLabelGenerator(Grado::getNombre_grado);
        //grado.setReadOnly(false);
        add(nombre,apellido,email,grado,createButtonsLayout());

    }
    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave()); // <1>
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, estudiante))); // <2>
        close.addClickListener(event -> fireEvent(new CloseEvent(this))); // <3>

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid())); // <4>
        return  new HorizontalLayout(save,delete,close);
    }

    private void validateAndSave(){
        try{
            binder.writeBean(estudiante);
            fireEvent(new SaveEvent(this,estudiante));
        }catch (ValidationException e){
            System.out.println("ERRRORRRR ES "+e.getMessage());
            e.printStackTrace();
        }
    }

    public void setEstudiante(Estudiante estudiante){
        this.estudiante = estudiante;
        binder.readBean(estudiante);
    }

    // Events
    public static abstract class EstudianteFormEvent extends ComponentEvent<EstudianteForm> {
        private Estudiante estudiante;
        protected EstudianteFormEvent(EstudianteForm source,Estudiante estudiante) {
            super(source, false);
            this.estudiante = estudiante;
        }
        public Estudiante getEstudiante() {
            return estudiante;
        }
    }
    public static class SaveEvent extends EstudianteFormEvent {
        SaveEvent(EstudianteForm source,Estudiante estudiante) {
            super(source, estudiante);
        }
    }
    public static class DeleteEvent extends EstudianteFormEvent {
        DeleteEvent(EstudianteForm source,Estudiante estudiante) {
            super(source, estudiante);
        }
    }
    public static class CloseEvent extends EstudianteFormEvent {
        CloseEvent(EstudianteForm source) {
            super(source, null);
        }
    }
    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }


}
