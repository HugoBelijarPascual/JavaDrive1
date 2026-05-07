package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FurgonetaTest {

    @Test
    void testFurgonetaCarga() throws NumPlazasException {
        Furgoneta furgon = new Furgoneta("1234ABC", "Ford", "Transit", true, true, 1000);

        assertEquals(1000, furgon.getCapacidad());
        assertTrue(furgon.isEsDeCarga());
        assertTrue(furgon.obtenerDetalles().contains("kg"));
    }

    @Test
    void testFurgonetaPasajeros() throws NumPlazasException {
        Furgoneta furgon = new Furgoneta("5678DEF", "Fiat", "Ducato", true, false, 5);

        assertEquals(5, furgon.getCapacidad());
        assertFalse(furgon.isEsDeCarga());
        assertTrue(furgon.obtenerDetalles().contains("personas"));
    }

    @Test
    void testFurgonetaPasajerosError() {
        assertThrows(NumPlazasException.class, () -> {
            new Furgoneta("9999XYZ", "Renault", "Master", true, false, 10);
        });
    }

    @Test
    void testSettersYLimites() {
        Furgoneta f = new Furgoneta("111", "Marca", "Mod", true, true, 500);

        // Test de Setters
        f.setEsDeCarga(false);
        f.setCapacidad(7);

        assertEquals(7, f.getCapacidad());
        assertFalse(f.isEsDeCarga());

        Furgoneta f2 = new Furgoneta("222", "A", "B", true, false, 2);
        assertEquals(2, f2.getCapacidad());

        assertThrows(NumPlazasException.class, () -> {
            new Furgoneta("333", "A", "B", true, false, 1);
        });
    }

}