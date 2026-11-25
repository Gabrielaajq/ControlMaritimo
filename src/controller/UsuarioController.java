package controller;

import dao.UsuarioDAO;
import enums.Rol;
import java.util.List;
import model.Usuario;
import utils.ConsolaUtils;

/**
 * Controlador de consola para la administración de cuentas de {@link Usuario}
 * y asignación de roles de acceso
 */
public class UsuarioController implements CrudController {
    
    private final UsuarioDAO usuarioDao = new UsuarioDAO();
    
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
                      \n=== GESTIÓN DE USUARIOS ===
                      1. Registrar Usuario.
                      2. Consultar Usuarios.
                      3. Editar Usuario.
                      4. Eliminar Usuario.
                      5. Volver.
                      """;
        System.out.println(menu);
    }

    @Override
    public void registrar() {
        System.out.println("\n=== Nuevo Usuario ===");
        String username = ConsolaUtils.leerTexto("Nombre de usuario (máx. 15 caracteres): ", 15);
        String password = ConsolaUtils.leerTexto("Contraseña (máx. 15 caracteres): ", 15);
        Rol rol = seleccionarRol();

        try {
            Usuario nuevoUsuario = new Usuario(username, password, rol);
            if (usuarioDao.registrar(nuevoUsuario)) {
                System.out.println("Usuario registrado correctamente.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("No se pudo agregar al usuario: " + e.getMessage());
        }
        
        
    }

    @Override
    public void consultar() {
        System.out.println("\n=== Catálogo de Usuarios ===");
        List<Usuario> usuarios = usuarioDao.consultarTodos();

        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return;
        }

        for (Usuario u : usuarios) {
            System.out.printf("[ID: %d] Usuario: %s | Rol: %s%n",
                    u.getUsuarioId(),
                    u.getUsername(),
                    u.getRol());
        }
    }

    @Override
    public void editar() {
        System.out.println("\n=== Editar Usuario ===");
        int id = ConsolaUtils.leerEntero("ID del usuario a modificar: ", 1, 999999);

        Usuario actual = usuarioDao.buscarPorId(id);
        if (actual == null) {
            System.out.println("No se encontró ningún usuario con el ID " + id + ".");
            return;
        }

        System.out.println("(Presione ENTER si gusta conservar el valor actual)");

        String usernameFinal = actual.getUsername();
        String nuevoUsername = ConsolaUtils.leerTextoOpcional("Nuevo usuario [" + actual.getUsername() + "]: ", 15);
        if (!nuevoUsername.isEmpty()) {
            usernameFinal = nuevoUsername;
        }

        String passwordFinal = actual.getPassword();
        String nuevaPassword = ConsolaUtils.leerTextoOpcional("Nueva contraseña [****]: ", 15);
        if (!nuevaPassword.isEmpty()) {
            passwordFinal = nuevaPassword;
        }

        Rol rolFinal = actual.getRol();
        while (true) {
            System.out.println("Rol actual: " + actual.getRol() + " -> [A] Administrador  [U] Usuario");
            String opcionRol = ConsolaUtils.leerTextoOpcional("Nuevo rol (A/U o ENTER para conservar): ", 1);

            if (opcionRol.isEmpty()) {
                break;
            } else if (opcionRol.equalsIgnoreCase("A")) {
                rolFinal = Rol.ADMINISTRADOR;
                break;
            } else if (opcionRol.equalsIgnoreCase("u")) {
                rolFinal = Rol.USUARIO;
                break;
            } else {
                System.out.println("Opción no válida. Escribe 'A', 'u' o presiona ENTER para conservar.");
            }
        }

        try {
            Usuario usuarioEditado = new Usuario(id, usernameFinal, passwordFinal, rolFinal);
            if (usuarioDao.editar(usuarioEditado)) {
                System.out.println("Usuario actualizado correctamente.");
            }
        } catch(IllegalArgumentException e) {
            System.out.println("Error al editar usuario: " + e.getMessage());
        }
        
    }

    @Override
    public void eliminar() {
        System.out.println("\n=== Eliminar Usuario ===");
        int id = ConsolaUtils.leerEntero("ID del usuario a eliminar: ", 1, 999999);

        Usuario actual = usuarioDao.buscarPorId(id);
        if (actual == null) {
            System.out.println("No se encontró ningún usuario con el ID " + id + ".");
            return;
        }

        String confirmacion = ConsolaUtils.leerTexto(
                "¿Confirmas eliminar al usuario '" + actual.getUsername() + "'? (S/N): ", 1);

        if (confirmacion.equalsIgnoreCase("S")) {
            if (usuarioDao.eliminar(id)) {
                System.out.println("Usuario eliminado correctamente.");
            }
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    private Rol seleccionarRol() {
        while(true) {
            System.out.println("Tipo de usuario:");
            System.out.println("[A] Admnistrador    [u] Usuario");
            String opcion = ConsolaUtils.leerTexto("Selecciona una opción (A/u): ", 1);
            
            if(opcion.equalsIgnoreCase("A")){
                return Rol.ADMINISTRADOR;
            } else if (opcion.equalsIgnoreCase("u")) {
                return Rol.USUARIO;
            } else{
                System.out.println("Opción no válida. Escribe 'A' para administrador o 'u' para Usuario");
            }
        }
    }
    
}
