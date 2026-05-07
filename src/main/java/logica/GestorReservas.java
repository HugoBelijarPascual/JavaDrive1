package logica;

import app.Main;
import model.Cliente;
import model.Reserva;
import model.Vehiculo;

import java.time.LocalDate;

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

    // MÉTODO TESTEABLE: Ahora devuelve la reserva creada
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
}
