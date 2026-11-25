package controller;

import dao.BarcoPescaDAO;
import dao.CrudDAO;
import java.time.LocalDate;
import java.util.List;
import model.BarcoPesca;
import utils.ConsolaUtils;

/**
 * Controlador de consola para {@link BarcoPesca}
 * y el catálogo de especies que cada barco busca
 */
public class BarcoPescaController implements CrudController{

    private final CrudDAO<BarcoPesca, String> barcoPescaDao = new BarcoPescaDAO();

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
                      \n=== GESTIÓN DE BARCOS DE PESCA ===
                      1. Registrar Barco de Pesca.
                      2. Consultar Barco de Pesca.
                      3. Editar Barco de Pesca.
                      4. Eliminar Barco de Pesca.
                      5. Volver.
                      """;

        System.out.println(menu);

    }

    @Override
    public void registrar() {

        System.out.println("\n=== Nuevo Barco de Pesca ===");
        String matricula = ConsolaUtils.leerTexto("Matricula (máx. 10 caracteres): ", 10).toUpperCase();
        String bandera = ConsolaUtils.leerTexto("Bandera: ", 20);
        String nombre = ConsolaUtils.leerTexto("Nombre del barco: ", 50);
        Double pesoTonelada = ConsolaUtils.leerDecimal("Peso del Barco (En Toneladas): ");
        LocalDate fechaBotadura = ConsolaUtils.leerFecha("Fecha de Botadura ");
        Double cantidadToneladas = ConsolaUtils.leerDecimal("Capacidad máxima de Contenedores: ");

        try {
            BarcoPesca barcoCarga = new BarcoPesca(matricula, bandera, 
                    nombre, pesoTonelada, fechaBotadura, cantidadToneladas);
            if(barcoPescaDao.registrar(barcoCarga)){
                System.out.println("Barco de Pesca registrado correctamente.");
            }

        } catch (IllegalArgumentException e){
            System.out.println("Error al agregar el barco: " + e.getMessage());
        }
    }

    @Override
    public void consultar() {

        System.out.println("\n=== Cátalogo de Barcos de Pesca ===");
        List<BarcoPesca> barcosPesca = barcoPescaDao.consultarTodos();

        if(barcosPesca.isEmpty()){
            System.out.println("No hay Barcos de Carga "
                    + "registrados en este momento.");
            return;
        }

        for(BarcoPesca bsPc : barcosPesca){

            System.out.printf("Matricula: %s, Bandera: %s, Nombre: %s "
                    + "Peso del Barco: %st, Fecha de Botadura: %s "
                    + "Peso Maxima de Carga de Pescado: %st \n",
                    bsPc.getMatricula(),
                    bsPc.getBandera(),
                    bsPc.getNombre(),
                    bsPc.getPesoTonelada(),
                    bsPc.getFechaBotadura(),
                    bsPc.getCantidadToneladas());
        }
    }

    @Override
    public void editar() {
        System.out.println("\n=== Editar Barco de Pesca ===");

        String matricula = ConsolaUtils.leerTexto("Matricula del barco a editar: ", 10).toUpperCase();

        BarcoPesca actual = barcoPescaDao.buscarPorId(matricula);
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

        Double numCantidadToneladasFinal = actual.getCantidadToneladas();
        Double nuevoCantidadToneladas = ConsolaUtils.leerDecimalOpcional("Nueva capacidad de Contenedores Maximo del barco [" + actual.getCantidadToneladas() + "]: ", 1, 10000);
        if (nuevoCantidadToneladas != null) {
            numCantidadToneladasFinal = nuevoCantidadToneladas;
        }

        try {
            BarcoPesca barcoPescaEditado = new BarcoPesca(matricula, 
                    banderaFinal, nombreFinal, pesoFinal, 
                    fechaFinal, numCantidadToneladasFinal);
            if(barcoPescaDao.editar(barcoPescaEditado)){
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
                if(barcoPescaDao.eliminar(matricula)){
                    System.out.println("Barcos de Carga eliminado correctamente.");
                }
            }

        } catch (IllegalArgumentException e){
            System.out.println("Error al eliminar el barco: " + e.getMessage());
        }

    }

}
