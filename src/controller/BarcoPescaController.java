package controller;

import dao.BarcoConPescadoDAO;
import dao.BarcoPescaDAO;
import dao.CrudDAO;
import dao.PescadoDAO;
import dao.RelacionBarcoPescadoDAO;
import java.time.LocalDate;
import java.util.List;
import model.BarcoPesca;
import model.Pescado;
import utils.ConsolaUtils;

/**
 * Controlador de consola para {@link BarcoPesca}
 * y el catálogo de especies que cada barco busca
 */
public class BarcoPescaController implements CrudController{

    private final CrudDAO<BarcoPesca, String> barcoPescaDao = new BarcoPescaDAO();
    private final CrudDAO<Pescado, Integer> pescadoDao = new PescadoDAO();
    private final RelacionBarcoPescadoDAO<Pescado, String, Integer> 
            barcoConPescadoDao = new BarcoConPescadoDAO();

    @Override
    public void iniciarMenu() {
        int opcion;
        do{
            mostrarMenu();
            opcion = ConsolaUtils.leerEntero("Seleccione una opción: ", 1, 8);

            switch(opcion) {
                case 1 -> registrar();
                case 2 -> consultar();
                case 3 -> editar();
                case 4 -> eliminar();
                case 5 -> asignarPescado();
                case 6 -> consultarPescado();
                case 7 -> eliminarPescado();
                case 8 -> System.out.println("Regresando...");
                default -> System.out.println("Opción no válida...");
            }
        } while (opcion != 8);
    }

    private void mostrarMenu() {

        String menu = """
                      \n=== GESTIÓN DE BARCOS DE PESCA ===
                      1. Registrar Barco de Pesca.
                      2. Consultar Barco de Pesca.
                      3. Editar Barco de Pesca.
                      4. Eliminar Barco de Pesca.
                      5. Asociar Pescado a un Barco
                      6. Consultar tipo de Pescado de un Barco
                      7. Eliminar Pescado de un Barco
                      8. Volver.
                      """;

        System.out.println(menu);

    }

    @Override
    public void registrar() {

        System.out.println("\n=== Nuevo Barco de Pesca ===");
        String matricula = ConsolaUtils.leerTexto("Matricula (máx. 10 caracteres): ", 10).toUpperCase();
        String bandera = ConsolaUtils.leerTexto("Bandera: ", 20);
        String nombre = ConsolaUtils.leerTexto("Nombre del barco: ", 50);
        Double pesoTonelada = ConsolaUtils.leerDecimal("Peso del Barco (En Toneladas): ", 1, 100000000);
        LocalDate fechaBotadura = ConsolaUtils.leerFecha("Fecha de Botadura ");
        Double cantidadToneladas = ConsolaUtils.leerDecimal("Capacidad máxima de carga de pescado (En Toneladas): ", 1, 10000);

        try {
            BarcoPesca barcoPesca = new BarcoPesca(matricula, bandera, 
                    nombre, pesoTonelada, fechaBotadura, cantidadToneladas);
            if(barcoPescaDao.registrar(barcoPesca)){
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
            System.out.println("No hay Barcos de Pesca "
                    + "registrados en este momento.");
            return;
        }

        for(BarcoPesca bsPc : barcosPesca){

            System.out.printf("Matricula: %s, Bandera: %s, Nombre: %s, "
                    + "Peso del Barco: %st, Fecha de Botadura: %s, "
                    + "Peso Maximo de Carga de Pescado: %st%n",
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

        String matricula = ConsolaUtils.leerTexto("Matricula del Barco a editar: ", 10).toUpperCase();

        BarcoPesca actual = barcoPescaDao.buscarPorId(matricula);
        if (actual == null) {
            System.out.println("No se encontró níngún Barco de Pesca con la matricula escrita " 
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

        Double cantidadToneladasFinal = actual.getCantidadToneladas();
        Double nuevoCantidadToneladas = ConsolaUtils.leerDecimalOpcional("Nueva capacidad máxima de carga de pescado [" + actual.getCantidadToneladas() + "]: ", 1, 10000);
        if (nuevoCantidadToneladas != null) {
            cantidadToneladasFinal = nuevoCantidadToneladas;
        }

        try {
            BarcoPesca barcoPescaEditado = new BarcoPesca(matricula, 
                    banderaFinal, nombreFinal, pesoFinal, 
                    fechaFinal, cantidadToneladasFinal);
            if(barcoPescaDao.editar(barcoPescaEditado)){
                System.out.println("Barco actualizado correctamente.");
            }

        } catch (IllegalArgumentException e){
            System.out.println("Error al realizar los cambios del barco de Pesca: " + e.getMessage());
        }
    }

    @Override
    public void eliminar() {
        System.out.println("\n=== Eliminar Barco de Pesca ===");
        String matricula = ConsolaUtils.leerTexto("Matricula del Barco de Pesca a eliminar: ", 10).toUpperCase();
        String confirmacion = ConsolaUtils.leerTexto("¿Confirmas eliminar el Barco de Pesca " + matricula + "? (S/N): ", 1);


        try {
            if(confirmacion.equalsIgnoreCase("S")){
                if(barcoPescaDao.eliminar(matricula)){
                    System.out.println("Barcos de Pesca eliminado correctamente.");
                }
            }

        } catch (IllegalArgumentException e){
            System.out.println("Error al eliminar el barco: " + e.getMessage());
        }

    }

    public void asignarPescado() {

        System.out.println("\n=== Asignar Pescado a Barco de Pesca ===");

        String matricula = ConsolaUtils.leerTexto(
                "Matricula del Barco al cual se le asignara el pescado: ",
                10).toUpperCase();

        BarcoPesca barcoActual = barcoPescaDao.buscarPorId(matricula);

        if (barcoActual == null) {
            System.out.println("No se encontró ningún Barco de Pesca "
                    + "con la matricula " + matricula + ".");
            return;
        }
        
        List<Pescado> listaPescados = pescadoDao.consultarTodos();

        if (listaPescados.isEmpty()) {
            System.out.println("No hay pescados registrados en este momento.");
            return;
        }
        
        System.out.println("\n=== Pescados disponibles ===");
        for (Pescado pescado : listaPescados) {
            System.out.printf("ID del Pescado: %d, Nombre del Pescado: %s%n",
                    pescado.getPescadoId(),
                    pescado.getNombre());
        }

        Integer id = ConsolaUtils.leerEntero(
                "Escribe el ID del Pescado a asignar: ",
                1, Integer.MAX_VALUE);

        Pescado buscarPescado = pescadoDao.buscarPorId(id);

        if (buscarPescado == null) {
            System.out.println("No se encontró ningún Pescado "
                    + "con el ID " + id + ".");
            return;
        }

        if (barcoConPescadoDao.asignarPescado(matricula, id)) {
            System.out.println("Pescado asignado correctamente.");
        } else {
            System.out.println("No se pudo asignar el pescado.");
        }
    }
        
    public void consultarPescado() {

        System.out.println("\n=== Consultar Pescados de un Barco ===");

        String matricula = ConsolaUtils.leerTexto(
                "Matricula del Barco a consultar los pescados que busca: ", 10).toUpperCase();

        BarcoPesca barcoActual = barcoPescaDao.buscarPorId(matricula);

        if (barcoActual == null) {
            System.out.println("Error. No se encontró ningún Barco de Pesca "
                    + "con la matricula " + matricula + ".");
            return;
        }

        List<Pescado> listaPescados =
                barcoConPescadoDao.consultarTodos(matricula);

        if (listaPescados.isEmpty()) {
            System.out.println("No se encontraron pescados asociados al barco");
            return;
        }

        System.out.println("\nPescados asociados al barco "
                + barcoActual.getNombre() + ":");

        for (Pescado pescado : listaPescados) {
            System.out.printf("ID del Pescado: %s, Nombre del Pescado: %s%n",
                    pescado.getPescadoId(),
                    pescado.getNombre());
        }
    }
    
    public void eliminarPescado() {

        System.out.println("\n=== Eliminar Pescado de un Barco ===");

        String matricula = ConsolaUtils.leerTexto(
                "Matricula del Barco: ", 10).toUpperCase();

        BarcoPesca barcoActual = barcoPescaDao.buscarPorId(matricula);

        if (barcoActual == null) {
            System.out.println("Error. No se encontró ningún Barco de Pesca "
                    + "con la matricula " + matricula + ".");
            return;
        }

        List<Pescado> listaPescados =
                barcoConPescadoDao.consultarTodos(matricula);

        if (listaPescados.isEmpty()) {
            System.out.println("No hay pescados asociados al barco "
                    + "o no se pudieron consultar.");
            return;
        }

        System.out.println("\n=== Pescados Asociados ===");

        for (Pescado pescado : listaPescados) {
            System.out.printf("ID del Pescado: %s, Nombre del Pescado: %s%n",
                    pescado.getPescadoId(),
                    pescado.getNombre());
        }

        Integer id = ConsolaUtils.leerEntero(
                "Escribe el ID del Pescado a eliminar: ",
                1, Integer.MAX_VALUE);

        boolean resultado = barcoConPescadoDao.eliminar(matricula, id);

        if (resultado) {
            System.out.println("Pescado eliminado correctamente del barco.");
        } else {
            System.out.println("Error. No se pudo eliminar el pescado "
                    + "con la matricula " + matricula + " del barco "
                    + barcoActual.getNombre() + ".");
        }
    }
}
