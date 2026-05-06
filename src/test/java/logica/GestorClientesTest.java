package logica;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import model.Cliente;
import logica.GestorClientes;

import java.util.ArrayList;

class GestorClientesTest {
    @BeforeEach
    void setUp() {
        GestorReservas.clientes = new ArrayList<>();
    }

    @Test
    void testConstructorYGetters() {
        Cliente c = new Cliente("12345678Z", "Pepe", "600111222");
        assertEquals("12345678Z", c.getDni());
        assertEquals("Pepe", c.getNombre());
        assertEquals("600111222", c.getTelefono());
    }

    @Test
    void testSetters() {
        Cliente c = new Cliente("1", "A", "111");
        c.setNombre("Nuevo");
        c.setTelefono("999888777");
        assertEquals("Nuevo", c.getNombre());
        assertEquals("999888777", c.getTelefono());
    }

    @Test
    void testAñadirALista() {
        Cliente c = new Cliente("1", "A", "111");
        GestorReservas.clientes.add(c);
        assertEquals(1, GestorReservas.clientes.size());
    }

    @Test
    void testToString() {
        Cliente c = new Cliente("1", "Pepe", "111");
        assertTrue(c.toString().contains("Pepe"));
    }
}