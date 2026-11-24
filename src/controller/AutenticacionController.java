package controller;

import dao.UsuarioDAO;
import model.Usuario;
import utils.ConsolaUtils;

/**
 * Clase controladora que se encarga del proceso de inicio de sesión
 * y validación de credenciales comparando con la base de datos
 */
public class AutenticacionController {
    
    private final UsuarioDAO usuarioDao;
    
    public AutenticacionController(UsuarioDAO usuarioDao) {
        this.usuarioDao = usuarioDao;
    }
    
    /**
     *  Se solicita usuario y contraseña para verificar
     *  que existe
     * 
     * @return el objeto Usuario si es verdadero, null o no corresponde
     */
    public Usuario autenticar() {
        System.out.println("\n=== Inicio de Sesión===");
        String username = ConsolaUtils.leerTexto("Usuario: ", 15);
        String password = ConsolaUtils.leerTexto("Contraseña: ", 15);
    
        Usuario usuario = usuarioDao.iniciarSesion(username, password);

        if (usuario == null) {
            System.out.println("Error: Usuario o contraseña incorrectos.");
            return null;
        }

        System.out.printf("\nBienvenido, %s [Rol: %s]%n",
                usuario.getUsername(),
                usuario.getRol());
        return usuario;
        }
      
}
