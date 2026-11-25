package controller;

/**
 * Puente para los controladores encargados de gestionar
 * la interacción por consola para operaciones de registro, consulta, edición y 
 * eliminación
 */
public interface CrudController {
    
    /**
     * Despliega el menú principal y gestiona el ciclo de 
     * selección del usuario
     */
    void iniciarMenu();
    
    /**
     * Solicita los datos necesarios por consola y realiza el registro 
     * de una nueva entidad
     */
    void registrar();
    
    /**
     * Muestra en consola el listado de registros almacenados de la
     * entidad seleccionada
     */
    void consultar();
    
    /**
     * Permite localizar un registro existente y modificar sus 
     * atributos de forma opcional
     */
    void editar();
    
    /**
     * Solicitud del identificador de un registro y gestiona su 
     * eliminación
     */
    void eliminar();
    
}
