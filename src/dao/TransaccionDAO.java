/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import enums.TipoTransaccion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Transaccion;
/**
 *
 * @author gabri
 */
public class TransaccionDAO implements CrudDAO<Transaccion, String> {
    
    @Override
    public boolean registrar(Transaccion transaccion) {
        String sql = "INSERT INTO Transaccion "
                   + "(claveTransaccion, fechaTransaccion, tipoTransacion, elementos, atracoId_FK, pescadoId_FK) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, transaccion.getClaveTransaccion());
            ps.setDate(2, Date.valueOf(transaccion.getFechaTransaccion()));
            ps.setString(3, tipoParaSQL(transaccion.getTipoTransaccion()));
            ps.setDouble(4, transaccion.getElementos());
            ps.setInt(5, transaccion.getAtracoId());

            return ps.executeUpdate() > 0;

        } catch (SQLException error) {
            if (error.getErrorCode() == 2627) {
                System.out.println("Error. Ya existe una transacción con esa clave.");
            } else if (error.getErrorCode() == 547) {
                System.out.println("Error. El ID de atraco o el ID de pescado no existen.");
            } else {
                System.out.println("Error al registrar la transacción: " + error.getMessage());
            }
            return false;
        }
    }

    @Override
    public List<Transaccion> consultarTodos() {
        List<Transaccion> listaTransacciones = new ArrayList<>();
        String sql = "SELECT claveTransaccion, fechaTransaccion, tipoTransacion, elementos, atracoId_FK "
                   + "FROM Transaccion";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                listaTransacciones.add(mapearTransaccion(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar las transacciones: " + e.getMessage());
        }
        return listaTransacciones;
    }

    @Override
    public Transaccion buscarPorId(String claveTransaccion) {
        String sql = "SELECT claveTransaccion, fechaTransaccion, tipoTransacion, elementos, atracoId_FK, pescadoId_FK "
                   + "FROM Transaccion WHERE claveTransaccion = ?";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, claveTransaccion);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearTransaccion(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar la transacción: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean editar(Transaccion transaccion) {
        String sql = "UPDATE Transaccion "
                   + "SET fechaTransaccion = ?, tipoTransacion = ?, elementos = ?, "
                   + "atracoId_FK = ?,"
                   + "WHERE claveTransaccion = ?";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(transaccion.getFechaTransaccion()));
            ps.setString(2, tipoParaSQL(transaccion.getTipoTransaccion()));
            ps.setDouble(3, transaccion.getElementos());
            ps.setInt(4, transaccion.getAtracoId());
            ps.setString(5, transaccion.getClaveTransaccion());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            if (e.getErrorCode() == 547) {
                System.out.println("Error. El ID de atraco o el ID de pescado no existen.");
            } else {
                System.out.println("Error al actualizar la transacción: " + e.getMessage());
            }
            return false;
        }
    }

    @Override
    public boolean eliminar(String claveTransaccion) {
        String sql = "DELETE FROM Transaccion WHERE claveTransaccion = ?";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, claveTransaccion);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar la transacción: " + e.getMessage());
            return false;
        }
    }

    public List<Transaccion> consultarPorAtraco(int atracoId) {
        List<Transaccion> lista = new ArrayList<>();
        String sql = "SELECT claveTransaccion, fechaTransaccion, tipoTransacion, elementos, atracoId_FK "
                   + "FROM Transaccion WHERE atracoId_FK = ?";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, atracoId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearTransaccion(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar transacciones del atraco: " + e.getMessage());
        }
        return lista;
    }

    private Transaccion mapearTransaccion(ResultSet rs) throws SQLException {
        Date sqlFecha = rs.getDate("fechaTransaccion");
        LocalDate fecha = null;
        if (sqlFecha != null) {
            fecha = sqlFecha.toLocalDate();
        }

        int idPescadoCrudo = rs.getInt("pescadoId_FK");
        Integer pescadoId = null;
        if (!rs.wasNull()) {
            pescadoId = idPescadoCrudo;
        }

        return new Transaccion(
            rs.getString("claveTransaccion"),
            fecha,
            tipoDesdeSQL(rs.getString("tipoTransaccion")),
            rs.getDouble("elementos"),
            rs.getInt("atracoId_FK")
        );
    }

    // Java -> SQL
    private String tipoParaSQL(TipoTransaccion tipo) {
        if (tipo == TipoTransaccion.CARGA) {
            return "Carga";
        }
        return "Descarga";
    }

    // SQL -> Java
    private TipoTransaccion tipoDesdeSQL(String tipoSQL) {
        return TipoTransaccion.valueOf(tipoSQL.trim().toUpperCase());
    }
    
}
