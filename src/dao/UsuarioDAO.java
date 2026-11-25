package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Usuario;
import enums.Rol;

/**
 * Implementa las operaciones de persistencia y validación de credenciales
 * para la tabla {@code Usuario} en SQL Server
 */
public class UsuarioDAO implements CrudDAO<Usuario, Integer> {
    
    @Override
    public boolean registrar(Usuario usuario) {
        String sql = "INSERT INTO Usuario (username, password, rol) VALUES (?, ?, ?)";
        
        try (Connection con = ConexionDB.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getPassword());
            ps.setString(3, rolParaSQL(usuario.getRol()));
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException error) {
            System.out.println("Error al registrar el usuario: " + error.getMessage());
            return false;
        }
    }

    @Override
    public List<Usuario> consultarTodos() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        String sql = "SELECT usuarioId, username, password, rol FROM Usuario";
        
        try (Connection con = ConexionDB.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Usuario usuario = new Usuario(
                    rs.getInt("usuarioId"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rolDesdeSQL(rs.getString("rol"))
                );
                listaUsuarios.add(usuario);
            }
        } catch (SQLException error) {
            System.out.println("Error al consultar los usuarios: " + error.getMessage());
        }
        return listaUsuarios;
    }

    @Override
    public Usuario buscarPorId(Integer usuarioId) {
        String sql = "SELECT usuarioId, username, password, rol FROM Usuario WHERE usuarioId = ?";
        
        try (Connection con = ConexionDB.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                        rs.getInt("usuarioId"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rolDesdeSQL(rs.getString("rol"))
                    );
                }
            }
        } catch (SQLException error) {
            System.out.println("Error al buscar el usuario: " + error.getMessage());
        }
        return null;
    }

    @Override
    public boolean editar(Usuario usuario) {
        String sql = "UPDATE Usuario SET username = ?, password = ?, rol = ? WHERE usuarioId = ?";
        
        try (Connection con = ConexionDB.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getPassword());
            ps.setString(3, rolParaSQL(usuario.getRol()));
            ps.setInt(4, usuario.getUsuarioId());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException error) {
            System.out.println("Error al actualizar el usuario: " + error.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(Integer usuarioId) {
        
        String sql = "DELETE FROM Usuario WHERE usuarioId = ?";
        
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, usuarioId);
            return ps.executeUpdate() > 0;
            
        } catch (SQLException error) {
            System.out.println("Error al eliminar el usuario: " + error.getMessage());
            return false;
        }
    }
    
    /**
     * Verifica si existen credenciales coincidentes en la base de datos
     *
     * @param username Nombre de usuario ingresado
     * @param password Contraseña ingresada
     * @return Objeto {@link Usuario} con su rol asignado
     * o {@code null} si son inválidas
     */
    public Usuario iniciarSesion(String username, String password) {
        String sql = "SELECT usuarioId, username, password, rol FROM Usuario WHERE username = ? AND password = ?";
        
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, username);
            ps.setString(2, password);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                        rs.getInt("usuarioId"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rolDesdeSQL(rs.getString("rol"))
                    );
                }
            }
        } catch (SQLException error) {
            System.out.println("Error al iniciar sesión: " + error.getMessage());
        }
        return null;
    }

    private String rolParaSQL(Rol rol) {
        if (rol == Rol.ADMINISTRADOR) {
            return "Administrador";
        }
        return "Usuario";
    }
    
    private Rol rolDesdeSQL(String rolSQL) {
        return Rol.valueOf(rolSQL.trim().toUpperCase());
    }
    
}
