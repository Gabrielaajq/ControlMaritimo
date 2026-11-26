/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.util.List;

/**
 * Interfaz genérica que define las operaciones estándar de persistencia (CRUD)
 * sobre la base de datos relacional para cualquier entidad del dominio.
 * @param <Entidad> Tipo de modelo o entidad que gestiona el DAO.
 * @param <Clave1> Tipo de dato de la llave primaria de la primera entidad.
 * @param <Clave2> Tipo de dato de la llave primaria de la segunda entidad.
 */
public interface RelacionBarcoPescadoDAO<Entidad, Clave1, Clave2> {
    
    boolean asignarPescado(Clave1 matricula, Clave2 pescadoId);
    
    List<Entidad> consultarTodos(Clave1 matricula);
    
    boolean eliminar(Clave1 matricula, Clave2 pescadoid);
}
