package model;

/**
 * Modela una especie catalogada dentro del sistema para vincularse
 * tanto con zonas de pesca como con barcos pesqueros
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
