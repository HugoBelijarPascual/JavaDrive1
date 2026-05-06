package logica;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import model.Cliente;

class GestorClientesTest {
    @Test
    void testListarClientesCompleto() {
        // Caso A: Lista vacía (cubre el "No se han encontrado clientes")
        GestorClientes.clientes.clear();
        GestorClientes.listarClientes();

        // Caso B: Lista con datos (cubre el for y los print)
        GestorClientes.clientes.add(new Cliente("1", "Ana", "111"));
        GestorClientes.clientes.add(new Cliente("2", "Bob", "222"));

        assertDoesNotThrow(() -> GestorClientes.listarClientes());
    }
}