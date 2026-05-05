package logica;

import model.Cliente;

public class GestorClientes {


    public static void crearCliente() {
        System.out.println("====ALTA DE NUEVO CLIENTE====");
        System.out.println("DNI: ");
        String dni = GestorReservas.sc.nextLine();

        System.out.println("Nombre: ");
        String nombre = GestorReservas.sc.nextLine();

        System.out.println("Tlf: ");
        String telefono = GestorReservas.sc.nextLine();

        GestorReservas.clientes.add(new Cliente(dni, nombre, telefono));
    }

    public static void listarClientes() {
        boolean encontrado = false;

        for (Cliente c : GestorReservas.clientes) {
            System.out.println(c);
            System.out.println("----------------------------------------");
            encontrado = true;
        }

        if (!encontrado) {
            System.out.println("No se han encontrado clientes.");
        }
    }
}
