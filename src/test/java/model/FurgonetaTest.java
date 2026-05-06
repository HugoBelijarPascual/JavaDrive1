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

}