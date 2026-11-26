package dao;

import java.util.List;

/**
 * Interfaz que define las operaciones de persistencia para gestionar
 * la relación muchos a muchos entre barcos pesqueros y especies 
 *
 * @param <Entidad> Tipo de modelo de la especie asociada
 * @param <Clave1> Tipo de dato de la llave primaria del barco pesquero
 * @param <Clave2> Tipo de dato de la llave primaria del pescado
 */
public interface RelacionBarcoPescadoDAO<Entidad, Clave1, Clave2> {
    
    /**
     * Registra una asociación entre un barco pesquero y una especie de pescado
     *
     * @param matricula Identificador único del barco pesquero
     * @param pescadoId Identificador único de la especie de pescado
     * @return {@code true} si la vinculación se guardó correctamente; 
     * {@code false} en caso contrario.
     */
    boolean asignarPescado(Clave1 matricula, Clave2 pescadoId);
    
    /**
     * Consulta todas las especies de pescado asociadas a un barco pesquero específico
     *
     * @param matricula Identificador único del barco pesquero
     * @return Lista de especies vinculadas al barco
     */
    List<Entidad> consultarTodos(Clave1 matricula);
    
    
    /**
     * Elimina la asociación entre un barco pesquero y una especie de pescado
     *
     * @param matricula Identificador único del barco pesquero
     * @param pescadoid Identificador único de la especie a desvincular
     * @return {@code true} si el vínculo fue eliminado; 
     * {@code false} si no existía
     */
    boolean eliminar(Clave1 matricula, Clave2 pescadoid);
}
