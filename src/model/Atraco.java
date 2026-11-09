/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

/**
 *
 * @author gabri
 */
public class Atraco {
    
    private Integer atracoId;
    private LocalDate fechaAtraco;

    public Integer getAtracoId() {
        return atracoId;
    }

    public void setAtracoId(Integer atracoId) {
        this.atracoId = atracoId;
    }

    public LocalDate getFechaAtraco() {
        return fechaAtraco;
    }

    public void setFechaAtraco(LocalDate fechaAtraco) {
        this.fechaAtraco = fechaAtraco;
    }
    
}
