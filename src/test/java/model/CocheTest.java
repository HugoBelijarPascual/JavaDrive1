package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CocheTest {
    @Test
    void testCrearCocheCorrecto() {
        // Probamos un coche con 5 plazas (dentro del rango 2-7)
        Coche coche = new Coche("1234ABC", "Toyota", "Corolla", true, TipoCoche.Pequeño, 5);

        assertNotNull(coche);
        assertEquals(5, coche.getNumPlazas());
        assertTrue(coche.numPlazasEsCorrecto(5));
        assertNotNull(coche.obtenerDetalles());
    }

    @Test
    void testCochePlazasIncorrectas() {
        // Probamos que lance la excepción si ponemos 1 plaza (fuera de rango)
        assertThrows(NumPlazasException.class, () -> {
            new Coche("1234ABC", "Toyota", "Corolla", true, TipoCoche.Deportivo, 1);
        });
    }

    @Test
    void testGettersYHerencia() {
        // Probamos que los getters funcionen, incluidos los de la clase Vehiculo (marca)
        Coche coche = new Coche("9999XYZ", "Ford", "Focus", false, TipoCoche.Familiar, 4);

        assertEquals("Ford", coche.getMarca());
        assertEquals(TipoCoche.Familiar, coche.getTipoCoche());
        assertFalse(coche.isDisponible()); // Asumiendo que el método en Vehiculo se llama así
    }

    @Test
    void testSetters() {
        Coche coche = new Coche("1234ABC", "Toyota", "Corolla", true, TipoCoche.Pequeño, 5);

        // Testeamos setTipoCoche
        coche.setTipoCoche(TipoCoche.Deportivo);
        assertEquals(TipoCoche.Deportivo, coche.getTipoCoche());

        // Testeamos setNumPlazas
        coche.setNumPlazas(7);
        assertEquals(7, coche.getNumPlazas());
    }

    @Test
    void testLimitesPlazas() {
        // Caso límite máximo (7 está permitido)
        assertDoesNotThrow(() -> new Coche("111", "Marca", "Mod", true, TipoCoche.Familiar, 7));

        // Caso fuera de rango por arriba (8 lanza excepción)
        assertThrows(NumPlazasException.class, () -> {
            new Coche("222", "Marca", "Mod", true, TipoCoche.Familiar, 8);
        });
    }
}