package controller;

import dao.BarcoPescaDAO;
import dao.CrudDAO;
import dao.ZonaDAO;
import dao.ZonaReportadaDAO;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.BarcoPesca;
import model.Zona;
import model.ZonaReportada;
import utils.ConsolaUtils;

/**
 * Controlador de consola para gestionar las entradas y salidas de barcos pesqueros
 * en zonas de pesca mediante {@link ZonaReportada}
 */
public class ZonaReportadaController implements CrudController {

    private final CrudDAO<ZonaReportada, Integer> zonaReportadaDao = new ZonaReportadaDAO();
    private final CrudDAO<Zona, String> zonaDao = new ZonaDAO();
    private final CrudDAO<BarcoPesca, String> barcoPescaDao = new BarcoPescaDAO();

    private static final DateTimeFormatter FORMATO_FECHA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

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
                    \n=== GESTIÓN DE ACTIVIDAD EN ZONAS DE PESCA ===
                    1. Registrar Entrada/Actividad en Zona.
                    2. Consultar Zonas Reportadas.
                    3. Editar Reporte / Registrar Salida.
                    4. Eliminar Reporte de Zona.
                    5. Volver.
                    """;
        System.out.println(menu);
    }

    @Override
    public void registrar() {
        System.out.println("\n=== Nuevo Reporte de Zona de Pesca ===");

        List<Zona> zonas = zonaDao.consultarTodos();
        if (zonas.isEmpty()) {
            System.out.println("No hay zonas de pesca registradas. Registre una zona primero.");
            return;
        }

        List<BarcoPesca> barcosPesca = barcoPescaDao.consultarTodos();
        if (barcosPesca.isEmpty()) {
            System.out.println("No hay barcos pesqueros registrados. Registre un barco de pesca primero.");
            return;
        }

        mostrarZonasDisponibles(zonas);
        String claveZona = ConsolaUtils.leerTexto("Clave de la zona de pesca: ", 10).toUpperCase();
        if (zonaDao.buscarPorId(claveZona) == null) {
            System.out.println("Error: No existe una zona con la clave " + claveZona + ".");
            return;
        }

        mostrarBarcosPescaDisponibles(barcosPesca);
        String matricula = ConsolaUtils.leerTexto("Matrícula del barco pesquero: ", 10).toUpperCase();
        if (barcoPescaDao.buscarPorId(matricula) == null) {
            System.out.println("Error: No existe un barco pesquero con la matrícula " + matricula + ".");
            return;
        }

        LocalDateTime fechaEntrada = ConsolaUtils.leerFechaHora("Fecha y hora de entrada");

        LocalDateTime fechaSalida = null;
        String yaSalio = ConsolaUtils.leerTexto("¿El barco ya salió de la zona de pesca? (S/N): ", 1);
        if (yaSalio.equalsIgnoreCase("S")) {
            fechaSalida = leerFechaHoraSalidaValida(fechaEntrada);
        }

        ZonaReportada nuevoReporte = new ZonaReportada(fechaEntrada, fechaSalida, claveZona, matricula);
        if (zonaReportadaDao.registrar(nuevoReporte)) {
            System.out.println("Reporte de zona registrado correctamente con ID: " + nuevoReporte.getZonaReportadaId());
        }
    }

    @Override
    public void consultar() {
        System.out.println("\n=== Historial de Actividad en Zonas de Pesca ===");
        List<ZonaReportada> reportes = zonaReportadaDao.consultarTodos();

        if (reportes.isEmpty()) {
            System.out.println("No hay reportes de zonas registrados.");
            return;
        }

        for (ZonaReportada zr : reportes) {
            BarcoPesca barco = barcoPescaDao.buscarPorId(zr.getMatricula());
            String nombreBarco = (barco != null) ? barco.getNombre() : "Desconocido";

            String entradaStr = (zr.getFechaEntrada() != null)
                    ? zr.getFechaEntrada().format(FORMATO_FECHA_HORA)
                    : "Sin registro";
            String salidaStr = (zr.getFechaSalida() != null)
                    ? zr.getFechaSalida().format(FORMATO_FECHA_HORA)
                    : "En zona (sin salida)";

            System.out.printf("ID: %d | Zona: [%s] | Barco Pesquero: [%s] %s | Entrada: %s | Salida: %s%n",
                    zr.getZonaReportadaId(),
                    zr.getClaveZona(),
                    zr.getMatricula(),
                    nombreBarco,
                    entradaStr,
                    salidaStr);
        }
    }

    @Override
    public void editar() {
        System.out.println("\n=== Editar Reporte de Zona / Registrar Salida ===");
        int id = ConsolaUtils.leerEntero("ID del reporte a modificar: ", 1, Integer.MAX_VALUE);

        ZonaReportada actual = zonaReportadaDao.buscarPorId(id);
        if (actual == null) {
            System.out.println("No se encontró ningún reporte con el ID " + id + ".");
            return;
        }

        System.out.println("(Presione ENTER si gusta conservar el valor actual)");

        String zonaFinal = actual.getClaveZona();
        mostrarZonasDisponibles(zonaDao.consultarTodos());
        String nuevaZona = ConsolaUtils.leerTextoOpcional(
                "Nueva clave de zona [" + actual.getClaveZona() + "]: ", 10).toUpperCase();
        if (!nuevaZona.isEmpty()) {
            if (zonaDao.buscarPorId(nuevaZona) == null) {
                System.out.println("Error: No existe una zona con la clave " + nuevaZona + ".");
                return;
            }
            zonaFinal = nuevaZona;
        }

        String matriculaFinal = actual.getMatricula();
        mostrarBarcosPescaDisponibles(barcoPescaDao.consultarTodos());
        String nuevaMatricula = ConsolaUtils.leerTextoOpcional(
                "Nueva matrícula de barco pesquero [" + actual.getMatricula() + "]: ", 10).toUpperCase();
        if (!nuevaMatricula.isEmpty()) {
            if (barcoPescaDao.buscarPorId(nuevaMatricula) == null) {
                System.out.println("Error: No existe un barco pesquero con la matrícula " + nuevaMatricula + ".");
                return;
            }
            matriculaFinal = nuevaMatricula;
        }

        LocalDateTime entradaFinal = actual.getFechaEntrada();
        String entradaStr = (entradaFinal != null) ? entradaFinal.format(FORMATO_FECHA_HORA) : "Sin registro";
        String cambiarEntrada = ConsolaUtils.leerTexto(
                "¿Desea cambiar la fecha/hora de entrada [" + entradaStr + "]? (S/N): ", 1);
        if (cambiarEntrada.equalsIgnoreCase("S")) {
            entradaFinal = ConsolaUtils.leerFechaHora("Nueva fecha y hora de entrada");
        }

        LocalDateTime salidaFinal = actual.getFechaSalida();
        String salidaStr = (salidaFinal != null) ? salidaFinal.format(FORMATO_FECHA_HORA) : "En zona (sin salida)";
        String cambiarSalida = ConsolaUtils.leerTexto(
                "¿Desea registrar o cambiar la fecha/hora de salida [" + salidaStr + "]? (S/N): ", 1);
        if (cambiarSalida.equalsIgnoreCase("S")) {
            salidaFinal = leerFechaHoraSalidaValida(entradaFinal);
        } else if (salidaFinal != null && salidaFinal.isBefore(entradaFinal)) {
            System.out.println("La fecha de salida actual quedó anterior a la nueva entrada. Capture una nueva salida:");
            salidaFinal = leerFechaHoraSalidaValida(entradaFinal);
        }

        ZonaReportada reporteEditado = new ZonaReportada(id, entradaFinal, salidaFinal, zonaFinal, matriculaFinal);
        if (zonaReportadaDao.editar(reporteEditado)) {
            System.out.println("Reporte de zona actualizado correctamente.");
        }
    }

    @Override
    public void eliminar() {
        System.out.println("\n=== Eliminar Reporte de Zona ===");
        int id = ConsolaUtils.leerEntero("ID del reporte a eliminar: ", 1, Integer.MAX_VALUE);

        if (zonaReportadaDao.buscarPorId(id) == null) {
            System.out.println("No se encontró ningún reporte con el ID " + id + ".");
            return;
        }

        String confirmacion = ConsolaUtils.leerTexto("¿Confirmas eliminar el reporte ID " + id + "? (S/N): ", 1);
        if (confirmacion.equalsIgnoreCase("S")) {
            if (zonaReportadaDao.eliminar(id)) {
                System.out.println("Reporte de zona eliminado correctamente.");
            }
        }
    }

    private void mostrarZonasDisponibles(List<Zona> zonas) {
        System.out.println("Zonas de pesca disponibles:");
        for (Zona z : zonas) {
            System.out.printf("  - [%s] Lat: %.4f, Lon: %.4f (Radio: %.2f km)%n",
                    z.getClaveZona(), z.getLatitud(), z.getLongitud(), z.getRadio());
        }
    }

    private void mostrarBarcosPescaDisponibles(List<BarcoPesca> barcos) {
        System.out.println("Barcos pesqueros disponibles:");
        for (BarcoPesca bp : barcos) {
            System.out.printf("  - [%s] %s (Capacidad: %.2f ton)%n",
                    bp.getMatricula(), bp.getNombre(), bp.getCantidadToneladas());
        }
    }

    private LocalDateTime leerFechaHoraSalidaValida(LocalDateTime fechaEntrada) {
        while (true) {
            LocalDateTime fechaSalida = ConsolaUtils.leerFechaHora("Fecha y hora de salida");
            if (!fechaSalida.isBefore(fechaEntrada)) {
                return fechaSalida;
            }
            System.out.println("Error: La fecha y hora de salida no pueden ser anteriores a la de entrada.");
        }
    }
}
