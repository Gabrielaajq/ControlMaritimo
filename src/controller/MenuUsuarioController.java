package controller;

/**
 * Gestiona las opciones disponibles como rol de Usuario
 */
public class MenuUsuarioController extends MenuBaseController {
    
    public MenuUsuarioController() {
        super("Menú del Usuario");
        agregarOpcion("Gestión de Transacciones", new TransaccionController());
        agregarOpcion("Gestión de Atracos", new AtracoController());
        agregarOpcion("Gestión de Zonas Reportadas", new ZonaReportadaController());
    }
    
}
