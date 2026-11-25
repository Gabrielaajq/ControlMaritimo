package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Atraco;

/**
 * Administra la persistencia de los registros de {@link Atraco} 
 * entre puertos y barcos
 */
public class AtracoDAO implements CrudDAO<Atraco, Integer> {

    @Override
    public boolean registrar(Atraco atraco) {
        String sql = "INSERT INTO Atraco (fechaAtraco, codigoPuerto_FK, matricula_FK) VALUES (?, ?, ?)";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(atraco.getFechaAtraco()));
            ps.setString(2, atraco.getCodigoPuerto());
            ps.setString(3, atraco.getMatricula());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        atraco.setAtracoId(rs.getInt(1));
                    }
                }
                return true;
            }
            return false;

        } catch (SQLException error) {
            if (error.getErrorCode() == 547) { 
                System.out.println("Error. El código de puerto o la matrícula del barco no existen.");
            } else {
                System.out.println("Error al registrar el atraco: " + error.getMessage());
            }
            return false;
        }
    }

    @Override
    public List<Atraco> consultarTodos() {
        List<Atraco> listaAtracos = new ArrayList<>();
        String sql = "SELECT atracoId, fechaAtraco, codigoPuerto_FK, matricula_FK FROM Atraco";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                listaAtracos.add(mapearAtraco(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar los atracos: " + e.getMessage());
        }
        return listaAtracos;
    }

    @Override
    public Atraco buscarPorId(Integer atracoId) {
        String sql = "SELECT atracoId, fechaAtraco, codigoPuerto_FK, matricula_FK FROM Atraco WHERE atracoId = ?";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, atracoId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearAtraco(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar el atraco: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean editar(Atraco atraco) {
        String sql = "UPDATE Atraco SET fechaAtraco = ?, codigoPuerto_FK = ?, matricula_FK = ? WHERE atracoId = ?";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(atraco.getFechaAtraco()));
            ps.setString(2, atraco.getCodigoPuerto());
            ps.setString(3, atraco.getMatricula());
            ps.setInt(4, atraco.getAtracoId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            if (e.getErrorCode() == 547) {
                System.out.println("Error. El código de puerto o la matrícula del barco no existen.");
            } else {
                System.out.println("Error al actualizar el atraco: " + e.getMessage());
            }
            return false;
        }
    }

    @Override
    public boolean eliminar(Integer atracoId) {
        String sql = "DELETE FROM Atraco WHERE atracoId = ?";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, atracoId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            if (e.getErrorCode() == 547) {
                System.out.println("Error. El atraco tiene transacciones mercantiles asociadas.");
            } else {
                System.out.println("Error al eliminar el atraco: " + e.getMessage());
            }
            return false;
        }
    }

    public List<Atraco> consultarPorBarco(String matricula) {
        List<Atraco> lista = new ArrayList<>();
        String sql = "SELECT atracoId, fechaAtraco, codigoPuerto_FK, matricula_FK FROM Atraco WHERE matricula_FK = ?";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, matricula);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearAtraco(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar atracos del barco: " + e.getMessage());
        }
        return lista;
    }

    public List<Atraco> consultarPorPuerto(String codigoPuerto) {
        List<Atraco> lista = new ArrayList<>();
        String sql = "SELECT atracoId, fechaAtraco, codigoPuerto_FK, matricula_FK FROM Atraco WHERE codigoPuerto_FK = ?";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, codigoPuerto);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearAtraco(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar atracos del puerto: " + e.getMessage());
        }
        return lista;
    }

    private Atraco mapearAtraco(ResultSet rs) throws SQLException {
        Date sqlFecha = rs.getDate("fechaAtraco");
        LocalDate fecha = (sqlFecha != null) ? sqlFecha.toLocalDate() : null;

        return new Atraco(
            rs.getInt("atracoId"),
            fecha,
            rs.getString("codigoPuerto_FK"),
            rs.getString("matricula_FK")
        );
    }
}