/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.ZonaDAO;
import java.util.List;
import model.Pescado;
import model.Zona;
import utils.ConsolaUtils;

/**
 *
 * @author gabri
 */
public class ZonaController implements CrudController {
    
    private final ZonaDAO zonaDao = new ZonaDAO();

    @Override
    public void iniciarMenu() {
        int opcion;
        do {
            mostrarMenu();
            opcion = ConsolaUtils.leerEntero("Seleccione una opción: ", 1, 8);

            switch (opcion) {
                case 1 -> registrar();
                case 2 -> consultar();
                case 3 -> editar();
                case 4 -> eliminar();
                case 5 -> asociarPescado();
                case 6 -> quitarPescado();
                case 7 -> consultarPescadosDeZona();
                case 8 -> System.out.println("Regresando...");
                default -> System.out.println("Opción no válida...");
            }
        } while (opcion != 8);
    }

    private void mostrarMenu() {
        String menu = """
                      \n=== GESTIÓN DE ZONAS DE PESCA ===
                      1. Registrar Zona.
                      2. Consultar Zonas.
                      3. Editar Zona.
                      4. Eliminar Zona.
                      5. Asociar Pescado a Zona.
                      6. Quitar Pescado de Zona.
                      7. Consultar Pescados por Zona.
                      8. Volver.
                      """;
        System.out.println(menu);
    }

    @Override
    public void registrar() {
        System.out.println("\n=== Nueva Zona ===");
        String clave = ConsolaUtils.leerTexto("Clave de la zona (máx. 10 caracteres): ", 10).toUpperCase();
        double latitud = ConsolaUtils.leerDecimal("Latitud (-90 a 90): ", -90.0, 90.0);
        double longitud = ConsolaUtils.leerDecimal("Longitud (-180 a 180): ", -180.0, 180.0);
        double radio = ConsolaUtils.leerDecimal("Radio en km (mayor a 0): ", 0.01, 10000.0);

        try{
            Zona nuevaZona = new Zona(clave, latitud, longitud, radio);
            if (zonaDao.registrar(nuevaZona)) {
                System.out.println("Zona registrada correctamente.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error al agregar la zona: " + e.getMessage());
        }
        
    }

    @Override
    public void consultar() {
        System.out.println("\n=== Catálogo de Zonas ===");
        List<Zona> zonas = zonaDao.consultarTodos();

        if (zonas.isEmpty()) {
            System.out.println("No hay zonas registradas.");
            return;
        }

        for (Zona z : zonas) {
            System.out.printf("[%s] Lat: %.4f | Lon: %.4f | Radio: %.2f km%n",
                    z.getClaveZona(),
                    z.getLatitud(),
                    z.getLongitud(),
                    z.getRadio());
        }
    }

    @Override
    public void editar() {
        System.out.println("\n=== Editar Zona ===");
        String clave = ConsolaUtils.leerTexto("Clave de la zona a modificar: ", 10).toUpperCase();

        Zona actual = zonaDao.buscarPorId(clave);
        if (actual == null) {
            System.out.println("No se encontró ninguna zona con la clave " + clave + ".");
            return;
        }

        System.out.println("(Presione ENTER si gusta conservar el valor actual)");

        double latitudFinal = actual.getLatitud();
        Double nuevaLatitud = ConsolaUtils.leerDecimalOpcional("Nueva latitud [" + actual.getLatitud() + "]: ", -90.0, 90.0);
        if (nuevaLatitud != null) {
            latitudFinal = nuevaLatitud;
        }

        double longitudFinal = actual.getLongitud();
        Double nuevaLongitud = ConsolaUtils.leerDecimalOpcional("Nueva longitud [" + actual.getLongitud() + "]: ", -180.0, 180.0);
        if (nuevaLongitud != null) {
            longitudFinal = nuevaLongitud;
        }

        double radioFinal = actual.getRadio();
        Double nuevoRadio = ConsolaUtils.leerDecimalOpcional("Nuevo radio [" + actual.getRadio() + "]: ", 0.01, 10000.0);
        if (nuevoRadio != null) {
            radioFinal = nuevoRadio;
        }

        try{
            Zona zonaEditada = new Zona(clave, latitudFinal, longitudFinal, radioFinal);
            if (zonaDao.editar(zonaEditada)) {
                System.out.println("Zona actualizada correctamente.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error al agregar la zona: " + e.getMessage());
        }
        
    }

    @Override
    public void eliminar() {
        System.out.println("\n=== Eliminar Zona ===");
        String clave = ConsolaUtils.leerTexto("Clave de la zona a eliminar: ", 10).toUpperCase();
        String confirmacion = ConsolaUtils.leerTexto("¿Confirmas eliminar la zona " + clave + "? (S/N): ", 1);

        if (confirmacion.equalsIgnoreCase("S")) {
            if (zonaDao.eliminar(clave)) {
                System.out.println("Zona eliminada correctamente.");
            }
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    private void asociarPescado() {
        System.out.println("\n=== Asociar Pescado a Zona ===");
        String clave = ConsolaUtils.leerTexto("Clave de la zona: ", 10).toUpperCase();
        int pescadoId = ConsolaUtils.leerEntero("ID del pescado: ", 1, 999999);

        if (zonaDao.asociarPescado(clave, pescadoId)) {
            System.out.println("Especie asociada correctamente a la zona.");
        }
    }

    private void quitarPescado() {
        System.out.println("\n=== Quitar Pescado de Zona ===");
        String clave = ConsolaUtils.leerTexto("Clave de la zona: ", 10).toUpperCase();
        int pescadoId = ConsolaUtils.leerEntero("ID del pescado a quitar: ", 1, 999999);

        if (zonaDao.quitarPescado(clave, pescadoId)) {
            System.out.println("Especie eliminada de la zona.");
        } else {
            System.out.println("No se encontró esa especie asociada a la zona.");
        }
    }

    private void consultarPescadosDeZona() {
        System.out.println("\n=== Pescados en la Zona ===");
        String clave = ConsolaUtils.leerTexto("Clave de la zona: ", 10).toUpperCase();

        List<Pescado> pescados = zonaDao.consultarPescadoPorZona(clave);
        if (pescados.isEmpty()) {
            System.out.println("No hay especies registradas en esta zona (o la clave no existe).");
            return;
        }

        for (Pescado p : pescados) {
            System.out.printf("[ID: %d] %s%n", p.getPescadoId(), p.getNombre());
        }
    }
    
}
