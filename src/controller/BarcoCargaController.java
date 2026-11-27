package controller;

import dao.BarcoCargaDAO;
import dao.CrudDAO;
import java.time.LocalDate;
import java.util.List;
import model.BarcoCarga;
import utils.ConsolaUtils;

/**
 * Controlador de consola para {@link BarcoCarga}
 */
public class BarcoCargaController implements CrudController {
    
    private final CrudDAO<BarcoCarga, String> barcoCargaDao = new BarcoCargaDAO();
    
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
                      \n=== GESTIÓN DE BARCOS DE CARGA ===
                      1. Registrar Barco de Carga.
                      2. Consultar Barco de Carga.
                      3. Editar Barco de Carga.
                      4. Eliminar Barco de Carga.
                      5. Volver.
                      """;
        
        System.out.println(menu);
        
    }
    
    @Override
    public void registrar() {
        
        System.out.println("\n=== Nuevo Barco de Carga ===");
        String matricula = ConsolaUtils.leerTexto("Matricula (máx. 10 caracteres): ", 10).toUpperCase();
        String bandera = ConsolaUtils.leerTexto("Bandera: ", 20);
        String nombre = ConsolaUtils.leerTexto("Nombre del barco: ", 50);
        Double pesoTonelada = ConsolaUtils.leerDecimal("Peso del Barco (En Toneladas): ", 1 , 100000);
        LocalDate fechaBotadura = ConsolaUtils.leerFecha("Fecha de Botadura ");
        Integer numContenedores = ConsolaUtils.leerEntero("Capacidad máxima de Contenedores: ", 1 , 10000);

        try {
            BarcoCarga barcoCarga = new BarcoCarga(matricula, bandera, 
                    nombre, pesoTonelada, fechaBotadura, numContenedores);
            if(barcoCargaDao.registrar(barcoCarga)){
                System.out.println("Barco de Carga registrado correctamente.");
            }
            
        } catch (IllegalArgumentException e){
            System.out.println("Error al agregar el barco: " + e.getMessage());
        }
    }
    
    @Override
    public void consultar() {
        
        System.out.println("\n=== Cátalogo de Barcos de Carga ===");
        List<BarcoCarga> barcosCarga = barcoCargaDao.consultarTodos();
        
        if(barcosCarga.isEmpty()){
            System.out.println("No hay Barcos de Carga "
                    + "registrados en este momento.");
            return;
        }
        
        for(BarcoCarga bsCg : barcosCarga){
            
            System.out.printf("Matricula: %s, Bandera: %s, Nombre: %s, "
                    + "Peso del Barco: %st, Fecha de Botadura: %s, "
                    + "Capacidad Maxima de Contenedores: %s%n",
                    bsCg.getMatricula(),
                    bsCg.getBandera(),
                    bsCg.getNombre(),
                    bsCg.getPesoTonelada(),
                    bsCg.getFechaBotadura(),
                    bsCg.getNumContenedores());
        }
    }
    
    @Override
    public void editar() {
        System.out.println("\n=== Editar Barco de Carga ===");
        
        String matricula = ConsolaUtils.leerTexto("Matricula del barco a editar: ", 10).toUpperCase();
        
        BarcoCarga actual = barcoCargaDao.buscarPorId(matricula);
        if (actual == null) {
            System.out.println("No se encontró níngún barco de carga con la matricula escrita " 
            + matricula + " .");
            return;
        }
        
        System.out.println("(Presione ENTER si gusta conservar el valor actual)");
        
        String banderaFinal = actual.getBandera();
        String nuevaBandera = ConsolaUtils.leerTextoOpcional("Nueva bandera [" + actual.getBandera() + "]: ", 20);
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
        LocalDate nuevaFecha = ConsolaUtils.leerFechaOpcional("Nueva fecha del Barco [" + actual.getFechaBotadura() + "]: ");
        if (nuevaFecha != null) {
            fechaFinal = nuevaFecha;
        }

        Integer numContenedoresFinal = actual.getNumContenedores();
        Integer nuevoNumContenedores = ConsolaUtils.leerEnteroOpcional("Nueva capacidad maxima de Contenedores del barco [" + actual.getNumContenedores() + "]: ", 1, 10000);
        if (nuevoNumContenedores != null) {
            numContenedoresFinal = nuevoNumContenedores;
        }
        
        try {
            BarcoCarga barcoCargaEditado = new BarcoCarga(matricula, 
                    banderaFinal, nombreFinal, pesoFinal, 
                    fechaFinal, numContenedoresFinal);
            if(barcoCargaDao.editar(barcoCargaEditado)){
                System.out.println("Barco actualizado correctamente.");
            }
            
        } catch (IllegalArgumentException e){
            System.out.println("Error al realizar los cambios del barco de Carga: " + e.getMessage());
        }
    }
    
    @Override
    public void eliminar() {
        System.out.println("\n=== Eliminar Barco de Carga ===");
        String matricula = ConsolaUtils.leerTexto("Matricula del Barco de Carga a eliminar: ", 10).toUpperCase();
        String confirmacion = ConsolaUtils.leerTexto("¿Confirmas eliminar el Barco de Carga " + matricula + "? (S/N): ", 1);
        
        
        try {
            if(confirmacion.equalsIgnoreCase("S")){
                if(barcoCargaDao.eliminar(matricula)){
                    System.out.println("Barco de Carga eliminado correctamente.");
                }
            }
            
        } catch (IllegalArgumentException e){
            System.out.println("Error al eliminar el barco: " + e.getMessage());
        }
        
    }
    
}