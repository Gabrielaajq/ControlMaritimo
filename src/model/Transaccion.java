/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;
import enums.TipoTransaccion;

/**
 *
 * @author gabri
 */
public class Transaccion {
    
    private String claveTransaccion;
    private LocalDate fechaTransaccion;
    private TipoTransaccion tipoTransaccion;
    private Double elementos;
    private Integer atracoId;
    
    public Transaccion() {}

    public Transaccion(String claveTransaccion, LocalDate fechaTransaccion, TipoTransaccion tipoTransaccion, Double elementos, Integer atracoId) {
        this.claveTransaccion = claveTransaccion;
        this.fechaTransaccion = fechaTransaccion;
        this.tipoTransaccion = tipoTransaccion;
        this.elementos = elementos;
        this.atracoId = atracoId;
    }

    public String getClaveTransaccion() {
        return claveTransaccion;
    }

    public void setClaveTransaccion(String claveTransaccion) {
        this.claveTransaccion = claveTransaccion;
    }

    public LocalDate getFechaTransaccion() {
        return fechaTransaccion;
    }

    public void setFechaTransaccion(LocalDate fechaTransaccion) {
        this.fechaTransaccion = fechaTransaccion;
    }

    public TipoTransaccion getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(TipoTransaccion tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public Double getElementos() {
        return elementos;
    }

    public void setElementos(Double elementos) {
        this.elementos = elementos;
    }

    public Integer getAtracoId() {
        return atracoId;
    }

    public void setAtracoId(Integer atracoId) {
        this.atracoId = atracoId;
    }
    
}
