/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.ZonaReportada;

/**
 *
 * @author leonm
 */
public class ZonaReportadaDAO implements CrudDAO<ZonaReportada, Integer> {

    @Override
    public boolean registrar(ZonaReportada reporte) {
        String sql = "INSERT INTO ZonaReportada (fechaEntrada, fechaSalida, claveZona_FK, matricula_FK) "
                   + "VALUES (?, ?, ?, ?)";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setTimestamp(1, Timestamp.valueOf(reporte.getFechaEntrada()));

            if (reporte.getFechaSalida() != null) {
                ps.setTimestamp(2, Timestamp.valueOf(reporte.getFechaSalida()));
            } else {
                ps.setNull(2, Types.TIMESTAMP);
            }

            ps.setString(3, reporte.getClaveZona());
            ps.setString(4, reporte.getMatricula());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        reporte.setZonaReportadaId(rs.getInt(1));
                    }
                }
                return true;
            }
            return false;

        } catch (SQLException error) {
            if (error.getErrorCode() == 547) { // Error de restricción FK en SQL Server
                System.out.println("Error. La clave de zona no existe o la matrícula no corresponde a un barco pesquero registrado.");
            } else {
                System.out.println("Error al registrar el reporte de zona: " + error.getMessage());
            }
            return false;
        }
    }

    @Override
    public List<ZonaReportada> consultarTodos() {
        List<ZonaReportada> listaReportes = new ArrayList<>();
        String sql = "SELECT zonaReportadaId, fechaEntrada, fechaSalida, claveZona_FK, matricula_FK "
                   + "FROM ZonaReportada";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                listaReportes.add(mapearZonaReportada(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar los reportes de zona: " + e.getMessage());
        }
        return listaReportes;
    }

    @Override
    public ZonaReportada buscarPorId(Integer zonaReportadaId) {
        String sql = "SELECT zonaReportadaId, fechaEntrada, fechaSalida, claveZona_FK, matricula_FK "
                   + "FROM ZonaReportada WHERE zonaReportadaId = ?";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, zonaReportadaId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearZonaReportada(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar el reporte de zona: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean editar(ZonaReportada reporte) {
        String sql = "UPDATE ZonaReportada "
                   + "SET fechaEntrada = ?, fechaSalida = ?, claveZona_FK = ?, matricula_FK = ? "
                   + "WHERE zonaReportadaId = ?";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(reporte.getFechaEntrada()));

            if (reporte.getFechaSalida() != null) {
                ps.setTimestamp(2, Timestamp.valueOf(reporte.getFechaSalida()));
            } else {
                ps.setNull(2, Types.TIMESTAMP);
            }

            ps.setString(3, reporte.getClaveZona());
            ps.setString(4, reporte.getMatricula());
            ps.setInt(5, reporte.getZonaReportadaId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            if (e.getErrorCode() == 547) {
                System.out.println("Error. La clave de zona no existe o la matrícula no corresponde a un barco pesquero.");
            } else {
                System.out.println("Error al actualizar el reporte de zona: " + e.getMessage());
            }
            return false;
        }
    }

    @Override
    public boolean eliminar(Integer zonaReportadaId) {
        String sql = "DELETE FROM ZonaReportada WHERE zonaReportadaId = ?";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, zonaReportadaId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar el reporte de zona: " + e.getMessage());
            return false;
        }
    }

    // =========================================================================
    // MÉTODOS EXTRAS PARA CONTROLADORES
    // =========================================================================

    public boolean registrarSalida(int zonaReportadaId, LocalDateTime fechaSalida) {
        String sql = "UPDATE ZonaReportada SET fechaSalida = ? WHERE zonaReportadaId = ?";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(fechaSalida));
            ps.setInt(2, zonaReportadaId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al registrar la salida de la zona: " + e.getMessage());
            return false;
        }
    }

    public List<ZonaReportada> consultarPorZona(String claveZona) {
        List<ZonaReportada> lista = new ArrayList<>();
        String sql = "SELECT zonaReportadaId, fechaEntrada, fechaSalida, claveZona_FK, matricula_FK "
                   + "FROM ZonaReportada WHERE claveZona_FK = ?";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, claveZona);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearZonaReportada(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar reportes por zona: " + e.getMessage());
        }
        return lista;
    }

    public List<ZonaReportada> consultarPorBarco(String matricula) {
        List<ZonaReportada> lista = new ArrayList<>();
        String sql = "SELECT zonaReportadaId, fechaEntrada, fechaSalida, claveZona_FK, matricula_FK "
                   + "FROM ZonaReportada WHERE matricula_FK = ?";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, matricula);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearZonaReportada(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar reportes por barco pesquero: " + e.getMessage());
        }
        return lista;
    }

    private ZonaReportada mapearZonaReportada(ResultSet rs) throws SQLException {
        Timestamp tsEntrada = rs.getTimestamp("fechaEntrada");
        Timestamp tsSalida = rs.getTimestamp("fechaSalida");

        LocalDateTime entrada = (tsEntrada != null) ? tsEntrada.toLocalDateTime() : null;
        LocalDateTime salida = (tsSalida != null) ? tsSalida.toLocalDateTime() : null;

        return new ZonaReportada(
            rs.getInt("zonaReportadaId"),
            entrada,
            salida,
            rs.getString("claveZona_FK"),
            rs.getString("matricula_FK")
        );
    }
}
