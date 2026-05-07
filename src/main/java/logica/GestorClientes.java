package logica;

import model.Cliente;

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
}
