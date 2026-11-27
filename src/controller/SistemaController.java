package controller;

import dao.UsuarioDAO;
import model.Usuario;
import utils.ConsolaUtils;

/**
 *  Es el gestor principal, gestiona los ciclos de entrada,
 *  la autenticación y asignaciones de opciones correspondientes
 */
public class SistemaController {
    
    private final AutenticacionController authController = new AutenticacionController(new UsuarioDAO());
    private final MenuRol menuAdmin = new MenuAdminController();
    private final MenuRol menuUsuario = new MenuUsuarioController();
    
    /**
     * Inicio del sistema
     */
    public void iniciar() {
        int opcion;
        do{
            System.out.println("""
                               \n=======================================
                                    Sistema de Control Marítimo MX
                               =========================================
                               1. Iniciar sesión
                               2. Salir
                                     /`·.¸
                                    /¸...¸`:·
                                ¸.·´  ¸   `·.¸.·´)
                               : © ):´;      ¸  {
                                `·.¸ `·  ¸.·´\\`·¸)
                                    `\\´´\\¸.·´
                               
                               
                               """);
            opcion = ConsolaUtils.leerEntero("Seleccione una opción: ", 1, 2);
            if (opcion == 1) {
                Usuario usuario = authController.autenticar();
                if (usuario != null) {
                    abrirMenuSegunRol(usuario);
                }
            } else {
                System.out.println("Cerrando sistema. ¡Hasta pronto!");
            }
        }while (opcion != 2);
    }
    
    private void abrirMenuSegunRol(Usuario usuario) {
        switch(usuario.getRol()) {
            case ADMINISTRADOR -> menuAdmin.iniciarMenu();
            case USUARIO -> menuUsuario.iniciarMenu();
        }
    }
    
}
