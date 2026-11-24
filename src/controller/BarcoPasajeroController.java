/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.BarcoPasajeroDAO;
import dao.CrudDAO;
import java.time.LocalDate;
import java.util.List;
import model.BarcoPasajero;
import model.Puerto;
import utils.ConsolaUtils;

/**
 *
 * @author emman
 */
public class BarcoPasajeroController implements CrudController {
    
    private final CrudDAO<BarcoPasajero, String> barcoPasajeroDao = new BarcoPasajeroDAO();
    
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
                      \n=== GESTIÓN DE BARCOS DE PASAJEROS ===
                      1. Registrar Barco de Pasajeros.
                      2. Consultar Barco de Pasajeros.
                      3. Editar Barco de Pasajeros.
                      4. Eliminar Barco de Pasajeros.
                      5. Volver.
                      """;
        
        System.out.println(menu);
        
    }
    
    @Override
    public void registrar() {
        
        System.out.println("\n=== Nuevo Barco de Pasajeros ===");
        String matricula = ConsolaUtils.leerTexto("Matricula (máx. 10 caracteres): ", 10).toUpperCase();
        String bandera = ConsolaUtils.leerTexto("Bandera: ", 20);
        String nombre = ConsolaUtils.leerTexto("Nombre del barco: ", 50);
        Double pesoTonelada = ConsolaUtils.leerDecimal("Peso del Barco (En Toneladas): ");
        LocalDate fechaBotadura = ConsolaUtils.leerFecha("Fecha de Botadura ");
        Integer numPasajeros = ConsolaUtils.leerEntero("Capacidad máxima de Pasajeros: ");
        Integer numPisos =  ConsolaUtils.leerEntero("Numero de Pisos del Barco: ");
        String tipoViaje = ConsolaUtils.leerTexto("Tipo de Viaje: ", 15);

        try {
            BarcoPasajero barcoPasajero = new BarcoPasajero(matricula, bandera, 
                    nombre, pesoTonelada, fechaBotadura,
                    numPasajeros, numPisos, tipoViaje);
            if(barcoPasajeroDao.registrar(barcoPasajero)){
                System.out.println("Barco de Pasajeros registrado correctamente.");
            }
            
        } catch (IllegalArgumentException e){
            System.out.println("Error al agregar el barco: " + e.getMessage());
        }
    }
    
    @Override
    public void consultar() {
        
        System.out.println("\n=== Cátalogo de Barcos de Pasajeros ===");
        List<BarcoPasajero> barcosPasajeros = barcoPasajeroDao.consultarTodos();
        
        if(barcosPasajeros.isEmpty()){
            System.out.println("No hay Barcos de Pasajeros "
                    + "registrados en este momento.");
            return;
        }
        
        for(BarcoPasajero bsPj : barcosPasajeros){
            
            System.out.printf("Matricula: %s, Bandera: %s, Nombre: %s "
                    + "Peso (En Toneladas): %s, Fecha de Botadura: %s "
                    + "Capacidad Maxima de Pasajeros: %s, "
                    + "Numero de Pisos del Barco: %s, Tipo de Viaje: %s",
                    bsPj.getMatricula(),
                    bsPj.getBandera(),
                    bsPj.getNombre(),
                    bsPj.getPesoTonelada(),
                    bsPj.getFechaBotadura(),
                    bsPj.getNumPasajeros(),
                    bsPj.getNumPisos(),
                    bsPj.getTipoViaje());
        }
    }
    
    @Override
    public void editar() {
        System.out.println("\n=== Editar Barcos de Pasajeros ===");
        
        String matricula = ConsolaUtils.leerTexto("Matricula del barco a editar: ", 10).toUpperCase();
        
        BarcoPasajero actual = barcoPasajeroDao.buscarPorId(matricula);
        if (actual == null) {
            System.out.println("No se encontró níngún barco de pasajeros con la matricula escrita " 
            + matricula + " .");
            return;
        }
        
        System.out.println("(Presione ENTER si gusta conservar el valor actual)");
        
        String banderaFinal = actual.getBandera();
        String nuevaBandera = ConsolaUtils.leerTextoOpcional("Nuevo bandera [" + actual.getBandera() + "]: ", 20);
        if (!nuevaBandera.isEmpty()) {
            banderaFinal = nuevaBandera;
        }
        
        String nombreFinal = actual.getNombre();
        String nuevoNombre = ConsolaUtils.leerTextoOpcional("Nuevo nombre del Barco [" + actual.getNombre() + "]: ", 50);
        if (!nuevoNombre.isEmpty()) {
            nombreFinal = nuevoNombre;
        }

        Double pesoFinal = actual.getPesoTonelada();
        Double nuevoPeso = ConsolaUtils.leerDecimalOpcional("Nuevo peso del Barco [" + actual.getPesoTonelada() + "]: ", 1, 100000000);
        if (nuevoPeso != null) {
            pesoFinal = nuevoPeso;
        }
        
        LocalDate fechaFinal = actual.getFechaBotadura();
        String nuevaFecha = ConsolaUtils.leerFechaOpcional("Nueva fecha del Barco [" + actual.getFechaBotadura() + "]: ", 50);
        if (!nuevaCiudad.isEmpty()) {
            ciudadFinal = nuevaCiudad;
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
