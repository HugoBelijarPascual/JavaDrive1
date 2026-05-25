package logica;

import app.Main;
import model.Cliente;
import model.Reserva;
import model.Vehiculo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestorReservas {

    public static Cliente buscarCliente(String dni) {
        for (Cliente c : GestorClientes.clientes) {
            if (c.getDni().equalsIgnoreCase(dni)) {
                return c;
            }
        }
        return null;
    }

    public static Vehiculo buscarVehiculo(String matricula) {
        for (Vehiculo v : GestorFlota.flota) {
            if (v.getMatricula().equalsIgnoreCase(matricula)) {
                return v;
            }
        }
        return null;
    }

    public static Reserva realizarReserva(Cliente cliente, Vehiculo vehiculo, int dias) {
        if (cliente == null || vehiculo == null || !vehiculo.isDisponible()) {
            return null;
        }

        vehiculo.setDisponible(false);
        Reserva reserva = new Reserva(cliente, vehiculo, LocalDate.now(), LocalDate.now().plusDays(dias));

        exportarTicket(reserva);
        guardarDatos();
        return reserva;
    }

    public static void guardarDatos() {
        GestorPersistencia.gestor.guardarVehiculos(GestorFlota.flota);
        GestorPersistencia.gestor.guardarClientes(GestorClientes.clientes);
    }

    public static void exportarTicket(Reserva reserva) {
        GestorPersistencia.gestor.exportarTicket(reserva);
    }

    public static List<Reserva> ListarReservasBD() {
        List<Reserva> lista = new ArrayList<>();

        String sql = "SELECT * FROM reservar";

        try (Connection conn = GestorConexion.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()){
                String dni = rs.getString("dni");
                String matricula = rs.getString("matricula");
                LocalDate fechaInicio = LocalDate.parse(rs.getString("fechaInicio"));
                LocalDate fechaFin = LocalDate.parse(rs.getString("fechFin"));

                Reserva r = new Reserva(buscarCliente(dni), buscarVehiculo(matricula), fechaInicio, fechaFin);

                lista.add(r);
            }



        }catch (SQLException e) {
            System.out.println( "Error al insertar en la base de datos: " + e.getMessage());
        }
        System.out.println("Se han cargado las reservas");
        System.out.println("----------------------------------------");
        return lista;
    }
}
