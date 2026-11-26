package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.BarcoPasajero;

/**
 * Gestiona las operaciones CRUD de {@link BarcoPasajero}
 * entre la tabla padre {@code Barco} y la tabla {@code BarcoPasajero}
 */
public class BarcoPasajeroDAO implements CrudDAO<BarcoPasajero, String> {

    @Override
    public boolean registrar(BarcoPasajero barco) {
        String sqlBarco = "INSERT INTO Barco "
                + "(matricula, bandera, nombre, pesoTonelada, fechaBotadura) "
                + "VALUES (?, ?, ?, ?, ?)";
        String sqlPasajero = "INSERT INTO BarcoPasajero "
                + "(matricula_FK, numPasajeros, numPisos, tipoViaje) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection con = ConexionDB.obtenerConexion()) {
            con.setAutoCommit(false);
            try (PreparedStatement psBarco = con.prepareStatement(sqlBarco);
                 PreparedStatement psPasajero = con.prepareStatement(sqlPasajero)) {

                psBarco.setString(1, barco.getMatricula());
                psBarco.setString(2, barco.getBandera());
                psBarco.setString(3, barco.getNombre());
                psBarco.setDouble(4, barco.getPesoTonelada());
                psBarco.setDate(5, Date.valueOf(barco.getFechaBotadura()));
                psBarco.executeUpdate();

                psPasajero.setString(1, barco.getMatricula());
                psPasajero.setInt(2, barco.getNumPasajeros());
                psPasajero.setInt(3, barco.getNumPisos());
                psPasajero.setString(4, barco.getTipoViaje());
                psPasajero.executeUpdate();

                con.commit();
                return true;
            } catch (SQLException e) {
                con.rollback();
                if (e.getErrorCode() == 2627) {
                    System.out.println("Error: Ya existe un barco registrado con la matrícula " + barco.getMatricula());
                } else {
                    System.out.println("Error al registrar el barco de pasajeros: " + e.getMessage());
                }
                return false;
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Error de conexión al registrar el barco de pasajeros: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<BarcoPasajero> consultarTodos() {
        List<BarcoPasajero> lista = new ArrayList<>();
        String sql = "SELECT b.matricula, b.bandera, b.nombre, b.pesoTonelada, b.fechaBotadura, "
                   + "bp.numPasajeros, bp.numPisos, bp.tipoViaje "
                   + "FROM Barco b "
                   + "INNER JOIN BarcoPasajero bp ON b.matricula = bp.matricula_FK";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearBarcoPasajero(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar los barcos de pasajeros: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public BarcoPasajero buscarPorId(String matricula) {
        String sql = "SELECT b.matricula, b.bandera, b.nombre, b.pesoTonelada, b.fechaBotadura, "
                   + "bp.numPasajeros, bp.numPisos, bp.tipoViaje "
                   + "FROM Barco b "
                   + "INNER JOIN BarcoPasajero bp ON b.matricula = bp.matricula_FK "
                   + "WHERE b.matricula = ?";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, matricula);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearBarcoPasajero(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar el barco de pasajeros: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean editar(BarcoPasajero barco) {
        String sqlBarco = "UPDATE Barco "
                + "SET bandera = ?, "
                + "nombre = ?, "
                + "pesoTonelada = ?, "
                + "fechaBotadura = ? "
                + "WHERE matricula = ?";
        String sqlPasajero = "UPDATE BarcoPasajero "
                + "SET numPasajeros = ?, "
                + "numPisos = ?, "
                + "tipoViaje = ? "
                + "WHERE matricula_FK = ?";

        try (Connection con = ConexionDB.obtenerConexion()) {
            con.setAutoCommit(false);
            try (PreparedStatement psBarco = con.prepareStatement(sqlBarco);
                 PreparedStatement psPasajero = con.prepareStatement(sqlPasajero)) {

                psBarco.setString(1, barco.getBandera());
                psBarco.setString(2, barco.getNombre());
                psBarco.setDouble(3, barco.getPesoTonelada());
                psBarco.setDate(4, Date.valueOf(barco.getFechaBotadura()));
                psBarco.setString(5, barco.getMatricula());
                int filasBarco = psBarco.executeUpdate();

                psPasajero.setInt(1, barco.getNumPasajeros());
                psPasajero.setInt(2, barco.getNumPisos());
                psPasajero.setString(3, barco.getTipoViaje());
                psPasajero.setString(4, barco.getMatricula());
                int filasPasajero = psPasajero.executeUpdate();

                if (filasBarco > 0 && filasPasajero > 0) {
                    con.commit();
                    return true;
                }
                con.rollback();
                return false;
            } catch (SQLException e) {
                con.rollback();
                System.out.println("Error al actualizar el barco de pasajeros: " + e.getMessage());
                return false;
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Error de conexión al actualizar el barco de pasajeros: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(String matricula) {
        String sqlPasajero = "DELETE FROM BarcoPasajero "
                + "WHERE matricula_FK = ?";
        String sqlBarco = "DELETE FROM Barco "
                + "WHERE matricula = ?";

        try (Connection con = ConexionDB.obtenerConexion()) {
            con.setAutoCommit(false);
            try (PreparedStatement psPasajero = con.prepareStatement(sqlPasajero);
                 PreparedStatement psBarco = con.prepareStatement(sqlBarco)) {

                psPasajero.setString(1, matricula);
                int filasPasajero = psPasajero.executeUpdate();

                psBarco.setString(1, matricula);
                int filasBarco = psBarco.executeUpdate();

                if (filasPasajero > 0 && filasBarco > 0) {
                    con.commit();
                    return true;
                }
                con.rollback();
                return false;
            } catch (SQLException e) {
                con.rollback();
                if (e.getErrorCode() == 547) {
                    System.out.println("Error: No se puede eliminar el barco porque tiene atracos registrados");
                } else {
                    System.out.println("Error al eliminar el barco de pasajeros: " + e.getMessage());
                }
                return false;
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Error de conexión al eliminar el barco de pasajeros: " + e.getMessage());
            return false;
        }
    }

    private BarcoPasajero mapearBarcoPasajero(ResultSet rs) throws SQLException {
        Date sqlFecha = rs.getDate("fechaBotadura");
        LocalDate fechaBotadura;
        if (sqlFecha != null){
            fechaBotadura = sqlFecha.toLocalDate();
        } else{
            fechaBotadura = null;
        }

        return new BarcoPasajero(
                rs.getString("matricula"),
                rs.getString("bandera"),
                rs.getString("nombre"),
                rs.getDouble("pesoTonelada"),
                fechaBotadura,
                rs.getInt("numPasajeros"),
                rs.getInt("numPisos"),
                rs.getString("tipoViaje")
        );
    }
}