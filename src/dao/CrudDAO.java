package dao;


import java.util.List;

/**
 * Interfaz genérica que define las operaciones estándar de persistencia (CRUD)
 * sobre la base de datos relacional para cualquier entidad del dominio.
 *
 * @param <Entidad> Tipo de modelo o entidad que gestiona el DAO.
 * @param <Clave> Tipo de dato de la llave primaria de la entidad.
 */
public interface CrudDAO<Entidad, Clave> {
    
    /**
     * Inserta un nuevo registro de la entidad en la base de datos
     *
     * @param objeto
     * @return {@code true} si el registro se guardó correctamente; {@code false} en caso contrario
     */
    boolean registrar(Entidad objeto);
    
    /**
     * Recupera todos los registros almacenados de la entidad
     *
     * @return Lista con todas las instancias encontradas
     */
    List<Entidad> consultarTodos();
    
    /**
     * Busca un registro específico a partir de su identificador 
     *
     * @param id es la Llave primaria del registro a consultar
     * @return La instancia encontrada, o {@code null} si no existe coincidencia
     */
    Entidad buscarPorId(Clave id); 
    
    /**
     * Actualiza los datos de un registro existente en la base de datos
     *
     * @param objeto
     * @return {@code true} si se modificó al menos una fila; {@code false} si falló
     */
    boolean editar(Entidad objeto);
    
    /**
     * Elimina un registro de la base de datos mediante su identificador único
     *
     * @param id Llave primaria del registro a eliminar
     * @return {@code true} si el registro fue eliminado o {@code false} en caso contrario
     */
    boolean eliminar(Clave id);
    
}
