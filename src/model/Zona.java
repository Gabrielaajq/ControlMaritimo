package model;

/**
 * Representa una zona geográfica de pesca delimitada por coordenadas de latitud,
 * longitud y un radio en kilómetros
 */
public class Zona {
    
    private String claveZona;
    private Double latitud;
    private Double longitud;
    private Double radio;

    public Zona() {
    }

    public Zona(String claveZona, Double latitud, 
                Double longitud, Double radio) {
        this.claveZona = claveZona;
        this.latitud = latitud;
        this.longitud = longitud;
        this.radio = radio;
    }

    public String getClaveZona() {
        return claveZona;
    }

    public void setClaveZona(String claveZona) {
        this.claveZona = claveZona;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getRadio() {
        return radio;
    }

    public void setRadio(Double radio) {
        this.radio = radio;
    }
    
}
