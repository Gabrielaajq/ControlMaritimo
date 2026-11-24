package controller;

/**
 * Gestiona las opciones disponibles del rol Administrador
 */
public class MenuAdminController extends MenuBaseController {
    
    public MenuAdminController() {
        super("\n=== Menú del Administrador ===");
        agregarOpcion("Gestión de Usuarios", new UsuarioController());
        agregarOpcion("Gestión de Puertos", new PuertoController());
        agregarOpcion("Gestión de Zonas de Pesca", new ZonaController());
        agregarOpcion("Gestión de Transacciones", new TransaccionController());
        // agregarOpcion("Gestión de Barcos", new BarcoController());
        // agregarOpcion("Gestión de Atracos", new AtracoController());
        // agregarOpcion("Gestión de Pescados", new PescadoController());
        // agregarOpcion("Gestión de Zonas Reportadas", new ZonaReportadaController());
    }
    
}
