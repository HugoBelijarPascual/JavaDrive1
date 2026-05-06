package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VehiculoTest {

    @Test
    void testSettersVehiculo() {
        Coche v = new Coche("MAT", "MAR", "MOD", true, TipoCoche.Pequeño, 5);

        v.setMatricula("X");
        v.setMarca("X");
        v.setModelo("X");
        v.setDisponible(false);

        assertEquals("X", v.getMatricula());
        assertFalse(v.isDisponible());
    }

}