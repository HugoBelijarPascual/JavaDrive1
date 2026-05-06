package logica;

import app.Main;
import model.Cliente;

import java.util.ArrayList;
import java.util.List;

public class GestorClientes {


    public static List<Cliente> clientes = new ArrayList<>();

    public static void crearCliente() {
        System.out.println("====ALTA DE NUEVO CLIENTE====");
        System.out.println("DNI: ");
        String dni = Main.sc.nextLine();

        System.out.println("Nombre: ");
        String nombre = Main.sc.nextLine();

        System.out.println("Tlf: ");
        String telefono = Main.sc.nextLine();

        clientes.add(new Cliente(dni, nombre, telefono));
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
}
