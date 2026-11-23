package controller;

/**
 *  Esta clase sirve para vincular el título del menú
 *  y la clase controladora para trabajar
 */
public class OpcionMenu {
    
    private final String titulo;
    private final CrudController controlador;

    public OpcionMenu(String titulo, CrudController controlador) {
        this.titulo = titulo;
        this.controlador = controlador;
    }

    public String getTitulo() {
        return titulo;
    }

    public CrudController getControlador() {
        return controlador;
    }
   
}
