package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.BarcoPesca;

/**
 * Gestiona las operaciones CRUD de {@link BarcoPesca}
 * entre la tabla padre {@code Barco} y la tabla {@code BarcoPesca}
 */
public class BarcoPescaDAO implements CrudDAO<BarcoPesca, String> {

    @Override
    public boolean registrar(BarcoPesca barco) {
        String sqlBarco = "INSERT INTO Barco "
                + "(matricula, bandera, nombre, pesoTonelada, fechaBotadura) "
                + "VALUES (?, ?, ?, ?, ?)";
        String sqlPesca = "INSERT INTO BarcoPesca "
                + "(matricula_FK, cantidadToneladas) "
                + "VALUES (?, ?)";

        try (Connection con = ConexionDB.obtenerConexion()) {
            con.setAutoCommit(false);
            try (PreparedStatement psBarco = con.prepareStatement(sqlBarco);
                 PreparedStatement psPesca = con.prepareStatement(sqlPesca)) {

                psBarco.setString(1, barco.getMatricula());
                psBarco.setString(2, barco.getBandera());
                psBarco.setString(3, barco.getNombre());
                psBarco.setDouble(4, barco.getPesoTonelada());
                psBarco.setDate(5, Date.valueOf(barco.getFechaBotadura()));
                psBarco.executeUpdate();

                psPesca.setString(1, barco.getMatricula());
                psPesca.setDouble(2, barco.getCantidadToneladas());
                psPesca.executeUpdate();

                con.commit();
                return true;
            } catch (SQLException e) {
                con.rollback();
                if (e.getErrorCode() == 2627) {
                    System.out.println("Error: Ya existe un barco registrado con la matrícula " + barco.getMatricula());
                } else {
                    System.out.println("Error al registrar el barco pesquero: " + e.getMessage());
                }
                return false;
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Error de conexión al registrar el barco pesquero: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<BarcoPesca> consultarTodos() {
        List<BarcoPesca> lista = new ArrayList<>();
        String sql = "SELECT b.matricula, b.bandera, b.nombre, b.pesoTonelada, "
                + "b.fechaBotadura, bp.cantidadToneladas "
                + "FROM Barco b "
                + "INNER JOIN BarcoPesca bp ON b.matricula = bp.matricula_FK";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearBarcoPesca(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar los barcos pesqueros: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public BarcoPesca buscarPorId(String matricula) {
        String sql = "SELECT b.matricula, b.bandera, b.nombre, b.pesoTonelada, "
                + "b.fechaBotadura, bp.cantidadToneladas "
                + "FROM Barco b "
                + "INNER JOIN BarcoPesca bp ON b.matricula = bp.matricula_FK "
                + "WHERE b.matricula = ?";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, matricula);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearBarcoPesca(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar el barco pesquero: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean editar(BarcoPesca barco) {
        String sqlBarco = "UPDATE Barco "
                + "SET bandera = ?, "
                + "nombre = ?, "
                + "pesoTonelada = ?, "
                + "fechaBotadura = ? "
                + "WHERE matricula = ?";
        String sqlPesca = "UPDATE BarcoPesca "
                + "SET cantidadToneladas = ? "
                + "WHERE matricula_FK = ?";

        try (Connection con = ConexionDB.obtenerConexion()) {
            con.setAutoCommit(false);
            try (PreparedStatement psBarco = con.prepareStatement(sqlBarco);
                 PreparedStatement psPesca = con.prepareStatement(sqlPesca)) {

                psBarco.setString(1, barco.getBandera());
                psBarco.setString(2, barco.getNombre());
                psBarco.setDouble(3, barco.getPesoTonelada());
                psBarco.setDate(4, Date.valueOf(barco.getFechaBotadura()));
                psBarco.setString(5, barco.getMatricula());
                int filasBarco = psBarco.executeUpdate();

                psPesca.setDouble(1, barco.getCantidadToneladas());
                psPesca.setString(2, barco.getMatricula());
                int filasPesca = psPesca.executeUpdate();

                if (filasBarco > 0 && filasPesca > 0) {
                    con.commit();
                    return true;
                }
                con.rollback();
                return false;
            } catch (SQLException e) {
                con.rollback();
                System.out.println("Error al actualizar el barco pesquero: " + e.getMessage());
                return false;
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Error de conexión al actualizar el barco pesquero: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(String matricula) {
        String sqlPesca = "DELETE FROM BarcoPesca "
                + "WHERE matricula_FK = ?";
        String sqlBarco = "DELETE FROM Barco "
                + "WHERE matricula = ?";

        try (Connection con = ConexionDB.obtenerConexion()) {
            con.setAutoCommit(false);
            try (PreparedStatement psPesca = con.prepareStatement(sqlPesca);
                 PreparedStatement psBarco = con.prepareStatement(sqlBarco)) {

                psPesca.setString(1, matricula);
                int filasPesca = psPesca.executeUpdate();

                psBarco.setString(1, matricula);
                int filasBarco = psBarco.executeUpdate();

                if (filasPesca > 0 && filasBarco > 0) {
                    con.commit();
                    return true;
                }
                con.rollback();
                return false;
            } catch (SQLException e) {
                con.rollback();
                if (e.getErrorCode() == 547) {
                    System.out.println("Error: No se puede eliminar el barco porque tiene atracos, zonas reportadas o especies asociadas");
                } else {
                    System.out.println("Error al eliminar el barco pesquero: " + e.getMessage());
                }
                return false;
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Error de conexión al eliminar el barco pesquero: " + e.getMessage());
            return false;
        }
    }

    private BarcoPesca mapearBarcoPesca(ResultSet rs) throws SQLException {
        Date sqlFecha = rs.getDate("fechaBotadura");
        LocalDate fechaBotadura;
        if (sqlFecha != null){
            fechaBotadura = sqlFecha.toLocalDate();
        } else{
            fechaBotadura = null;
        }

        return new BarcoPesca(
                rs.getString("matricula"),
                rs.getString("bandera"),
                rs.getString("nombre"),
                rs.getDouble("pesoTonelada"),
                fechaBotadura,
                rs.getDouble("cantidadToneladas")
        );
    }
}