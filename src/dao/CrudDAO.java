/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.util.List;

/**
 *
 * @author gabri
 */
public interface CrudDAO<Entidad, Clave> { // Entidad: clases pojo, clave: las pk de cada una (string o integer)
    
    boolean registrar(Entidad objeto);
    List<Entidad> consultarTodos();
    Entidad buscarPorId(Clave id); // <-- Sirve para cualquier PK (String o Integer)
    boolean editar(Entidad objeto);
    boolean eliminar(Clave id);
    
}
