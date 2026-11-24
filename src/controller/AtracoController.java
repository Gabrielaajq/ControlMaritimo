/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package controller;

import dao.AtracoDAO;
import dao.BarcoCargaDAO;
import dao.BarcoPasajeroDAO;
import dao.BarcoPescaDAO;
import dao.CrudDAO;
import dao.PuertoDAO;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.Atraco;
import model.Barco;
import model.BarcoCarga;
import model.BarcoPasajero;
import model.BarcoPesca;
import model.Puerto;
import utils.ConsolaUtils;

/**
 *
 * @author leonm
 */
public class AtracoController implements CrudController {

    private final CrudDAO<Atraco, Integer> atracoDao = new AtracoDAO();
    private final CrudDAO<Puerto, String> puertoDao = new PuertoDAO();
    private final CrudDAO<BarcoCarga, String> barcoCargaDao = new BarcoCargaDAO();
    private final CrudDAO<BarcoPasajero, String> barcoPasajeroDao = new BarcoPasajeroDAO();
    private final CrudDAO<BarcoPesca, String> barcoPescaDao = new BarcoPescaDAO();

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public void iniciarMenu() {
        int opcion;
        do {
            mostrarMenu();
            opcion = ConsolaUtils.leerEntero("Seleccione una opción: ", 1, 5);

            switch (opcion) {
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
                      \n=== GESTIÓN DE ATRACOS ===
1. Registrar Atraco.
2. Consultar Atracos.
3. Editar Atraco.
4. Eliminar Atraco.
5. Volver.
                      """;
        System.out.println(menu);
    }

    @Override
    public void registrar() {
        System.out.println("\n=== Nuevo Atraco ===");

        List<Puerto> puertos = puertoDao.consultarTodos();
        if (puertos.isEmpty()) {
            System.out.println("No hay puertos registrados. Registre un puerto primero.");
            return;
        }

        if (!hayBarcosRegistrados()) {
            System.out.println("No hay barcos registrados. Registre un barco primero.");
            return;
        }

        mostrarPuertosDisponibles(puertos);
        String codigoPuerto = ConsolaUtils.leerTexto("Código del puerto: ", 10).toUpperCase();
        if (puertoDao.buscarPorId(codigoPuerto) == null) {
            System.out.println("Error: No existe un puerto con el código " + codigoPuerto + ".");
            return;
        }

        mostrarBarcosDisponibles();
        String matricula = ConsolaUtils.leerTexto("Matrícula del barco: ", 10).toUpperCase();
        if (buscarBarcoPorMatricula(matricula) == null) {
            System.out.println("Error: No existe un barco con la matrícula " + matricula + ".");
            return;
        }

        LocalDate fechaAtraco = ConsolaUtils.leerFecha("Fecha de atraco ");

        Atraco nuevoAtraco = new Atraco(fechaAtraco, codigoPuerto, matricula);
        if (atracoDao.registrar(nuevoAtraco)) {
            System.out.println("Atraco registrado correctamente con ID: " + nuevoAtraco.getAtracoId());
        }
    }

    @Override
    public void consultar() {
        System.out.println("\n=== Registro de Atracos ===");
        List<Atraco> atracos = atracoDao.consultarTodos();

        if (atracos.isEmpty()) {
            System.out.println("No hay atracos registrados.");
            return;
        }

        for (Atraco a : atracos) {
            Puerto puerto = puertoDao.buscarPorId(a.getCodigoPuerto());
            Barco barco = buscarBarcoPorMatricula(a.getMatricula());

            String nombrePuerto = (puerto != null) ? puerto.getNombre() : "Desconocido";
            String nombreBarco = (barco != null) ? barco.getNombre() : "Desconocido";
            String fechaStr = (a.getFechaAtraco() != null) ? a.getFechaAtraco().format(FORMATO_FECHA) : "Sin fecha";

            System.out.printf("ID: %d | Fecha: %s | Puerto: [%s] %s | Barco: [%s] %s%n",
                    a.getAtracoId(),
                    fechaStr,
                    a.getCodigoPuerto(),
                    nombrePuerto,
                    a.getMatricula(),
                    nombreBarco);
        }
    }

    @Override
    public void editar() {
        System.out.println("\n=== Editar Atraco ===");
        int id = ConsolaUtils.leerEntero("ID del atraco a modificar: ", 1, Integer.MAX_VALUE);

        Atraco actual = atracoDao.buscarPorId(id);
        if (actual == null) {
            System.out.println("No se encontró ningún atraco con el ID " + id + ".");
            return;
        }

        System.out.println("(Presione ENTER si gusta conservar el valor actual)");

        String puertoFinal = actual.getCodigoPuerto();
        mostrarPuertosDisponibles(puertoDao.consultarTodos());
        String nuevoPuerto = ConsolaUtils.leerTextoOpcional(
                "Nuevo código de puerto [" + actual.getCodigoPuerto() + "]: ", 10).toUpperCase();
        if (!nuevoPuerto.isEmpty()) {
            if (puertoDao.buscarPorId(nuevoPuerto) == null) {
                System.out.println("Error: No existe un puerto con el código " + nuevoPuerto + ".");
                return;
            }
            puertoFinal = nuevoPuerto;
        }

        String matriculaFinal = actual.getMatricula();
        mostrarBarcosDisponibles();
        String nuevaMatricula = ConsolaUtils.leerTextoOpcional(
                "Nueva matrícula de barco [" + actual.getMatricula() + "]: ", 10).toUpperCase();
        if (!nuevaMatricula.isEmpty()) {
            if (buscarBarcoPorMatricula(nuevaMatricula) == null) {
                System.out.println("Error: No existe un barco con la matrícula " + nuevaMatricula + ".");
                return;
            }
            matriculaFinal = nuevaMatricula;
        }

        LocalDate fechaFinal = actual.getFechaAtraco();
        String fechaActualStr = (fechaFinal != null) ? fechaFinal.format(FORMATO_FECHA) : "Sin fecha";
        String cambiarFecha = ConsolaUtils.leerTexto(
                "¿Desea cambiar la fecha de atraco [" + fechaActualStr + "]? (S/N): ", 1);
        if (cambiarFecha.equalsIgnoreCase("S")) {
            fechaFinal = ConsolaUtils.leerFecha("Nueva fecha de atraco ");
        }

        Atraco atracoEditado = new Atraco(id, fechaFinal, puertoFinal, matriculaFinal);
        if (atracoDao.editar(atracoEditado)) {
            System.out.println("Atraco actualizado correctamente.");
        }
    }

    @Override
    public void eliminar() {
        System.out.println("\n=== Eliminar Atraco ===");
        int id = ConsolaUtils.leerEntero("ID del atraco a eliminar: ", 1, Integer.MAX_VALUE);

        if (atracoDao.buscarPorId(id) == null) {
            System.out.println("No se encontró ningún atraco con el ID " + id + ".");
            return;
        }

        String confirmacion = ConsolaUtils.leerTexto("¿Confirmas eliminar el atraco ID " + id + "? (S/N): ", 1);
        if (confirmacion.equalsIgnoreCase("S")) {
            if (atracoDao.eliminar(id)) {
                System.out.println("Atraco eliminado correctamente.");
            }
        }
    }

    private void mostrarPuertosDisponibles(List<Puerto> puertos) {
        System.out.println("Puertos disponibles:");
        for (Puerto p : puertos) {
            System.out.printf("  - [%s] %s (%s, %s)%n",
                    p.getCodigoPuerto(), p.getNombre(), p.getCiudad(), p.getPais());
        }
    }

    private void mostrarBarcosDisponibles() {
        System.out.println("Barcos disponibles:");
        for (BarcoCarga b : barcoCargaDao.consultarTodos()) {
            System.out.printf("  - [%s] %s (Carga - Bandera: %s)%n",
                    b.getMatricula(), b.getNombre(), b.getBandera());
        }
        for (BarcoPasajero b : barcoPasajeroDao.consultarTodos()) {
            System.out.printf("  - [%s] %s (Pasajeros - Bandera: %s)%n",
                    b.getMatricula(), b.getNombre(), b.getBandera());
        }
        for (BarcoPesca b : barcoPescaDao.consultarTodos()) {
            System.out.printf("  - [%s] %s (Pesca - Bandera: %s)%n",
                    b.getMatricula(), b.getNombre(), b.getBandera());
        }
    }

    private boolean hayBarcosRegistrados() {
        return !barcoCargaDao.consultarTodos().isEmpty()
                || !barcoPasajeroDao.consultarTodos().isEmpty()
                || !barcoPescaDao.consultarTodos().isEmpty();
    }

    private Barco buscarBarcoPorMatricula(String matricula) {
        Barco barco = barcoCargaDao.buscarPorId(matricula);
        if (barco != null) {
            return barco;
        }
        barco = barcoPasajeroDao.buscarPorId(matricula);
        if (barco != null) {
            return barco;
        }
        return barcoPescaDao.buscarPorId(matricula);
    }
}
