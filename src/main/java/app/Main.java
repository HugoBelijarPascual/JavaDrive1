package app;

import logica.GestorClientes;
import logica.GestorFlota;
import logica.GestorReservas;
import model.*;

public class Main {


    public static void main(String[] args) {

        cargarDatos();
        int opcion;

        do {
            opcion = mostrarMenu();
            GestorReservas.sc.nextLine();
            switch (opcion) {
                case 1:
                    GestorClientes.crearCliente();
                    break;
                case 2:
                    GestorFlota.crearVehiculo();
                    break;
                case 3:
                    GestorFlota.listarVehiculosDisponibles();
                    break;
                case 4:
                    GestorReservas.pedirDatosReserva();
                    break;
                case 5:
                    GestorClientes.listarClientes();
                    break;
                case 6:
                    System.out.println("Saliendo del programa...");
                    GestorReservas.guardarDatos();
                    System.out.println("Datos guardados correctamente.");
            }

        } while (opcion != 6);

    }

    public static int mostrarMenu(){
        System.out.println("\n =============== MENU ===============");
        System.out.println("1. Alta Cliente");
        System.out.println("2. Alta Vehiculo");
        System.out.println("3. Listar Vehiculos Disponibles");
        System.out.println("4. Realizar Reserva");
        System.out.println("5. Listar Clientes");
        System.out.println("6. Salir");
        System.out.println("\nElegir una opcion:");
        return GestorReservas.sc.nextInt();

    }

    public static void cargarDatos() {
        GestorReservas.flota = GestorReservas.gestor.cargarVehiculos();
        GestorReservas.clientes = GestorReservas.gestor.cargarClientes();
        Reserva.setNextId(GestorReservas.gestor.calcularSiguienteIdReserva());
    }

}
