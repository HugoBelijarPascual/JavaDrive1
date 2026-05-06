package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    @Test
    void testCrearYObtenerDatos() {
        Cliente cliente = new Cliente("12345678Z", "Juan Perez", "600123456");

        assertEquals("12345678Z", cliente.getDni());
        assertEquals("Juan Perez", cliente.getNombre());
        assertEquals("600123456", cliente.getTelefono());
    }

    @Test
    void testSetters() {
        Cliente cliente = new Cliente("", "", "");
        cliente.setDni("87654321X");
        cliente.setNombre("Ana Lopez");
        cliente.setTelefono("655000000");

        assertEquals("87654321X", cliente.getDni());
        assertEquals("Ana Lopez", cliente.getNombre());
        assertEquals("655000000", cliente.getTelefono());
    }

    @Test
    void testToString() {
        Cliente cliente = new Cliente("123", "Pepe", "456");
        String esperado = "Nombre: Pepe\nDNI: 123\nTelefono: 456";
        assertEquals(esperado, cliente.toString());
    }

}