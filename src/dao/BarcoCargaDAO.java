package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.BarcoCarga;

/**
 * Gestiona las operaciones CRUD de {@link BarcoCarga} 
 * entre la tabla padre {@code Barco} y la tabla {@code BarcoCarga}.
 */
public class BarcoCargaDAO implements CrudDAO<BarcoCarga, String> {

    @Override
    public boolean registrar(BarcoCarga barco) {
        String sqlBarco = "INSERT INTO Barco "
                + "(matricula, bandera, nombre, pesoTonelada, fechaBotadura) "
                + "VALUES (?, ?, ?, ?, ?)";
        
        String sqlCarga = "INSERT INTO BarcoCarga "
                + "(matricula_FK, numContenedores)"
                + " VALUES (?, ?)";

        try (Connection con = ConexionDB.obtenerConexion()) {
            con.setAutoCommit(false);
            try (PreparedStatement psBarco = con.prepareStatement(sqlBarco);
                 PreparedStatement psCarga = con.prepareStatement(sqlCarga)) {

                psBarco.setString(1, barco.getMatricula());
                psBarco.setString(2, barco.getBandera());
                psBarco.setString(3, barco.getNombre());
                psBarco.setDouble(4, barco.getPesoTonelada());
                psBarco.setDate(5, Date.valueOf(barco.getFechaBotadura()));
                psBarco.executeUpdate();

                psCarga.setString(1, barco.getMatricula());
                psCarga.setInt(2, barco.getNumContenedores());
                psCarga.executeUpdate();

                con.commit();
                return true;
            } catch (SQLException e) {
                con.rollback();
                if (e.getErrorCode() == 2627) {
                    System.out.println("Error: Ya existe un barco registrado con la matrícula " + barco.getMatricula());
                } else {
                    System.out.println("Error al registrar el barco de carga: " + e.getMessage());
                }
                return false;
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Error de conexión al registrar el barco de carga: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<BarcoCarga> consultarTodos() {
        List<BarcoCarga> lista = new ArrayList<>();
        String sql = "SELECT b.matricula, b.bandera, b.nombre, b.pesoTonelada, b.fechaBotadura, bc.numContenedores "
                   + "FROM Barco b "
                   + "INNER JOIN BarcoCarga bc ON b.matricula = bc.matricula_FK";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearBarcoCarga(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar los barcos de carga: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public BarcoCarga buscarPorId(String matricula) {
        String sql = "SELECT b.matricula, b.bandera, b.nombre, b.pesoTonelada, b.fechaBotadura, bc.numContenedores "
                   + "FROM Barco b "
                   + "INNER JOIN BarcoCarga bc ON b.matricula = bc.matricula_FK "
                   + "WHERE b.matricula = ?";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, matricula);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearBarcoCarga(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar el barco de carga: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean editar(BarcoCarga barco) {
        String sqlBarco = "UPDATE Barco "
                + "SET bandera = ?, "
                + "nombre = ?, "
                + "pesoTonelada = ?, "
                + "fechaBotadura = ? "
                + "WHERE matricula = ?";
        String sqlCarga = "UPDATE BarcoCarga "
                + "SET numContenedores = ? "
                + "WHERE matricula_FK = ?";

        try (Connection con = ConexionDB.obtenerConexion()) {
            con.setAutoCommit(false);
            try (PreparedStatement psBarco = con.prepareStatement(sqlBarco);
                 PreparedStatement psCarga = con.prepareStatement(sqlCarga)) {

                psBarco.setString(1, barco.getBandera());
                psBarco.setString(2, barco.getNombre());
                psBarco.setDouble(3, barco.getPesoTonelada());
                psBarco.setDate(4, Date.valueOf(barco.getFechaBotadura()));
                psBarco.setString(5, barco.getMatricula());
                int filasBarco = psBarco.executeUpdate();

                psCarga.setInt(1, barco.getNumContenedores());
                psCarga.setString(2, barco.getMatricula());
                int filasCarga = psCarga.executeUpdate();

                if (filasBarco > 0 && filasCarga > 0) {
                    con.commit();
                    return true;
                }
                con.rollback();
                return false;
            } catch (SQLException e) {
                con.rollback();
                System.out.println("Error al actualizar el barco de carga: " + e.getMessage());
                return false;
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Error de conexión al actualizar el barco de carga: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(String matricula) {
        String sqlCarga = "DELETE FROM BarcoCarga "
                + "WHERE matricula_FK = ?";
        String sqlBarco = "DELETE FROM Barco "
                + "WHERE matricula = ?";

        try (Connection con = ConexionDB.obtenerConexion()) {
            con.setAutoCommit(false);
            try (PreparedStatement psCarga = con.prepareStatement(sqlCarga);
                 PreparedStatement psBarco = con.prepareStatement(sqlBarco)) {

                psCarga.setString(1, matricula);
                int filasCarga = psCarga.executeUpdate();

                psBarco.setString(1, matricula);
                int filasBarco = psBarco.executeUpdate();

                if (filasCarga > 0 && filasBarco > 0) {
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
                    System.out.println("Error al eliminar el barco de carga: " + e.getMessage());
                }
                return false;
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Error de conexión al eliminar el barco de carga: " + e.getMessage());
            return false;
        }
    }

    private BarcoCarga mapearBarcoCarga(ResultSet rs) throws SQLException {
        Date sqlFecha = rs.getDate("fechaBotadura");
        LocalDate fechaBotadura;
        if (sqlFecha != null){
            fechaBotadura = sqlFecha.toLocalDate();
        } else{
            fechaBotadura = null;
        }
        
        return new BarcoCarga(
                rs.getString("matricula"),
                rs.getString("bandera"),
                rs.getString("nombre"),
                rs.getDouble("pesoTonelada"),
                fechaBotadura,
                rs.getInt("numContenedores")
        );
    }
}