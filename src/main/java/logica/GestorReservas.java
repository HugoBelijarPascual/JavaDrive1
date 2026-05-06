package logica;

import app.Main;
import model.Cliente;
import model.Reserva;
import model.Vehiculo;

import java.time.LocalDate;

public class GestorReservas {

    public static void pedirDatosReserva(){
        System.out.println("Ingrese el DNI del cliente:");
        String dni = Main.sc.nextLine();
        Cliente c = buscarCliente(dni);


        System.out.println("Matrícula del vehículo: ");
        String matricula = Main.sc.nextLine();
        Vehiculo v = buscarVehiculo(matricula);

        if (c == null || v == null || !v.isDisponible()) {
            System.out.println("ERROR: Cliente no existe o vehículo no disponible.");
        } else {
            System.out.println("¿Cuántos días de alquiler?");
            int dias = Main.sc.nextInt();
            Main.sc.nextLine();

            realizarReserva(c, v, LocalDate.now(), LocalDate.now().plusDays(dias));
        }
    }

    public static Cliente buscarCliente(String dni) {
        for (Cliente c : GestorClientes.clientes) {
            if (c.getDni().equals(dni)) {
                return c;
            }
        }
        return null;
    }

    public static Vehiculo buscarVehiculo(String matricula) {
        for (Vehiculo v : GestorFlota.flota) {
            if (v.getMatricula().equals(matricula)) {
                return v;
            }
        }
        return null;
    }

    public static void realizarReserva(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio, LocalDate fechaFin) {
        vehiculo.setDisponible(false);
        Reserva reserva = new Reserva(cliente, vehiculo, fechaInicio, fechaFin);
        exportarTicket(reserva);
        guardarDatos();
    }

    public static void guardarDatos() {
        GestorPersistencia.gestor.guardarVehiculos(GestorFlota.flota);
        GestorPersistencia.gestor.guardarClientes(GestorClientes.clientes);
    }

    public static void exportarTicket(Reserva reserva) {

        GestorPersistencia.gestor.exportarTicket(reserva);
    }
}
