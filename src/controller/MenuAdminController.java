package controller;

/**
 * Gestiona las opciones disponibles del rol Administrador
 */
public class MenuAdminController extends MenuBaseController {
    
    public MenuAdminController() {
        super("Menú del Administrador");
        agregarOpcion("Gestión de Usuarios", new UsuarioController());
        agregarOpcion("Gestión de Puertos", new PuertoController());
        agregarOpcion("Gestión de Zonas de Pesca", new ZonaController());
        agregarOpcion("Gestión de Transacciones", new TransaccionController());
        agregarOpcion("Gestión de Barcos de Carga", new BarcoCargaController());
        agregarOpcion("Gestión de Barcos con Pasajeros", new BarcoPasajeroController());
        agregarOpcion("Gestión de Barcos Pesqueros", new BarcoPescaController());
        agregarOpcion("Gestión de Atracos", new AtracoController());
        agregarOpcion("Gestión de Zonas Reportadas", new ZonaReportadaController());
    }
    
}
