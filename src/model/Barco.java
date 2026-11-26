package model;

import java.time.LocalDate;

/**
 * Clase padre que modela los atributos y comportamientos generales de cualquier
 * barco registrado en el sistema 
 */
public abstract class Barco {
    
    private String matricula;
    private String bandera;
    private String nombre;
    private Double pesoTonelada;
    private LocalDate fechaBotadura;

    public Barco(String matricula, String bandera, String nombre, Double pesoTonelada, LocalDate fechaBotadura) {
        this.matricula = matricula;
        this.bandera = bandera;
        this.nombre = nombre;
        this.pesoTonelada = pesoTonelada;
        this.fechaBotadura = fechaBotadura;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getBandera() {
        return bandera;
    }

    public void setBandera(String bandera) {
        this.bandera = bandera;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPesoTonelada() {
        return pesoTonelada;
    }

    public void setPesoTonelada(Double pesoTonelada) {
        this.pesoTonelada = pesoTonelada;
    }

    public LocalDate getFechaBotadura() {
        return fechaBotadura;
    }

    public void setFechaBotadura(LocalDate fechaBotadura) {
        this.fechaBotadura = fechaBotadura;
    }

    
    
    public abstract String  detalles();
    public abstract Double capacidadCarga();
    
}
