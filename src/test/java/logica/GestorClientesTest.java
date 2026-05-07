package logica;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import model.Cliente;

class GestorClientesTest {
    @BeforeEach
    void setUp() {
        // Limpiamos la lista antes de cada test
        GestorClientes.clientes.clear();
    }

    @Test
    void testAddClienteExitoso() {
        Cliente c = new Cliente("123", "Pepe", "600");
        boolean resultado = GestorClientes.crearCliente(c);

        assertTrue(resultado, "El cliente debería haberse añadido");
        assertEquals(1, GestorClientes.clientes.size());
    }

    @Test
    void testAddClienteDuplicado() {
        GestorClientes.crearCliente(new Cliente("111", "Ana", "111"));

        // Intentamos añadir un DNI duplicado
        boolean resultado = GestorClientes.crearCliente(new Cliente("111", "Luis", "222"));

        assertFalse(resultado, "No debería permitir DNIs duplicados");
        assertEquals(1, GestorClientes.clientes.size());
    }

    @Test
    void testListarClientesVacioYConDatos() {
        assertDoesNotThrow(() -> GestorClientes.listarClientes());

        GestorClientes.crearCliente(new Cliente("222", "Marta", "999"));
        assertDoesNotThrow(() -> GestorClientes.listarClientes());
    }
}