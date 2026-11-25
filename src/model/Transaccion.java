package model;

import java.time.LocalDateTime;
import enums.TipoTransaccion;

/**
 * Modela una operación de carga o descarga asociada a un
 * atraco portuario, registrando fecha, hora y volumen operado
 */
public class Transaccion {
    
    private String claveTransaccion;
    private LocalDateTime fechaTransaccion;
    private TipoTransaccion tipoTransaccion;
    private Double elementos;
    private Integer atracoId;
    
    public Transaccion() {}

    public Transaccion(String claveTransaccion, LocalDateTime fechaTransaccion, TipoTransaccion tipoTransaccion, Double elementos, Integer atracoId) {
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

    public LocalDateTime getFechaTransaccion() {
        return fechaTransaccion;
    }

    public void setFechaTransaccion(LocalDateTime fechaTransaccion) {
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
