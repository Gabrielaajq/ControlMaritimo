/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author gabri
 */
public class Pescado {
    
    private Integer pescadoId;
    private String nombre;

    public Pescado(Integer pescadoId, String nombre) {
        this.pescadoId = pescadoId;
        this.nombre = nombre;
    }

    public Integer getPescadoId() {
        return pescadoId;
    }

    public void setPescadoId(Integer pescadoId) {
        this.pescadoId = pescadoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
