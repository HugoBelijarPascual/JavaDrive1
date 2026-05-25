package logica;

import model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GestorClientes {


    public static List<Cliente> clientes = new ArrayList<>();


    public static boolean crearCliente(Cliente nuevo) {
        if (nuevo == null || nuevo.getDni() == null || nuevo.getDni().isEmpty()) {
            return false;
        }

        // Lógica de negocio: No permitir DNIs duplicados
        for (Cliente c : clientes) {
            if (c.getDni().equalsIgnoreCase(nuevo.getDni())) {
                return false;
            }
        }

        return clientes.add(nuevo);
    }

    public static void listarClientes() {
        boolean encontrado = false;

        for (Cliente c : clientes) {
            System.out.println(c);
            System.out.println("----------------------------------------");
            encontrado = true;
        }

        if (!encontrado) {
            System.out.println("No se han encontrado clientes.");
        }

    }
    //TODO RECORDAR QUE LOS ATRIBUTOS DEL STRING SQL PUEDEN CAMBIAR
    public static List<Cliente> ListarClientesBD() {
        List<Cliente> lista = new ArrayList<>();

        String sql = "SELECT * FROM cliente";

        try (Connection conn = GestorConexion.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()){
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                String telefono = rs.getString("telefono");

                Cliente c = new Cliente(dni, nombre, telefono);
                lista.add(c);
            }



        }catch (SQLException e) {
            System.out.println( "Error al insertar en la base de datos: " + e.getMessage());
        }
        System.out.println("Se han cargado los clientes");
        System.out.println("----------------------------------------");
        return lista;
    }

}
