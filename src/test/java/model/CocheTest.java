package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CocheTest {
    @Test
    void testCrearCocheCorrecto() {
        // Probamos un coche con 5 plazas
        Coche coche = new Coche("1234ABC", "Toyota", "Corolla", true, TipoCoche.Pequeño, 5);

        assertNotNull(coche);
        assertEquals(5, coche.getNumPlazas());
        assertTrue(coche.numPlazasEsCorrecto(5));
        assertNotNull(coche.obtenerDetalles());
    }

    @Test
    void testCochePlazasIncorrectas() {
        // Probamos que lance la excepción si ponemos 1 plaza
        assertThrows(NumPlazasException.class, () -> {
            new Coche("1234ABC", "Toyota", "Corolla", true, TipoCoche.Deportivo, 1);
        });
    }

    @Test
    void testGettersYHerencia() {
        // Probamos que los getters funcionen, incluidos los de la clase Vehiculo
        Coche coche = new Coche("9999XYZ", "Ford", "Focus", false, TipoCoche.Familiar, 4);

        assertEquals("Ford", coche.getMarca());
        assertEquals(TipoCoche.Familiar, coche.getTipoCoche());
        assertFalse(coche.isDisponible());
    }

    @Test
    void testSetters() {
//        Testeamos los setters
        Coche coche = new Coche("1234ABC", "Toyota", "Corolla", true, TipoCoche.Pequeño, 5);

        coche.setTipoCoche(TipoCoche.Deportivo);
        assertEquals(TipoCoche.Deportivo, coche.getTipoCoche());

        coche.setNumPlazas(7);
        assertEquals(7, coche.getNumPlazas());
    }

    @Test
    void testLimitesPlazas() {
        // Caso límite máximo
        assertDoesNotThrow(() -> new Coche("111", "Marca", "Mod", true, TipoCoche.Familiar, 7));

        // Caso fuera de rango
        assertThrows(NumPlazasException.class, () -> {
            new Coche("222", "Marca", "Mod", true, TipoCoche.Familiar, 8);
        });
    }
}