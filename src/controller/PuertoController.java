package controller;

import dao.CrudDAO;
import dao.PuertoDAO;
import java.util.List;
import model.Puerto;
import utils.ConsolaUtils;

/**
 * Controlador de consola para {@link Puerto}
 */
public class PuertoController implements CrudController {
    
    private final CrudDAO<Puerto, String> puertoDao = new PuertoDAO();
    
    @Override
    public void iniciarMenu() {
        int opcion;
        do{
            mostrarMenu();
            opcion = ConsolaUtils.leerEntero("Seleccione una opción: ", 1, 5);

            switch(opcion) {
                case 1 -> registrar();
                case 2 -> consultar();
                case 3 -> editar();
                case 4 -> eliminar();
                case 5 -> System.out.println("Regresando...");
                default -> System.out.println("Opción no válida...");
            }
        } while (opcion != 5);
    }
    
    private void mostrarMenu() {
        
        String menu = """
                      \n=== GESTIÓN DE PUERTOS ===
                      1. Registrar Puerto.
                      2. Consultar Puertos.
                      3. Editar Puerto.
                      4. Eliminar Puerto.
                      5. Volver.
                      """;
        
        System.out.println(menu);
        
    }
    
    @Override
    public void registrar() {
        
        System.out.println("\n=== Nuevo Puerto ===");
        String codigo = ConsolaUtils.leerTexto("Código del puerto (máx. 10 caracteres): ", 10).toUpperCase();
        String nombre = ConsolaUtils.leerTexto("Nombre del puerto: ", 50);
        String ciudad = ConsolaUtils.leerTexto("Ciudad: ", 50);
        String pais = ConsolaUtils.leerTexto("País: ", 50);
        int numBarcos = ConsolaUtils.leerEntero("Capacidad máxima de barcos: ", 1, 10000);

        try {
            Puerto puertoNuevo = new Puerto(codigo, nombre, 
                    ciudad, pais, numBarcos);
            if(puertoDao.registrar(puertoNuevo)){
                System.out.println("Puerto registrado correctamente.");
            }
            
        } catch (IllegalArgumentException e){
            System.out.println("Error al agregar puerto: " + e.getMessage());
        }
    }
    
    @Override
    public void consultar() {
        
        System.out.println("\n=== Cátalogo de Puertos ===");
        List<Puerto> puertos = puertoDao.consultarTodos();
        
        if(puertos.isEmpty()){
            System.out.println("No hay puertos registrados.");
            return;
        }
        
        for(Puerto pu : puertos){
            
            System.out.printf("[%s] %s - %s, %s (Capacidad: %d barcos)%n",
                    pu.getCodigoPuerto(),
                    pu.getNombre(),
                    pu.getCiudad(),
                    pu.getPais(),
                    pu.getNumBarcos());
        }
    }
    
    @Override
    public void editar() {
        System.out.println("\n=== Editar Puerto===");
        
        String codigo = ConsolaUtils.leerTexto("Código del puerto a modificar: ", 10).toUpperCase();
        
        Puerto actual = puertoDao.buscarPorId(codigo);
        if (actual == null) {
            System.out.println("No se encontró níngún puerto con el código " 
            + codigo + " .");
            return;
        }
        
        System.out.println("(Presione ENTER si gusta conservar el valor actual)");
        
        String nombreFinal = actual.getNombre();
        String nuevoNombre = ConsolaUtils.leerTextoOpcional("Nuevo nombre [" + actual.getNombre() + "]: ", 50);
        if (!nuevoNombre.isEmpty()) {
            nombreFinal = nuevoNombre;
        }

        String ciudadFinal = actual.getCiudad();
        String nuevaCiudad = ConsolaUtils.leerTextoOpcional("Nueva ciudad [" + actual.getCiudad() + "]: ", 50);
        if (!nuevaCiudad.isEmpty()) {
            ciudadFinal = nuevaCiudad;
        }

        String paisFinal = actual.getPais();
        String nuevoPais = ConsolaUtils.leerTextoOpcional("Nuevo país [" + actual.getPais() + "]: ", 50);
        if (!nuevoPais.isEmpty()) {
            paisFinal = nuevoPais;
        }

        int numBarcosFinal = actual.getNumBarcos();
        Integer nuevoNumBarcos = ConsolaUtils.leerEnteroOpcional("Nueva capacidad de barcos [" + actual.getNumBarcos() + "]: ", 1, 10000);
        if (nuevoNumBarcos != null) {
            numBarcosFinal = nuevoNumBarcos;
        }
        
        try {
            Puerto puertoEditado = new Puerto(codigo, nombreFinal, 
                    ciudadFinal, paisFinal, numBarcosFinal);
            if(puertoDao.editar(puertoEditado)){
                System.out.println("Puerto actualizado correctamente.");
            }
            
        } catch (IllegalArgumentException e){
            System.out.println("Error al agregar puerto: " + e.getMessage());
        }
    }
    
    @Override
    public void eliminar() {
        System.out.println("\n=== Eliminar Puerto ===");
        String codigo = ConsolaUtils.leerTexto("Código del puerto a eliminar: ", 10).toUpperCase();
        String confirmacion = ConsolaUtils.leerTexto("¿Confirmas eliminar el puerto " + codigo + "? (S/N): ", 1);
        
        
        try {
            if(confirmacion.equalsIgnoreCase("S")){
                if(puertoDao.eliminar(codigo)){
                    System.out.println("Puerto eliminado correctamente.");
                }
            }
            
        } catch (IllegalArgumentException e){
            System.out.println("No se pudo eliminar: " + e.getMessage());
        }
        
    }
}