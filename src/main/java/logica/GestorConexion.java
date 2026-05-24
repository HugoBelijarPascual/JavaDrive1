package logica;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
//TODO ACORDARSE DE CAMBIAR LA RUTA DEL ARCHIVO DE CONFIGURACION
public class GestorConexion {

    private static Properties props = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("src/resources/config.properties")) {
            props.load(fis);

        } catch (IOException e) {
            System.out.println("No se pudo cargar el archivo de configuración");
        }
    }


    /* Devuelve un objeto de la clase Connection con la información para conectar a la BD */
    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(
                props.getProperty("db.url"),
                props.getProperty("db.user"),
                props.getProperty("db.password")
        );
    }

    public static boolean probarConexion() {
        System.out.println("Intentando conectar a MariaDB...");
        try (Connection conn = obtenerConexion()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("Conexión establecida con éxito");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error de conexión");
        }
        return false;
    }
}