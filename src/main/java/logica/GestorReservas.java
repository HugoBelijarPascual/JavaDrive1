package logica;

import model.Cliente;
import model.Reserva;
import model.Vehiculo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestorReservas {
    public static List<Vehiculo> flota = new ArrayList<>();
    public static List<Cliente> clientes = new ArrayList<>();
    public static GestorPersistencia gestor = new GestorPersistencia();
    public static Scanner sc = new Scanner(System.in);

    public static void pedirDatosReserva(){
        System.out.println("Ingrese el DNI del cliente:");
        String dni = sc.nextLine();
        Cliente c = buscarCliente(dni);


        System.out.println("Matrícula del vehículo: ");
        String matricula = sc.nextLine();
        Vehiculo v = buscarVehiculo(matricula);

        if (c == null || v == null || !v.isDisponible()) {
            System.out.println("ERROR: Cliente no existe o vehículo no disponible.");
        } else {
            System.out.println("¿Cuántos días de alquiler?");
            int dias = sc.nextInt();
            sc.nextLine();

            realizarReserva(c, v, LocalDate.now(), LocalDate.now().plusDays(dias));
        }
    }

    public static Cliente buscarCliente(String dni) {
        for (Cliente c : clientes) {
            if (c.getDni().equals(dni)) {
                return c;
            }
        }
        return null;
    }

    public static Vehiculo buscarVehiculo(String matricula) {
        for (Vehiculo v : flota) {
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
        gestor.guardarVehiculos(flota);
        gestor.guardarClientes(clientes);
    }

    public static void exportarTicket(Reserva reserva) {

        gestor.exportarTicket(reserva);
    }
}
