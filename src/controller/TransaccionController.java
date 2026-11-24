package controller;

import dao.AtracoDAO;
import dao.TransaccionDAO;
import enums.TipoTransaccion;
import java.time.LocalDateTime;
import java.util.List;
import model.Transaccion;
import utils.ConsolaUtils;

/**
 *
 * @author gabri
 */
public class TransaccionController implements CrudController {
    
    private final TransaccionDAO transaccionDao = new TransaccionDAO();
    private final AtracoDAO atracoDao = new AtracoDAO();
    
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
                      \n=== GESTIÓN DE TRANSACCIONES ===
                      1. Registrar Transacción.
                      2. Consultar Transacciones.
                      3. Editar Transacción.
                      4. Eliminar Transacción.
                      5. Volver.
                      """;
        System.out.println(menu);
    }

    @Override
    public void registrar() {
        System.out.println("\n=== Nueva Transacción ===");
        String clave = ConsolaUtils.leerTexto("Clave de la transacción (máx. 15 caracteres): ", 15).toUpperCase();

        if (transaccionDao.buscarPorId(clave) != null) {
            System.out.println("Error. Ya existe una transacción registrada con la clave " + clave + ".");
            return;
        }

        int atracoId = ConsolaUtils.leerEntero("ID del atraco asociado: ", 1, 999999);
        if (atracoDao.buscarPorId(atracoId) == null) {
            System.out.println("Error. No existe ningún atraco registrado con el ID " + atracoId + ".");
            return;
        }

        LocalDateTime fecha = ConsolaUtils.leerFechaHora("Fecha de la transacción ");
        TipoTransaccion tipo = seleccionarTipoTransaccion();
        double elementos = ConsolaUtils.leerDecimal("Cantidad de elementos / toneladas: ", 0.01, 99999999.99);

        try {
            Transaccion nuevaTransaccion = new Transaccion(clave, fecha, tipo, elementos, atracoId);
            if (transaccionDao.registrar(nuevaTransaccion)) {
                System.out.println("Transacción registrada correctamente.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("No se pudo agregar la transacción: " + e.getMessage());
        }
    }

    @Override
    public void consultar() {
        System.out.println("\n=== Catálogo de Transacciones ===");
        List<Transaccion> transacciones = transaccionDao.consultarTodos();

        if (transacciones.isEmpty()) {
            System.out.println("No hay transacciones registradas.");
            return;
        }

        for (Transaccion t : transacciones) {
            System.out.printf("[%s] Fecha: %s | Tipo: %s | Elementos: %.2f | Atraco ID: %d%n",
                    t.getClaveTransaccion(),
                    t.getFechaTransaccion(),
                    t.getTipoTransaccion(),
                    t.getElementos(),
                    t.getAtracoId());
        }
    }

    @Override
    public void editar() {
        System.out.println("\n=== Editar Transacción ===");
        String clave = ConsolaUtils.leerTexto("Clave de la transacción a modificar: ", 15).toUpperCase();

        Transaccion actual = transaccionDao.buscarPorId(clave);
        if (actual == null) {
            System.out.println("No se encontró ninguna transacción con la clave " + clave + ".");
            return;
        }

        System.out.println("(Presione ENTER si gusta conservar el valor actual)");

        LocalDateTime fechaFinal = actual.getFechaTransaccion();
        LocalDateTime nuevaFecha = ConsolaUtils.leerFechaHoraOpcional("Nueva fecha [" + actual.getFechaTransaccion() + "] ");
        if (nuevaFecha != null) {
            fechaFinal = nuevaFecha;
        }

        TipoTransaccion tipoFinal = actual.getTipoTransaccion();
        while (true) {
            System.out.println("Tipo actual: " + actual.getTipoTransaccion() + " -> [C] Carga  [D] Descarga");
            String opcionTipo = ConsolaUtils.leerTextoOpcional("Nuevo tipo (C/D o ENTER para conservar): ", 1);

            if (opcionTipo.isEmpty()) {
                break;
            } else if (opcionTipo.equalsIgnoreCase("C")) {
                tipoFinal = TipoTransaccion.CARGA;
                break;
            } else if (opcionTipo.equalsIgnoreCase("D")) {
                tipoFinal = TipoTransaccion.DESCARGA;
                break;
            } else {
                System.out.println("Opción no válida. Escribe 'C', 'D' o presiona ENTER para conservar.");
            }
        }

        double elementosFinal = actual.getElementos();
        Double nuevosElementos = ConsolaUtils.leerDecimalOpcional("Nueva cantidad [" + actual.getElementos() + "]: ", 0.01, 99999999.99);
        if (nuevosElementos != null) {
            elementosFinal = nuevosElementos;
        }

        int atracoIdFinal = actual.getAtracoId();
        Integer nuevoAtracoId = ConsolaUtils.leerEnteroOpcional("Nuevo ID de atraco [" + actual.getAtracoId() + "]: ", 1, 999999);
        if (nuevoAtracoId != null) {
            if (atracoDao.buscarPorId(nuevoAtracoId) == null) {
                System.out.println("Error. El nuevo ID de atraco " + nuevoAtracoId + " no existe.");
                return;
            }
            atracoIdFinal = nuevoAtracoId;
        }

        try {
            Transaccion transaccionEditada = new Transaccion(clave, fechaFinal, tipoFinal, elementosFinal, atracoIdFinal);
            if (transaccionDao.editar(transaccionEditada)) {
                System.out.println("Transacción actualizada correctamente.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("No se pudo actualizar la transacción: " + e.getMessage());
        }
    }

    @Override
    public void eliminar() {
        System.out.println("\n=== Eliminar Transacción ===");
        String clave = ConsolaUtils.leerTexto("Clave de la transacción a eliminar: ", 15).toUpperCase();

        Transaccion actual = transaccionDao.buscarPorId(clave);
        if (actual == null) {
            System.out.println("No se encontró ninguna transacción con la clave " + clave + ".");
            return;
        }

        String confirmacion = ConsolaUtils.leerTexto(
                "¿Confirmas eliminar la transacción " + clave + "? (S/N): ", 1);

        try {
            if (confirmacion.equalsIgnoreCase("S")) {
                if (transaccionDao.eliminar(clave)) {
                    System.out.println("Transacción eliminada correctamente.");
                }
            } else {
                System.out.println("Operación cancelada.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("No se pudo eliminar la transacción: " + e.getMessage());
        }
    }

    private TipoTransaccion seleccionarTipoTransaccion() {
        while (true) {
            System.out.println("Tipo de transacción:");
            System.out.println("[C] Carga  [D] Descarga");
            String opcion = ConsolaUtils.leerTexto("Selecciona una opción (C/D): ", 1);

            if (opcion.equalsIgnoreCase("C")) {
                return TipoTransaccion.CARGA;
            } else if (opcion.equalsIgnoreCase("D")) {
                return TipoTransaccion.DESCARGA;
            } else {
                System.out.println("Opción no válida. Escribe 'C' para Carga o 'D' para Descarga.");
            }
        }
    }
    
}
