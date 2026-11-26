package dao;

import enums.TipoTransaccion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Transaccion;

/**
 * Gestiona las operaciones CRUD de {@link Transaccion} en la tabla
 * {@code Transaccion} de SQL Server, vinculando movimientos de carga y descarga
 * con un atraco
 */
public class TransaccionDAO implements CrudDAO<Transaccion, String> {

    @Override
    public boolean registrar(Transaccion transaccion) {
        String sql = "INSERT INTO Transaccion "
                   + "(claveTransaccion, fechaTransaccion, "
                   + "tipoTransacion, elementos, atracoId_FK) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, transaccion.getClaveTransaccion());
            ps.setTimestamp(2, Timestamp.valueOf(transaccion.getFechaTransaccion()));
            ps.setString(3, tipoParaSQL(transaccion.getTipoTransaccion()));
            ps.setDouble(4, transaccion.getElementos());
            ps.setInt(5, transaccion.getAtracoId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            if (e.getErrorCode() == 2627) {
                System.out.println("Error: Ya existe una transacción con la clave " + transaccion.getClaveTransaccion());
            } else if (e.getErrorCode() == 547) {
                System.out.println("Error: El ID de atraco asociado (" + transaccion.getAtracoId() + ") no existe");
            } else {
                System.out.println("Error al registrar la transacción: " + e.getMessage());
            }
            return false;
        }
    }

    @Override
    public List<Transaccion> consultarTodos() {
        List<Transaccion> lista = new ArrayList<>();
        String sql = "SELECT claveTransaccion, fechaTransaccion, "
                + "tipoTransacion, elementos, atracoId_FK "
                + "FROM Transaccion";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearTransaccion(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar las transacciones: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public Transaccion buscarPorId(String claveTransaccion) {
        String sql = "SELECT claveTransaccion, fechaTransaccion, "
                + "tipoTransacion, elementos, atracoId_FK "
                + "FROM Transaccion "
                + "WHERE claveTransaccion = ?";

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
                + "SET fechaTransaccion = ?, "
                + "tipoTransacion = ?, "
                + "elementos = ?, "
                + "atracoId_FK = ? "
                + "WHERE claveTransaccion = ?";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(transaccion.getFechaTransaccion()));
            ps.setString(2, tipoParaSQL(transaccion.getTipoTransaccion()));
            ps.setDouble(3, transaccion.getElementos());
            ps.setInt(4, transaccion.getAtracoId());
            ps.setString(5, transaccion.getClaveTransaccion());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            if (e.getErrorCode() == 547) {
                System.out.println("Error: El ID de atraco asociado (" + transaccion.getAtracoId() + ") no existe");
            } else {
                System.out.println("Error al actualizar la transacción: " + e.getMessage());
            }
            return false;
        }
    }

    @Override
    public boolean eliminar(String claveTransaccion) {
        String sql = "DELETE FROM Transaccion "
                + "WHERE claveTransaccion = ?";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, claveTransaccion);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar la transacción: " + e.getMessage());
            return false;
        }
    }

    /**
     * Consulta todas las transacciones vinculadas a un atraco específico
     *
     * @param atracoId Identificador único del atraco
     * @return Lista de transacciones asociadas a dicho atraco
     */
    public List<Transaccion> consultarPorAtraco(int atracoId) {
        List<Transaccion> lista = new ArrayList<>();
        String sql = "SELECT claveTransaccion, "
                + "fechaTransaccion, "
                + "tipoTransacion, "
                + "elementos, "
                + "atracoId_FK "
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
            System.out.println("Error al consultar transacciones por atraco: " + e.getMessage());
        }
        return lista;
    }

    private Transaccion mapearTransaccion(ResultSet rs) throws SQLException {
        Timestamp ts = rs.getTimestamp("fechaTransaccion");
        LocalDateTime fecha = (ts != null) ? ts.toLocalDateTime() : null;

        return new Transaccion(
                rs.getString("claveTransaccion"),
                fecha,
                tipoDesdeSQL(rs.getString("tipoTransacion")),
                rs.getDouble("elementos"),
                rs.getInt("atracoId_FK")
        );
    }

    private String tipoParaSQL(TipoTransaccion tipo) {
        if (tipo == TipoTransaccion.CARGA) {
            return "Carga";
        } else {
            return "Descarga";
        }
    }

    private TipoTransaccion tipoDesdeSQL(String tipoSQL) {
        if (tipoSQL != null && tipoSQL.equalsIgnoreCase("Descarga")) {
            return TipoTransaccion.DESCARGA;
        }
        return TipoTransaccion.CARGA;
    }
}